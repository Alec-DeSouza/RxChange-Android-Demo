package com.umbraltech.rxchangedemo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "text_elements")
public class TextElement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private long mUid;

    @ColumnInfo(name = "text")
    private String mText;

    @ColumnInfo(name = "timestamp")
    private long mTimestamp;

    public TextElement(final String text) {
        setText(text);
    }

    public void setUid(final long uid) {
        mUid = uid;
    }

    public long getUid() {
        return mUid;
    }

    public void setText(final String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setTimestamp(final long timestamp) {
        mTimestamp = timestamp;
    }

    public long getTimestamp() {
        return mTimestamp;
    }
}
