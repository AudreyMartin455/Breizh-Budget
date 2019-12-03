package com.example.breizhbudget.ui.budgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.breizhbudget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BudgetsAdapter extends RecyclerView.Adapter<BudgetsAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Budget> budgetList;
    private List<Budget> budgetListFiltered;
    private BudgetsAdapterListener listener;

    @BindView(R.id.nom_budget)
    public TextView nom;
    @BindView(R.id.montant_budget)
    public TextView montant;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, montant;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(budgetListFiltered.get(getAdapterPosition()));
                }
            });

        }
    }


    public BudgetsAdapter(Context context, List<Budget> contactList, BudgetsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.budgetList = contactList;
        this.budgetListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Budget budget = budgetListFiltered.get(position);
        holder.name.setText(budget.getName());
        holder.montant.setText(budget.getMontant());

    }

    @Override
    public int getItemCount() {
        return budgetListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    budgetListFiltered = budgetList;
                } else {
                    List<Budget> filteredList = new ArrayList<>();
                    for (Budget row : budgetList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                       /* if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }*/
                    }

                    budgetListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = budgetListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                budgetListFiltered = (ArrayList<Budget>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface BudgetsAdapterListener {
        void onContactSelected(Budget budget);
    }
}