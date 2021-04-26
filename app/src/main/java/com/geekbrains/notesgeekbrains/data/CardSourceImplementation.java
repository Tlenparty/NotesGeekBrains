package com.geekbrains.notesgeekbrains.data;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.geekbrains.notesgeekbrains.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardSourceImplementation implements CardSource {

    private final List<CardData> dataSource; // данные
    private final Resources resources; // ресурсы приложения

    // CardSource
    public CardSourceImplementation(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardSource init(CardSourceResponse cardSourceResponse) {
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.titles);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        // изображения
        int[] pictures = getImageArray();
        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i], pictures[i], false, Calendar.getInstance().getTime()));
        }
        // Если данные из облака пришли, инитиализиурем данные
        if (cardSourceResponse != null) {
            cardSourceResponse.initialized(this);
        }
        return this; // возвращаем cardSourceResponse
    }

    // Вытаскивание индефикаторов из картинок
    private int[] getImageArray() {
        @SuppressLint("Recycle")
        // TypedArray отличается от StringArray
                TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for (int i = 0; i < length; i++) {
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardData(int position) {

    }

    @Override
    public void updateCardData(int position, CardData cardData) {

    }

    @Override
    public void addCardData(CardData cardData) {

    }

    @Override
    public void clearCardData() {

    }
}
