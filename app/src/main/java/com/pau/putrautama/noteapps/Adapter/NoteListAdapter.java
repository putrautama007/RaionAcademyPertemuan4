package com.pau.putrautama.noteapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pau.putrautama.noteapps.Model.Note;
import com.pau.putrautama.noteapps.R;
import com.pau.putrautama.noteapps.UpdateNote;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NotesViewHolder> {
    private List<Note> mNoteList;
    private Context context;


    public NoteListAdapter(List<Note> mNoteList, Context context) {
        this.mNoteList = mNoteList;
        this.context = context;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDate;
        public CardView cvNotes;


        public NotesViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvDate = itemView.findViewById(R.id.tvNoteDate);
            cvNotes = itemView.findViewById(R.id.cardNote);
        }
    }



    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list, parent, false);
        NotesViewHolder nvh = new NotesViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, final int position) {
        final Note currentNote = mNoteList.get(position);
        holder.tvTitle.setText(currentNote.getTitle());
        holder.tvDate.setText(currentNote.getDateModified());
        holder.cvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNote.class);

//                //Pass the data to UpdateNoteEditor activity
                intent.putExtra("id", currentNote.getId());
                intent.putExtra("title", currentNote.getTitle());
                intent.putExtra("content", currentNote.getContent());
                intent.putExtra("created", currentNote.getDateCreated());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }


}