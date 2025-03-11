package com.example.project4_cs550;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ArrayList<String> list = new ArrayList<String>();

        TextView textViewDisplay = findViewById(R.id.textViewDisplay);
//        Button buttonBackA3 = findViewById(R.id.buttonBackA3);

        Intent intent = getIntent();
        String ssn = intent.getStringExtra("info");
        //Toast.makeText(MainActivity3.this, ssn, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity3.this);
        String url = "http://10.0.2.2:8080/sleepyhollow/Transactions.jsp?ssn="+ ssn;
        StringRequest request1 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                StringBuilder formattedOutput = new StringBuilder();

                formattedOutput.append(String.format("%-20s %-20s %-40s\n", "Txn ID", "Date", "Amount"));

                String dashes = new String(new char[formattedOutput.length()]).replace("\0", "-");
                formattedOutput.append(dashes).append("\n");

                String result = s.trim();

                String[] transactions = result.split("#");
                //Toast.makeText(MainActivity3.this, result, Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity3.this, transactions.toString(), Toast.LENGTH_LONG).show();
                for (String entry : transactions) {
                    String[] fields = entry.split(",");
                    if (fields.length == 3) {
                        String transactionId = fields[0];
                        String date = fields[1].split(" ")[0];
                        String amount = fields[2];
                        formattedOutput.append(String.format("%-20s %-20s %-40s\n", transactionId, date, amount));
                        list.add(transactionId);
                    }
                }
                //Toast.makeText(MainActivity3.this, formattedOutput, Toast.LENGTH_LONG).show();
                textViewDisplay.setText(formattedOutput);

            }
        }, null);
        queue.add(request1);

        Button buttonBackA3 = findViewById(R.id.buttonBackA3);
        buttonBackA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
////                intent.putExtra("Enter Username and Password ");
//                startActivity(intent);
                finish();
            }
        });

    }
}