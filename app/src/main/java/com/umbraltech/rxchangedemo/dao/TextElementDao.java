package com.umbraltech.rxchangedemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.umbraltech.rxchangedemo.data.TextElement;

import java.util.List;

@Dao
public interface TextElementDao {

    @Query("SELECT * FROM text_elements WHERE uid = :uid")
    TextElement getTextElement(long uid);

    @Query("SELECT * FROM text_elements ORDER BY timestamp DESC")
    List<TextElement> getAllTextElements();

    @Insert
    void insertTextElement(TextElement element);

    @Insert
    void insertTextElements(List<TextElement> elementList);

    @Update
    void updateTextElement(TextElement element);

    @Update
    void updateTextElements(List<TextElement> elementList);

    @Delete
    void deleteTextElement(TextElement element);

    @Delete
    void deleteTextElements(List<TextElement> elementList);
}
