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

public class UserSalaryAdapter extends RecyclerView.Adapter<UserSalaryAdapter.UserSalaryViewHolder> {

    private List<UserSalary> userSalaryList;
    private Context context;
    private ContactDatabase database;

    public UserSalaryAdapter(List<UserSalary> userSalaryList, Context context) {

        this.userSalaryList = userSalaryList;
        this.context = context;
        this.database = ContactDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public UserSalaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salary_view, parent, false);
        return new UserSalaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSalaryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserSalary userSalary = userSalaryList.get(position);

        holder.nameTextView.setText(userSalary.getNameTextView());
        holder.salaryTextView.setText(String.format(Locale.getDefault(), "%.2f", userSalary.getSalaryTextView()));
        holder.bonusTextView.setText(String.format(Locale.getDefault(), "%.2f", userSalary.getBonusTextView()));
        holder.supplementTextView.setText(String.format(Locale.getDefault(), "%.2f", userSalary.getSupplementTextView()));
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Potwierdzenie")
                    .setMessage("Czy na pewno chcesz usunąć? Ta czynność jest nieodwracalna.")
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Usuwamy dane z bazy danych
                            new Thread(() -> {
                                // Zdobądź ID UserData (np. po VIN, id, czy innym unikalnym identyfikatorze)
                                String userNameTextView = userSalary.getNameTextView(); // Zakładając, że VIN jest unikalne
                                database.userSalaryDao().deleteUserSalaryById(userNameTextView);

                                // Usuwamy element z listy użytkownika
                                userSalaryList.remove(position);

                                // Aktualizujemy UI - korzystając z kontekstu
                                if (context instanceof AppCompatActivity) {  // Sprawdzamy, czy context to aktywność
                                    ((AppCompatActivity) context).runOnUiThread(() -> {
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, userSalaryList.size());
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
        return userSalaryList.size();
    }

    public static class UserSalaryViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, salaryTextView, bonusTextView, supplementTextView;
        Button deleteButton;

        public UserSalaryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
            bonusTextView = itemView.findViewById(R.id.bonusTextView);
            supplementTextView = itemView.findViewById(R.id.supplementTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
