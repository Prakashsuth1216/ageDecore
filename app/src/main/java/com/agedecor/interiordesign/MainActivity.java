package com.agedecor.interiordesign;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.agedecor.interiordesign.adapter.ProjectAdapter;
import com.agedecor.interiordesign.data.ProjectModel;
import com.agedecor.interiordesign.data.ProjectRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvProjects;
    private ProjectAdapter adapter;
    private List<ProjectModel> allProjects;
    private ChipGroup chipGroupCategories;
    private TextView tvProjectCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allProjects = ProjectRepository.getAllProjects();

        initViews();
        setupCategoryChips();
        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        rvProjects = findViewById(R.id.rvProjects);
        chipGroupCategories = findViewById(R.id.chipGroupCategories);
        tvProjectCount = findViewById(R.id.tvProjectCount);

        MaterialButton btnUserDataHub = findViewById(R.id.btnUserDataHub);
        MaterialButton btnOpenEstimator = findViewById(R.id.btnOpenEstimator);
        ExtendedFloatingActionButton fabQuickInquiry = findViewById(R.id.fabQuickInquiry);

        // User Data Sovereignty & Privacy Hub
        btnUserDataHub.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserDataControlActivity.class);
            startActivity(intent);
        });

        // Instant Cost Estimator
        btnOpenEstimator.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CostEstimatorActivity.class);
            startActivity(intent);
        });

        // Quick Consultation Form
        fabQuickInquiry.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InquiryFormActivity.class);
            intent.putExtra("project_title", "General Luxury Consultation (Custom)");
            startActivity(intent);
        });
    }

    private void setupCategoryChips() {
        List<String> categories = ProjectRepository.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            String cat = categories.get(i);
            Chip chip = new Chip(this);
            chip.setText(cat);
            chip.setCheckable(true);
            chip.setChipBackgroundColorResource(R.color.bg_card);
            chip.setTextColor(getResources().getColor(R.color.text_white, null));
            chip.setChipStrokeColorResource(R.color.surface_border);
            chip.setChipStrokeWidth(2f);

            if (i == 0) {
                chip.setChecked(true);
            }

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    filterProjects(cat);
                }
            });

            chipGroupCategories.addView(chip);
        }
    }

    private void filterProjects(String category) {
        if ("All".equalsIgnoreCase(category)) {
            adapter.updateList(allProjects);
            tvProjectCount.setText("Signature Projects (" + allProjects.size() + ")");
        } else {
            List<ProjectModel> filtered = new ArrayList<>();
            for (ProjectModel p : allProjects) {
                if (p.getCategory().equalsIgnoreCase(category)) {
                    filtered.add(p);
                }
            }
            adapter.updateList(filtered);
            tvProjectCount.setText("Signature Projects (" + filtered.size() + ")");
        }
    }

    private void setupRecyclerView() {
        adapter = new ProjectAdapter(this, allProjects);
        rvProjects.setLayoutManager(new LinearLayoutManager(this));
        rvProjects.setAdapter(adapter);
    }
}
