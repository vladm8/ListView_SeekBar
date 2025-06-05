package com.example.zad9;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView settingsListView;
    private TextView editingLabelTextView;
    private SeekBar valueSeekBar;
    private TextView seekBarValueTextView;

    private ArrayList<String> settingNames;
    private ArrayList<Integer> settingValues;
    private ArrayList<String> settingUnits;
    private ArrayList<String> displayItemsForListView;

    private ArrayAdapter<String> adapter;
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsListView = findViewById(R.id.settingsListView);
        editingLabelTextView = findViewById(R.id.editingLabelTextView);
        valueSeekBar = findViewById(R.id.valueSeekBar);
        seekBarValueTextView = findViewById(R.id.seekBarValueTextView);

        settingNames = new ArrayList<>();
        settingValues = new ArrayList<>();
        settingUnits = new ArrayList<>();
        displayItemsForListView = new ArrayList<>();

        settingNames.add("Jasność Ekranu");
        settingValues.add(50);
        settingUnits.add("%");

        settingNames.add("Głośność Dźwięków");
        settingValues.add(80);
        settingUnits.add("%");

        settingNames.add("Czas Automatycznej Blokady");
        settingValues.add(30);
        settingUnits.add("s");

        updateDisplayItemsForListView();

        adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, R.id.itemTextView, displayItemsForListView);
        settingsListView.setAdapter(adapter);

        editingLabelTextView.setText("Wybierz opcję z listy powyżej");
        seekBarValueTextView.setText("Wartość: --");
        valueSeekBar.setEnabled(false);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;
                String selectedOptionName = settingNames.get(selectedItemPosition);
                editingLabelTextView.setText("Edytujesz: " + selectedOptionName);
                int currentValue = settingValues.get(selectedItemPosition);
                valueSeekBar.setProgress(currentValue);
                seekBarValueTextView.setText("Wartość: " + currentValue);
                valueSeekBar.setEnabled(true);
            }
        });
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                if (fromUser && selectedItemPosition != -1) {
                    seekBarValueTextView.setText("Wartość: " + progressValue);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (selectedItemPosition != -1) {
                    Toast.makeText(MainActivity.this, "Rozpoczynasz zmianę wartości...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (selectedItemPosition != -1) {
                    int newValue = seekBar.getProgress();
                    settingValues.set(selectedItemPosition, newValue);
                    updateDisplayItemsForListView();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void updateDisplayItemsForListView() {
        displayItemsForListView.clear();
        for (int i = 0; i < settingNames.size(); i++) {
            String formattedString = settingNames.get(i) + ": " + settingValues.get(i) + settingUnits.get(i);
            displayItemsForListView.add(formattedString);
        }
    }
}