package com.agedecor.interiordesign.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.agedecor.interiordesign.R;
import com.agedecor.interiordesign.data.AppDatabaseHelper;
import com.agedecor.interiordesign.data.InquiryModel;
import com.google.android.material.button.MaterialButton;

import java.net.URLEncoder;
import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.InquiryViewHolder> {

    private final Context context;
    private List<InquiryModel> inquiryList;
    private final AppDatabaseHelper dbHelper;
    private final Runnable onDeleteCallback;

    public InquiryAdapter(Context context, List<InquiryModel> inquiryList, Runnable onDeleteCallback) {
        this.context = context;
        this.inquiryList = inquiryList;
        this.dbHelper = new AppDatabaseHelper(context);
        this.onDeleteCallback = onDeleteCallback;
    }

    public void updateList(List<InquiryModel> list) {
        this.inquiryList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inquiry_card, parent, false);
        return new InquiryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryViewHolder holder, int position) {
        InquiryModel inq = inquiryList.get(position);

        holder.tvTitle.setText(inq.getProjectTitle());
        holder.tvClient.setText(inq.getClientName() + " • " + inq.getPhone() + " (" + inq.getMumbaiLocality() + ")");
        holder.tvScope.setText("Config: " + inq.getPropertyType() + " • " + inq.getCarpetArea() + " sq.ft • Budget: " + inq.getBudgetRange());
        holder.tvNotes.setText("Special Needs: " + inq.getSpecialRequirements());
        holder.tvTimestamp.setText("Saved on Device: " + inq.getTimestamp());

        // Full User Control: Delete individual inquiry
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Inquiry Data?")
                    .setMessage("This will permanently remove this consultation record from your device. You have 100% control over this data.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbHelper.deleteInquiry(inq.getId());
                        Toast.makeText(context, "Inquiry deleted successfully", Toast.LENGTH_SHORT).show();
                        if (onDeleteCallback != null) {
                            onDeleteCallback.run();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // WhatsApp direct action
        holder.btnWhatsApp.setOnClickListener(v -> {
            try {
                String msg = "Hello Age Décor Interior Design Mumbai Team,\n\n" +
                        "I had submitted an interior design inquiry:\n" +
                        "• Project Interest: " + inq.getProjectTitle() + "\n" +
                        "• Name: " + inq.getClientName() + "\n" +
                        "• Locality: " + inq.getMumbaiLocality() + "\n" +
                        "• Configuration: " + inq.getPropertyType() + " (" + inq.getCarpetArea() + " sq.ft)\n" +
                        "• Budget: " + inq.getBudgetRange() + "\n" +
                        "• Specific Needs: " + inq.getSpecialRequirements();

                String encodedMsg = URLEncoder.encode(msg, "UTF-8");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=918000737561&text=" + encodedMsg));
                context.startActivity(sendIntent);
            } catch (Exception e) {
                Toast.makeText(context, "Could not launch WhatsApp: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    static class InquiryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvClient, tvScope, tvNotes, tvTimestamp;
        ImageButton btnDelete;
        MaterialButton btnWhatsApp;

        public InquiryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvInquiryProjectTitle);
            tvClient = itemView.findViewById(R.id.tvInquiryClientDetails);
            tvScope = itemView.findViewById(R.id.tvInquiryScope);
            tvNotes = itemView.findViewById(R.id.tvInquiryNotes);
            tvTimestamp = itemView.findViewById(R.id.tvInquiryTimestamp);
            btnDelete = itemView.findViewById(R.id.btnDeleteInquiry);
            btnWhatsApp = itemView.findViewById(R.id.btnSendWhatsAppDirect);
        }
    }
}
