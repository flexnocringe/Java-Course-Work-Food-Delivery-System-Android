package com.example.javacourseworkandoid.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.javacourseworkandoid.utils.Constants.CREATE_BASIC_USER_URL;
import static com.example.javacourseworkandoid.utils.Constants.CREATE_DRIVER_URL;
import static com.example.javacourseworkandoid.utils.Constants.UPDATE_USER_BY_ID;
import static com.example.javacourseworkandoid.utils.Constants.VALIDATE_USER_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseworkandoid.R;
import com.example.javacourseworkandoid.model.BasicUser;
import com.example.javacourseworkandoid.model.Driver;
import com.example.javacourseworkandoid.model.VechicleType;
import com.example.javacourseworkandoid.utils.LocalDateSerializer;
import com.example.javacourseworkandoid.utils.LocalDateTimeSerializer;
import com.example.javacourseworkandoid.utils.RestOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegistrationActivity extends AppCompatActivity {
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button updateButton = findViewById(R.id.updateUserButton);
        Button createUserButton = findViewById(R.id.create_acc_btn);
        updateButton.setVisibility(INVISIBLE);
        updateButton.setEnabled(false);
        createUserButton.setVisibility(VISIBLE);
        createUserButton.setEnabled(true);
        Intent intent = getIntent();
        boolean isForUpdate = intent.getBooleanExtra("isForUpdate", false);
        if(isForUpdate){
            fillForUpdate();
        }
    }

    private void fillForUpdate() {
        Button updateButton = findViewById(R.id.updateUserButton);
        Button createUserButton = findViewById(R.id.create_acc_btn);
        updateButton.setVisibility(VISIBLE);
        updateButton.setEnabled(true);
        createUserButton.setVisibility(INVISIBLE);
        createUserButton.setEnabled(false);

        TextView username = findViewById(R.id.username_field);
        TextView password = findViewById(R.id.password_field);
        TextView name = findViewById(R.id.name_field);
        TextView surname = findViewById(R.id.surname_field);
        TextView address = findViewById(R.id.address_field);
        TextView phone = findViewById(R.id.phone_num_field);
        TextView license = findViewById(R.id.license_field);
        TextView birthDate = findViewById(R.id.b_date_field);
        TextView vechicle = findViewById(R.id.vechicle_field);
        CheckBox isDriver = findViewById(R.id.is_driver_check);

        Intent intent = getIntent();


            String userInfo = intent.getStringExtra("userInfo");
            GsonBuilder build = new GsonBuilder();
            build.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
            build.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            Gson gson = build.setPrettyPrinting().create();
            BasicUser user = gson.fromJson(userInfo, BasicUser.class);
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            name.setText(user.getName());
            surname.setText(user.getSurname());
            address.setText(user.getAddress());
            phone.setText(user.getPhoneNumber());
    }

    public void createNewUser(View view) {
        TextView username = findViewById(R.id.username_field);
        TextView password = findViewById(R.id.password_field);
        TextView name = findViewById(R.id.name_field);
        TextView surname = findViewById(R.id.surname_field);
        TextView address = findViewById(R.id.address_field);
        TextView phone = findViewById(R.id.phone_num_field);
        TextView license = findViewById(R.id.license_field);
        TextView birthDate = findViewById(R.id.b_date_field);
        TextView vechicle = findViewById(R.id.vechicle_field);
        CheckBox isDriver = findViewById(R.id.is_driver_check);

        String userInfo;
        GsonBuilder build = new GsonBuilder();
        build.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        build.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = build.setPrettyPrinting().create();
        if(isDriver.isChecked()){
            Driver driver = new Driver(username.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), phone.getText().toString(), LocalDateTime.now(), address.getText().toString(), license.getText().toString(), LocalDate.parse( birthDate.getText().toString()), VechicleType.valueOf(vechicle.getText().toString()));
            userInfo = gson.toJson(driver);
        } else {
            BasicUser user = new BasicUser(username.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), phone.getText().toString(), LocalDateTime.now(), address.getText().toString());
            userInfo = gson.toJson(user);
        }
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            try {
                String response;
                if(isDriver.isChecked())
                {
                    response = RestOperations.sendPost(CREATE_DRIVER_URL, userInfo);
                } else {
                    response = RestOperations.sendPost(CREATE_BASIC_USER_URL, userInfo);
                }
                System.out.println(response);
                handler.post(()->{
                    if(!response.equals("Error!")){
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (IOException e) {
                System.out.println("epic fail "+e.getMessage());
            }

        });
    }

    public void updateUser(View view) {
        TextView username = findViewById(R.id.username_field);
        TextView password = findViewById(R.id.password_field);
        TextView name = findViewById(R.id.name_field);
        TextView surname = findViewById(R.id.surname_field);
        TextView address = findViewById(R.id.address_field);
        TextView phone = findViewById(R.id.phone_num_field);
        TextView license = findViewById(R.id.license_field);
        TextView birthDate = findViewById(R.id.b_date_field);
        TextView vechicle = findViewById(R.id.vechicle_field);
        CheckBox isDriver = findViewById(R.id.is_driver_check);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);
        String userInfo;
        GsonBuilder build = new GsonBuilder();
        build.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        build.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = build.setPrettyPrinting().create();
        if(isDriver.isChecked()){
            Driver driver = new Driver(username.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), phone.getText().toString(), LocalDateTime.now(), address.getText().toString(), license.getText().toString(), LocalDate.parse( birthDate.getText().toString()), VechicleType.valueOf(vechicle.getText().toString()));
            userInfo = gson.toJson(driver);
        } else {
            BasicUser user = new BasicUser(username.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString(), phone.getText().toString(), LocalDateTime.now(), address.getText().toString());
            userInfo = gson.toJson(user);
        }
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            try {
                String response;
                if(isDriver.isChecked())
                {
                    response = RestOperations.sendPost(CREATE_DRIVER_URL, userInfo); // No no no
                } else {
                    response = RestOperations.sendPut(UPDATE_USER_BY_ID+userId, userInfo);
                }
                System.out.println(response);
            } catch (IOException e) {
                System.out.println("epic fail "+e.getMessage());
            }

        });
    }

    public void goToPreviousWindow(View view) {
        Intent thisIntent = getIntent();
        boolean isForUpdate = thisIntent.getBooleanExtra("isForUpdate", false);
        if(isForUpdate){
            Intent prevIntent = new Intent(RegistrationActivity.this, WoltRestaurants.class);
            prevIntent.putExtra("userId" ,thisIntent.getIntExtra("userId", 0));
            prevIntent.putExtra("userJsonObject", thisIntent.getStringExtra("userInfo"));
            startActivity(prevIntent);
        } else {
            Intent prevIntent = new Intent (RegistrationActivity.this, MainActivity.class);
            startActivity(prevIntent);
        }
    }
}