# Notes Firestore

A prototype Android app in Java for note cards. Each card has an
image, a title, a description, and a "like" flag; the data source is
abstracted behind the `CardSource` interface with two implementations: a local one
(from resources) and a cloud one backed by Cloud Firestore.

> Status: prototype. The list screen and the data layer are written, but the app
> is not in a working state: Firestore requires your own Firebase project,
> and the adapter initialization in `ListNotesFragment` needs more work.

## Features

- A list of cards in a RecyclerView (CardView: image, title, description, "like" checkbox)
- A `CardSource` data source interface with asynchronous initialization via `CardSourceResponse`
- `CardSourceImplementation`: cards built from string and drawable resources
- `CardSourceFirebaseImpl`: CRUD on the `cards` collection in Firestore,
  sorting by date, and document mapping (`CardDataMapping`)
- Storing the picture in a document by index (`PictureIndexConverter`)

## Tech stack

| Area     | Technologies                                  |
|----------|-----------------------------------------------|
| Language | Java 8                                        |
| UI       | Fragments, RecyclerView, CardView, Material Components |
| Data     | Firebase Cloud Firestore                      |
| Build    | Gradle 7.3.3, Android Gradle Plugin 7.0.4     |

## Project structure

```
app/src/main/java/com/tlenparty/notesfirestore/
├── MainActivity.java      # fragment container
├── Navigation.java        # fragment switching
├── data/                  # CardData, CardSource and its implementations, Firestore mapping
└── ui/                    # ListNotesFragment, ListNotesAdapter
```

## Build

Requires JDK 11–17 and Android SDK 30.

```bash
./gradlew assembleDebug
```

To use Firestore, connect your own Firebase project: put
`google-services.json` in `app/` and apply the `com.google.gms.google-services` plugin.
