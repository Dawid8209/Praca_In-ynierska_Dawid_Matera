package pl.dawid.yourmotobudget.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.dawid.yourmotobudget.R;

public class OverallProfitAdapter extends RecyclerView.Adapter<OverallProfitAdapter.OverallProfitHolder> {

    private List<Costs> costsList;
    private List<UserData> userDataList;
    private List<UserSalary> userSalaryList;
    private Context context;
    private ContactDatabase database;
    private List<OverallProfit> overallProfitList = new ArrayList<>();

    /*/
    public OverallProfitAdapter(List<Costs> costsList, Context context) {

        this.costsList = costsList;
        this.userSalaryList = userSalaryList;
        this.userDataList = userDataList;
        this.database = ContactDatabase.getInstance(context);
    }/*/
    public OverallProfitAdapter(List<OverallProfit> overallProfitList, Context context) {
        if (overallProfitList != null) {
            this.overallProfitList = overallProfitList;
            this.context = context;
            this.database = ContactDatabase.getInstance(context);
        }
    }

    @NonNull
    @Override
    public OverallProfitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profit_view, parent, false);
        return new OverallProfitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OverallProfitHolder holder, @SuppressLint("RecyclerView") int position) {

        Costs costs = costsList.get(position);
        UserData userData = userDataList.get(position);
        UserSalary userSalary = userSalaryList.get(position);
        OverallProfit data = overallProfitList.get(position);

        holder.expensesTextView.setText(String.valueOf((data.getCostsTextView() != null ? data.getCostsTextView() : 0) +
                (data.getPriceItem() != null ? data.getPriceItem() : 0) +
                (data.getSalaryTextView() != null ? data.getSalaryTextView() : 0) +
                (data.getBonusTextView() != null ? data.getBonusTextView() : 0) +
                (data.getSupplementTextView() != null ? data.getSupplementTextView() : 0)));
        holder.profitTextView.setText(String.valueOf( (data.getPriceHour() != null ? data.getPriceHour() : 0) +
                ((data.getPriceItem() != null ? data.getPriceItem() : 0) *
                        ((data.getPriceItem() != null ? data.getPriceItem() : 0) / 5))));
        holder.togetherTextView.setText(String.valueOf((data.getCostsTextView() != null ? data.getCostsTextView() : 0) +
                (data.getPriceItem() != null ? data.getPriceItem() : 0) +
                (data.getSalaryTextView() != null ? data.getSalaryTextView() : 0) +
                (data.getBonusTextView() != null ? data.getBonusTextView() : 0) +
                (data.getSupplementTextView() != null ? data.getSupplementTextView() : 0) -
                (data.getPriceHour() != null ? data.getPriceHour() : 0) -
                ((data.getPriceItem() != null ? data.getPriceItem() : 0) *
                        ((data.getPriceItem() != null ? data.getPriceItem() : 0) / 5))));
    }

    @Override
    public int getItemCount(){
        return overallProfitList != null ? overallProfitList.size() : 0;
    }

    public static class OverallProfitHolder extends RecyclerView.ViewHolder {
        TextView expensesTextView, profitTextView, togetherTextView;

        public OverallProfitHolder(@NonNull View itemView) {
            super(itemView);
            expensesTextView = itemView.findViewById(R.id.expensesTextView);
            profitTextView = itemView.findViewById(R.id.profitTextView);
            togetherTextView = itemView.findViewById(R.id.togetherTextView);
        }
    }
}