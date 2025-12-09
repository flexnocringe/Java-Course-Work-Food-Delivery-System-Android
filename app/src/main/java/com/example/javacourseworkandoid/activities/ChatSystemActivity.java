package com.example.javacourseworkandoid.activities;

import static com.example.javacourseworkandoid.utils.Constants.GET_BUYERS_ORDERS_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseworkandoid.R;
import com.example.javacourseworkandoid.model.FoodOrder;
import com.example.javacourseworkandoid.utils.LocalDateTimeSerializer;
import com.example.javacourseworkandoid.utils.RestOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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
                String response = RestOperations.sendGet("");
                handler.post(()->{
                    try{
                        System.out.println(response);
                        if(!response.equals("Error!")){

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

                            Gson gsonRestaurants = gsonBuilder.setPrettyPrinting().create();
                            Type foodOrderListType = new TypeToken<List<FoodOrder>>(){}.getType();
                            List<FoodOrder> foodOrderListFromJson = gsonRestaurants.fromJson(response, foodOrderListType);

                            ListView foodOrderListElement = findViewById(R.id.orders_list);
                            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foodOrderListFromJson);
                            foodOrderListElement.setAdapter(adapter);

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
}