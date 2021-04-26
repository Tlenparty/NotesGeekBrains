package com.geekbrains.notesgeekbrains.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.geekbrains.notesgeekbrains.data.CardSource;
import com.geekbrains.notesgeekbrains.R;
import com.geekbrains.notesgeekbrains.data.CardData;

// Adapter - посредник между данными и ViewHolder (отображает данные)
public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ListNotesViewHolder> {

    private CardSource dataSource;
    private MyItemClickListener itemClickListener; // Слушатель будет устанавливаться извне
    private final static String TAG = "SocialNetworkAdapter";

    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД

    public ListNotesAdapter(Fragment fragment) {
        this.dataSource = dataSource;
    }

    public void setDataSource(CardSource dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    // вызывается столько раз сколько элементов на экране
    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public ListNotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // через Inflater
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        Log.d(TAG,"onCreateViewHolder");
        return new ListNotesViewHolder(view); // view - элемент списка
    }

    // вызывается столько сколько прокручивается список
    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull ListNotesViewHolder viewHolder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder и позицию
        viewHolder.onBind(dataSource.getCardData(position));
        Log.d(TAG,"onBindViewHolder");
    }

    // возращает размер списка.RecyclerView должен знать сколько в списке(массив) элементов
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Сеттер слушателя нажатий
    public void setOnItemClickListener(MyItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface  MyItemClickListener {
        void onItemClick(View view , int position);
    }

    public class ListNotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView description;
        private final AppCompatImageView image;
        private final CheckBox like;

        // обязаны переопределить конструктор, т.к. приходит сюда вью списка
        public ListNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);
            // Обработчик нажатий на картинке
            image.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    int position = getAdapterPosition();
                    // Вешаем кликлистнер на itemView
                    itemClickListener.onItemClick(v, position);
                }
            });
        }

        // в onBind приходит наша карточка
        public void onBind(CardData cardData) {
            title.setText(cardData.getTitle());
            description.setText(cardData.getDescription());
            like.setChecked(cardData.isLike());
            image.setImageResource(cardData.getPicture());
        }
    }
}
