package com.mk.convert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.Locale;

public class DateConverterActivity extends AppCompatActivity {

    TextView fromDate,toDate,yearsValue,monthsValue,daysValue,cardFromValue,cardToValue;
    private Calendar today,selectedFromDate, selectedToDate;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.date_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.date_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        // Set default values to today's date
        today = Calendar.getInstance();
        fromDate.setText(formatDate(today));
        toDate.setText(formatDate(today));
        cardFromValue.setText(formatDate(today));
        cardToValue.setText(formatDate(today));

    }
    private void initializeViews() {
        fromDate = findViewById(R.id.fromDateValue);
        toDate = findViewById(R.id.toDateValue);
        yearsValue = findViewById(R.id.years_value);
        monthsValue = findViewById(R.id.months_value);
        daysValue = findViewById(R.id.days_value);
        cardFromValue = findViewById(R.id.card_from_value);
        cardToValue = findViewById(R.id.card_to_value);

        backBtn = findViewById(R.id.backBtn);

        int defaultFontColor = getResources().getColor(R.color.black);
        int selectedFontColor = getResources().getColor(R.color.orange);

        fromDate.setOnClickListener(v -> {
            fromDate.setTextColor(selectedFontColor);
            toDate.setTextColor(defaultFontColor);
            showDatePickerDialog(fromDate,"From");
        });
        toDate.setOnClickListener(v -> {
            toDate.setTextColor(selectedFontColor);
            fromDate.setTextColor(defaultFontColor);
            showDatePickerDialog(toDate,"To");
        });
        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void showDatePickerDialog(TextView textView,String title) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.date_picker_bottom_sheet, null);
        dialog.setContentView(view);

        NumberPicker dayPicker = view.findViewById(R.id.numberPickerDay);
        NumberPicker monthPicker = view.findViewById(R.id.numberPickerMonth);
        NumberPicker yearPicker = view.findViewById(R.id.numberPickerYear);

        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        Button buttonOk = view.findViewById(R.id.buttonOk);
        //set title
        TextView bottomSheetTitle = view.findViewById(R.id.bottom_sheet_title);
        bottomSheetTitle.setText(title);

        // Set up day picker
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        // Set up month picker
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setDisplayedValues(months);

        // Set up year picker
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(2100);

        //set today's date
        dayPicker.setValue(today.get(Calendar.DAY_OF_MONTH));
        monthPicker.setValue(today.get(Calendar.MONTH) + 1); // Month is 0-based in Calendar
        yearPicker.setValue(today.get(Calendar.YEAR));

        // Handle cancel button
        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        // Handle OK button
        buttonOk.setOnClickListener(v -> {
            int day = dayPicker.getValue();
            String month = months[monthPicker.getValue() - 1];
            int year = yearPicker.getValue();

            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, monthPicker.getValue() - 1, day);

            if(title.equals("From")) {
                selectedFromDate = selectedDate;
                fromDate.setText(formatDate(selectedDate));
                cardFromValue.setText(formatDate(selectedDate));
            }
            else {
                selectedToDate = selectedDate;
                toDate.setText(formatDate(selectedDate));
                cardToValue.setText(formatDate(selectedDate));
            }

            if (selectedFromDate != null && selectedToDate != null) {
                calculateDifference();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void calculateDifference() {
        if (selectedFromDate != null && selectedToDate != null) {
            Calendar startDate = (Calendar) selectedFromDate.clone();
            Calendar endDate = (Calendar) selectedToDate.clone();

            if (startDate.after(endDate)) {
                Calendar temp = startDate;
                startDate = endDate;
                endDate = temp;
            }

            int years = endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR);
            int months = endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
            int days = endDate.get(Calendar.DAY_OF_MONTH) - startDate.get(Calendar.DAY_OF_MONTH);

            if (days < 0) {
                months--;
                startDate.add(Calendar.MONTH, 1);
                days += startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
            }

            if (months < 0) {
                years--;
                months += 12;
            }

            yearsValue.setText(String.valueOf(years));
            monthsValue.setText(String.valueOf(months));
            daysValue.setText(String.valueOf(days));
        }
    }

    private String formatDate(Calendar date) {
        int day = date.get(Calendar.DAY_OF_MONTH);
        String month = new java.text.DateFormatSymbols().getMonths()[date.get(Calendar.MONTH)];
        int year = date.get(Calendar.YEAR);
        return String.format(Locale.getDefault(),"%s %d, %d", month, day, year);
    }
}

