package pl.dawid.yourmotobudget.data;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;
import java.util.Locale;

import pl.dawid.yourmotobudget.R;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder> {

    private List<UserData> userDataList;
    private Context context;
    private ContactDatabase database;

    public UserDataAdapter(List<UserData> userDataList, Context context) {

        this.userDataList = userDataList;
        this.context = context;
        this.database = ContactDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public UserDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_view, parent, false);
        return new UserDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDataViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserData userData = userDataList.get(position);

        holder.nameTextView.setText(userData.getName());
        holder.plateTextView.setText(userData.getPlate());
        holder.vinTextView.setText(userData.getVin());
        holder.taskTextView.setText(userData.getTask());
        holder.buyItemTextView.setText(userData.getBuyItem());
        holder.priceItemTextView.setText(String.format(Locale.getDefault(), "%.2f", userData.getPriceItem()));
        holder.priceHourTextView.setText(String.format(Locale.getDefault(), "%.2f", userData.getPriceHour()));
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Potwierdzenie")
                    .setMessage("Czy na pewno chcesz usunąć? Ta czynność jest nieodwracalna.")
                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Thread(() -> {
                                String userName = userData.getName();
                                database.userDataDao().deleteUserDataById(userName);

                                userDataList.remove(position);

                                if (context instanceof AppCompatActivity) {
                                    ((AppCompatActivity) context).runOnUiThread(() -> {
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, userDataList.size());
                                        Toast.makeText(context, "Element został usunięty", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            }).start();
                        }
                    })
                    .setNegativeButton("Nie", null)
                    .create()
                    .show();
        });

        if (userData.getImagePath() != null && !userData.getImagePath().isEmpty()) {
            File imgFile = new File(userData.getImagePath());
            if(imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imageView.setImageBitmap(bitmap);
            }else {
                Log.e("ImageLoader", "Plik nie istnieje: " + userData.getImagePath());
                holder.imageView.setImageResource(R.drawable.burning_wheel);
            }
        } else {
            Log.e("ImageLoader", "Ścieżka obrazu jest pusta lub null!");
            holder.imageView.setImageResource(R.drawable.burning_wheel);
        }
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static class UserDataViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView nameTextView, plateTextView, vinTextView, taskTextView, buyItemTextView, priceItemTextView, priceHourTextView;
        Button deleteButton;

        public UserDataViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            plateTextView = itemView.findViewById(R.id.plateTextView);
            vinTextView = itemView.findViewById(R.id.vinTextView);
            taskTextView = itemView.findViewById(R.id.taskTextView);
            buyItemTextView = itemView.findViewById(R.id.buyItemTextView);
            priceItemTextView = itemView.findViewById(R.id.priceItemTextView);
            priceHourTextView = itemView.findViewById(R.id.priceHourTextView);
            imageView = itemView.findViewById(R.id.imageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
