package com.example.festafimdeano.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.festafimdeano.R;
import com.example.festafimdeano.constant.FimDeAnoConstants;
import com.example.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday = findViewById(R.id.text_today);
        this.mViewHolder.textDaysLeft = findViewById(R.id.text_label_days_left);
        this.mViewHolder.textDaysLeft = findViewById(R.id.text_days_left);
        this.mViewHolder.button = findViewById(R.id.button);

        this.mViewHolder.button.setOnClickListener(this);


        //Datas
        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        String daysLeft =  String.format("%s %s", String.valueOf(this.getDaysLeft()), getString(R.string.dias));
        this.mViewHolder.textDaysLeft.setText(daysLeft);

        // tem a função de verificar a presença do usuário
        this.verifiyPresence();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifiyPresence();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }

    @Override
    // responsável por abrir a activity de detalhes
    public void onClick(View v) {
        if(v.getId() == R.id.button) {

            String presence = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);
            Intent intent = new Intent (this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY,presence);
            startActivity(intent);
        }
    }

    private void verifiyPresence() {
        // não confirmado, sim, não
       String presence =  this.mSecurityPreferences.getStoredString(FimDeAnoConstants.PRESENCE_KEY);
       if(presence.equals("")) {
           this.mViewHolder.button.setText(getString(R.string.não_confirmado));
       }else if (presence.equals(FimDeAnoConstants.CONFIRMATION_YES)) {
           this .mViewHolder.button.setText(getString(R.string.sim));
       }else {
           this .mViewHolder.button.setText(getString(R.string.não));
       }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getDaysLeft(){
        // Data de hoje
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        // Dia máximo do ano
        // [1-365]

        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button button;
    }
}