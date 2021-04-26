package com.geekbrains.notesgeekbrains.data;

import com.google.android.material.datepicker.RangeDateSelector;
import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {

    public static class Fields {
        public static final String PICTURE = "picture";
        public static final String DATE = "date";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String LIKE = "like";
    }

    // Передаем id и Map
    // doc - это просто map
    public static CardData toCardData(String id, Map<String, Object> doc) {
        long indexPic = (long) doc.get(Fields.PICTURE); // Достаем индекс для картинки по ключу PICTURE
        Timestamp timestamp = (Timestamp) doc.get(Fields.DATE);
        CardData answer = new CardData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                PictureIndexConverter.getPictureByIndex((int) indexPic),
                (boolean) doc.get(Fields.LIKE),
                timestamp.toDate());
        answer.setId(id);
        return answer;
    }

    // Перевод Map в документ, который понятен FireStore
    public static Map<String, Object> toDocument(CardData cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.PICTURE, PictureIndexConverter.getIndexByPicture(cardData.getPicture()));
        answer.put(Fields.LIKE, cardData.isLike());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }
}
