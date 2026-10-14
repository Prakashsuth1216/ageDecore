package com.agedecor.interiordesign;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.agedecor.interiordesign.adapter.InquiryAdapter;
import com.agedecor.interiordesign.data.AppDatabaseHelper;
import com.agedecor.interiordesign.data.InquiryModel;
import com.agedecor.interiordesign.data.UserPrivacyPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class UserDataControlActivity extends AppCompatActivity {

    private AppDatabaseHelper dbHelper;
    private UserPrivacyPreferences privacyPrefs;
    private RecyclerView rvInquiries;
    private InquiryAdapter inquiryAdapter;
    private TextView tvInquirySectionTitle, tvNoInquiries;
    private SwitchMaterial switchLocalOnly, switchAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_control);

        dbHelper = new AppDatabaseHelper(this);
        privacyPrefs = new UserPrivacyPreferences(this);

        initViews();
        loadInquiries();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnDataHubBack);
        btnBack.setOnClickListener(v -> finish());

        rvInquiries = findViewById(R.id.rvUserInquiries);
        rvInquiries.setLayoutManager(new LinearLayoutManager(this));

        tvInquirySectionTitle = findViewById(R.id.tvInquirySectionTitle);
        tvNoInquiries = findViewById(R.id.tvNoInquiries);

        switchLocalOnly = findViewById(R.id.switchLocalOnly);
        switchAnalytics = findViewById(R.id.switchAnalytics);

        switchLocalOnly.setChecked(privacyPrefs.isLocalOnly());
        switchAnalytics.setChecked(privacyPrefs.isAnalyticsAllowed());

        switchLocalOnly.setOnCheckedChangeListener((buttonView, isChecked) -> {
            privacyPrefs.setLocalOnly(isChecked);
            Toast.makeText(this, isChecked ? "Strict local-only mode active" : "Local mode altered", Toast.LENGTH_SHORT).show();
        });

        switchAnalytics.setOnCheckedChangeListener((buttonView, isChecked) -> {
            privacyPrefs.setAnalyticsAllowed(isChecked);
            Toast.makeText(this, isChecked ? "Anonymous metrics enabled" : "All analytics disabled", Toast.LENGTH_SHORT).show();
        });

        MaterialButton btnExport = findViewById(R.id.btnExportJson);
        MaterialButton btnWipe = findViewById(R.id.btnWipeAllData);

        // Export data sovereignty action
        btnExport.setOnClickListener(v -> exportUserData());

        // Wipe all data action
        btnWipe.setOnClickListener(v -> confirmWipeAllData());
    }

    private void loadInquiries() {
        List<InquiryModel> list = dbHelper.getAllInquiries();
        int count = list.size();

        tvInquirySectionTitle.setText("My Saved Inquiries (" + count + ")");

        if (count == 0) {
            tvNoInquiries.setVisibility(View.VISIBLE);
            rvInquiries.setVisibility(View.GONE);
        } else {
            tvNoInquiries.setVisibility(View.GONE);
            rvInquiries.setVisibility(View.VISIBLE);

            if (inquiryAdapter == null) {
                inquiryAdapter = new InquiryAdapter(this, list, this::loadInquiries);
                rvInquiries.setAdapter(inquiryAdapter);
            } else {
                inquiryAdapter.updateList(list);
            }
        }
    }

    private void exportUserData() {
        String jsonData = dbHelper.exportAllUserDataAsJson();

        new AlertDialog.Builder(this)
                .setTitle("Export My Data (JSON)")
                .setMessage("Your complete data footprint on Age Décor has been prepared.\n\nYou can copy this data or share it to your storage.")
                .setPositiveButton("Share Archive", (dialog, which) -> {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Age Décor - User Data Archive");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, jsonData);
                    startActivity(Intent.createChooser(shareIntent, "Export User Data Archive"));
                })
                .setNeutralButton("Copy to Clipboard", (dialog, which) -> {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("AgeDecorUserData", jsonData);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "JSON copied to clipboard!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void confirmWipeAllData() {
        new AlertDialog.Builder(this)
                .setTitle("Wipe All My Data?")
                .setMessage("Are you sure you want to permanently erase all inquiries, saved wishlist projects, and local preferences?\n\nThis action cannot be undone.")
                .setPositiveButton("Wipe Everything", (dialog, which) -> {
                    dbHelper.wipeAllUserData();
                    privacyPrefs.clearAllPreferences();
                    loadInquiries();
                    Toast.makeText(this, "All user data has been permanently deleted from this device.", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
