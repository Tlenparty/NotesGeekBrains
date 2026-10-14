# Notes Firestore

Прототип Android-приложения для заметок-карточек на Java. Каждая карточка —
картинка, заголовок, описание и отметка «нравится»; источник данных
абстрагирован интерфейсом `CardSource` с двумя реализациями: локальной
(из ресурсов) и облачной на Cloud Firestore.

> Статус: прототип. Экран списка и слой данных написаны, но приложение
> не доведено до рабочего состояния: для Firestore нужен свой Firebase-проект,
> а инициализация адаптера в `ListNotesFragment` требует доработки.

## Возможности

- Список карточек в RecyclerView (CardView: изображение, заголовок, описание, чекбокс «нравится»)
- Интерфейс источника данных `CardSource` с асинхронной инициализацией через `CardSourceResponse`
- `CardSourceImplementation` — карточки из строковых и графических ресурсов
- `CardSourceFirebaseImpl` — CRUD по коллекции `cards` в Firestore,
  сортировка по дате, маппинг документов (`CardDataMapping`)
- Сохранение картинки в документе по индексу (`PictureIndexConverter`)

## Стек

| Область    | Технологии                                    |
|------------|-----------------------------------------------|
| Язык       | Java 8                                        |
| UI         | Fragments, RecyclerView, CardView, Material Components |
| Данные     | Firebase Cloud Firestore                      |
| Сборка     | Gradle 7.3.3, Android Gradle Plugin 7.0.4     |

## Структура

```
app/src/main/java/com/tlenparty/notesfirestore/
├── MainActivity.java      # контейнер фрагментов
├── Navigation.java        # переключение фрагментов
├── data/                  # CardData, CardSource и его реализации, маппинг Firestore
└── ui/                    # ListNotesFragment, ListNotesAdapter
```

## Сборка

Требуется JDK 11–17 и Android SDK 30.

```bash
./gradlew assembleDebug
```

Для работы с Firestore подключите свой Firebase-проект: положите
`google-services.json` в `app/` и примените плагин `com.google.gms.google-services`.
