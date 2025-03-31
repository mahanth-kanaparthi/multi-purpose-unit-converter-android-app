package com.mk.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.widget.Toast;

public class CurrencyConverterModel {
    private static final String PREF_NAME = "CurrencyPrefs";
    private static final String KEY_RATES = "exchangeRatesMap";
    private static final String KEY_DATE = "fetchDate";
    private static final String KEY_SOURCE = "source";
    private static final String TAG = "CurrencyConverterModel";

    private final SharedPreferences sharedPreferences;
    private final Context context;

    // Replace this with your actual API key
    private static final String API_KEY = "facc327a2e014ce090fea191c671cb7c";

    private  Map<String, Double> exchangeRatesMap;
    public Map<String, String> countriesList;

    private final String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    public String exchangeRatesDataNote = "";

    public CurrencyConverterModel(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        fetchExchangeRates();
    }

    public void fetchExchangeRates() {

        // Check if the rates are already fetched today
        String lastFetchedDate = sharedPreferences.getString(KEY_DATE, null);
        if (today.equals(lastFetchedDate)) {
            Log.d(TAG, "Rates already fetched for today.");
            Toast.makeText(context, "Rates already fetched for today.", Toast.LENGTH_SHORT).show();
            useStoredData();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangeratesapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExchangeRatesApi service = retrofit.create(ExchangeRatesApi.class);

        service.getLatestRates(API_KEY).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject jsonResponse = response.body();
                    String date = jsonResponse.get("date").getAsString();
                    JsonObject rates = jsonResponse.getAsJsonObject("rates");

                    // Convert JSON to Map
                    exchangeRatesMap = new Gson().fromJson(rates, Map.class);

                    // Save data in SharedPreferences
                    saveDataToPreferences(exchangeRatesMap, date, "exchangeratesapi.io");
                    // update exchangeRatesDataNote
                    exchangeRatesDataNote = "Exchange rates are provided by exchangeratesapi.io"+"("+date+")";

                    // Create countriesList
                    countriesList = createCountriesList(exchangeRatesMap);
                    Log.d(TAG, "Countries List: " + countriesList);
                } else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "Failed to fetch exchange rates: " + t.getMessage());
                handleFailure();
            }
        });

    }

    private void saveDataToPreferences(Map<String, Double> exchangeRatesMap, String date, String source) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_RATES, new Gson().toJson(exchangeRatesMap));
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_SOURCE, source);
        editor.apply();
    }

    private Map<String, Double> getSavedExchangeRates() {
        String ratesJson = sharedPreferences.getString(KEY_RATES, null);
        if (ratesJson != null) {
            return new Gson().fromJson(ratesJson, Map.class);
        }
        return null;
    }

    private Map<String, String> createCountriesList(Map<String, Double> exchangeRatesMap) {
        Map<String, String> countriesList = new HashMap<>();
        try {
            InputStream is = context.getAssets().open("countriesToBeDisplayed.json");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            Map<String, String> countriesToBeDisplayed = new Gson().fromJson(reader, Map.class);

            for (Map.Entry<String, Double> entry : exchangeRatesMap.entrySet()) {
                String countryCode = entry.getKey();
                if (countriesToBeDisplayed.containsKey(countryCode)) {
                    countriesList.put(countryCode, countriesToBeDisplayed.get(countryCode));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error reading JSON asset: " + e.getMessage());
        }
        return countriesList;
    }

    private void handleFailure() {
        Map<String, Double> savedRates = getSavedExchangeRates();
        // Check if the rates are already fetched today
        String lastFetchedDate = sharedPreferences.getString(KEY_DATE, null);
        if (savedRates != null) {
            countriesList = createCountriesList(savedRates);
            exchangeRatesDataNote = "Exchange rates are provided by exchangeratesapi.io on " + lastFetchedDate;
            exchangeRatesMap = getSavedExchangeRates();
            Log.d(TAG, "Using stored data. Countries List: " + countriesList);
            Log.d(TAG,"stored exchange rates: "+ exchangeRatesMap);
        } else {
            Log.e(TAG, "No data available.");
        }
    }

    private void useStoredData() {
        Map<String, Double> savedRates = getSavedExchangeRates();
        // Check if the rates are already fetched today
        String lastFetchedDate = sharedPreferences.getString(KEY_DATE, null);
        if (savedRates != null) {
            exchangeRatesMap = savedRates;
            countriesList = createCountriesList(savedRates);
            exchangeRatesDataNote = "Exchange rates are provided by exchangeratesapi.io on "+lastFetchedDate;
            Log.d(TAG, "Using stored data. Countries List: " + countriesList);
            Log.d(TAG,"stored exchange rates: "+ exchangeRatesMap);
        } else {
            Log.e(TAG, "No data available.");
        }
    }

    interface ExchangeRatesApi {
        @retrofit2.http.GET("latest")
        Call<JsonObject> getLatestRates(@retrofit2.http.Query("access_key") String apiKey);
    }
    public Map<String, String> getCountriesList() {
        Map<String,String> map = new TreeMap<>(countriesList);
        return map;
    }
    public Map<String,Double> getExchangeRatesMap(){
        return exchangeRatesMap;
    }

    public String getExchangeRatesDataNote() {
        return exchangeRatesDataNote;
    }

    public List<Double> convertCurrency(double amount, String fromCurrency, String toCurrency1, String toCurrency2) {
        List<Double> convertedAmounts = new ArrayList<>();
        if (exchangeRatesMap != null && exchangeRatesMap.containsKey(fromCurrency)
                && exchangeRatesMap.containsKey(toCurrency1)
                && exchangeRatesMap.containsKey(toCurrency2)) {
            double fromRate = exchangeRatesMap.get(fromCurrency);
            double toRate1 = exchangeRatesMap.get(toCurrency1);
            double toRate2 = exchangeRatesMap.get(toCurrency2);
            double amount1 = amount * (toRate1 / fromRate);
            double amount2 = amount * (toRate2 / fromRate);
            convertedAmounts.add(amount1);
            convertedAmounts.add(amount2);
        } else {
            Log.e(TAG, "Invalid currency codes or exchange rates not available.");
            convertedAmounts.add(0.0);
            convertedAmounts.add(0.0);
        }
        return convertedAmounts;
    }


}