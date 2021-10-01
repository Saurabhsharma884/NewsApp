package com.example.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity {

    private final String MODE_KEY = "uiMode";
    private final String COUNTRY_KEY = "savedCountry";
    private final String LANGUAGE_KEY = "savedLanguage";
    private int countrySelection = 0;
    private int languageSelection = 0;

    private boolean modeState;

    Spinner langSpinner;
    Spinner countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        langSpinner = findViewById(R.id.language_spinner);
        countrySpinner = findViewById(R.id.countrylist_spinner);

        setCountrySpinner();
        setLangSpinner();

        setSettings();

        SwitchMaterial modeSwitch = findViewById(R.id.mode_switch);
        modeSwitch.setChecked(modeState);

        Button saveBtn = findViewById(R.id.save_btn);


        ImageButton backBtn = findViewById(R.id.back_Btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(SettingActivity.this);
            }
        });


        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modeState = isChecked;
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                Log.d("Saurabh", "saveSettings:" + MainActivity.country + " " + MainActivity.language);
                Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
                NavUtils.navigateUpFromSameTask(SettingActivity.this);
            }
        });

    }

    private void setSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        languageSelection = sharedPreferences.getInt(LANGUAGE_KEY, 0);
        countrySelection = sharedPreferences.getInt(COUNTRY_KEY, 0);
        langSpinner.setSelection(languageSelection);
        countrySpinner.setSelection(countrySelection);
        modeState = sharedPreferences.getBoolean(MODE_KEY, false);
    }

    private void saveSettings() {

        SharedPreferences sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MODE_KEY, modeState);
        editor.putInt(COUNTRY_KEY, countrySelection);
        editor.putInt(LANGUAGE_KEY, languageSelection);
        editor.apply();

    }

    private void setLangSpinner() {

        ArrayAdapter<CharSequence> langSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.lang_array, android.R.layout.simple_spinner_item);
        langSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(langSpinnerAdapter);

        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch ((String) parent.getItemAtPosition(position)) {
                    case "Hindi":
                        MainActivity.language = "hi";
                        break;
                    case "Japanese":
                        MainActivity.language = "ja";
                        break;
                    case "Chinese":
                        MainActivity.language = "jh";
                        break;
                    case "Arabic":
                        MainActivity.language = "ar";
                        break;
                    default:
                        MainActivity.language = "eng";
                }
                languageSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MainActivity.language = "en";
            }
        });
    }

    private void setCountrySpinner() {

        ArrayAdapter<CharSequence> countrySpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.country_list_array, android.R.layout.simple_spinner_item);
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countrySpinnerAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.country = (String) parent.getItemAtPosition(position);
                switch ((String) parent.getItemAtPosition(position)) {
                    case "United States":
                        MainActivity.country = "us";
                        break;
                    case "Pakistan":
                        MainActivity.country = "pk";
                        break;
                    case "Japan":
                        MainActivity.country = "jp";
                        break;
                    case "India":
                        MainActivity.country = "in";
                        break;
                    case "China":
                        MainActivity.country = "cn";
                        break;
                    default:
                        MainActivity.country = "any";
                }
                countrySelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MainActivity.country = "All";
            }
        });
    }
}