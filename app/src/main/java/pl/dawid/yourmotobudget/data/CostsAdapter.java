package pl.dawid.yourmotobudget.data;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.R;

public class CostsAdapter extends RecyclerView.Adapter<CostsAdapter.CostsViewHolder> {

    private List<Costs> costsList;
    private Context context;
    private ContactDatabase database;

    public CostsAdapter(List<Costs> costsList, Context context) {

        this.costsList = costsList;
        this.context = context;
        this.database = ContactDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public CostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.costs_view, parent, false);
        return new CostsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CostsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Costs costs = costsList.get(position);

        holder.nameTextView.setText(costs.getNameTextView());
        holder.costsTextView.setText(String.format(Locale.getDefault(), "%.2f", costs.getCostsTextView()));
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Potwierdzenie")
                    .setMessage("Czy na pewno chcesz usunąć? Ta czynność jest nieodwracalna.")
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Usuwamy dane z bazy danych
                            new Thread(() -> {
                                // Zdobądź ID Costs
                                String nameTextView = costs.getNameTextView();
                                database.costsDao().deleteCostsById(nameTextView);

                                // Usuwamy element z listy użytkownika
                                costsList.remove(position);

                                // Aktualizujemy UI - korzystając z kontekstu
                                if (context instanceof AppCompatActivity) {  // Sprawdzamy, czy context to aktywność
                                    ((AppCompatActivity) context).runOnUiThread(() -> {
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, costsList.size());
                                        Toast.makeText(context, "Element został usunięty", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            }).start();
                        }
                    })
                    .setNegativeButton("Nie", null) // Jeśli użytkownik kliknie "Nie", nic się nie dzieje
                    .create()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return costsList.size();
    }

    public static class CostsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, costsTextView;
        Button deleteButton;

        public CostsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            costsTextView = itemView.findViewById(R.id.costsTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}