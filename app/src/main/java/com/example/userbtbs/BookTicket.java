package com.example.userbtbs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookTicket extends AppCompatActivity {
    private TextView tvBusNo, tvDate, tvStarting, tvDestination, tvStartingTime, tvArrivalTime,
            tvSeatAvailable, tvBusType;
    private Button btnConfirmBooking, btnAddTraveller;
    private String stBusNo, stDate, stStarting, stDestination, stStartingTime, stArrivalTime,
            stSeatAvailable, stBusType, stTicketPrice;
    private FirebaseUser firebaseUser;
    private DatabaseReference seatsNo, confirmTicket;
    private String seatNoRef;
    int noOfSeats;
    EditText edtTraveller;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        Initialize();
        GetStringFromIntent();
        ProgressDialog configure = new ProgressDialog(this);
        configure.setCancelable(false);
        configure.setTitle("Configuring Environment");
        configure.show();
        SetStringToTextView(configure);

        //Arraylist to call data
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(BookTicket.this, android.R.layout
                .simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        onButtonClickAdd();

        btnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!arrayList.isEmpty()) {
                    GetNoOfPSeat();
                    BookTickets();
                } else {
                    Toast.makeText(BookTicket.this, "Please select some places", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private String getData(){
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

    private void Initialize() {
        tvBusNo = findViewById(R.id.tvBusNo);
        tvDate = findViewById(R.id.tvDate);
        tvStarting = findViewById(R.id.tvStarting);
        tvDestination = findViewById(R.id.tvDestination);
        tvStartingTime = findViewById(R.id.tvStartingTime);
        tvArrivalTime = findViewById(R.id.tvArrivalTime);
        tvSeatAvailable = findViewById(R.id.tvSeatAvailable);
        tvBusType = findViewById(R.id.tvBusType);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        btnAddTraveller = findViewById(R.id.btnAddTraveller);
        edtTraveller = findViewById(R.id.edtTraveller);
        listView = findViewById(R.id.lvTraveller);
    }

    private void GetStringFromIntent() {
        stBusNo = getIntent().getStringExtra("BusNo");
        stDate = tvDate.getText().toString();
        stStarting = getIntent().getStringExtra("Starting");
        stDestination = getIntent().getStringExtra("Destination");
        stStartingTime = getIntent().getStringExtra("StartingTime");
        stArrivalTime = getIntent().getStringExtra("ArrivalTime");
        stSeatAvailable = getIntent().getStringExtra("NumberOfSeat");
        stBusType = getIntent().getStringExtra("BusType");
        stTicketPrice = getIntent().getStringExtra("Price");
        seatNoRef = "Runs Everyday " + stStarting + " " + stDestination;
        seatsNo = FirebaseDatabase.getInstance().getReference().child("Buses")
                .child(seatNoRef).child(stBusNo);
    }

    private void SetStringToTextView(ProgressDialog configure) {
        tvBusNo.setText("Bus No :" + stBusNo);
        tvDate.setText(getData());
        tvStarting.setText("From :" + stStarting);
        tvDestination.setText("To :" + stDestination);
        tvStartingTime.setText("Starting Time :" + stStartingTime);
        tvArrivalTime.setText("Arrival Time :" + stArrivalTime);
        tvSeatAvailable.setText("Seat Available :" + stSeatAvailable);
        tvBusType.setText("Bus Type :" + stBusType);
        btnConfirmBooking.setText("Confirm(" + stTicketPrice + " Tk)");
        Toast.makeText(BookTicket.this, stTicketPrice, Toast.LENGTH_LONG).show();
        configure.dismiss();
    }

    public void onButtonClickAdd() {
        btnAddTraveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = edtTraveller.getText().toString();
                arrayList.add(result);
                adapter.notifyDataSetChanged();
                edtTraveller.setText("");
                calculate_price();
            }
        });
    }

    private void calculate_price() {
        int number_of_traveller = arrayList.size();
        int Price = Integer.parseInt(stTicketPrice);
        int total_price = Price * number_of_traveller;
        String str_total_price = Integer.toString(total_price);
        btnConfirmBooking.setText("Confirm(" + str_total_price + " Tk)");
    }

    private void GetNoOfPSeat() {
        noOfSeats = Integer.parseInt(stSeatAvailable);
        if (noOfSeats <= 0) {
            Toast.makeText(BookTicket.this, "Sorry seats are full", Toast.LENGTH_LONG).show();
        } else {
            int new_val = Integer.parseInt(stSeatAvailable) - arrayList.size();
            String st = Integer.toString(new_val);
            Toast.makeText(BookTicket.this, st, Toast.LENGTH_LONG).show();
            Map<String, Object> upSeats = new HashMap<>();
            upSeats.put("NumberOfSeat", st);
            seatsNo.updateChildren(upSeats).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent in = new Intent(BookTicket.this, Ticekets_booked.class);
                        startActivity(in);
                    }
                }
            });
        }
    }

    private void BookTickets() {

        if (noOfSeats <= 0) {
        } else {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            String User = firebaseUser.getUid();
            confirmTicket = FirebaseDatabase.getInstance().getReference().
                    child("Tickets").child(stDate).child(stBusNo).child(User);

            DatabaseReference TicketID = FirebaseDatabase.getInstance().getReference().
                    child("Tickets").child("TicketID").child(stDate);

            DatabaseReference Ticket_Check_User_Admin = FirebaseDatabase.getInstance().
                    getReference().child("Tickets").child("Ticket_Check_User_Admin")
                    .child("On " + stDate + " by bus no " + stBusNo);

            DatabaseReference Ticket_User_Search = FirebaseDatabase.getInstance().
                    getReference().child("Tickets").child("Tickets_User_Search").child(User)
                    .child("On " + stDate + " by bus no " + stBusNo);

            DatabaseReference Ticket_HashCode = FirebaseDatabase.getInstance().getReference()
                    .child("Tickets").child("Ticket_HashCode").child(User);

            //Here is the code to store the ticket in TicketID node
            HashMap<String, Object> H_TicketID = new HashMap<>();
            H_TicketID.put(stDate + stBusNo, "On " + stDate + " by bus no " + stBusNo);
            TicketID.updateChildren(H_TicketID);

            //Here is the code to store Ticket_Check_User_Admin
            int Length = arrayList.size();//Data stored in list
            HashMap<String, Object> H_Ticket_Check_User_Admin = new HashMap<>();//Data stored in list are accessed one by one
            for (int a = 0; a < Length; a++) {
                H_Ticket_Check_User_Admin.put(User + "_Traveller_" + a, arrayList.get(a));
            }
            Ticket_Check_User_Admin.updateChildren(H_Ticket_Check_User_Admin);

            //Here is the code to store Ticket_User_Search node it will have same hashmap as Ticket_Check_User_Admin
            Ticket_User_Search.updateChildren(H_Ticket_Check_User_Admin);

            //Ticket_HashCode is stored
            HashMap<String, Object> H_Ticket_HashCode = new HashMap<>();
            H_Ticket_HashCode.put(stDate + stBusNo, "On " + stDate + " by bus no " + stBusNo);
            Ticket_HashCode.updateChildren(H_Ticket_HashCode);
        }
    }
}