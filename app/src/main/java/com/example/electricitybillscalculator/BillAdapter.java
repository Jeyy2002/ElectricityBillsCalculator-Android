package com.example.electricitybillscalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private final Context context;
    private final List<BillRecord> billList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BillRecord billRecord);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BillAdapter(Context context, List<BillRecord> billList) {
        this.context = context;
        this.billList = billList;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_record, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        // Get the BillRecord object for the current position
        BillRecord currentBill = billList.get(position);

        // Format final cost to 2 decimal places
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedFinalCost = "RM " + df.format(currentBill.getFinalCost());

        // Set data to TextViews
        holder.tvMonth.setText("Month: " + currentBill.getMonth());
        holder.tvFinalCost.setText(formattedFinalCost);
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the list
        return billList.size();
    }

    // ViewHolder class to hold the views for each list item
    public class BillViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonth;
        TextView tvFinalCost;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvListItemMonth);
            tvFinalCost = itemView.findViewById(R.id.tvListItemFinalCost);

            // Set OnClickListener for the whole item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if listener exists and position is valid
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        // Trigger the onItemClick method with the clicked BillRecord
                        listener.onItemClick(billList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}