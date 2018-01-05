package com.umbraltech.rxchangedemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.umbraltech.rxchangedemo.dao.TextElementDao;
import com.umbraltech.rxchangedemo.data.TextElement;

@Database(entities = {TextElement.class}, version = 1)
public abstract class TextElementDatabase extends RoomDatabase {
    public abstract TextElementDao getTextElementDao();
}
