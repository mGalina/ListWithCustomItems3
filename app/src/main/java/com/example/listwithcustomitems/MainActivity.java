package com.example.listwithcustomitems;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random = new Random();
    private ItemsDataAdapter adapter;
    private List<Drawable> images = new ArrayList<>();
    private File file;
    private static final String DIVIDER = ";";

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

        adapter = new ItemsDataAdapter(this, null, new RemoveItemClickListener() {
            @Override
            public void onRemoveItemClicked(int position) {
                removeItem(position);
            }
        });

        listView.setAdapter(adapter);
        file = new File(getExternalFilesDir(null), "my.txt");

        loadText();

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

    private void removeItem(int position) {
        adapter.removeItem(position);

        // Тут уже перезаписываем файл
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0, length = adapter.getCount(); i < length; i++) {
                saveItem(writer, adapter.getItem(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadText() {
        if (!isExternalStorageReadable()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] split = reader.readLine().split(DIVIDER);
            for (int i = 0, length = split.length; i < length;) {
                String title = split[i++];
                String subtitle = split[i++];
                Drawable image = images.get(Integer.parseInt(split[i++]));

                adapter.addItem(new ItemData(image, title, subtitle));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveItem(Writer writer, ItemData item) throws IOException {
        writer.write(item.getTitle());
        writer.write(DIVIDER);
        writer.write(item.getSubtitle());
        writer.write(DIVIDER);
        writer.write(String.valueOf(images.indexOf(item.getImage())));
        writer.write(DIVIDER);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
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
        ItemData item = new ItemData(
                images.get(random.nextInt(images.size())),
                "Sport" + adapter.getCount(),
                "This is for me");

        adapter.addItem(item);

        try (FileWriter writer = new FileWriter(file, true)) {
            saveItem(writer, item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle(),
                Toast.LENGTH_SHORT).show();
    }
}