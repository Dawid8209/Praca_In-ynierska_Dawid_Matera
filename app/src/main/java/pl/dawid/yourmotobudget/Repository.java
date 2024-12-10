package pl.dawid.yourmotobudget;

import android.content.Context;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import pl.dawid.yourmotobudget.data.*;

public class Repository {
    private final UserDao userDao;

    public Repository(Context context) {
        this.userDao = ContactDatabase.getInstance(context).userDao();
    }

    public CompletableFuture<Void> insertUser(User user) {
        return CompletableFuture.runAsync(() -> userDao.insert(user));
    }

    public CompletableFuture<Void> updateUser(User user) {
        return CompletableFuture.runAsync(() -> userDao.update(user));
    }

    public CompletableFuture<Void> deleteUser(User user) {
        return CompletableFuture.runAsync(() -> userDao.delete(user));
    }

    public CompletableFuture<List<User>> getAllUsers() {
        return CompletableFuture.supplyAsync(() -> userDao.getAllUsers());
    }

    public CompletableFuture<User> getUserByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> userDao.getUserByEmail(email));
    }
}
