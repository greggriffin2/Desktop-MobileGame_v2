package com.example.sccopilotapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(
                "Created By..." + "\n" +
                        "Z Noah Hounshel" + "\n"
                        + "Greg Griffin" + "\n"
                        + "Reed Farrow" + "\n"
                        + "Kole Bostic" + "\n"
                        + "Tom Sanford" + "\n"
        );
    }

    public LiveData<String> getText() {
        return mText;
    }
}
