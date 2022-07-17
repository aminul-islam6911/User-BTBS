package com.example.userbtbs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Ticekets_booked extends AppCompatActivity {
    ArrayList<String> Tickets = new ArrayList<>();
    private ListView myListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticekets_booked);
    }
}