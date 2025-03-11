package com.example.project4_cs550;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String ssn = intent.getStringExtra("info");

        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
        String url = "http://10.0.2.2:8080/sleepyhollow/Info.jsp?ssn=" + ssn;

        StringRequest request1 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String result = s.trim();
                String[] rows = result.split(",");
                String name = rows[0];
                String totalsales = "$" + rows[1];

                TextView textViewName = findViewById(R.id.textViewName);
                TextView textViewTotalSales = findViewById(R.id.textViewTotalSales);

                textViewName.setText(name);
                textViewTotalSales.setText(totalsales);
            }
        }, null); // null if we sure we wont get any exception

        queue.add(request1);// Without this it will not work.
        ImageView imageViewEmployee = findViewById(R.id.imageViewEmployee);
        String imageurl = "http://10.0.2.2:8080/sleepyhollow/images/" + ssn + ".jpg";
        ImageRequest request2 = new ImageRequest(imageurl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageViewEmployee.setImageBitmap(bitmap);
            }
        }, 0, 0, null, null);
        queue.add(request2);

//        Transaction Button ========================
        Button buttonTransactions = findViewById(R.id.buttonTransactions);
        buttonTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Below two lines allow to move from one screen to another on button click
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("info", ssn);
                startActivity(intent);

            }
        });

        //        TransactionDetails Button ========================
        Button buttonTransactionDetails = findViewById(R.id.buttonTransactionDetails);
        buttonTransactionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity4_TransactionDetails.class);
                intent.putExtra("info", ssn);
                startActivity(intent);
            }
        });


        //        Award Details Button ========================
        Button buttonAwardDetails = findViewById(R.id.buttonAwardDetails);
        buttonAwardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity5_GrantedDetails.class);
                intent.putExtra("info", ssn);
                startActivity(intent);
            }
        });

        //        Transfer Button ========================
        Button buttonTransfer = findViewById(R.id.buttonTransfer);
        buttonTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity6_Transfer.class);
                intent.putExtra("info", ssn);
                startActivity(intent);
            }
        });

        //        Transfer Button ========================
        Button buttonExit = findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
////                intent.putExtra("Enter Username and Password ");
//                startActivity(intent);
                finish();
            }
        });


    }
}