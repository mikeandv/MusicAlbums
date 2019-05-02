package com.teenspirit88.musicalbums.network;

import com.teenspirit88.musicalbums.model.AlbumList;
import com.teenspirit88.musicalbums.model.SongList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Интерфейс для подготовки запроса к API выполняемого с помошью библиотеки retrofit2
 */
public interface DataService {

     /**
     * Поиск по заданому термину
     * @param termPattern термин для поиск
     * @param entity сущность, которая будет возвращена
     * @return метод возвращает коллекцию альбовом
     */
    @GET("/search")
    Call<AlbumList> searchAlbum(@Query("term") String termPattern,
                                @Query("entity") String entity);

    /**
     * Получение результата(lookup) по id
     * @param albumId идентификатор альбома
     * @param entity сущность, которая будет возвращена
     * @param attribute уточняющий атрибут
     * @return метод возвращает коллекцию песен, запрошенного альбома
     */
    @GET("/lookup")
    Call<SongList> getSongsByAlbumId(@Query("id") int albumId,
                                     @Query("entity") String entity,
                                     @Query("attribute") String attribute);
}
