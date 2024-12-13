package pl.dawid.yourmotobudget.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.dawid.yourmotobudget.R;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.UserDataViewHolder> {

    private List<UserData> userDataList;

    public UserDataAdapter(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public UserDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_view, parent, false);
        return new UserDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDataViewHolder holder, int position) {
        UserData userData = userDataList.get(position);
        holder.nameTextView.setText(userData.getName());
        holder.plateTextView.setText(userData.getPlate());
        holder.vinTextView.setText(userData.getVin());
        holder.taskTextView.setText(userData.getTask());
        holder.buyItemTextView.setText(userData.getBuyItem());
        holder.priceItemTextView.setText(userData.getPriceItem());
        holder.priceHourTextView.setText(userData.getPriceHour());
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static class UserDataViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, plateTextView, vinTextView, taskTextView, buyItemTextView, priceItemTextView, priceHourTextView;

        public UserDataViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            plateTextView = itemView.findViewById(R.id.plateTextView);
            vinTextView = itemView.findViewById(R.id.vinTextView);
            taskTextView = itemView.findViewById(R.id.taskTextView);
            buyItemTextView = itemView.findViewById(R.id.buyItemTextView);
            priceItemTextView = itemView.findViewById(R.id.priceItemTextView);
            priceHourTextView = itemView.findViewById(R.id.priceHourTextView);
        }
    }
}
