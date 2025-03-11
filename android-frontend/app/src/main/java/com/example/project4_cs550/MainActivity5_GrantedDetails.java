package com.example.project4_cs550;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity5_GrantedDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_activity5_granted_details);

        Spinner spinnerAwardIds = findViewById(R.id.spinnerAwardId);
        TextView textViewDisplayA5 = findViewById(R.id.textViewDisplayA5);
        ArrayList<String> list = new ArrayList<>();

        Intent intent = getIntent();
        String ssn = intent.getStringExtra("info");
        //Toast.makeText(MainActivity5_GrantedDetails.this, ssn, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity5_GrantedDetails.this);
        String url = "http://10.0.2.2:8080/sleepyhollow/AwardIds.jsp?ssn=" + ssn;
        StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String result = s.trim();

                String[] awardIds = result.split("#");
                //Toast.makeText(MainActivity5_GrantedDetails.this, awardIds.toString(), Toast.LENGTH_LONG).show();
                for (String entry : awardIds) {
                    list.add(entry);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity5_GrantedDetails.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                spinnerAwardIds.setAdapter(adapter);
            }
        }, null);
        queue.add(request1);

        spinnerAwardIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String awardid = adapterView.getSelectedItem().toString();
                String url1 = "http://10.0.2.2:8080/sleepyhollow/GrantedDetails.jsp?awardid="+awardid+"&ssn="+ssn;
                StringRequest request2 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        StringBuilder formattedOutput = new StringBuilder();

// Updated header with increased spacing
                        formattedOutput.append(String.format("%-42s %-42s\n", "Award Date", "Award Center"));

// Dashes line with the correct length to match updated headers
                        String dashes = new String(new char[formattedOutput.length()-1]).replace("\0", "-");
                        formattedOutput.append(dashes).append("\n");

                        String result = s.trim();
                        String[] transactions = result.split("#");

                        for (String entry : transactions) {
                            String[] fields = entry.split(",");
                            if (fields.length == 2) {
                                String awardDate = fields[0];
                                String centerName = fields[1];
                                formattedOutput.append(String.format("%-42s %-42s\n", awardDate, centerName));
                            }
                        }
                        formattedOutput.append(dashes); // Append the dashes at the end again

// Set the formatted text to the TextView
                        textViewDisplayA5.setText(formattedOutput);
                    }
                }, null);
                queue.add(request2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button buttonBackA5 = findViewById(R.id.buttonBackA5);
        buttonBackA5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity5_GrantedDetails.this, MainActivity2.class);
////                intent.putExtra("Enter Username and Password ");
//                startActivity(intent);
                finish();
            }
        });
    }
}