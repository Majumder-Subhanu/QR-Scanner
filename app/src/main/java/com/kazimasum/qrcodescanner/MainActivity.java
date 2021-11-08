package com.kazimasum.qrcodescanner;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button scanbtn, toggle;
    ConstraintLayout layout;
    public static ImageView imageView;
    public static TextView scantext;
    private static final int PICK_IMAGE = 100;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scantext = (TextView) findViewById(R.id.scantext);
        scanbtn = (Button) findViewById(R.id.scanbtn);
        toggle = (Button) findViewById(R.id.toggle);
        imageView = (ImageView) findViewById(R.id.imageView);
        layout = findViewById(R.id.layout);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), scannerView.class));
            }
        });

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                if (count % 2 != 0) {
                    layout.setBackgroundColor(Color.BLACK);
                    scanbtn.setBackgroundResource(R.drawable.custom_button2);
                    toggle.setTextColor(Color.WHITE);
                    if (! check_URL(scantext.getText().toString())) scantext.setTextColor(Color.WHITE);
                    else scantext.setTextColor(Color.BLUE);
                } else {
                    layout.setBackgroundColor(Color.WHITE);
                    scanbtn.setBackgroundResource(R.drawable.custom_button);
                    toggle.setTextColor(Color.BLACK);
                    toggle.setTextColor(Color.BLACK);
                    if (! check_URL(scantext.getText().toString())) scantext.setTextColor(Color.BLACK);
                    else scantext.setTextColor(Color.BLUE);
                }
            }
        });

        scantext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = scantext.getText().toString();
                if (!check_URL(s)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboard.setText(scantext.getText());
                    Toast.makeText(getApplicationContext(), "Qr code information scanned", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(s));
                        startActivity(i);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
    }

    public static boolean check_URL(String str) {
        try {
            new URL(str).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}