package com.mk.convert;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mk.utils.InvestmentCalculator;
import com.mk.utils.LoanCalculator;

import java.math.BigDecimal;

public class FinanceCalculatorActivity extends AppCompatActivity {

    private TextView financeType,durationTextView,durationDialogTextView;
    private EditText amountEditText,interestRateEditText;
    private RadioButton loanRadioButton,investmentRadioButton;
    private Button calculateButton;
    private ImageButton backBtn;
    private RelativeLayout investmentTypeGroup;
    private byte selectedYears , selectedMonths;
    private BigDecimal calculatedLoan,calculatedInvestment,
            calculatedTotalInterest,calculatedEmi;

    private Spinner investmentTypeSpinner;
    private String investmentType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.finance_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.finance_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setupListeners();
        setupDefaults();
    }

    private void initializeViews(){
        financeType = findViewById(R.id.finance_type);
        durationTextView = findViewById(R.id.tv_duration);
        durationDialogTextView = findViewById(R.id.tv_duration_dialog);
        amountEditText = findViewById(R.id.et_amount);
        interestRateEditText = findViewById(R.id.et_interest_rate);
        loanRadioButton = findViewById(R.id.rb_loan);
        loanRadioButton.setChecked(true);
        investmentRadioButton = findViewById(R.id.rb_investment);
        investmentTypeSpinner = findViewById(R.id.sp_investment_type);
        investmentTypeSpinner.setSelection(0);
        calculateButton = findViewById(R.id.btn_calculate);
        investmentTypeGroup = findViewById(R.id.rl_investmentType_group);
        backBtn = findViewById(R.id.backBtn);

        selectedYears = selectedMonths = 0;
    }
    private void setupListeners(){

        loanRadioButton.setOnClickListener(v -> {
            investmentTypeGroup.setVisibility(View.GONE);
            financeType.setText(getResources().getString(R.string.finance_type_principal));
        });
        investmentRadioButton.setOnClickListener(v -> {
            investmentTypeGroup.setVisibility(View.VISIBLE);
            financeType.setText(getResources().getString(R.string.finance_type_investment));
        });


        durationTextView.setOnClickListener(v -> {
            showDurationDialog();
        });
        durationDialogTextView.setOnClickListener(v -> {
            showDurationDialog();
        });

        calculateButton.setOnClickListener(v -> {
            performCalculation();
        });
        backBtn.setOnClickListener(v -> finish());
        setupSpinner();
    }
    private void setupDefaults(){
        interestRateEditText.setText(getResources().getString(R.string.interest_rate));
        durationDialogTextView.setText(getResources().getString(R.string.tv_duration_dialog));
        selectedYears = 1;
        selectedMonths = 0;
        investmentTypeSpinner.setSelection(0);
        investmentType = investmentTypeSpinner.getSelectedItem().toString();

    }
    private void showDurationDialog(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.duration_picker_bottoms_sheet, null);
        dialog.setContentView(view);
        NumberPicker yearsPicker = view.findViewById(R.id.numberPickerYears);
        NumberPicker monthsPicker = view.findViewById(R.id.numberPickerMonths);
        Button cancelButton = view.findViewById(R.id.buttonCancel);
        Button okButton = view.findViewById(R.id.buttonOk);
        // Set up years picker
        yearsPicker.setMinValue(0);
        yearsPicker.setMaxValue(30);
        // Set up months picker
        monthsPicker.setMinValue(0);
        monthsPicker.setMaxValue(11);
        //default values
        yearsPicker.setValue(1);
        monthsPicker.setValue(0);
        // Handle cancel button
        cancelButton.setOnClickListener(v -> dialog.dismiss());
        okButton.setOnClickListener(v -> {
            selectedYears = (byte) yearsPicker.getValue();
            selectedMonths = (byte) monthsPicker.getValue();
            // Update the text view with the selected duration
            String pluralYears = selectedYears == 1 ? "year" : "years";
            String pluralMonths = selectedMonths == 1 ? "month" : "months";
            String durationText = selectedYears + " " + pluralYears + " and " + selectedMonths + " " + pluralMonths;
            durationDialogTextView.setText(durationText);

            dialog.dismiss();
        });
        dialog.show();
    }
    private void setupSpinner(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.investment_types, // Replace with your string array resource
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        investmentTypeSpinner.setAdapter(adapter);
        investmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection here
                investmentType = parent.getItemAtPosition(position).toString();
                //Toast.makeText(FinanceCalculatorActivity.this, "Selected: " + investmentType, Toast.LENGTH_SHORT).show();
                // ... your logic based on the selected item ...
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected (optional)
                investmentType = parent.getItemAtPosition(0).toString();
            }
        });
    }
    private void performCalculation(){
        String interestRate = interestRateEditText.getText().toString();
        double enteredInterestRate = Double.parseDouble(interestRate);
        String amount = amountEditText.getText().toString();
        double enteredAmount = Double.parseDouble(amount);
        String duration = durationDialogTextView.getText().toString();
        boolean isEnteredValuesValid = amount.isEmpty() || interestRate.isEmpty() || duration.isEmpty();
        if(loanRadioButton.isChecked()){
            // Loan calculation logic

            if(isEnteredValuesValid){
                // Handle empty fields
                Toast.makeText(this,
                        "Please enter all fields correctly",
                        Toast.LENGTH_SHORT).show();
            }else{
                // Perform calculation
                calculatedEmi =  LoanCalculator.calculateEMI(
                        BigDecimal.valueOf(enteredAmount),
                        BigDecimal.valueOf(enteredInterestRate),
                        selectedYears,selectedMonths);

                calculatedLoan = LoanCalculator.calculateTotalRepaymentWithEMI(
                        BigDecimal.valueOf(enteredAmount),
                        BigDecimal.valueOf(enteredInterestRate),
                        selectedYears,selectedMonths);

                calculatedTotalInterest = calculatedLoan.subtract(BigDecimal.valueOf(enteredAmount));
            }
            displayResultCard("EMI", duration,calculatedEmi.doubleValue(),calculatedLoan.doubleValue(),enteredAmount,calculatedTotalInterest.doubleValue());

        }else if(investmentRadioButton.isChecked()){
            // Investment calculation logic
            double total_investment =0.0;

            if(isEnteredValuesValid) {
                // Handle empty fields
                Toast.makeText(this,
                        "Please enter all fields correctly",
                        Toast.LENGTH_SHORT).show();
            }else {
                // Perform calculation
                if(investmentType.equals("One-time")){
                    calculatedInvestment = InvestmentCalculator.calculateOneTimeInvestmentValue(BigDecimal.valueOf(enteredAmount),
                            BigDecimal.valueOf(enteredInterestRate),
                            selectedYears,selectedMonths);
                    total_investment = enteredAmount;

                }else{
                    calculatedInvestment = InvestmentCalculator.calculateRecurringInvestmentValue(BigDecimal.valueOf(enteredAmount),
                            BigDecimal.valueOf(enteredInterestRate),
                            selectedYears,selectedMonths);
                    total_investment = enteredAmount * (selectedYears * 12) + selectedMonths;
                }
                calculatedTotalInterest = calculatedInvestment.subtract(BigDecimal.valueOf(total_investment));
                displayResultCard("Total value", duration,calculatedInvestment.doubleValue(),total_investment,calculatedTotalInterest.doubleValue());
            }

        }

    }
    private void displayResultCard(@NonNull String title,@NonNull String duration,double calculatedEmi,double calculatedLoan,double amount,double calculatedTotalInterest){
        Intent financeResultIntent = new Intent(FinanceCalculatorActivity.this,FinanceActivityResult.class);
        financeResultIntent.putExtra("title",title);
        financeResultIntent.putExtra("duration",duration);
        financeResultIntent.putExtra("emiAmount",calculatedEmi);
        System.out.println("main: "+calculatedLoan);
        financeResultIntent.putExtra("calculatedLoan",calculatedLoan);
        financeResultIntent.putExtra("enteredAmount",amount);
        financeResultIntent.putExtra("totalInterest",calculatedTotalInterest);
        startActivity(financeResultIntent);

    }
    private void displayResultCard(@NonNull String title,@NonNull String duration, double calculatedInvestment,double amount,double calculatedTotalInterest){
        Intent financeResultIntent = new Intent(FinanceCalculatorActivity.this,FinanceActivityResult.class);
        financeResultIntent.putExtra("title",title);
        financeResultIntent.putExtra("duration",duration);
        financeResultIntent.putExtra("calculatedInvestment",calculatedInvestment);
        financeResultIntent.putExtra("enteredAmount",amount);
        financeResultIntent.putExtra("totalInterest",calculatedTotalInterest);
        startActivity(financeResultIntent);
    }

}
