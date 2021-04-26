package com.geekbrains.notesgeekbrains.data;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImpl implements CardSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    //Загружаемый список карточек
    private List<CardData> cardsData = new ArrayList<>();


    private Exception exception;

    @SuppressLint("LongLogTag")
    @Override
    public CardSource init(final CardSourceResponse cardSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Дата» DESCENDING восходящий или нисходящий формат
        // Так как Map не отсортированная коллекция
        // При удачном считывании данных загрузим список карточек
// Task класс в котором все наши карточки
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cardsData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData(); // у документа достаем Map
                            String id = document.getId(); // ключ, поулчаем  id
                            CardData cardData = CardDataMapping.toCardData(id, doc); // парсим документ
                            cardsData.add(cardData);
                        }
                        Log.d(TAG, "success " + cardsData.size() + " qnt");
                        cardSourceResponse.initialized(CardSourceFirebaseImpl.this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                        clearCardData();
                        exception = task.getException();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                        clearCardData();
                        exception = e;
                    }
                });
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData.get(position).getId()).delete(); // collection связано с FireBase
        cardsData.remove(position); // ударяем из массива карточек
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
        cardsData.set(position,cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearCardData() {
        for (CardData cardData : cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<>();
    }
}
