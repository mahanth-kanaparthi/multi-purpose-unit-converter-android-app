package com.mk.convert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinanceActivityResult extends AppCompatActivity{
    private TextView title,duration,displayAmount,totalPayment_tv,totalPayment,financeTypeTotal_tv,enteredAmount,totalInterest;
    private ProgressBar progressBar;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.finance_result_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.finance_result_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setData();
    }
    private void initializeViews(){
        title = findViewById(R.id.resultCard_title_tv);
        duration = findViewById(R.id.resultCard_duration_tv);
        displayAmount = findViewById(R.id.resultCard_calculatedAmount);
        totalPayment_tv = findViewById(R.id.resultCard_totalPayment_tv);
        totalPayment = findViewById(R.id.resultCard_totalPaymentValue);
        financeTypeTotal_tv = findViewById(R.id.financeTypeTotal_tv);
        enteredAmount = findViewById(R.id.resultCard_totalPrincipalValue);
        totalInterest = findViewById(R.id.resultCard_totalInterestValue);
        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }
    private void setData(){
        Intent financeResultIntent = getIntent();
        double totalProgressBarAmount = 0.0,interest = 0.0,amount = 0.0;

        title.setText(financeResultIntent.getStringExtra("title"));
        duration.setText(financeResultIntent.getStringExtra("duration"));

        amount = financeResultIntent.getDoubleExtra("enteredAmount",0.0);
        interest = financeResultIntent.getDoubleExtra("totalInterest",0.0);

        if(title.getText().equals("EMI")){
            displayAmount.setText(String.valueOf(financeResultIntent.getDoubleExtra("emiAmount",0.0)));
            totalProgressBarAmount = interest + amount;
            double totalAmountPayable = financeResultIntent.getDoubleExtra("calculatedLoan",0.0);
            //debugging
            System.out.println(totalAmountPayable);

            totalPayment.setText(String.valueOf(totalAmountPayable));
            financeTypeTotal_tv.setText(getResources().getString(R.string.resultCard_totalPrincipal));
            System.out.println("debug: "+totalPayment_tv.getText().toString());
            totalPayment_tv.setVisibility(TextView.VISIBLE);
            totalPayment.setVisibility(TextView.VISIBLE);
        }
        else{
            totalProgressBarAmount = financeResultIntent.getDoubleExtra("calculatedInvestment",0.0);
            displayAmount.setText(String.valueOf(totalProgressBarAmount));
            financeTypeTotal_tv.setText(getResources().getString(R.string.resultCard_totalInvestment));
            totalPayment_tv.setVisibility(TextView.GONE);
            totalPayment.setVisibility(TextView.GONE);
        }
        progressBar.setMax((int) totalProgressBarAmount);
        progressBar.setProgress((int) amount);
        progressBar.setSecondaryProgress((int)totalProgressBarAmount);
        enteredAmount.setText(String.valueOf(amount));
        totalInterest.setText(String.valueOf(interest));

    }

}
