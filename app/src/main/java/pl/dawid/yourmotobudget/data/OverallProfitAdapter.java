package pl.dawid.yourmotobudget.data;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.R;

public class OverallProfitAdapter extends RecyclerView.Adapter<OverallProfitAdapter.OverallProfitHolder> {

    private final List<OverallProfit> overallProfitList;

    private String currentMonth = getCurrentMonth();

    private String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag("en-EN"));
        return sdf.format(new Date());
    }

    public OverallProfitAdapter(List<OverallProfit> overallProfitList) {

        this.overallProfitList = overallProfitList;
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

        OverallProfit data = overallProfitList.get(position);

        double costs = data.getCostsTextView() != null ? data.getCostsTextView() : 0.0;
        double salary = data.getSalaryTextView() != null ? data.getSalaryTextView() : 0.0;
        double bonus = data.getBonusTextView() != null ? data.getBonusTextView() : 0.0;
        double supplement = data.getSupplementTextView() != null ? data.getSupplementTextView() : 0.0;
        double priceItem = data.getPriceItem() != null ? data.getPriceItem() : 0.0;
        double priceHour = data.getPriceHour() != null ? data.getPriceHour() : 0.0;

        holder.month.setText(currentMonth);

        holder.expensesTextView.setText(String.format(Locale.getDefault(), "%.2f",
                costs +
                        salary +
                        bonus +
                        supplement +
                        priceItem));
        holder.profitTextView.setText(String.format(Locale.getDefault(), "%.2f",
                priceHour +
                        (priceItem * 0.2)));
        holder.togetherTextView.setText(String.format(Locale.getDefault(), "%.2f",
                (priceHour +
                        (priceItem * 0.2)) -
                        (costs +
                                priceItem +
                                salary +
                                bonus +
                                supplement)));
    }

    @Override
    public int getItemCount(){
        return overallProfitList != null ? overallProfitList.size() : 0;
    }

    public static class OverallProfitHolder extends RecyclerView.ViewHolder {
        TextView expensesTextView, profitTextView, togetherTextView, month;

        public OverallProfitHolder(@NonNull View itemView) {
            super(itemView);
            expensesTextView = itemView.findViewById(R.id.expensesTextView);
            profitTextView = itemView.findViewById(R.id.profitTextView);
            togetherTextView = itemView.findViewById(R.id.togetherTextView);
            month = itemView.findViewById(R.id.month);
        }
    }
}