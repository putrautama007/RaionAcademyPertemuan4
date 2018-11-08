package com.pau.putrautama.noteapps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pau.putrautama.noteapps.Adapter.NoteListAdapter;
import com.pau.putrautama.noteapps.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    RecyclerView.Adapter mAdapter;
    List<Note> notesList = new ArrayList<>();
    FloatingActionButton tambahButton;

    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("Note");


        setRecyclerView();
        loadNotesFromFirebase();

        tambahButton = findViewById(R.id.fabTambahNotes);
        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }
    private void setRecyclerView(){
        rvNotes = findViewById(R.id.RecyclerNotes);
        rvNotes.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        rvNotes.addItemDecoration(new DividerItemDecoration(rvNotes.getContext(),
                linearLayout.getOrientation()));
        rvNotes.setLayoutManager(linearLayout);
    }

    private void loadNotesFromFirebase(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()){
                    Note noteSnapshot = tiapDataSnapshot.getValue(Note.class);
                    notesList.add(noteSnapshot);

                }
                mAdapter = new NoteListAdapter(notesList,MainActivity.this);
                rvNotes.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addNote() {
        Intent i = new Intent(MainActivity.this, MakeNoteActivity.class);
        startActivity(i);
    }
}
