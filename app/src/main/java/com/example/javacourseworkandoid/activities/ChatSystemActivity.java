package com.example.javacourseworkandoid.activities;

import static com.example.javacourseworkandoid.utils.Constants.GET_BUYERS_ORDERS_URL;
import static com.example.javacourseworkandoid.utils.Constants.GET_ORDER_MESSAGES_URL;
import static com.example.javacourseworkandoid.utils.Constants.SEND_MESSAGE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseworkandoid.R;
import com.example.javacourseworkandoid.model.FoodOrder;
import com.example.javacourseworkandoid.model.Review;
import com.example.javacourseworkandoid.utils.LocalDateSerializer;
import com.example.javacourseworkandoid.utils.LocalDateTimeSerializer;
import com.example.javacourseworkandoid.utils.RestOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatSystemActivity extends AppCompatActivity {

    private int userId;
    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_system);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadMessages();
    }

    private void loadMessages() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        orderId = intent.getIntExtra("orderId", 0);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            try {
                String response = RestOperations.sendGet(GET_ORDER_MESSAGES_URL+orderId);
                handler.post(()->{
                    try{
                        System.out.println(response);
                        if(!response.equals("Error!")){

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
                            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
                            Gson gsonMessages = gsonBuilder.setPrettyPrinting().create();
                            Type reviewListType = new TypeToken<List<Review>>(){}.getType();
                            List<Review> reviewListFromJson = gsonMessages.fromJson(response, reviewListType);

                            ListView messagesList = findViewById(R.id.message_list);
                            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, reviewListFromJson);
                            messagesList.setAdapter(adapter);

                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                System.out.println("No bizniss "+e.getMessage());
            }
        });
    }

    public void sendMessage(View view) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        TextView messageBody = findViewById(R.id.messageField);

        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", userId);
        jsonObject.addProperty("orderId", orderId);
        jsonObject.addProperty("messageText", messageBody.getText().toString());

        String message = gson.toJson(jsonObject);

        executor.execute(() -> {
            try {
                String response = RestOperations.sendPost(SEND_MESSAGE, message);
                System.out.println(response);
                handler.post(() -> {
                    try {
                        if (!response.equals("Error")) {
                            loadMessages();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}