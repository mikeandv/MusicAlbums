package com.teenspirit88.musicalbums.util;

import com.teenspirit88.musicalbums.model.Album;

import java.util.Comparator;

/**
 * Класс компаратора для сортировки коллекции альбомов по авфавиту
 */
public class AlbumComparator implements Comparator<Album>{

    /**
     * Объекты сравниваются по полю CollectionName c учетом регистра
     * @param album первый осравниваемый объект
     * @param t1 второй стравниваемый объект
     * @return
     */
    @Override
    public int compare(Album album, Album t1) {
        int res = String.CASE_INSENSITIVE_ORDER.compare(album.getCollectionName(), t1.getCollectionName());

        if(res == 0) {
            res = album.getCollectionName().compareTo(t1.getCollectionName());
        }

        return res;
    }
}
