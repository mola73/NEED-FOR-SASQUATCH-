package com.example.needforsasquatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;



public class ModeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        ImageButton dashButton = findViewById(R.id.dash_button);


        dashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSelectionActivity.this, DashModeActivity.class);
                startActivity(intent);
            }
        });
        ImageButton destructionButton = findViewById(R.id.destruction_button);
        destructionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ModeSelectionActivity.this, DestructionModeActivity.class);
                startActivity(intent);
            }
        });
        //runaway mode
        ImageButton runawayButton = findViewById(R.id.run_away_button);
        runawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSelectionActivity.this, RunawayModeActivity.class);
                startActivity(intent);
            }
        });
    }
}
