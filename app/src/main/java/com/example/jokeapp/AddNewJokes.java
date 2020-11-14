package com.example.jokeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewJokes extends AppCompatActivity {
    private static final String CHANNEL_ID = "100CH";
    RequestQueue queue;
    String url ="https://official-joke-api.appspot.com/random_joke";
    TextView txtjokes,txtid,txttype,txtsetup,txtpunch;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_jokes);

        queue = Volley.newRequestQueue(this);
        txtjokes=findViewById(R.id.textJokes);
        txtid =findViewById(R.id.txtID);
        txttype=findViewById(R.id.txtType);
        txtsetup=findViewById(R.id.txtSetup);
        txtpunch=findViewById(R.id.txtPunch);

        progressBar = findViewById(R.id.progressBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public void getJokes(View view) {

        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        int ID=0;
                        String type,setup,punch;

                        try {
                            ID = response.getInt("id");
                            type=response.getString("type");
                            setup=response.getString("setup");
                            punch=response.getString("punchline");

                            Joke joke=new Joke(ID,type,setup,punch);

                            txtid.setText(joke.getID()+"");
                            txtid.setVisibility(View.VISIBLE);
                            txttype.setText(joke.getType()+"");
                            txttype.setVisibility(View.VISIBLE);
                            txtsetup.setText(joke.getSetup()+"");
                            txtsetup.setVisibility(View.VISIBLE);
                            txtpunch.setText(joke.getPunchline()+"");
                            txtpunch.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.INVISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        txtjokes.setText("Response: " +ID);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err=error.toString();
                        txtjokes.setText("Cannot get data: " +error.toString());

                    }


                });
        queue.add(jsonObjectRequest);
        sendNotification();


    }

    private void sendNotification() {
        Intent intent = new Intent(AddNewJokes.this, AddNewJokes.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.register)
                .setContentTitle("Joke")
                .setContentText("Added New joke sucessfully..")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent).setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0,builder.build());
    }
}
