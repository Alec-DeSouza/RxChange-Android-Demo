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

package com.umbraltech.rxchangedemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umbraltech.rxchange.adapter.collections.ListChangeAdapter;
import com.umbraltech.rxchangedemo.R;
import com.umbraltech.rxchangedemo.data.TextElement;

import java.util.ArrayList;
import java.util.List;

public class TextElementAdapter extends RecyclerView.Adapter {
    private final List<TextElement> mElementList = new ArrayList<>();
    private final ListChangeAdapter<TextElement> mElementChangeAdapter;

    private static class TextElementViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
        private final ImageView mRemoveView;

        public TextElementViewHolder(final View v) {
            super(v);

            mTextView = v.findViewById(R.id.text_view);
            mRemoveView = v.findViewById(R.id.remove_view);
        }

        public TextView getTextView() {
            return mTextView;
        }

        public ImageView getRemoveView() {
            return mRemoveView;
        }
    }

    public TextElementAdapter(final ListChangeAdapter<TextElement> elementChangeAdapter) {
        mElementChangeAdapter = elementChangeAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextElementViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_element, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TextElementViewHolder textViewHolder = (TextElementViewHolder) holder;

        final TextElement element = mElementList.get(position);

        textViewHolder.getTextView().setText(element.getText());
        textViewHolder.getRemoveView().setOnClickListener(v ->
                mElementChangeAdapter.remove(textViewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mElementList.size();
    }

    public void addElement(final TextElement element) {
        mElementList.add(element);
        notifyDataSetChanged();
    }

    public void addElements(final List<TextElement> elementList) {
        mElementList.addAll(elementList);
        notifyDataSetChanged();
    }

    public void removeElement(final TextElement element) {
        mElementList.remove(element);
        notifyDataSetChanged();
    }
}
