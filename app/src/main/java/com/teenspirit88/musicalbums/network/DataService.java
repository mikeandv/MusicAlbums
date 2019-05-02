package com.teenspirit88.musicalbums.network;

import com.teenspirit88.musicalbums.model.AlbumList;
import com.teenspirit88.musicalbums.model.SongList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {
    @GET("/search")
    Call<AlbumList> searchAlbum(@Query("term") String termPattern,
                                @Query("entity") String entity);

    @GET("/lookup")
    Call<SongList> getSongsByAlbumId(@Query("id") int albumId,
                                     @Query("entity") String entity,
                                     @Query("attribute") String attribute);
}
