package com.example.studentregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etFirstname, etMiddlename, etLastname, etAge, etAddress;
    Button btnInsertStudent, btnDisplayStudents;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NukeSSLCerts.Nuke.nuke();

        etFirstname = findViewById(R.id.etFirstname);
        etMiddlename = findViewById(R.id.etMiddlename);
        etLastname = findViewById(R.id.etLastname);
        etAge = findViewById(R.id.etAddress);
        etAddress = findViewById(R.id.etAddress);
        btnInsertStudent = findViewById(R.id.btnInsertStudent);
        btnDisplayStudents = findViewById(R.id.btnDisplayStudents);

        btnInsertStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertStudent();
            }
        });

        btnDisplayStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayStudents();
            }
        });
    }

    private void displayStudents() {
        startActivity(new Intent(getApplicationContext(), DisplayStudentsActivity.class));
    }

    private void insertStudent() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.POST,
                "https://192.168.254.108/samplerest/insertstudent.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    response,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("firstname", etFirstname.getText().toString());
                map.put("middlename", etMiddlename.getText().toString());
                map.put("lastname", etLastname.getText().toString());
                map.put("age", etAge.getText().toString());
                map.put("address", etAddress.getText().toString());

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}