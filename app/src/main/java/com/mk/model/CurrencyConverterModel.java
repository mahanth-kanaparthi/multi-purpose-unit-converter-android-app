// CurrencyConverterModel.java
package com.mk.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyConverterModel implements Parcelable {
    private static final String PREF_NAME = "CurrencyPrefsV2";
    private static final String TAG = "CurrencyConverterModelV2";
    private static final String API_KEY = "facc327a2e014ce090fea191c671cb7c";
    private static final int MAX_DAYS = 5;

    private transient SharedPreferences sharedPreferences;
    private transient Context context;

    private Map<String, BigDecimal> exchangeRatesMap = new HashMap<>();
    public Map<String, String> countriesList = new HashMap<>();
    public String exchangeRatesDataNote = "";

    public CurrencyConverterModel(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        fetchExchangeRates();
    }

    public void setContext(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public void fetchExchangeRates() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (sharedPreferences.contains(today)) {
            useStoredData(today);
            return;
        }

        if (!isInternetAvailable(context)) {
            Log.d(TAG, "Offline. Trying to use most recent stored data.");
            useMostRecentStoredData();
            showToast("No internet. Using last saved rates.");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangeratesapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExchangeRatesApi service = retrofit.create(ExchangeRatesApi.class);

        service.getLatestRates(API_KEY).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject json = response.body();
                    String date = json.get("date").getAsString();
                    JsonObject ratesJson = json.getAsJsonObject("rates");

                    Map<String, BigDecimal> ratesMap = new HashMap<>();
                    for (Map.Entry<String, ?> entry : ratesJson.entrySet()) {
                        ratesMap.put(entry.getKey(), ratesJson.get(entry.getKey()).getAsBigDecimal());
                    }

                    saveRates(date, ratesMap);
                    cleanupOldEntries();
                    useStoredData(date);
                    showToast("Rates updated for " + date);
                } else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, "API fetch failed: " + t.getMessage());
                handleFailure();
            }
        });
    }

    private void saveRates(String date, Map<String, BigDecimal> rates) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
            stringMap.put(entry.getKey(), entry.getValue().toPlainString());
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(date, new Gson().toJson(stringMap));
        editor.apply();
    }

    private void useStoredData(String date) {
        String json = sharedPreferences.getString(date, null);
        if (json != null) {
            Map<String, String> stringMap = new Gson().fromJson(json, Map.class);
            exchangeRatesMap = new HashMap<>();
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                exchangeRatesMap.put(entry.getKey(), new BigDecimal(entry.getValue()));
            }
            countriesList = createCountriesList(exchangeRatesMap);
            exchangeRatesDataNote = "Exchange rates provided by exchangeratesapi.io (" + date + ")";
        }
    }

    private void useMostRecentStoredData() {
        List<String> dates = new ArrayList<>(sharedPreferences.getAll().keySet());
        dates.sort(Collections.reverseOrder());
        for (String date : dates) {
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                useStoredData(date);
                return;
            }
        }
        showToast("No saved exchange rate data available.");
    }

    private void cleanupOldEntries() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -MAX_DAYS);
        String cutoffDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.matches("\\d{4}-\\d{2}-\\d{2}") && key.compareTo(cutoffDate) < 0) {
                editor.remove(key);
            }
        }
        editor.apply();
    }

    private void handleFailure() {
        useMostRecentStoredData();
    }

    private Map<String, String> createCountriesList(Map<String, BigDecimal> ratesMap) {
        Map<String, String> result = new HashMap<>();
        try {
            InputStream is = context.getAssets().open("countriesToBeDisplayed.json");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Map<String, String> displayList = new Gson().fromJson(reader, Map.class);
            for (String code : ratesMap.keySet()) {
                if (displayList.containsKey(code)) {
                    result.put(code, displayList.get(code));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "JSON error: " + e.getMessage());
        }
        return result;
    }

    public Map<String, String> getCountriesList() {
        return countriesList != null ? new TreeMap<>(countriesList) : new TreeMap<>();
    }

    public Map<String, BigDecimal> getExchangeRatesMap() {
        return exchangeRatesMap != null ? exchangeRatesMap : new HashMap<>();
    }

    public String getExchangeRatesDataNote() {
        return exchangeRatesDataNote.isEmpty() ? "Exchange rates not available." : exchangeRatesDataNote;
    }

    public List<BigDecimal> convertCurrency(BigDecimal amount, String from, String to1, String to2) {
        List<BigDecimal> converted = new ArrayList<>();
        if (exchangeRatesMap.containsKey(from) &&
                exchangeRatesMap.containsKey(to1) &&
                exchangeRatesMap.containsKey(to2)) {
            BigDecimal fromRate = exchangeRatesMap.get(from);
            BigDecimal amount1 = amount.multiply(exchangeRatesMap.get(to1)).divide(fromRate, 6, BigDecimal.ROUND_HALF_UP);
            BigDecimal amount2 = amount.multiply(exchangeRatesMap.get(to2)).divide(fromRate, 6, BigDecimal.ROUND_HALF_UP);
            converted.add(amount1);
            converted.add(amount2);
        } else {
            converted.add(BigDecimal.ZERO);
            converted.add(BigDecimal.ZERO);
        }
        return converted;
    }

    private void showToast(String message) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

    interface ExchangeRatesApi {
        @retrofit2.http.GET("latest")
        Call<JsonObject> getLatestRates(@retrofit2.http.Query("access_key") String apiKey);
    }

    // -------------------- Parcelable --------------------

    protected CurrencyConverterModel(Parcel in) {
        exchangeRatesDataNote = in.readString();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            BigDecimal value = new BigDecimal(in.readString());
            exchangeRatesMap.put(key, value);
        }
        int countrySize = in.readInt();
        for (int i = 0; i < countrySize; i++) {
            countriesList.put(in.readString(), in.readString());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exchangeRatesDataNote);
        dest.writeInt(exchangeRatesMap.size());
        for (Map.Entry<String, BigDecimal> entry : exchangeRatesMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue().toPlainString());
        }
        dest.writeInt(countriesList.size());
        for (Map.Entry<String, String> entry : countriesList.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurrencyConverterModel> CREATOR = new Creator<>() {
        @Override
        public CurrencyConverterModel createFromParcel(Parcel in) {
            return new CurrencyConverterModel(in);
        }

        @Override
        public CurrencyConverterModel[] newArray(int size) {
            return new CurrencyConverterModel[size];
        }
    };
}
