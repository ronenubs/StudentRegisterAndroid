package com.example.studentregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayStudentsActivity extends AppCompatActivity {

    ListView lvDisplayStudents;

    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayAdapter adapter;
    ArrayList<Student> studentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students);
        NukeSSLCerts.Nuke.nuke();

        studentsList = new ArrayList<>();
        loadStudents();
        lvDisplayStudents = findViewById(R.id.lvDisplayStudents);

        lvDisplayStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),
//                        String.valueOf(studentsList.get(i).getStudentID()),
//                        Toast.LENGTH_LONG).show();
                confirmDelete(studentsList.get(i).getStudentID());
            }
        });

    }

    private void confirmDelete(final int a) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //dialogBuilder.setMessage("Delete student");
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteStudent(a);
            }
        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setTitle("Delete student with id# " + a);
        alertDialog.show();


    }


    private void deleteStudent(final int i) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.POST,
                "https://192.168.254.108/samplerest/deletestudent.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(),
                                    "Student with id#" + i + " is successfully removed.",
                                    Toast.LENGTH_LONG).show();
                            adapter.notifyDataSetChanged();
                            studentsList.clear();
                            loadStudents();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("ERROR DELETING: ", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("studentID", String.valueOf(i));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void loadStudents() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.GET,
                "https://192.168.254.108/samplerest/displaystudents.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                studentsList.add(
                                        new Student(
                                                jsonObject.getInt("studentID"),
                                                jsonObject.getString("firstname"),
                                                jsonObject.getString("middlename"),
                                                jsonObject.getString("lastname"),
                                                jsonObject.getString("address"),
                                                jsonObject.getInt("age")
                                        )
                                );
                            }

                            adapter = new StudentAdapter(
                                    getApplicationContext(),
                                    studentsList
                            );


                            lvDisplayStudents.setAdapter(adapter);

                            Log.v("JSON Array:", jsonArray.toString());
                            //Log.v("LENGTH:", String.valueOf(obj.length()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);
    }
}