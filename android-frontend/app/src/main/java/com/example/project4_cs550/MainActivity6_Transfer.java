package com.example.project4_cs550;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity6_Transfer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_activity6_transfer);

        EditText editTextSSN2 = findViewById(R.id.editTextDestId);
        EditText editTextAmount = findViewById(R.id.editTextAmount);
        Button buttonExecuteTransfer = findViewById(R.id.buttonExecuteTransfer);

        Intent intent = getIntent();
        String ssn = intent.getStringExtra("info");
//        Toast.makeText(MainActivity6_Transfer.this, ssn, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity6_Transfer.this);

        buttonExecuteTransfer.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String ssn2 = editTextSSN2.getText().toString();
                String amount = editTextAmount.getText().toString();

//                Toast.makeText(MainActivity6_Transfer.this, ssn2, Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity6_Transfer.this, amount, Toast.LENGTH_LONG).show();

                String url = "http://10.0.2.2:8080/sleepyhollow/Transfer.jsp?ssn1="+ssn+"&ssn2="+ssn2+"&amount="+amount;
                //Toast.makeText(MainActivity6_Transfer.this, url, Toast.LENGTH_LONG).show();

                // Proceed with the transfer since the amount is valid
                StringRequest request6 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String result = s.trim();
                        Toast.makeText(MainActivity6_Transfer.this,result,Toast.LENGTH_LONG).show();
                    }
                }, null);
                queue.add(request6);
          }
        });
        Button buttonBackA6 = findViewById(R.id.buttonBackA6);
        buttonBackA6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity6_Transfer.this, MainActivity2.class);
////                intent.putExtra("Enter Username and Password ");
//                startActivity(intent);
                finish();
            }
        });

    }
}