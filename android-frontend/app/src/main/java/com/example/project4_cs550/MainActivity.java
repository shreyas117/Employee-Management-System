package com.example.project4_cs550;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);

        Button button = findViewById(R.id.button);
        EditText editText = findViewById(R.id.editText);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText.getText().toString();
                String password = editTextPassword.getText().toString();

                String url = "http://10.0.2.2:8080/sleepyhollow/login?user=" + username + "&pass=" + password;
                StringRequest request3 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                        String result = s.trim();
                        String[] rows = result.split(":");
                        String ssn = rows[1];

                        //Below two lines allow to move from one screen to another on button click
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        intent.putExtra("info", ssn);
                        startActivity(intent);

                    }
                }, null);
                queue.add(request3);

            }
        });

        String logout = "Enter Username and Password to Login!";
        Toast.makeText(MainActivity.this,logout, Toast.LENGTH_LONG).show();

    }
}