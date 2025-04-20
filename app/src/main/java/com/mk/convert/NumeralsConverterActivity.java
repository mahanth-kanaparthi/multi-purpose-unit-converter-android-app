package com.mk.convert;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mk.controller.NumeralsConverterController;
import com.mk.model.NumeralsConverterModel;
import com.mk.utils.CallbackDialog;
import com.mk.utils.TextFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NumeralsConverterActivity extends BaseActivity{

    private TextView unit1Value,unit2Value;
    private TextView unit1Name,unit1Code,unit2Name,unit2Code,selectedTextView,selectedLengthCode;
    private Button btn_a,btn_b,btn_c,btn_d,btn_e,btn_f;

    NumeralsConverterModel model;
    NumeralsConverterController controller;

    public List<TextView> units;
    public List<TextView> codes;
    public List<TextView> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.numerals_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.numerals_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        initializeResources();
        setupListeners();
        setupExtraListeners();

        //applying input text filters to the textviews
        TextFilter.applyMultipleInputTextFilters(10,19,unit1Value,unit2Value);

        model = new NumeralsConverterModel(this);
        controller = new NumeralsConverterController(model,this);

    }
    protected void initializeViews(){
        selectedTextView=null;
        unit1Name=findViewById(R.id.numeralsFirstListView);
        unit2Name=findViewById(R.id.numeralsSecondListView);
        unit1Code=findViewById(R.id.numeralsFirstListViewCode);
        unit2Code=findViewById(R.id.numeralsSecondListViewCode);
        unit1Value=findViewById(R.id.numeralsFirstListViewValue);
        unit2Value=findViewById(R.id.numeralsSecondListViewValue);

        units = new ArrayList<>((Arrays.asList(unit1Name,unit2Name)));
        codes = new ArrayList<>(Arrays.asList(unit1Code,unit2Code));
        values = new ArrayList<>(Arrays.asList(unit1Value, unit2Value));

        btn_a=findViewById(R.id.btn_a);
        btn_b=findViewById(R.id.btn_b);
        btn_c=findViewById(R.id.btn_c);
        btn_d=findViewById(R.id.btn_d);
        btn_e=findViewById(R.id.btn_e);
        btn_f=findViewById(R.id.btn_f);
    }

    @Override
    protected void setupListeners(){
        Button[] buttons = {btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_00,btn_backspace, btn_dot,
                btn_a,btn_b,btn_c,btn_d,btn_e,btn_f};

        for (Button button : buttons) {
            button.setOnClickListener(v -> updateSelectedTextView(button.getText().toString()));
        }

        setupBackspaceBtn();

        backBtn.setOnClickListener(v -> finish());
    }

    protected void setupExtraListeners(){
        int defaultFontColor = getResources().getColor(R.color.black);
        int selectedFontColor = getResources().getColor(R.color.orange);



        unit1Value.setOnClickListener(v -> {
            selectedTextViewValue = unit1Value;
            setValueTextLimitations(unit1Value);
            selectedTextView = unit1Name;

            String selectedUnit = unit1Name.getText().toString();
            toggleButtons(selectedUnit);


            unit1Value.setTextColor(selectedFontColor);
            unit2Value.setTextColor(defaultFontColor);

            String currentText = selectedTextViewValue.getText().toString();

            if(!firstTimePressed && !currentText.equals("1")){
                selectedTextViewValue.setText("1");
                firstTimePressed = true;
            }

        });
        unit2Value.setOnClickListener(v -> {
            selectedTextViewValue = unit2Value;
            setValueTextLimitations(unit2Value);
            selectedTextView = unit2Name;

            String selectedUnit = unit2Name.getText().toString();
            toggleButtons(selectedUnit);

            unit2Value.setTextColor(selectedFontColor);
            unit1Value.setTextColor(defaultFontColor);

            String currentText = selectedTextViewValue.getText().toString();

            if(!firstTimePressed && !currentText.equals("1")){
                selectedTextViewValue.setText("1");
                firstTimePressed = true;
            }

        });

        unit1Name.setOnClickListener(v ->{
            selectedTextView = unit1Name;
            selectedLengthCode = unit1Code;
            showUnitsDialog(unit1Name,unit1Code);


        });
        unit2Name.setOnClickListener(v ->{
            selectedTextView = unit2Name;
            selectedLengthCode = unit2Code;
            showUnitsDialog(unit2Name,unit2Code);

        });
    }

    private void showUnitsDialog(TextView unitName,TextView unitCode){
        // get map from model
        Map<String, String> map = model.getNumeralsUnitsWithCodes();

//        MapDialog.showMapDialog(this, map, unitName,unitCode);
        CallbackDialog.showMapDialog(this, map, unitName, unitCode, new CallbackDialog.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String key, String value) {
                // Handle the selected item here
                toggleButtons(value);
                if(selectedTextView != null)
                    updateConvertedValues(selectedTextViewValue);
            }
        });

    }

    @Override
    protected void updateConvertedValues(TextView selectedValue) {

        int idx = values.indexOf(selectedValue);
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextViewValue = (TextView) values.get(targetIdx);

        String resultText = controller.performConversion(selectedValue);
        if(resultText.matches(".*[a-zA-Z].*")) {

        }else{

            resultText = String.format(Locale.getDefault(),"%.6f", Double.valueOf(resultText));
        }
        if(resultText.endsWith(".000000")){
            resultText = resultText.substring(0, resultText.length() - 7);
        }else if(resultText.endsWith("00000")){
            resultText = resultText.substring(0, resultText.length() - 5);
        }else if(resultText.endsWith("0000")){
            resultText = resultText.substring(0, resultText.length() - 4);
        }else if(resultText.endsWith("000")){
            resultText = resultText.substring(0, resultText.length() - 3);
        }else if(resultText.endsWith("00")){
            resultText = resultText.substring(0, resultText.length() - 2);
        }
        if(resultText.isEmpty()){
            resultText = "0";
        }

        targetUnitTextViewValue.setText(resultText);


    }
    private void toggleOctalButtons(){
        toggleDecimalButtons();
        btn_8.setEnabled(!btn_8.isEnabled());
        btn_8.setTextColor(Color.GRAY);
        btn_9.setEnabled(!btn_9.isEnabled());
        btn_9.setTextColor(Color.GRAY);

    }
    private void toggleDecimalButtons(){
        btn_a.setEnabled(!btn_a.isEnabled());
        btn_a.setTextColor(Color.GRAY);
        btn_b.setEnabled(!btn_b.isEnabled());
        btn_b.setTextColor(Color.GRAY);
        btn_c.setEnabled(!btn_c.isEnabled());
        btn_c.setTextColor(Color.GRAY);
        btn_d.setEnabled(!btn_d.isEnabled());
        btn_d.setTextColor(Color.GRAY);
        btn_e.setEnabled(!btn_e.isEnabled());
        btn_e.setTextColor(Color.GRAY);
        btn_f.setEnabled(!btn_f.isEnabled());
        btn_f.setTextColor(Color.GRAY);

    }
    private void toggleBinaryButtons(){
        toggleDecimalButtons();
        btn_2.setEnabled(!btn_2.isEnabled());
        btn_2.setTextColor(Color.GRAY);
        btn_3.setEnabled(!btn_3.isEnabled());
        btn_3.setTextColor(Color.GRAY);
        btn_4.setEnabled(!btn_4.isEnabled());
        btn_4.setTextColor(Color.GRAY);
        btn_5.setEnabled(!btn_5.isEnabled());
        btn_5.setTextColor(Color.GRAY);
        btn_6.setEnabled(!btn_6.isEnabled());
        btn_6.setTextColor(Color.GRAY);
        btn_7.setEnabled(!btn_7.isEnabled());
        btn_7.setTextColor(Color.GRAY);
        btn_8.setEnabled(!btn_8.isEnabled());
        btn_8.setTextColor(Color.GRAY);
        btn_9.setEnabled(!btn_9.isEnabled());
        btn_9.setTextColor(Color.GRAY);

    }
    private void toggleButtonsDefaultState(){
        btn_2.setEnabled(true);
        btn_2.setTextColor(Color.BLACK);
        btn_3.setEnabled(true);
        btn_3.setTextColor(Color.BLACK);
        btn_4.setEnabled(true);
        btn_4.setTextColor(Color.BLACK);
        btn_5.setEnabled(true);
        btn_5.setTextColor(Color.BLACK);
        btn_6.setEnabled(true);
        btn_6.setTextColor(Color.BLACK);
        btn_7.setEnabled(true);
        btn_7.setTextColor(Color.BLACK);
        btn_8.setEnabled(true);
        btn_8.setTextColor(Color.BLACK);
        btn_9.setEnabled(true);
        btn_9.setTextColor(Color.BLACK);
        btn_a.setEnabled(true);
        btn_a.setTextColor(Color.BLACK);
        btn_b.setEnabled(true);
        btn_b.setTextColor(Color.BLACK);
        btn_c.setEnabled(true);
        btn_c.setTextColor(Color.BLACK);
        btn_d.setEnabled(true);
        btn_d.setTextColor(Color.BLACK);
        btn_e.setEnabled(true);
        btn_e.setTextColor(Color.BLACK);
        btn_f.setEnabled(true);
        btn_f.setTextColor(Color.BLACK);
    }
    private void toggleButtons(String base){
        switch (base){
            case "Octal":
                toggleButtonsDefaultState();
                toggleOctalButtons();
                break;
            case "Decimal":
                toggleButtonsDefaultState();
                toggleDecimalButtons();
                break;
            case "Binary" :
                toggleButtonsDefaultState();
                toggleBinaryButtons();
                break;
            default:
                toggleButtonsDefaultState();
        }
    }

}
