/*
 * Copyright 2018 - present, RxChange contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
