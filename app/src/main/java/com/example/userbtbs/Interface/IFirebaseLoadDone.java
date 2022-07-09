package com.example.userbtbs.Interface;

import com.example.userbtbs.Model.IDs;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<IDs> Locationlist);
    void onFirebaseLoadFailed(String Message);

}
