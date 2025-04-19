# Multi-Purpose Unit Converter Android App

A comprehensive Android application developed by **Mahanth Kanaparthi** using **Java** and **Gradle Kotlin DSL**. This app offers an all-in-one solution for various unit conversions and useful financial and health utilities.

![AppIcon](docs/ic_launcher_round.jpg)  
Converter

Note: It is inspired from Xiaomi's Calculator App

---

## Table of Contents
- [Purpose](#purpose)
- [Key Features](#key-features)
- [Architecture](#architecture)
- [Build & Project Files](#build--project-files)
- [Testing](#testing)
- [Screenshots](#screenshots)

---

## Purpose

The application is designed to help users perform a wide range of **unit conversions** and **calculations** efficiently with a clean, user-friendly interface.

---

## Key Features

### Unit Conversions
- **Area**
- **Length**
- **Mass**
- **Time**
- **Speed**
- **Temperature**
- **Volume**
- **Data**
- **Force**
- **Energy**
- **Pressure**
- **Numeral Systems**
- **Date Calculations**
- **Discount Calculator**

### Finance Tools
- **EMI Calculator**
- **Investment Calculator**
- **GST Calculator**

### Health Tool
- **BMI Calculator** with animated result display

### Currency Converter
- Powered by [exchangeratesapi.io](https://exchangeratesapi.io)
- Uses **Retrofit** and **Gson**
- Stores rates in **SharedPreferences** for **offline access**

---

## Architecture

Follows **MVC Architecture**:

### Model
- Handles conversion data and logic

### View
- XML layouts for UI
- Custom themes, drawables, and animations

### Controller
- Java classes for each feature's logic and interaction

### Structure
```
com.mk.convert/
├── controller/        # Handles user interactions
├── convert/           # Activities for each conversion
├── model/             # Data models and logic
└── utils/             # Utility classes and dialogs
```

---

## Build & Project Files

- Written in **Java**
- Uses **Gradle Kotlin DSL**
- Project configuration:
  - `build.gradle.kts`
  - `settings.gradle.kts`
  - `libs.versions.toml`
- Gradle wrapper included:
  - `gradlew`, `gradlew.bat`, `gradle-wrapper.properties`

---

## Testing

- **Unit Tests**: Located in `src/test/`
- **Instrumented Tests**: Located in `src/androidTest/`

---

## Screenshots
<table>
  <tr>
      <td>
        <img src="docs/main-activity-layout(home).jpg" alt="Main" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/currency-converter-layout.jpg" alt="Currency-Converter" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/length-converter-layout.jpg" alt="Length-Converter" style="width:200px; height:400px;">
      </td>
  </tr>

  <tr>
      <td>
        <img src="docs/date-calculator-layout.jpg" alt="Date-Calculator" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/finance-calculator-emi-entering-data.jpg" alt="EMI-Calculator" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/finance-calculator-emi-result.jpg" alt="EMI-Calculator-Result" style="width:200px; height:400px;">
      </td>
  </tr>

  <tr>
      <td>
        <img src="docs/finance-calculator-investment-entering-data.jpg" alt="Investment-Calculator" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/finance-calculator-investment-result.jpg" alt="Investment-Calculator-Result" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/discount-calculator-layout.jpg" alt="Discount-Calculator" style="width:200px; height:400px;">
      </td>
  </tr>

  <tr>
      <td>
        <img src="docs/numeral-system-layout.jpg" alt="Numerical-System" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/temperature-converter-layout.jpg" alt="Temperature-Converter" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/gst-calculator-layout.jpg" alt="GST-Calculator" style="width:200px; height:400px;">
      </td>
  </tr>

  <tr>
      <td>
        <img src="docs/bmi-calculator-layout.jpg" alt="BMI-Calculator" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/bmi-calculator-user-entering-details.jpg" alt="BMI-Calculator-Entering-Data" style="width:200px; height:400px;">
      </td>
      <td>
        <img src="docs/bmi-calculator-results.jpg" alt="BMI-Calculator-Result" style="width:200px; height:400px;">
      </td>
  </tr>
</table>

---

## Conclusion

This Android application demonstrates advanced development skills with:
- Modular architecture
- Accurate and efficient conversion logic
- Beautiful, responsive UI
- Offline capabilities and API integration
- Inspired from Xiaomi's Calculator App

Feel free to explore and contribute!

---
