package com.example.electricitybillscalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Find the TextView for the website link
        TextView tvWebsiteLink = findViewById(R.id.tvWebsiteLink);

        // Make the website link clickable
        tvWebsiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = tvWebsiteLink.getText().toString();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url; // Ensure it has a scheme for Intent to work
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
