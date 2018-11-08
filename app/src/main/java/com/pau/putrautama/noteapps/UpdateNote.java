package com.pau.putrautama.noteapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateNote extends AppCompatActivity {

    EditText etJudul, etIsi;
    String title, content, dateCreated, dateModified, mCurrentDate;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private String noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        etJudul = findViewById(R.id.etTitle);
        etIsi = findViewById(R.id.etContent);

        noteId = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        dateCreated = getIntent().getStringExtra("created");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("Note");

        mCurrentDate = getDate();
        dateModified = mCurrentDate;


        etJudul.setText(title);
        etIsi.setText(content);

    }

    private void updateNoteToFirebase(){

        String currentTitle = etJudul.getText().toString();
        String currentContent = etIsi.getText().toString();

        if (!TextUtils.isEmpty(currentTitle) && !TextUtils.isEmpty(currentContent)){
            mDatabase.child(noteId).child("title").setValue(currentTitle);
            mDatabase.child(noteId).child("content").setValue(currentTitle);
            mDatabase.child(noteId).child("dateCreated").setValue(dateCreated);
            mDatabase.child(noteId).child("dateModified").setValue(dateModified);
        } else {
            Toast.makeText(this, "Harap mengisi field yang kosong", Toast.LENGTH_SHORT).show();
        }



        Intent intent = new Intent(UpdateNote.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            updateNoteToFirebase();
        }else if (id == R.id.action_delete){
            deleteNoteFromFirebase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNoteFromFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("Note");
        Query noteQuerry = mDatabase.child(noteId).equalTo(noteId);

        noteQuerry.getRef().removeValue();
        Intent intent = new Intent(UpdateNote.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private String getDate() {

        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String current = dateFormat.format(c.getTime());
            Date date = dateFormat.parse(current);
            SimpleDateFormat fmtOut = new SimpleDateFormat("d MMM yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }
        return "";
    }
}
