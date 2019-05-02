package com.teenspirit88.musicalbums.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс дает возможность создавать подключение к заданному базовому URI
 */
public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://itunes.apple.com";
    private Retrofit mRetrofit;

    /**
     * Приватный конструктор singleton,
     * метод конфигуреирует подключение через retrofit2.
     * При конфигурирование включено логирование запросов и ответов
     */
    private NetworkService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    /**
     * Регализация singleton
     * @return возвращается инстанс класса.
     */
    public static NetworkService getInstance() {
        if(mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public DataService getJSON() {
        return mRetrofit.create(DataService.class);
    }
}
