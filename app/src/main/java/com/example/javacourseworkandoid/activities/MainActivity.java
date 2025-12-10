package com.example.javacourseworkandoid.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.javacourseworkandoid.utils.Constants.COMPLETE_ORDER;
import static com.example.javacourseworkandoid.utils.Constants.GET_ALL_RESTAURANTS_URL;
import static com.example.javacourseworkandoid.utils.Constants.GET_IN_DELIVERY_ORDERS;
import static com.example.javacourseworkandoid.utils.Constants.GET_READY_FOR_PICKUP_ORDERS;
import static com.example.javacourseworkandoid.utils.Constants.PICKUP_ORDER;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseworkandoid.R;
import com.example.javacourseworkandoid.model.BasicUser;
import com.example.javacourseworkandoid.model.Driver;
import com.example.javacourseworkandoid.model.FoodOrder;
import com.example.javacourseworkandoid.model.Restaurant;
import com.example.javacourseworkandoid.model.User;
import com.example.javacourseworkandoid.utils.LocalDateSerializer;
import com.example.javacourseworkandoid.utils.LocalDateTimeSerializer;
import com.example.javacourseworkandoid.utils.RestOperations;
import com.example.javacourseworkandoid.utils.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private int userId;
    private String userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.restaurants), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RuntimeTypeAdapterFactory<User> userAdapter = RuntimeTypeAdapterFactory
                .of(User.class, "type")
                .registerSubtype(Driver.class, "Driver")
                .registerSubtype(BasicUser.class, "BasicUser")
                .registerSubtype(User.class, "User");

        Intent intent = getIntent();
        userInfo = intent.getStringExtra("userJsonObject");
        userId = intent.getIntExtra("userId", 0);
        Gson gson = new Gson();
        GsonBuilder build = new GsonBuilder();
        build.registerTypeAdapterFactory(userAdapter);
        build.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        build.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gson = build.setPrettyPrinting().create();
        User connectedUser = gson.fromJson(userInfo, User.class);
        if(userId==0) {
            userId = connectedUser.getId();
        }

        if(connectedUser instanceof Driver){
            ListView restaurantListElement = findViewById(R.id.restaurant_list);
            restaurantListElement.setEnabled(false);
            restaurantListElement.setVisibility(INVISIBLE);
            Button viewHistoryButton = findViewById(R.id.history_btn);
            viewHistoryButton.setEnabled(false);
            viewHistoryButton.setVisibility(INVISIBLE);
            Button pickupOrderButton = findViewById(R.id.startDeliveryButton);
            pickupOrderButton.setEnabled(true);
            pickupOrderButton.setVisibility(VISIBLE);
            Button completeOrderButton = findViewById(R.id.completeOrderButton);
            completeOrderButton.setEnabled(true);
            completeOrderButton.setVisibility(VISIBLE);
            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(()->{
                try {
                    String response = RestOperations.sendGet(GET_READY_FOR_PICKUP_ORDERS);
                    handler.post(()->{
                        try{
                            System.out.println(response);
                            if(!response.equals("Error!")){

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

                                Gson gsonOrders = gsonBuilder.setPrettyPrinting().create();
                                Type foodOrderListType = new TypeToken<List<FoodOrder>>(){}.getType();
                                List<FoodOrder> readyForPickupOrders = gsonOrders.fromJson(response, foodOrderListType);
                                ListView readyForPickupOrderListView = findViewById(R.id.readyForPickupOrders);
                                readyForPickupOrderListView.setEnabled(true);
                                readyForPickupOrderListView.setVisibility(VISIBLE);
                                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, readyForPickupOrders);
                                readyForPickupOrderListView.setAdapter(adapter);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    System.out.println("No bizniss "+e.getMessage());
                }
            });

            executor.execute(()->{
                try {
                    String response = RestOperations.sendGet(GET_IN_DELIVERY_ORDERS);
                    handler.post(()->{
                        try{
                            System.out.println(response);
                            if(!response.equals("Error!")){

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

                                Gson gsonOrders = gsonBuilder.setPrettyPrinting().create();
                                Type foodOrderListType = new TypeToken<List<FoodOrder>>(){}.getType();
                                List<FoodOrder> inDeliveryOrders = gsonOrders.fromJson(response, foodOrderListType);
                                ListView inDeliveryOrdersListView = findViewById(R.id.inDeliveryOrders);
                                inDeliveryOrdersListView.setEnabled(true);
                                inDeliveryOrdersListView.setVisibility(VISIBLE);
                                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, inDeliveryOrders);
                                inDeliveryOrdersListView.setAdapter(adapter);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    System.out.println("No bizniss "+e.getMessage());
                }
            });

        } else if (connectedUser instanceof BasicUser){
            Button pickupOrderButton = findViewById(R.id.startDeliveryButton);
            pickupOrderButton.setEnabled(false);
            pickupOrderButton.setVisibility(INVISIBLE);
            Button completeOrderButton = findViewById(R.id.completeOrderButton);
            completeOrderButton.setEnabled(false);
            completeOrderButton.setVisibility(INVISIBLE);
            Button viewHistoryButton = findViewById(R.id.history_btn);
            viewHistoryButton.setEnabled(true);
            viewHistoryButton.setVisibility(VISIBLE);
            ListView readyForPickupOrderListView = findViewById(R.id.readyForPickupOrders);
            readyForPickupOrderListView.setEnabled(false);
            readyForPickupOrderListView.setVisibility(INVISIBLE);
            ListView inDeliveryOrdersListView = findViewById(R.id.inDeliveryOrders);
            inDeliveryOrdersListView.setEnabled(false);
            inDeliveryOrdersListView.setVisibility(INVISIBLE);
            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(()->{
                try {
                    String response = RestOperations.sendGet(GET_ALL_RESTAURANTS_URL);
                    handler.post(()->{
                        try{
                            System.out.println(response);
                            if(!response.equals("Error!")){

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

                                Gson gsonRestaurants = gsonBuilder.setPrettyPrinting().create();
                                Type restaurantListType = new TypeToken<List<Restaurant>>(){}.getType();
                                List<Restaurant> restaurantListFromJson = gsonRestaurants.fromJson(response, restaurantListType);

                                ListView restaurantListElement = findViewById(R.id.restaurant_list);
                                restaurantListElement.setEnabled(true);
                                restaurantListElement.setVisibility(VISIBLE);
                                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, restaurantListFromJson);
                                restaurantListElement.setAdapter(adapter);

                                restaurantListElement.setOnItemClickListener((parent, view, position, id)->{
                                    Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                                    menuIntent.putExtra("restaurantId", restaurantListFromJson.get(position).getId());
                                    menuIntent.putExtra("userId", userId);
                                    menuIntent.putExtra("userInfo", userInfo);
                                    startActivity(menuIntent);
                                });
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

    public void viewPurchaseHistory(View view) {
        Intent intent = new Intent(MainActivity.this, MyOrdersActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userJsonObject", userInfo);
        startActivity(intent);
    }

    public void viewMyAccount(View view) {
        Intent intentForUpdate = new Intent(MainActivity.this, RegistrationActivity.class);
        intentForUpdate.putExtra("isForUpdate", true);
        intentForUpdate.putExtra("userInfo", userInfo);
        intentForUpdate.putExtra("userId", userId);
        startActivity(intentForUpdate);
    }

    public void pickupOrder(View view) {
        ListView foodOrderListReadyForPickup = findViewById(R.id.readyForPickupOrders);
        int position = foodOrderListReadyForPickup.getCheckedItemPosition();
        Object selectedItem = foodOrderListReadyForPickup.getItemAtPosition(position);
        FoodOrder selectedOrder = (FoodOrder) selectedItem;
        int orderId = selectedOrder.getId();
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            try {
                String response = RestOperations.sendPut(PICKUP_ORDER+orderId, userInfo);
                handler.post(()->{
                    try{
                        System.out.println(response);
                        if(!response.equals("Error!")){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("userId", userId);
                            intent.putExtra("userJsonObject", userInfo);
                            startActivity(intent);
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

    public void completeOrder(View view) {
        ListView foodOrderListInDelivery = findViewById(R.id.inDeliveryOrders);
        int position = foodOrderListInDelivery.getCheckedItemPosition();
        Object selectedItem = foodOrderListInDelivery.getItemAtPosition(position);
        FoodOrder selectedOrder = (FoodOrder) selectedItem;
        int orderId = selectedOrder.getId();
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            try {
                String response = RestOperations.sendPut(COMPLETE_ORDER+orderId, "");
                handler.post(()->{
                    try{
                        System.out.println(response);
                        if(!response.equals("Error!")){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("userId", userId);
                            intent.putExtra("userJsonObject", userInfo);
                            startActivity(intent);
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