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
import com.pau.putrautama.noteapps.Model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MakeNoteActivity extends AppCompatActivity {

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

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("Note");

        mCurrentDate = getDate();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_notes, menu);        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save){
            saveNoteToFirebase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNoteToFirebase() {
        String title = etJudul.getText().toString();
        String content = etIsi.getText().toString();

        dateCreated = mCurrentDate;
        dateModified = dateCreated;

        if (title.equals("") || content.equals("")){
            Toast.makeText(this, "Harap mengisi field yang kosong", Toast.LENGTH_SHORT).show();
        }else {


            if (TextUtils.isEmpty(noteId)){
                noteId = mDatabase.push().getKey();
            }
            Note note = new Note(title,content,dateCreated,dateModified,noteId);

            mDatabase.child(noteId).setValue(note);

            Intent intent = new Intent(MakeNoteActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
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