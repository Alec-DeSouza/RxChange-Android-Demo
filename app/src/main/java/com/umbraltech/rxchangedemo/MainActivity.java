package com.umbraltech.rxchangedemo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.umbraltech.rxchange.adapter.collections.ListChangeAdapter;
import com.umbraltech.rxchange.filter.ChangeTypeFilter;
import com.umbraltech.rxchange.filter.MetadataFilter;
import com.umbraltech.rxchange.message.MetaChangeMessage;
import com.umbraltech.rxchange.type.ChangeType;
import com.umbraltech.rxchangedemo.adapter.TextElementAdapter;
import com.umbraltech.rxchangedemo.dao.TextElementDao;
import com.umbraltech.rxchangedemo.data.TextElement;
import com.umbraltech.rxchangedemo.database.TextElementDatabase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private final ListChangeAdapter<TextElement> mElementChangeAdapter = new ListChangeAdapter<>();

    private final TextElementAdapter mElementAdapter = new TextElementAdapter(mElementChangeAdapter);

    public void addTextElement(final View v) {
        final EditText editText = findViewById(R.id.add_edit_text);
        final String text = editText.getText().toString();
        editText.getText().clear();

        final TextElement element = new TextElement(text);
        element.setTimestamp(System.currentTimeMillis());

        mElementChangeAdapter.add(element);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView elementRecyclerView = findViewById(R.id.element_recycler_view);
        elementRecyclerView.setHasFixedSize(true);
        elementRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        elementRecyclerView.setAdapter(mElementAdapter);

        // Load the database
        Observable.fromCallable(() -> Room.databaseBuilder(this, TextElementDatabase.class, "text-element-db")
                .build())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::initialize);
    }

    private void initialize(final TextElementDatabase database) {
        final TextElementDao textElementDao = database.getTextElementDao();

        initializeDB(textElementDao);
        initializeUI();

        // Load data
        mElementChangeAdapter.add(textElementDao.getAllTextElements());
    }

    private void initializeUI() {

        // Observe single element additions for updating the UI
        mElementChangeAdapter.getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new ChangeTypeFilter(ChangeType.ADD))
                .filter(new MetadataFilter(TextElement.class))
                .map(m -> (MetaChangeMessage<List<TextElement>, TextElement>) m)
                .subscribe(m -> mElementAdapter.addElement(m.getMetadata()));

        // Observe batch additions for updating the UI (used only for initial load in our case)
        mElementChangeAdapter.getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new ChangeTypeFilter(ChangeType.ADD))
                .filter(new MetadataFilter(List.class))
                .map(m -> (MetaChangeMessage<List<TextElement>, List<TextElement>>) m)
                .subscribe(m -> mElementAdapter.addElements(m.getMetadata()));

        // Observe single element removals for updating the UI
        mElementChangeAdapter.getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new ChangeTypeFilter(ChangeType.REMOVE))
                .filter(new MetadataFilter(TextElement.class))
                .map(m -> (MetaChangeMessage<List<TextElement>, TextElement>) m)
                .subscribe(m -> mElementAdapter.removeElement(m.getMetadata()));
    }

    private void initializeDB(final TextElementDao textElementDao) {

        // Observe single element additions for updating the database
        mElementChangeAdapter.getObservable()
                .observeOn(Schedulers.io())
                .filter(new ChangeTypeFilter(ChangeType.ADD))
                .filter(new MetadataFilter(TextElement.class))
                .map(m -> (MetaChangeMessage<List<TextElement>, TextElement>) m)
                .subscribe(m -> textElementDao.insertTextElement(m.getMetadata()));

        // Observe single element removals for updating the database
        mElementChangeAdapter.getObservable()
                .observeOn(Schedulers.io())
                .filter(new ChangeTypeFilter(ChangeType.REMOVE))
                .filter(new MetadataFilter(TextElement.class))
                .map(m -> (MetaChangeMessage<List<TextElement>, TextElement>) m)
                .subscribe(m -> textElementDao.deleteTextElement(m.getMetadata()));
    }
}
