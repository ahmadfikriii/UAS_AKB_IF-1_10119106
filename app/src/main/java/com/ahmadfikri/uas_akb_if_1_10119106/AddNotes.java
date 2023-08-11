package com.ahmadfikri.uas_akb_if_1_10119106;

/*
NIM     : 10119106
Nama    : Ahmad Fikri Maulana
Kelas   : IF-1/S1/VI
 */

import static android.content.ContentValues.TAG;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNotes extends AppCompatActivity {

    Toolbar toolbar;
    EditText noteTitle, noteDetail;
    Spinner spinner;
    TextView hari;
    Calendar c;
    String bulan;
    String todaysDate;
    String currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hari = findViewById(R.id.hari);
        noteTitle = findViewById(R.id.noteTitle);
        noteDetail = findViewById(R.id.noteDetail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 ){
                    getSupportActionBar().setTitle(charSequence);
                }else {
                    getSupportActionBar().setTitle("Catatan Baru");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //waktu hari ini
        c = Calendar.getInstance();
        Integer bln = (c.get(Calendar.MONTH)+1);
        if (bln == 1){
            bulan = "Jan";
        }else if (bln ==2){
            bulan = "Feb";
        }else if (bln ==3){
            bulan = "Mar";
        }else if (bln ==4){
            bulan = "Apr";
        }else if (bln ==5){
            bulan = "Mei";
        }else if (bln ==6){
            bulan = "Jun";
        }else if (bln ==7){
            bulan = "Jul";
        }else if (bln ==8){
            bulan = "Agu";
        }else if (bln ==9){
            bulan = "Sep";
        }else if (bln ==10){
            bulan = "Okt";
        }else if (bln ==11){
            bulan = "Nov";
        }else if (bln ==12){
            bulan = "Des";
        }
        todaysDate =  c.get(Calendar.DAY_OF_MONTH)+ " "  + bulan + " " + c.get(Calendar.YEAR);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" +pad(c.get(Calendar.MINUTE));
        hari.setText(todaysDate);
        Log.d("Kalender","Tanggal dan waktu : "+ todaysDate + " dan "+ currentTime);


        //spinner
        spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("📝 Notes");
        arrayList.add("🎓 Study");
        arrayList.add("🏠 Home");
        arrayList.add("🔏 Private");
        arrayList.add("❗ Priority");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

    }



    private String pad(int i) {
        if (i < 10){
            return "0"+i;
        }else {
            return String.valueOf(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save){
            Note note = new Note(noteTitle.getText().toString(),noteDetail.getText().toString() , spinner.getSelectedItem().toString(), todaysDate,currentTime);
            NoteDatabase db = new NoteDatabase(this);
            db.addNote(note);
            goToMain();

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();

                            // Log and toast
                            Log.d(TAG, token);
                            Toast.makeText(AddNotes.this, token, Toast.LENGTH_SHORT).show();
                        }
                    });

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.delete){
            Toast.makeText(this, "Not Saved.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        if (item.getItemId() == R.id.nav_home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}