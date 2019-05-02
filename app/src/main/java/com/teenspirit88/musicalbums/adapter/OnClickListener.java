package com.teenspirit88.musicalbums.adapter;

import com.teenspirit88.musicalbums.model.Album;

/**
 * Интерфейс для обработки клика по элементу в RecyclerView
 */
public interface OnClickListener {
    void OnItemClick(Album album);
}
