package com.mk.convert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BMI_CalculatorActivity extends AppCompatActivity {
    private TextView genderNameTextView;
    private EditText ageEditText,heightEditText,weightEditText;
    private RadioButton radioButtonMale,radioButtonFemale;
    private Button calculateButton;
    private String selectedGender;
    private ImageButton backButton;
    private double height,weight;
    private byte age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.bmi_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bmi_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setDefaults();
        setupListeners();
    }

    private void initializeViews(){
        genderNameTextView  = findViewById(R.id.tv_genderName);
        ageEditText = findViewById(R.id.et_age);
        heightEditText = findViewById(R.id.et_height);
        weightEditText = findViewById(R.id.et_weight);
        radioButtonMale = findViewById(R.id.rb_male);
        radioButtonFemale = findViewById(R.id.rb_female);
        calculateButton = findViewById(R.id.btn_calculate);
        backButton = findViewById(R.id.backBtn);
    }
    private void setDefaults(){
        selectedGender = "Male";
        age = 18;
        height = 152.4;
        weight = 50;
    }
    private void setupListeners() {
        radioButtonMale.setOnClickListener(v -> {
            genderNameTextView.setText(getResources().getString(R.string.gender_male_value));
            selectedGender = "Male";
        });
        radioButtonFemale.setOnClickListener(v -> {
            genderNameTextView.setText(getResources().getString(R.string.gender_female_value));
            selectedGender = "Female";
        });
        calculateButton.setOnClickListener(v -> calculateBMI());
        backButton.setOnClickListener(v -> finish());
    }

    private void calculateBMI() {
        String ageStr = ageEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();
        String weightStr = weightEditText.getText().toString();

        // Input validation (check for empty fields and numeric values)
        if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || selectedGender.isEmpty()) {
            Toast.makeText(BMI_CalculatorActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            age = (byte) Integer.parseInt(ageStr);
            height = Double.parseDouble(heightStr) / 100; // Convert cm to meters
            weight = Double.parseDouble(weightStr);

            // Calculate BMI based on selected gender (optional)
            // You can adjust the formula based on gender if needed.
            Intent intent = getIntent(weight, height, age);

            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(BMI_CalculatorActivity.this, "Invalid input. Please enter numbers only.", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private Intent getIntent(double weight, double height, byte age) {
        double bmi = weight / (height * height);

        // Calculate BMI category
        String bmiCategory = calculateBMICategoryWithFactors(bmi, age, selectedGender);

        // Create an intent to launch the ResultActivity
        Intent intent = new Intent(BMI_CalculatorActivity.this, BMIResultActivity.class);
        intent.putExtra("age", age);
        intent.putExtra("gender", selectedGender);
        intent.putExtra("height", height);
        intent.putExtra("weight", weight);
        intent.putExtra("bmi", bmi);
        intent.putExtra("bmiCategory", bmiCategory);
        return intent;
    }

    private String calculateBMICategoryWithFactors(double bmi, byte age, String gender) {
        if (age < 18) {
            // Use pediatric BMI percentiles for age-specific categorization
            if (bmi < 5) {
                return "Underweight";
            } else if (bmi <= 85) {
                return "Normal";
            } else if (bmi <= 95) {
                return "Overweight";
            } else {
                return "Obese";
            }
        } else if (age > 60) {
            // Adjust BMI ranges for older adults
            if (bmi < 22) {
                return "Underweight";
            } else if (bmi < 28) {
                return "Normal";
            } else if (bmi < 35) {
                return "Overweight";
            } else {
                return "Obese";
            }
        } else {
            // General BMI ranges (use gender-specific thresholds if needed)
            if (gender.equals("Male")) {
                if (bmi < 18.5) {
                    return "Underweight";
                } else if (bmi < 25) {
                    return "Normal";
                } else if (bmi < 30) {
                    return "Overweight";
                } else {
                    return "Obese";
                }
            } else { // Female
                if (bmi < 18) { // Slightly lower thresholds for women
                    return "Underweight";
                } else if (bmi < 24.5) {
                    return "Normal";
                } else if (bmi < 29.5) {
                    return "Overweight";
                } else {
                    return "Obese";
                }
            }
        }
    }
}
