package pl.dawid.yourmotobudget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FieldsViewModel extends ViewModel {
    private MutableLiveData<List<String>> fields = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getFields() {
        return fields;
    }

    public void addField(String field) {
        List<String> currentFields = fields.getValue();
        currentFields.add(field);
        fields.setValue(currentFields);
    }
}
