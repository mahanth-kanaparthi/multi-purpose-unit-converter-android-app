package com.mk.convert;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.mk.utils.StringFormatterWithPattern;

import java.text.DecimalFormat;

public class BMIResultActivity extends AppCompatActivity {

    private TextView bmiResultTextView,bmiHealthAdviceTextView,heightValue,suggestedWeight;
    private Chip bmiStatusChip;
    private ImageButton backButton;
    private String bmiCategory,gender;
    double bmi, height;
    byte age;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.bmi_result_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bmi_result_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        if(getExtrasFromBMIActivity()){
            setResults();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        backButton.setOnClickListener(v -> finish());
    }
    private void initViews() {
        bmiResultTextView = findViewById(R.id.bmi_result_value);
        bmiStatusChip = findViewById(R.id.statusChip);
        heightValue = findViewById(R.id.heightValue);
        suggestedWeight = findViewById(R.id.suggestedWeightValue);
        bmiHealthAdviceTextView = findViewById(R.id.bmi_advice);
        backButton = findViewById(R.id.backBtn);
    }

    private boolean getExtrasFromBMIActivity(){
        // Get data from the intent
        extras = getIntent().getExtras();

        if (extras != null) {
            age = extras.getByte("age");
            gender = extras.getString("gender");
            bmi = extras.getDouble("bmi");
            bmiCategory = extras.getString("bmiCategory");
            height = extras.getDouble("height");

            return true;
        }
        return false;
    }

    private String getSuggestedWeightRange(double height){
        DecimalFormat df = new DecimalFormat("#.0");

//        Log.e("BMI Result : ","test height cm : "+height);

        height = height / 100; // convert cm to m

        double lowerWeightBound = 18.5 * (height * height);
        double upperWeightBound = 24.9 * (height * height);

//        debug purpose
//        Log.e("BMI Result : ","test height m : "+height);
//        Log.e("BMI Result : ","test Lwr : "+lowerWeightBound);
//        Log.e("BMI Result : ","test Upr : "+upperWeightBound);

        return df.format(lowerWeightBound) + " ~ " + df.format(upperWeightBound) + " kg";
    }


    private void setResults(){
        String temp = "";
        temp = StringFormatterWithPattern.formatString(bmi,"#.00");
        bmiResultTextView.setText(temp);
        height = height * 100; // convert cm to m
        temp = StringFormatterWithPattern.formatString(height,"#.00");
        heightValue.setText(temp);
        suggestedWeight.setText(getSuggestedWeightRange(height));

        // Provide health advice based on BMI category, age, and gender
        if(!bmiCategory.isEmpty())
            setHealthAdviceAndStatusChip(bmiCategory, age, gender);
        else
            Toast.makeText(this,"something error occurred, unable to suggest",Toast.LENGTH_SHORT).show();

    }

    private void setHealthAdviceAndStatusChip(String bmiCategory, byte age, String gender) {
        String advice = "";

        switch (bmiCategory) {
            case "Underweight":
                advice = "You are underweight. Consider consulting a doctor or nutritionist for advice on healthy weight gain.";
                if (age < 18) {
                    advice += " Pay special attention to nutrition during growth and development."; //Age specific advice
                }
                bmiHealthAdviceTextView.setText(advice);
                bmiHealthAdviceTextView.setBackgroundColor(getResources().getColor(R.color.underweight_color));
                bmiStatusChip.setText(bmiCategory);
                bmiStatusChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.underweight_color)));
                //bmiStatusChip.setBackgroundColor(getResources().getColor(R.color.underweight_color));
                break;
            case "Normal":
                advice = "You have a healthy weight. Maintain a balanced diet and regular exercise.";
                bmiHealthAdviceTextView.setText(advice);
                bmiHealthAdviceTextView.setBackgroundColor(getResources().getColor(R.color.normal_color));
                bmiStatusChip.setText(bmiCategory);
                bmiStatusChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.normal_color)));
                break;
            case "Overweight":
                advice = "You are overweight. Consider adopting a healthier lifestyle with a balanced diet and regular exercise.";
                if (age > 60) {
                    advice += " Consult a doctor for personalized advice, especially if you have other health conditions."; //Age specific advice
                }
                bmiHealthAdviceTextView.setText(advice);
                bmiHealthAdviceTextView.setBackgroundColor(getResources().getColor(R.color.overweight_color));
                bmiStatusChip.setText(bmiCategory);
                bmiStatusChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.overweight_color)));
                break;
            case "Obese":
                advice = "You are obese. It's important to consult a doctor or healthcare professional for guidance on weight management and potential health risks.";
                if (gender.equals("Female")) {
                    advice += " Obesity can affect hormonal health. Talk to your doctor for further information."; // Gender specific advice
                }
                bmiHealthAdviceTextView.setText(advice);
                bmiHealthAdviceTextView.setBackgroundColor(getResources().getColor(R.color.obese_color));
                bmiStatusChip.setText(bmiCategory);
                bmiStatusChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.obese_color)));
                break;
        }
    }
}
