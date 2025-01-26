package pl.dawid.yourmotobudget.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.concurrent.CompletableFuture;

import pl.dawid.yourmotobudget.Repository;

public class UserViewModel extends AndroidViewModel {
    private final Repository repository;

    public UserViewModel(Application application) {
        super(application);
        repository = new Repository(application);
    }
    public CompletableFuture<Void> insertUser(User user) {
        return repository.insertUser(user);
    }
}
