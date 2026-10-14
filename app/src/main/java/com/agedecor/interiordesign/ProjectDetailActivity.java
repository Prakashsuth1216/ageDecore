package com.agedecor.interiordesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.agedecor.interiordesign.data.AppDatabaseHelper;
import com.agedecor.interiordesign.data.ProjectModel;
import com.google.android.material.button.MaterialButton;

import java.net.URLEncoder;

public class ProjectDetailActivity extends AppCompatActivity {

    private ProjectModel project;
    private AppDatabaseHelper dbHelper;
    private ImageButton btnDetailFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        dbHelper = new AppDatabaseHelper(this);
        project = (ProjectModel) getIntent().getSerializableExtra("project_data");

        if (project == null) {
            Toast.makeText(this, "Project details not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
    }

    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnDetailFav = findViewById(R.id.btnDetailFav);

        TextView tvCategory = findViewById(R.id.tvDetailCategory);
        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvLocality = findViewById(R.id.tvDetailLocality);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvRate = findViewById(R.id.tvDetailRate);
        TextView tvCarpet = findViewById(R.id.tvDetailCarpet);
        TextView tvDuration = findViewById(R.id.tvDetailDuration);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);
        LinearLayout containerHighlights = findViewById(R.id.containerHighlights);

        MaterialButton btnQuickWhatsApp = findViewById(R.id.btnQuickWhatsApp);
        MaterialButton btnFillInquiryForm = findViewById(R.id.btnFillInquiryForm);

        // Populate fields
        tvCategory.setText(project.getCategory());
        tvTitle.setText(project.getTitle());
        tvLocality.setText(project.getMumbaiLocality());
        tvPrice.setText(project.getPriceRange());
        tvRate.setText("Standard Mumbai Execution Rate: " + project.getRatePerSqFt());
        tvCarpet.setText(project.getCarpetAreaSqFt() + " sq.ft");
        tvDuration.setText(project.getDurationDays() + " Working Days");
        tvDescription.setText(project.getDescription());

        // Populate highlights
        containerHighlights.removeAllViews();
        for (String highlight : project.getHighlights()) {
            TextView item = new TextView(this);
            item.setText("✦ " + highlight);
            item.setTextColor(getResources().getColor(R.color.gold_light, null));
            item.setTextSize(13);
            item.setPadding(0, 8, 0, 8);
            containerHighlights.addView(item);
        }

        // Back action
        btnBack.setOnClickListener(v -> finish());

        // Favorite status
        boolean isFav = dbHelper.isFavorite(project.getId());
        btnDetailFav.setAlpha(isFav ? 1.0f : 0.45f);

        btnDetailFav.setOnClickListener(v -> {
            boolean newState = dbHelper.toggleFavorite(project.getId());
            btnDetailFav.setAlpha(newState ? 1.0f : 0.45f);
            String msg = newState ? "Added to your private wishlist" : "Removed from wishlist";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        // WhatsApp direct action
        btnQuickWhatsApp.setOnClickListener(v -> {
            try {
                String msg = "Hello Age Décor Interior Design Mumbai,\n\nI am interested in your project: *" +
                        project.getTitle() + "* located in " + project.getMumbaiLocality() +
                        ".\nCarpet Area: " + project.getCarpetAreaSqFt() + " sq.ft\nEstimated Investment: " + project.getPriceRange() +
                        "\n\nPlease share further consultation details.";
                String encoded = URLEncoder.encode(msg, "UTF-8");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=918000737561&text=" + encoded));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Could not launch WhatsApp: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to requirement form
        btnFillInquiryForm.setOnClickListener(v -> {
            Intent intent = new Intent(ProjectDetailActivity.this, InquiryFormActivity.class);
            intent.putExtra("project_title", project.getTitle());
            intent.putExtra("project_locality", project.getMumbaiLocality());
            intent.putExtra("project_area", String.valueOf(project.getCarpetAreaSqFt()));
            startActivity(intent);
        });
    }
}
