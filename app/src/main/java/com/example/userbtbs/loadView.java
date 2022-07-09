package com.example.userbtbs;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class loadView extends RecyclerView.ViewHolder {
    View mView;
    TextView textDate, textTimeStart, textTimeEnd, textLocationPinStart, textLocationPinEnd, textBusno, textSeatType, ticketPrice;
    CardView blCard;

    public loadView(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        textDate = mView.findViewById(R.id.date);
        textTimeStart = mView.findViewById(R.id.startingTime);
        textTimeEnd = mView.findViewById(R.id.arrivalTime);
        textLocationPinStart = mView.findViewById(R.id.starting);
        textLocationPinEnd = mView.findViewById(R.id.destination);
        textBusno = mView.findViewById(R.id.busNo);
        textSeatType = mView.findViewById(R.id.busType);
        blCard = mView.findViewById(R.id.cardView);
        ticketPrice = mView.findViewById(R.id.ticketPrice);
    }


    public void settextDate(String Textdate) {
        textDate.setText("Date: " + Textdate);
    }

    public void settextTimeStart(String TextTimeStart) {
        textTimeStart.setText("Starting at : " + TextTimeStart);
    }

    public void setTextTimeEnd(String TextTimeEnd) {
        textTimeEnd.setText("Arriving at : " + TextTimeEnd);
    }

    public void settextLocationStart(String TextLocationPinStart) {
        textLocationPinStart.setText("From : " + TextLocationPinStart);
    }

    public void settextLocationEnd(String TextLocationPinEnd) {
        textLocationPinEnd.setText("To : " + TextLocationPinEnd);
    }

    public void settextBusno(String TextBusno) {
        textBusno.setText("Bus no: " + TextBusno);
    }

    public void settextSeatType(String TextSeatType) {
        textSeatType.setText("Bus Type: " + TextSeatType);
    }

    public void settextPrice(String TextPrice) {
        ticketPrice.setText(TextPrice + " Rs");
    }

}
