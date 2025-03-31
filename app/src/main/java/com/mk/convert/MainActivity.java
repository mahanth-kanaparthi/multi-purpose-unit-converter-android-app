package com.mk.convert;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    CardView currencyCardView, lengthCardView,massCardView,discountCardView,
            volumeCardView,temperatureCardView,timeCardView,dataCardView,
            areaCardView,speedCardView,dateCardView,financeCardView,numeralsCardView,
            bmiCardView,gstCardView,pressureCardView,energyCardView,forceCardView;
    Animation button_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button_anim = AnimationUtils.loadAnimation(this, R.anim.button_squeeze);

        setListenersForActivities();


    }
    private void startCurrencyActivity(){
        Intent intent = new Intent(this, CurrencyConverterActivity.class);
        startActivity(intent);
    }

    private void startAreaActivity(){
        Intent intent = new Intent(this, AreaConverterActivity.class);
        startActivity(intent);
    }
    private void startLengthActivity(){
        Intent intent = new Intent(this, LengthConverterActivity.class);
        startActivity(intent);
    }
    private void startMassActivity(){
        Intent intent = new Intent(this, MassConverterActivity.class);
        startActivity(intent);
    }
    private void startTimeActivity(){
        Intent intent = new Intent(this, TimeConverterActivity.class);
        startActivity(intent);
    }
    private void startFinanceActivity(){
        Intent intent = new Intent(this, FinanceCalculatorActivity.class);
        startActivity(intent);
    }
    private void startDataActivity(){
        Intent intent = new Intent(this, DataConverterActivity.class);
        startActivity(intent);
    }
    private void startDateActivity(){
        Intent intent = new Intent(this, DateConverterActivity.class);
        startActivity(intent);
    }
    private void startDiscountActivity(){
        Intent intent = new Intent(this, DiscountConverterActivity.class);
        startActivity(intent);
    }
    private void startVolumeActivity(){
        Intent intent = new Intent(this, VolumeConverterActivity.class);
        startActivity(intent);
    }
    private void startNumeralsActivity(){
        Intent intent = new Intent(this, NumeralsConverterActivity.class);
        startActivity(intent);
    }

    private void startSpeedActivity(){
        Intent intent = new Intent(this, SpeedConverterActivity.class);
        startActivity(intent);
    }

    private void startTemperatureActivity(){
        Intent intent = new Intent(this, TemperatureConverterActivity.class);
        startActivity(intent);
    }
    private void startBmiActivity(){
        Intent intent = new Intent(this, BMI_CalculatorActivity.class);
        startActivity(intent);
    }
    private void startGstActivity(){
        Intent intent = new Intent(this, GST_CalculatorActivity.class);
        startActivity(intent);
    }
    private void startForceActivity(){
        Intent intent = new Intent(this, ForceConverterActivity.class);
        startActivity(intent);
    }
    private void startEnergyActivity(){
        Intent intent = new Intent(this, EnergyConverterActivity.class);
        startActivity(intent);
    }
    private void startPressureActivity(){
        Intent intent = new Intent(this, PressureConverterActivity.class);
        startActivity(intent);
    }

    private void setListenersForActivities(){
        currencyCardView = findViewById(R.id.currencyCard);
        currencyCardView.setAnimation(button_anim);
        currencyCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startCurrencyActivity();
        });
        lengthCardView = findViewById(R.id.lengthCard);
        lengthCardView.setAnimation(button_anim);
        lengthCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startLengthActivity();
        });
        massCardView = findViewById(R.id.massCard);
        massCardView.setAnimation(button_anim);
        massCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startMassActivity();
        });
        areaCardView = findViewById(R.id.areaCard);
        areaCardView.setAnimation(button_anim);
        areaCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startAreaActivity();
        });
        timeCardView = findViewById(R.id.timeCard);
        timeCardView.setAnimation(button_anim);
        timeCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startTimeActivity();
        });

        financeCardView = findViewById(R.id.financeCard);
        financeCardView.setAnimation(button_anim);
        financeCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startFinanceActivity();
        });
        dataCardView = findViewById(R.id.dataCard);
        dataCardView.setAnimation(button_anim);
        dataCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startDataActivity();
        });
        dateCardView = findViewById(R.id.dateCard);
        dateCardView.setAnimation(button_anim);
        dateCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startDateActivity();
        });

        discountCardView = findViewById(R.id.discountCard);
        discountCardView.setAnimation(button_anim);
        discountCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startDiscountActivity();
        });

        volumeCardView = findViewById(R.id.volumeCard);
        volumeCardView.setAnimation(button_anim);
        volumeCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startVolumeActivity();
        });
        numeralsCardView = findViewById(R.id.numeralsCard);
        numeralsCardView.setAnimation(button_anim);
        numeralsCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startNumeralsActivity();
        });
        speedCardView = findViewById(R.id.speedCard);
        speedCardView.setAnimation(button_anim);
        speedCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startSpeedActivity();
        });
        temperatureCardView = findViewById(R.id.temperatureCard);
        temperatureCardView.setAnimation(button_anim);
        temperatureCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startTemperatureActivity();
        });
        bmiCardView = findViewById(R.id.bmiCard);
        bmiCardView.setAnimation(button_anim);
        bmiCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startBmiActivity();
        });
        gstCardView = findViewById(R.id.gstCard);
        gstCardView.setAnimation(button_anim);
        gstCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startGstActivity();
        });

        forceCardView = findViewById(R.id.forceCard);
        forceCardView.setAnimation(button_anim);
        forceCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startForceActivity();
        });
        energyCardView = findViewById(R.id.energyCard);
        energyCardView.setAnimation(button_anim);
        energyCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startEnergyActivity();
        });
        pressureCardView = findViewById(R.id.pressureCard);
        pressureCardView.setAnimation(button_anim);
        pressureCardView.setOnClickListener(v -> {
            v.startAnimation(button_anim);
            startPressureActivity();
        });


    }


}