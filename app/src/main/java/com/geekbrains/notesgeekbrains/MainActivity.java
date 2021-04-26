package com.geekbrains.notesgeekbrains;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.geekbrains.notesgeekbrains.ui.ListNotesFragment;

public class MainActivity extends AppCompatActivity {
    /*
    Обеспечьте хранение данных приложения через Firestore.
    * Организуйте аутентификацию пользователя через Google.
    ** Обеспечьте вход через социальную сеть ВКонтакте или любую аналогичную.
     */

    private Navigation navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListNoteFragment();
    }

    private void setListNoteFragment() {
        ListNotesFragment listNotesFragment = new ListNotesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, listNotesFragment).commit();
    }
}