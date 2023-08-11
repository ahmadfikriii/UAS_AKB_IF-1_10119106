package com.ahmadfikri.uas_akb_if_1_10119106.fragment;

/*
NIM     : 10119106
Nama    : Ahmad Fikri Maulana
Kelas   : IF-1/S1/VI
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadfikri.uas_akb_if_1_10119106.AddNotes;
import com.ahmadfikri.uas_akb_if_1_10119106.IAdapter;
import com.ahmadfikri.uas_akb_if_1_10119106.Note;
import com.ahmadfikri.uas_akb_if_1_10119106.NoteDatabase;
import com.ahmadfikri.uas_akb_if_1_10119106.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class FragmentHome extends Fragment {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    FloatingActionButton button;
    IAdapter adapter;
    List<Note> notes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.listOfNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        button = view.findViewById(R.id.addBtn);
        toolbar = view.findViewById(R.id.toolbar);
        NoteDatabase db = new NoteDatabase(view.getContext());
        notes = db.getNotes();
        adapter = new IAdapter(view.getContext(),notes);
        recyclerView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddNotes.class);
                startActivity(i);
            }
        });

        return view;

    }

}