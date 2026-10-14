package com.agedecor.interiordesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.agedecor.interiordesign.InquiryFormActivity;
import com.agedecor.interiordesign.ProjectDetailActivity;
import com.agedecor.interiordesign.R;
import com.agedecor.interiordesign.data.AppDatabaseHelper;
import com.agedecor.interiordesign.data.ProjectModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private final Context context;
    private List<ProjectModel> projectList;
    private final AppDatabaseHelper dbHelper;

    public ProjectAdapter(Context context, List<ProjectModel> projectList) {
        this.context = context;
        this.projectList = projectList != null ? projectList : new ArrayList<>();
        this.dbHelper = new AppDatabaseHelper(context);
    }

    public void updateList(List<ProjectModel> newList) {
        this.projectList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_card, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        ProjectModel project = projectList.get(position);

        holder.tvTitle.setText(project.getTitle());
        holder.tvCategory.setText(project.getCategory());
        holder.tvLocation.setText(project.getMumbaiLocality());
        holder.tvDesignStyle.setText(project.getDesignStyle());
        holder.tvSnippet.setText(project.getDescription());
        holder.tvCarpetArea.setText(project.getCarpetAreaSqFt() + " sq.ft");
        holder.tvDuration.setText(project.getDurationDays() + " Days");
        holder.tvPriceEstimate.setText(project.getPriceRange());
        holder.tvRatePerSqFt.setText("(" + project.getRatePerSqFt() + ")");

        boolean isFav = dbHelper.isFavorite(project.getId());
        holder.btnLike.setAlpha(isFav ? 1.0f : 0.45f);

        holder.btnLike.setOnClickListener(v -> {
            boolean newState = dbHelper.toggleFavorite(project.getId());
            holder.btnLike.setAlpha(newState ? 1.0f : 0.45f);
            String msg = newState ? "Added to your private saved list" : "Removed from saved list";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        });

        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProjectDetailActivity.class);
            intent.putExtra("project_data", project);
            context.startActivity(intent);
        });

        holder.btnBookConsultation.setOnClickListener(v -> {
            Intent intent = new Intent(context, InquiryFormActivity.class);
            intent.putExtra("project_title", project.getTitle());
            intent.putExtra("project_locality", project.getMumbaiLocality());
            intent.putExtra("project_area", String.valueOf(project.getCarpetAreaSqFt()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvCategory, tvLocation, tvDesignStyle, tvSnippet, tvCarpetArea, tvDuration, tvPriceEstimate, tvRatePerSqFt;
        ImageButton btnLike;
        MaterialButton btnViewDetails, btnBookConsultation;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvProjectTitle);
            tvCategory = itemView.findViewById(R.id.tvCategoryBadge);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDesignStyle = itemView.findViewById(R.id.tvDesignStyle);
            tvSnippet = itemView.findViewById(R.id.tvProjectSnippet);
            tvCarpetArea = itemView.findViewById(R.id.tvCarpetArea);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvPriceEstimate = itemView.findViewById(R.id.tvPriceEstimate);
            tvRatePerSqFt = itemView.findViewById(R.id.tvRatePerSqFt);
            btnLike = itemView.findViewById(R.id.btnLikeProject);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btnBookConsultation = itemView.findViewById(R.id.btnBookConsultation);
        }
    }
}
