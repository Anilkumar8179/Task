package com.anilkumar.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText email, phoneNumber;
    private Button addBtn;
    private RecyclerView recyclerView;

    private Adapter adapter;
    private ArrayList<Model> courseModalArrayList;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        addBtn = findViewById(R.id.idBtnAdd);
        recyclerView = findViewById(R.id.idRVCourses);
        loadData();


        buildRecyclerView();
        validation(email, phoneNumber);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseModalArrayList.add(new Model(email
                        .getText().toString(), phoneNumber.getText().toString()));
                adapter.notifyItemInserted(courseModalArrayList.size());


            }
        });

    }


    private boolean validation(EditText email, EditText phoneNumber) {
        String emailToText = email.getText().toString();
        String phonenumber = phoneNumber.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
        }

        if (phoneNumber.getText().toString().isEmpty() && Patterns.PHONE.matcher(phonenumber).matches()) {
        } else {
            Toast.makeText(MainActivity.this, "Please enter mobile number ", Toast.LENGTH_LONG).show();
        }
        return false;

    }





        private void buildRecyclerView () {
            adapter = new Adapter(courseModalArrayList, MainActivity.this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }

        private void loadData () {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("courses", null);
            Type type = new TypeToken<ArrayList<Model>>() {
            }.getType();
            courseModalArrayList = gson.fromJson(json, type);
            if (courseModalArrayList == null) {

                courseModalArrayList = new ArrayList<>();
            }
        }


    }

