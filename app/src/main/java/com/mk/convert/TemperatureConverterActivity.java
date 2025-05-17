package com.mk.convert;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mk.controller.TemperatureConverterController;
import com.mk.model.TemperatureConverterModel;
import com.mk.utils.NumberResultFormatter;
import com.mk.utils.TextFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemperatureConverterActivity extends BaseActivity {

    public TextView unit1Value,unit2Value;
    public TextView unit1Name,unit1Code,unit2Name,unit2Code,selectedTextView,selectedLengthCode;
    private Button btn_plus_minus;

    TemperatureConverterController controller;
    TemperatureConverterModel model;

    public List<TextView> units;
    public List<TextView> codes;
    public List<TextView> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.temperature_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.temperature_activity_layout), (v, insets) -> {
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

        model = new TemperatureConverterModel(this);
        controller = new TemperatureConverterController(model,this);
        dataMap = model.getTemperatureUnitsWithCodes();

        unit1Value.performClick();

    }
    protected void initializeViews(){
        selectedTextView=null;
        unit1Name=findViewById(R.id.temperatureFirstListView);
        unit2Name=findViewById(R.id.temperatureSecondListView);
        unit1Code=findViewById(R.id.temperatureFirstListViewCode);
        unit2Code=findViewById(R.id.temperatureSecondListViewCode);
        unit1Value=findViewById(R.id.temperatureFirstListViewValue);
        unit2Value=findViewById(R.id.temperatureSecondListViewValue);

        //special for temperature
        btn_plus_minus = findViewById(R.id.btn_plus_minus);

        units = new ArrayList<>((Arrays.asList(unit1Name,unit2Name)));
        codes = new ArrayList<>(Arrays.asList(unit1Code,unit2Code));
        values = new ArrayList<>(Arrays.asList(unit1Value, unit2Value));
    }

    @Override
    protected void setupListeners(){
        Button[] buttons = {btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_00,btn_backspace,
                btn_clear, btn_dot,btn_plus_minus};

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
            showDialogForFirstList();
        });
        unit2Name.setOnClickListener(v ->{
            showDialogForSecondList();
        });
        unit1Code.setOnClickListener(v -> {
            showDialogForFirstList();
        });
        unit2Code.setOnClickListener(v -> {
            showDialogForSecondList();
        });
    }
    protected void showDialogForFirstList(){
        selectedTextView = unit1Name;
        selectedLengthCode = unit1Code;
        showDialog(unit1Name,unit1Code);
    }
    protected void showDialogForSecondList(){
        selectedTextView = unit2Name;
        selectedLengthCode = unit2Code;
        showDialog(unit2Name,unit2Code);
    }



    @Override
    protected void updateConvertedValues(TextView selectedValue) {
        int idx = values.indexOf(selectedValue);
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextViewValue = (TextView) values.get(targetIdx);

        // Get the converted value as BigDecimal
        BigDecimal result = NumberResultFormatter
                .format(BigDecimal.valueOf(controller.performConversion(selectedValue)));

        // Strip trailing zeros and format to plain string
        String resultText = result.stripTrailingZeros().toPlainString();

        // Handle edge case where result is empty (e.g., 0.000000)
        if (resultText.isEmpty()) {
            resultText = "0";
        }

        targetUnitTextViewValue.setText(resultText);
    }
}