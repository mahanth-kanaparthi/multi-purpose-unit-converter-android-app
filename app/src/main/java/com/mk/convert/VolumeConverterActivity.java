package com.mk.convert;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mk.controller.VolumeConverterController;
import com.mk.model.VolumeConverterModel;
import com.mk.utils.MapDialog;
import com.mk.utils.TextFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VolumeConverterActivity extends BaseActivity {

    public TextView unit1Value,unit2Value;
    public TextView unit1Name,unit1Code,unit2Name,unit2Code,selectedTextView,selectedLengthCode;

    VolumeConverterController controller;
    VolumeConverterModel model;

    public List<TextView> units;
    public List<TextView> codes;
    public List<TextView> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.volume_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.volume_activity_layout), (v, insets) -> {
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

        model = new VolumeConverterModel(this);
        controller = new VolumeConverterController(model,this);


    }
    protected void initializeViews(){
        selectedTextView=null;
        unit1Name=findViewById(R.id.volumeFirstListView);
        unit2Name=findViewById(R.id.volumeSecondListView);
        unit1Code=findViewById(R.id.volumeFirstListViewCode);
        unit2Code=findViewById(R.id.volumeSecondListViewCode);
        unit1Value=findViewById(R.id.volumeFirstListViewValue);
        unit2Value=findViewById(R.id.volumeSecondListViewValue);

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

    private void showDialog(TextView unitName,TextView unitCode){
        // get map from model
        Map<String, String> map = model.getVolumeUnitsWithCodes();

        MapDialog.showMapDialog(this, map, unitName,unitCode);

    }

    @Override
    protected void updateConvertedValues(TextView selectedValue) {

        int idx = values.indexOf(selectedValue);
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextViewValue = (TextView) values.get(targetIdx);

        String resultText = String.format("%.6f", controller.performConversion(selectedValue));
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
}