package com.teenspirit88.musicalbums;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teenspirit88.musicalbums.adapter.SongAdapter;
import com.teenspirit88.musicalbums.model.Album;
import com.teenspirit88.musicalbums.model.Song;
import com.teenspirit88.musicalbums.model.SongList;
import com.teenspirit88.musicalbums.network.NetworkService;
import com.teenspirit88.musicalbums.util.RoundRectTransform;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Активность отображает детальную информацию по выбранному альбому, включает в себя информацию и список песен
 */
public class DetailedInfo extends AppCompatActivity {
    private Album album;
    private Toolbar toolbar;
    private ListView songList;
    private ImageView albumArtwork;
    private TextView albumLabel;
    private TextView artistLabel;
    private TextView albumGenre;
    private TextView albumYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        // TODO: 2019-05-02 Добавить по нажатию кнопки добавление альбома на главный экран с сохранением объекта в базу
        //  При обращении сглавного экрана вычитывать объект из БД, а не из API.
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            album = (Album) bundle.get("album");

            initView();
            loadSongList();

        }
    }

    /**
     * Метод обрабатывает нажание кнопки назад
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Метод для инициализации всех вью на экране
     */
    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back_icon);
        setSupportActionBar(toolbar);

        albumArtwork = (ImageView) findViewById(R.id.artwork_large_detailed);
        albumLabel = (TextView) findViewById(R.id.album_label_detailed);
        artistLabel = (TextView) findViewById(R.id.artist_label_detailed);
        albumGenre = (TextView) findViewById(R.id.album_genre_detailed);
        albumYear = (TextView) findViewById(R.id.album_year_detailed);
        songList = (ListView) findViewById(R.id.song_list);

    }

    /**
     * Метод устанавливает данные в инициализированные вью на экране.
     * Картинка подгружается с помошью библиотеки Picasso, используется трансормация для скрушления углов
     * @param songs на вход передается коллекция песен
     */
    private void loadData(ArrayList<Song> songs) {

        Picasso
                .get()
                .load(album.getArtworkUrl100())
                .transform(new RoundRectTransform(7, 0))
//                .centerCrop()
                .placeholder(R.color.placeholderColor)
                .error(R.drawable.ic_placeholder)
                .into(albumArtwork);

        albumLabel.setText(album.getCollectionName());
        artistLabel.setText(album.getArtistName());
        albumGenre.setText(album.getPrimaryGenreName());
        albumYear.setText(Integer.toString(ZonedDateTime.parse(album.getReleaseDate()).getYear()));
        SongAdapter adapter = new SongAdapter(this, songs);
        songList.setAdapter(adapter);
    }

    /**
     * Метод для получения списка песен альбома через API
     */
    private void loadSongList() {
        NetworkService.getInstance()
                .getJSON()
                .getSongsByAlbumId(album.getCollectionId(), "song", "songTerm")
                .enqueue(new Callback<SongList>() {
                    @Override
                    public void onResponse(Call<SongList> call, Response<SongList> response) {
                        loadData((ArrayList<Song>) filterAlbum(response.body().getResults()));
                    }

                    @Override
                    public void onFailure(Call<SongList> call, Throwable t) {

                    }
                });
    }

    /**
     * Метод используется для фитриации колекции песен.
     * API возвращает в первой строке ответа информацию об альбоме, эту запись необходимо удалить из коллекции.
     * Для удаления используется итератор
     * @param songs Коллекция песен
     * @return возвращается отфильтрованая коллекция песен без элемента с типом "Album"
     */
    private List<Song> filterAlbum(List<Song> songs) {
        ListIterator<Song> iterator = songs.listIterator();
        while (iterator.hasNext()) {
            Song s = iterator.next();
            if(!(s.getCollectionType() == null) && s.getCollectionType().equals("Album")) {
                iterator.remove();
            }
        }
        return songs;
    }
}
