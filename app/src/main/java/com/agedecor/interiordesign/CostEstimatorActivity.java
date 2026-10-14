package com.agedecor.interiordesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.Locale;

public class CostEstimatorActivity extends AppCompatActivity {

    private EditText etCarpetArea;
    private RadioGroup rgTier;
    private TextView tvTotalCost, tvTimelineEstimate;

    private int ratePerSqFt = 1750; // Default Signature tier
    private String selectedTierName = "Signature Luxury";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_estimator);

        initViews();
        calculateCost();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnEstimatorBack);
        btnBack.setOnClickListener(v -> finish());

        etCarpetArea = findViewById(R.id.etCalcCarpetArea);
        rgTier = findViewById(R.id.rgTier);
        tvTotalCost = findViewById(R.id.tvCalculatedTotal);
        tvTimelineEstimate = findViewById(R.id.tvTimelineEstimate);
        MaterialButton btnBook = findViewById(R.id.btnBookWithEstimate);

        rgTier.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbTierEssential) {
                ratePerSqFt = 1350;
                selectedTierName = "Essential Luxury (₹1,350/sq.ft)";
            } else if (checkedId == R.id.rbTierPremium) {
                ratePerSqFt = 1750;
                selectedTierName = "Signature Luxury (₹1,750/sq.ft)";
            } else if (checkedId == R.id.rbTierElite) {
                ratePerSqFt = 2250;
                selectedTierName = "Haute Architecture (₹2,250/sq.ft)";
            }
            calculateCost();
        });

        etCarpetArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                calculateCost();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnBook.setOnClickListener(v -> {
            String areaStr = etCarpetArea.getText().toString().trim();
            Intent intent = new Intent(CostEstimatorActivity.this, InquiryFormActivity.class);
            intent.putExtra("project_title", "Custom Turnkey Interior (" + selectedTierName + ")");
            intent.putExtra("project_area", areaStr.isEmpty() ? "1500" : areaStr);
            startActivity(intent);
        });
    }

    private void calculateCost() {
        String input = etCarpetArea.getText().toString().trim();
        long area = 0;
        try {
            if (!input.isEmpty()) {
                area = Long.parseLong(input);
            }
        } catch (NumberFormatException e) {
            area = 0;
        }

        long total = area * ratePerSqFt;

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String formatted = format.format(total).replace(".00", "");
        tvTotalCost.setText(formatted);

        // Approximate duration formula: base 45 days + 10 days per 500 sq.ft
        long days = 45 + (area / 500) * 10;
        if (days < 45) days = 45;
        if (days > 150) days = 150;
        tvTimelineEstimate.setText("Estimated Execution Time: ~" + days + " Working Days");
    }
}
