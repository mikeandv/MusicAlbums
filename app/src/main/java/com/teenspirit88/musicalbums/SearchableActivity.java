package com.teenspirit88.musicalbums;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teenspirit88.musicalbums.adapter.AlbumAdapter;
import com.teenspirit88.musicalbums.adapter.OnClickListener;
import com.teenspirit88.musicalbums.model.Album;
import com.teenspirit88.musicalbums.model.AlbumList;
import com.teenspirit88.musicalbums.network.NetworkCheck;
import com.teenspirit88.musicalbums.network.NetworkService;
import com.teenspirit88.musicalbums.util.AlbumComparator;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AlbumAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private TextView noResult;
    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        initViews();
        handleIntent(getIntent());
    }

    /**
     * Метод обрабатывает нажатие кнопки назад на toolbar'е
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    /**
     * Метод обрабатывает подключение к API и получает резльтат в виде коллекции альбомов,
     * после чего обработка передается в метод generateAlbumList(ArrayList<Album> albums).
     * В случае ошибки при получении данный вызывется метод failureHandler().
     * @param intent намерение переданное из MainActivity содержит темин введенный в поисковой строке
     */
    public void handleIntent(final Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            NetworkService.getInstance()
                    .getJSON()
                    .searchAlbum(query.trim().replaceAll(" ", "+"), "album")
                    .enqueue(new Callback<AlbumList>() {
                        @Override
                        public void onResponse(Call<AlbumList> call, Response<AlbumList> response) {

                            generateAlbumList((ArrayList<Album>) response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<AlbumList> call, Throwable t) {
                            failureHandler();
                        }
                    });
        }
    }

    /**
     * Метод получает на вход коллекцию альбомов, в случае если коллекция пустая выводится сообшение "No result"
     * @param albums
     */
    private void generateAlbumList(ArrayList<Album> albums) {
        if(albums.isEmpty()) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            noResult.setText(R.string.no_result);
            noResult.setVisibility(TextView.VISIBLE);
        } else {

            //Сортировка коллеккции по алфавиту
            Collections.sort(albums, new AlbumComparator());
            recyclerView.setLayoutManager(layoutManager);

            //Передача события по клику на элемент
            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void OnItemClick(Album album) {
                    Intent intent = new Intent(SearchableActivity.this, DetailedInfo.class);
                    intent.putExtra("album", album);
                    startActivity(intent);

                }
            };
            adapter = new AlbumAdapter(albums, onClickListener);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    /**
     * Метод инициализирует все вью элементы на экране
     */
    private void initViews() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setTitle(query);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(SearchableActivity.this);

        noResult = (TextView) findViewById(R.id.text_no_result);

        progressBar = (ProgressBar) findViewById(R.id.progress_circular);
        progressBar.setVisibility(progressBar.VISIBLE);
    }

    /**
     * Метод скрывает progress bar и обрабатывает ошибку получения данный,
     * из-за отсуствия соединения с интернетом или из-за ошибки полученной в запросе к API
     */
    private void failureHandler() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        if(!NetworkCheck.isOnline(this)) {
            noResult.setText(R.string.no_internet_connection);
        } else {
            noResult.setText(R.string.wrong_res);
        }
        noResult.setVisibility(TextView.VISIBLE);
    }
}
