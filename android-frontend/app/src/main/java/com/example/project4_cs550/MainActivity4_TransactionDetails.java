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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity4_TransactionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_activity4_transaction_details);

        Spinner spinner=findViewById(R.id.spinnertxnid);
        ArrayList<String> list = new ArrayList<String>();

        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewAmount = findViewById(R.id.textViewTxnAmt);
        TextView textViewDisplay = findViewById(R.id.textViewDisplayA4);

        Intent intent = getIntent();
        String ssn = intent.getStringExtra("info");
        //Toast.makeText(MainActivity4_TransactionDetails.this, ssn, Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(MainActivity4_TransactionDetails.this);
        String url = "http://10.0.2.2:8080/sleepyhollow/Transactions.jsp?ssn="+ ssn;
        StringRequest request1 = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                String result = s.trim();

                String[] transactions = result.split("#");
                //Toast.makeText(MainActivity4_TransactionDetails.this, result, Toast.LENGTH_LONG).show();
//                Toast.makeText(MainActivity3.this, transactions.toString(), Toast.LENGTH_LONG).show();
                for (String entry : transactions) {
                    String[] fields = entry.split(",");
                    if (fields.length == 3) {
                        String transactionId = fields[0];
                        list.add(transactionId);
                    }
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity4_TransactionDetails.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
                spinner.setAdapter(adapter);

            }
        }, null);
        queue.add(request1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String txnid = adapterView.getSelectedItem().toString();
                String url1 = "http://10.0.2.2:8080/sleepyhollow/TransactionDetails.jsp?txnid="+ txnid;
                StringRequest request2 = new StringRequest(StringRequest.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        StringBuilder formattedOutput = new StringBuilder();

// Updated header with increased spacing
                        formattedOutput.append(String.format("%-27s %-27s %-27s\n", "Prod Name", "Price", "Qty"));

// Dashes line with the correct length to match updated headers
                        String dashes = new String(new char[formattedOutput.length()-1]).replace("\0", "-");
                        formattedOutput.append(dashes).append("\n");

                        String result = s.trim();
                        String[] transactions = result.split("#");

                        String txnDate = "";
                        String txnAmount = "";

                        for (String entry : transactions) {
                            String[] fields = entry.split(",");
                            if (fields.length == 5) {
                                txnDate = fields[0].split(" ")[0];
                                txnAmount = fields[1];
                                String prodName = fields[2];
                                String prodPrice = fields[3];
                                String quantity = fields[4];

                                // Ensuring data columns align with updated header spacing
                                formattedOutput.append(String.format("%-27s %-27s %-27s\n", prodName, prodPrice, quantity));
                            }
                        }

                        textViewDate.setText(txnDate);
                        textViewAmount.setText("$" + txnAmount);
                        formattedOutput.append(dashes); // Append the dashes at the end again

// Set the formatted text to the TextView
                        textViewDisplay.setText(formattedOutput);

                    }
                }, null);
                queue.add(request2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button buttonBackA4 = findViewById(R.id.buttonBackA4);
        buttonBackA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity4_TransactionDetails.this, MainActivity2.class);
////                intent.putExtra("Enter Username and Password ");
//                startActivity(intent);

                finish();
            }
        });

    }
}