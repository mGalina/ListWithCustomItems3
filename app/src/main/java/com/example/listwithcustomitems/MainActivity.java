package com.example.listwithcustomitems;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random();

    private ItemsDataAdapter adapter;

    private List<Drawable> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);

        fillImages();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomItemData();
            }
        });


        adapter = new ItemsDataAdapter(this, null);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                showItemData(position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeItem(position);
                return true;
            }
        });
    }

    private void fillImages() {
        images.add(getDrawable(R.drawable.ic_baseline_sports_basketball_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_esports_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_football_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_handball_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_hockey_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_kabaddi_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_motorsports_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_rugby_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_tennis_24));
        images.add(getDrawable(R.drawable.ic_baseline_sports_volleyball_24));
    }

    private void generateRandomItemData() {
        adapter.addItem(new ItemData(
                images.get(random.nextInt(images.size())),
                "Sport" + adapter.getCount(),
                "This is for me",
                random.nextBoolean()));
    }

    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle() + "\n" +
                        "Checked: " + itemData.isChecked(),
                Toast.LENGTH_SHORT).show();
    }
}