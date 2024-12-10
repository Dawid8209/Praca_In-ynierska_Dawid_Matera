package pl.dawid.yourmotobudget;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import pl.dawid.yourmotobudget.data.User;

public class UserViewModel extends AndroidViewModel {
    private final Repository repository;

    public UserViewModel(Application application) {
        super(application);
        repository = new Repository(application);
    }

    public CompletableFuture<Void> insertUser(User user) {
        return repository.insertUser(user);
    }

    public CompletableFuture<Void> updateUser(User user) {
        return repository.updateUser(user);
    }

    public CompletableFuture<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }
}
