package com.example.javacourseworkandoid.activities;

import static com.example.javacourseworkandoid.utils.Constants.CREATE_BASIC_USER_URL;
import static com.example.javacourseworkandoid.utils.Constants.CREATE_DRIVER_URL;
import static com.example.javacourseworkandoid.utils.Constants.CREATE_NEW_FOOD_ORDER;
import static com.example.javacourseworkandoid.utils.Constants.GET_BUYERS_ORDERS_URL;
import static com.example.javacourseworkandoid.utils.Constants.GET_RESTAURANT_FOOD_ITEMS_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseBooleanArray;
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
import com.example.javacourseworkandoid.model.BasicUser;
import com.example.javacourseworkandoid.model.Driver;
import com.example.javacourseworkandoid.model.FoodItem;
import com.example.javacourseworkandoid.model.FoodOrder;
import com.example.javacourseworkandoid.model.VechicleType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MenuActivity extends AppCompatActivity {

    private int userId;
    private int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        restaurantId = intent.getIntExtra("restaurantId", 0);
        userId = intent.getIntExtra("userId", 0);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()-> {
            try {
                String response = RestOperations.sendGet(GET_RESTAURANT_FOOD_ITEMS_URL + restaurantId);
                handler.post(() -> {
                    try {
                        System.out.println(response);
                        if (!response.equals("Error!")) {

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

                            Gson gsonFoodItems = gsonBuilder.setPrettyPrinting().create();
                            Type foodItemListType = new TypeToken<List<FoodItem>>() {
                            }.getType();
                            List<FoodItem> foodItemsListFromJson = gsonFoodItems.fromJson(response, foodItemListType);

                            ListView foodItemListElement = findViewById(R.id.restaurantMenuList);
                            foodItemListElement.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                            foodItemListElement.setItemsCanFocus(false);
                            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, foodItemsListFromJson);
                            foodItemListElement.setAdapter(adapter);

                            foodItemListElement.setOnItemClickListener((parent, view, position, id)->{
                                TextView totalPrice = findViewById(R.id.totalPriceView);
                                Double price = 0.;
                                SparseBooleanArray checked = foodItemListElement.getCheckedItemPositions();

                                for (int i = 0; i < checked.size(); i++) {
                                    int key = checked.keyAt(i);
                                    if (checked.valueAt(i)) {
                                        FoodItem item = (FoodItem) foodItemListElement.getItemAtPosition(key);
                                        price += item.getPrice();
                                    }
                                }

                                totalPrice.setText("Total Price: "+price);
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void createNewOrder(View view) {
        String orderInfo;
        ListView foodItemListElement = findViewById(R.id.restaurantMenuList);
        TextView totalPrice = findViewById(R.id.totalPriceView);
        Double price = Double.valueOf(totalPrice.getText().toString().split(": ")[1]);
        List<FoodItem> foodItems = new ArrayList<>();

        SparseBooleanArray checked = foodItemListElement.getCheckedItemPositions();
        for (int i = 0; i < checked.size(); i++) {
            int key = checked.keyAt(i);
            if (checked.valueAt(i)) {
                FoodItem item = (FoodItem) foodItemListElement.getItemAtPosition(key);
                foodItems.add(item);
            }
        }

        GsonBuilder build = new GsonBuilder();
        build.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        build.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = build.setPrettyPrinting().create();

        JsonObject orderJson = new JsonObject();
        orderJson.addProperty("userId", userId);
        orderJson.addProperty("restaurantId", restaurantId);
        orderJson.addProperty("price", price);
        orderJson.add("foodItems", gson.toJsonTree(foodItems));

        orderInfo = gson.toJson(orderJson);
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            try {
                String response = RestOperations.sendPost(CREATE_NEW_FOOD_ORDER,orderInfo);
                System.out.println(response);
                handler.post(()->{
                    if(!response.equals("Error!")){
                        Intent thisIntent = getIntent();
                        String userInfo = thisIntent.getStringExtra("userInfo");
                        Intent intent = new Intent(MenuActivity.this, WoltRestaurants.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("userJsonObject", userInfo);
                        startActivity(intent);
                    }
                });
            } catch (IOException e) {
                System.out.println("epic fail "+e.getMessage());
            }

        });
    }
}