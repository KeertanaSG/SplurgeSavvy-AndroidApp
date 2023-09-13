package com.example.splurgesavvy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splurgesavvy.R;
import com.example.splurgesavvy.entities.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetHolder> {

    private List<Budget> budgets = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public BudgetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_card_view, parent, false);
        return new BudgetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetHolder holder, int position) {
        Budget currentBudget = budgets.get(position);
        holder.budgetCategoryText.setText(currentBudget.getName());
        holder.amountRemaining.setText(String.valueOf(currentBudget.getValue()));
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Budget budget);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class BudgetHolder extends RecyclerView.ViewHolder {
        private TextView budgetCategoryText;
        private TextView amountRemaining;

        public BudgetHolder(@NonNull View itemView) {
            super(itemView);
            budgetCategoryText = itemView.findViewById(R.id.budget_category_text);
            amountRemaining = itemView.findViewById(R.id.amount_remaining);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(budgets.get(position));
                }
            });
        }
    }
}

