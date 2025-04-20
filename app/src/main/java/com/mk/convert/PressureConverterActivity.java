package com.mk.convert;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mk.controller.PressureConverterController;
import com.mk.model.PressureConverterModel;
import com.mk.utils.TextFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PressureConverterActivity extends BaseActivity{

    public TextView unit1Value,unit2Value;
    public TextView unit1Name,unit1Code,unit2Name,unit2Code,selectedTextView,selectedLengthCode;

    PressureConverterController controller;
    PressureConverterModel model;

    public List<TextView> units;
    public List<TextView> codes;
    public List<TextView> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pressure_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pressure_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        initializeResources();
        setupExtraListeners();
        setupListeners();

        //applying input text filters to the textviews
        TextFilter.applyMultipleInputTextFilters(10,19,unit1Value,unit2Value);

        model = new PressureConverterModel(this);
        controller = new PressureConverterController(model,this);
        dataMap = model.getPressureUnitsWithCodes();

    }
    protected void initializeViews(){
        selectedTextView=null;
        unit1Name=findViewById(R.id.pressureFirstListView);
        unit2Name=findViewById(R.id.pressureSecondListView);
        unit1Code=findViewById(R.id.pressureFirstListViewCode);
        unit2Code=findViewById(R.id.pressureSecondListViewCode);
        unit1Value=findViewById(R.id.pressureFirstListViewValue);
        unit2Value=findViewById(R.id.pressureSecondListViewValue);

        units = new ArrayList<>((Arrays.asList(unit1Name,unit2Name)));
        codes = new ArrayList<>(Arrays.asList(unit1Code,unit2Code));
        values = new ArrayList<>(Arrays.asList(unit1Value, unit2Value));
    }

    protected void setupExtraListeners(){
        int defaultFontColor = getResources().getColor(R.color.black);
        int selectedFontColor = getResources().getColor(R.color.orange);

        unit1Value.setOnClickListener(v -> {
            selectedTextViewValue = unit1Value;
            setValueTextLimitations(unit1Value);

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
            showDialog(unit1Name,unit1Code);
        });
        unit2Name.setOnClickListener(v ->{
            selectedTextView = unit2Name;
            selectedLengthCode = unit2Code;
            showDialog(unit2Name,unit2Code);
        });
    }


    @Override
    protected void updateConvertedValues(TextView selectedValue) {
        int idx = values.indexOf(selectedValue);
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextViewValue = (TextView) values.get(targetIdx);

        // Get the converted value as BigDecimal
        BigDecimal result = BigDecimal.valueOf(controller.performConversion(selectedValue));

        // Strip trailing zeros and format to plain string
        String resultText = result.stripTrailingZeros().toPlainString();

        // Handle edge case where result is empty (e.g., 0.000000)
        if (resultText.isEmpty()) {
            resultText = "0";
        }

        targetUnitTextViewValue.setText(resultText);
    }

}
