package com.geekbrains.notesgeekbrains.data;

public interface CardSource {
    // Инициализация БД. CardSourceResponse из FireStore
    CardSource init(CardSourceResponse cardSourceResponse);
    CardData getCardData(int position); // возвращает карточку
    int size(); // возвращает размер коллекции
    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);
    void addCardData(CardData cardData);
    void clearCardData();

}
