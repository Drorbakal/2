package com.example.haircutbooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private List<String> hoursList;
    private OnHourClickListener listener;
    private String selectedDate;
    private String todayDateString;
    private String currentTime;
    private boolean isFriday;
    private String selectedBarber;
    private List<String> barberList;


    public HourAdapter(List<String> barberList, boolean isFriday, String selectedBarber,String selectedDate,
                       String todayDateString, String currentTime, OnHourClickListener listener) {
        this.barberList = barberList; // Assign the passed barberList
        this.isFriday = isFriday;
        this.selectedBarber = selectedBarber;
        this.selectedDate = selectedDate;
        this.todayDateString = todayDateString; // Store today's date string
        this.currentTime = currentTime; // Store current time
        this.hoursList = generateHoursList();
        this.listener = listener;
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour, parent, false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        String hour = hoursList.get(position);
        holder.bind(hour);
        boolean isBooked = isHourBooked(hour);
        if (isBooked) {
            holder.buttonHour.setBackgroundResource(R.drawable.onclickable); // For example, set a different background color
            holder.buttonHour.setEnabled(false); // Disable the button if booked
        } else {
            holder.buttonHour.setBackgroundResource(R.drawable.clickable); // For example, set a different background color
            holder.buttonHour.setEnabled(true); // Enable the button if available
        }
        holder.buttonHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onHourClick(hour);
                }
            }
        });
    }

    private boolean isHourBooked(String hour) {
        for (String booking : barberList) {
            if (booking.equals(selectedDate + ": " + hour)) { // Assuming booking format is "selectedDate: selectedHour"
                return true; // If a match is found, return true
            }
        }
        return false; // If no match is found, return false
    }

    @Override
    public int getItemCount() {
        return hoursList.size();
    }

    private List<String> generateHoursList() {
        List<String> hours = new ArrayList<>();
        if (isFriday == true){
            if (todayDateString.equals(selectedDate)) {
                String[] timeParts_c = currentTime.split(":");
                int currenthour = Integer.parseInt(timeParts_c[0]);
                int currentmin = Integer.parseInt(timeParts_c[1]);
                for (int i = 10; i < 14; i++) {
                    if (currenthour < i) {
                        for (int minute = 0; minute < 60; minute += 15) {
                            hours.add(String.format("%02d:%02d", i, minute));
                        }
                    }
                    if (currenthour == i) {
                        for (int minute = 0; minute < 60; minute += 15) {
                            if (currentmin < minute) {
                                hours.add(String.format("%02d:%02d", i, minute));
                            }
                        }
                    }
                }
            }
            else {
                for (int hour = 10; hour <= 14; hour++) {
                    for (int minute = 0; minute < 60; minute += 15) {
                        hours.add(String.format("%02d:%02d",hour, minute));
                    }
                }
            }
        }
        else {
            if (todayDateString.equals(selectedDate)) {
                String[] timeParts_c = currentTime.split(":");
                int currenthour = Integer.parseInt(timeParts_c[0]);
                int currentmin = Integer.parseInt(timeParts_c[1]);
                for (int i = 10; i < 21; i++) {
                    if (currenthour < i) {
                        for (int minute = 0; minute < 60; minute += 15) {
                            hours.add(String.format("%02d:%02d", i, minute));
                        }
                    }
                    if (currenthour == i) {
                        for (int minute = 0; minute < 60; minute += 15) {
                            if (currentmin < minute) {
                                hours.add(String.format("%02d:%02d", i, minute));
                            }
                        }
                    }
                }
            } else {
                for (int hour = 10; hour <= 20; hour++) {
                    for (int minute = 0; minute < 60; minute += 15) {
                        hours.add(String.format("%02d:%02d", hour, minute));
                    }
                }
            }
        }
        return hours;
    }

    public void setOnHourClickListener(OnHourClickListener listener) {
        this.listener = listener;
    }


    static class HourViewHolder extends RecyclerView.ViewHolder {
        private Button buttonHour;

        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonHour = itemView.findViewById(R.id.buttonHour);
        }

        public void bind(String hour) {
            buttonHour.setText(hour);
        }
    }

    public interface OnHourClickListener {
        void onHourClick(String hour);
    }
}