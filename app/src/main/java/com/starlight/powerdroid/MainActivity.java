package com.starlight.powerdroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.PopupMenu;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Define & Declare Variables
    private EditText etName, etFatherName, etAddress, etEmail, etPhoneNumber, etDateOfBirth;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Spinner spinnerDegree, spinnerField;
    private SeekBar sliderLanguageFluency;
    private Button btnFavLang, btnSaveLocal, btnSubmit;
    private CheckBox checkBoxTermsConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference & Initialize UI components
        etName = findViewById(R.id.etName);
        etFatherName = findViewById(R.id.etFatherName);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        spinnerDegree = findViewById(R.id.Degree);
        spinnerField = findViewById(R.id.Field);
        sliderLanguageFluency = findViewById(R.id.sliderLanguageFluency);
        btnFavLang = findViewById(R.id.btnFavLang);
        btnSaveLocal = findViewById(R.id.btnSaveLocal);
        btnSubmit = findViewById(R.id.btnSubmit);
        checkBoxTermsConditions = findViewById(R.id.checkBoxTermsConditions);

        // Set up spinners
        ArrayAdapter<CharSequence> degreeAdapter = ArrayAdapter.createFromResource(
                this, R.array.degree_array, android.R.layout.simple_spinner_item);
        degreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDegree.setAdapter(degreeAdapter);

        spinnerDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Display a Toast when a degree is selected
                String selectedDegree = parentView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected Degree: " + selectedDegree, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        ArrayAdapter<CharSequence> fieldAdapter = ArrayAdapter.createFromResource(
                this, R.array.field_array, android.R.layout.simple_spinner_item);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerField.setAdapter(fieldAdapter);

        spinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Display a Toast when a field is selected
                String selectedField = parentView.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected Field: " + selectedField, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });

        btnFavLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click for selecting favorite language

                // Initializing the popup menu and giving the reference as the current context
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnFavLang);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                // Setting up a listener for menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked, displaying the selected language
                        Toast.makeText(MainActivity.this, "Selected Language: " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                // Showing the popup menu
                popupMenu.show();
            }
        });

        btnSaveLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to save data to a text file with user's name
                saveDataToLocalFile(etName.getText().toString());
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (checkBoxTermsConditions.isChecked()) {
                // If terms and conditions are agreed
                // You can perform further actions here

                Toast.makeText(MainActivity.this, "Submission successful", Toast.LENGTH_SHORT).show();
            }

            else {
                Toast.makeText(MainActivity.this, "Please agree to Terms and Conditions", Toast.LENGTH_SHORT).show();
            }

            }
        });
    }

    // Method to save data to a text file
    private void saveDataToLocalFile(String userName) {
        String fileName = userName + "_userdata.txt"; // Name of the file to save the data with user's name
        String data = ""; // String to store the data

        // Append each field's data to the 'data' string
        data += "Name: " + etName.getText().toString() + "\n";
        data += "Father's Name: " + etFatherName.getText().toString() + "\n";
        data += "Address: " + etAddress.getText().toString() + "\n";
        data += "Email: " + etEmail.getText().toString() + "\n";
        data += "Phone Number: " + etPhoneNumber.getText().toString() + "\n";
        data += "Date of Birth: " + etDateOfBirth.getText().toString() + "\n";

        // Get selected gender
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        if (selectedGenderRadioButton != null) {
            data += "Gender: " + selectedGenderRadioButton.getText().toString() + "\n";
        }

        // Get selected degree
        String selectedDegree = spinnerDegree.getSelectedItem().toString();
        data += "Degree: " + selectedDegree + "\n";

        // Get selected field
        String selectedField = spinnerField.getSelectedItem().toString();
        data += "Field: " + selectedField + "\n";

        // Get selected language fluency from the seek bar
        int languageFluency = sliderLanguageFluency.getProgress();
        data += "Language Fluency: " + languageFluency + "\n";

        // Get the Downloads directory
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Create the file object in the Downloads directory
        File file = new File(downloadsDir, fileName);

        // Write data to the file
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
            Toast.makeText(MainActivity.this, "Data saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}