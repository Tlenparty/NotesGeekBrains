package com.geekbrains.notesgeekbrains.data;

public interface CardSourceResponse {
    //Если успешно подсоеденимся к бд и возвращаем источник данных
    void initialized (CardSource cardSource); //Метод initialized() будет вызываться, когда данные проинициализируются и будут готовы.
}
