package com.agedecor.interiordesign;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.agedecor.interiordesign.data.AppDatabaseHelper;
import com.agedecor.interiordesign.data.InquiryModel;
import com.google.android.material.button.MaterialButton;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InquiryFormActivity extends AppCompatActivity {

    private TextView tvSelectedProject;
    private EditText etName, etPhone, etEmail, etCarpetArea, etRequirements;
    private Spinner spinnerLocality, spinnerPropertyType, spinnerBudget;
    private AppDatabaseHelper dbHelper;

    private String projectTitle = "General Luxury Consultation (Custom Space)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_form);

        dbHelper = new AppDatabaseHelper(this);

        if (getIntent().hasExtra("project_title")) {
            projectTitle = getIntent().getStringExtra("project_title");
        }

        initViews();
        setupSpinners();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnFormBack);
        btnBack.setOnClickListener(v -> finish());

        tvSelectedProject = findViewById(R.id.tvFormSelectedProject);
        tvSelectedProject.setText(projectTitle);

        etName = findViewById(R.id.etClientName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etCarpetArea = findViewById(R.id.etCarpetArea);
        etRequirements = findViewById(R.id.etRequirements);

        if (getIntent().hasExtra("project_area")) {
            etCarpetArea.setText(getIntent().getStringExtra("project_area"));
        }

        spinnerLocality = findViewById(R.id.spinnerLocality);
        spinnerPropertyType = findViewById(R.id.spinnerPropertyType);
        spinnerBudget = findViewById(R.id.spinnerBudget);

        MaterialButton btnSubmit = findViewById(R.id.btnSubmitForm);
        MaterialButton btnSubmitWhatsApp = findViewById(R.id.btnSubmitWhatsApp);

        btnSubmit.setOnClickListener(v -> processSubmission(false));
        btnSubmitWhatsApp.setOnClickListener(v -> processSubmission(true));
    }

    private void setupSpinners() {
        String[] localities = {
                "Bandra West / Pali Hill",
                "Worli Sea Face / Lower Parel",
                "Juhu / Juhu Tara Road",
                "Powai / Hiranandani",
                "South Mumbai (Marine Drive / Colaba / Malabar Hill)",
                "Andheri West / Lokhandwala",
                "Bandra Kurla Complex (BKC)",
                "Santacruz / Khar West",
                "Chembur / Wadala",
                "Navi Mumbai (Palm Beach Road / Vashi)",
                "Thane West"
        };
        ArrayAdapter<String> locAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, localities);
        spinnerLocality.setAdapter(locAdapter);

        // Pre-select if passed from project
        if (getIntent().hasExtra("project_locality")) {
            String passedLoc = getIntent().getStringExtra("project_locality");
            for (int i = 0; i < localities.length; i++) {
                if (passedLoc.toLowerCase().contains(localities[i].toLowerCase().split(" ")[0])) {
                    spinnerLocality.setSelection(i);
                    break;
                }
            }
        }

        String[] propTypes = {
                "3 BHK Luxury Apartment",
                "2 BHK Modern Apartment",
                "4 BHK / Duplex Sea-Facing",
                "Penthouse Suite",
                "Private Luxury Villa",
                "Corporate / Executive Office",
                "Commercial Retail Studio",
                "Complete Home Renovation"
        };
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, propTypes);
        spinnerPropertyType.setAdapter(typeAdapter);

        String[] budgets = {
                "₹10 Lakhs - ₹20 Lakhs",
                "₹20 Lakhs - ₹35 Lakhs",
                "₹35 Lakhs - ₹50 Lakhs",
                "₹50 Lakhs - ₹80 Lakhs",
                "₹80 Lakhs - ₹1.5 Crore",
                "Above ₹1.5 Crore (Ultra Luxury)"
        };
        ArrayAdapter<String> budgetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, budgets);
        spinnerBudget.setAdapter(budgetAdapter);
    }

    private void processSubmission(boolean openWhatsApp) {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String carpetArea = etCarpetArea.getText().toString().trim();
        String requirements = etRequirements.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Please enter your name");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Please enter your phone number");
            etPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(carpetArea)) {
            carpetArea = "Approx 1,500";
        }

        if (TextUtils.isEmpty(requirements)) {
            requirements = "General consultation for turnkey interior design.";
        }

        String locality = spinnerLocality.getSelectedItem().toString();
        String propType = spinnerPropertyType.getSelectedItem().toString();
        String budget = spinnerBudget.getSelectedItem().toString();

        String currentTime = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(new Date());

        InquiryModel inquiry = new InquiryModel(
                0,
                name,
                phone,
                email.isEmpty() ? "N/A" : email,
                projectTitle,
                locality,
                propType,
                carpetArea,
                budget,
                requirements,
                currentTime,
                "Local Saved & Active"
        );

        // Save locally to user's database
        long savedId = dbHelper.insertInquiry(inquiry);

        if (openWhatsApp) {
            try {
                String whatsappMessage = "Hello Age Décor Interior Design Mumbai Team,\n\n" +
                        "I would like to discuss my interior design requirements:\n" +
                        "• Project Interest: *" + projectTitle + "*\n" +
                        "• Client Name: " + name + "\n" +
                        "• Phone: " + phone + "\n" +
                        "• Locality: " + locality + "\n" +
                        "• Configuration: " + propType + "\n" +
                        "• Carpet Area: " + carpetArea + " sq.ft\n" +
                        "• Budget: " + budget + "\n" +
                        "• Specific Needs: " + requirements + "\n\n" +
                        "Please let me know when we can schedule an architectural consultation.";

                String encoded = URLEncoder.encode(whatsappMessage, "UTF-8");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=918000737561&text=" + encoded));
                startActivity(sendIntent);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Could not launch WhatsApp: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Inquiry Saved Successfully!")
                    .setMessage("Thank you, " + name + "!\n\nYour consultation request for '" + projectTitle +
                            "' has been safely registered on your device.\n\n" +
                            "Privacy Guarantee: You have 100% control over this data. You can inspect, export, or delete it anytime from 'Data Hub'.")
                    .setPositiveButton("View in Data Hub", (dialog, which) -> {
                        Intent intent = new Intent(InquiryFormActivity.this, UserDataControlActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Back to Home", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }
}
