package com.geekbrains.notesgeekbrains.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notesgeekbrains.R;
import com.geekbrains.notesgeekbrains.data.CardSource;
import com.geekbrains.notesgeekbrains.data.CardSourceFirebaseImpl;
import com.geekbrains.notesgeekbrains.data.CardSourceResponse;

public class ListNotesFragment extends Fragment {

    private CardSource data;
    private ListNotesAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Получим источник данных для списка
        data = new CardSourceFirebaseImpl().init(new CardSourceResponse() {
            @Override
            public void initialized(CardSource cardSource) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        initView(view);
    }

    @SuppressLint("DefaultLocale")
    private void initView(View view) {
        // Создаем, находим recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);
        // Будем работать со всетроенным менеджером. Создали LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        // Передаем layoutManager в recyclerView
        recyclerView.setLayoutManager(layoutManager);
        // Создадим, установим адаптер
        final ListNotesAdapter adapter = new ListNotesAdapter(this);
        // Передаем adapter в recyclerView
        recyclerView.setAdapter(adapter);
        // Установим слушателя
        adapter.setOnItemClickListener(new ListNotesAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ListNotesFragment.this.getContext(), String.format("Позиция - %d", position), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
