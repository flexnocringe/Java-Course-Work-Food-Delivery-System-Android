package com.example.javacourseworkandoid;

import static com.example.javacourseworkandoid.Constants.GET_ALL_RESTAURANTS_URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javacourseworkandoid.model.BasicUser;
import com.example.javacourseworkandoid.model.Driver;
import com.example.javacourseworkandoid.model.Restaurant;
import com.example.javacourseworkandoid.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WoltRestaurants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wolt_restautants);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.restaurants), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userJsonObject");
        Gson gson = new Gson();

        var connectedUser = gson.fromJson(userInfo, User.class);

        if(connectedUser instanceof Driver){

        } else if(connectedUser instanceof Restaurant){
            System.out.println("This is not for you punk");
        } else if (connectedUser instanceof BasicUser){
            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(()->{
                try {
                    String response = RestOperations.sendGet(GET_ALL_RESTAURANTS_URL);
                    handler.post(()->{
                        try{
                            if(!response.equals("Error!")){
                                Gson gsonRestaurants = new Gson();
                                Type restaurantListType = new TypeToken<List<Restaurant>>(){}.getType();
                                List<Restaurant> restaurantListFromJson = gsonRestaurants.fromJson(response, restaurantListType);

                                ListView restaurantListElement = findViewById(R.id.restaurant_list);
                                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurantListFromJson);
                                restaurantListElement.setAdapter(adapter);

                                restaurantListElement.setOnItemClickListener((parent, view, position, id)->{
                                    System.out.println(restaurantListFromJson.get(position));
                                    Intent menuIntent = new Intent(WoltRestaurants.this, MenuActivity.class);
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
    }

    public void viewMyAccount(View view) {
    }
}