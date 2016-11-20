package com.project.parcvehicule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class Start extends AppCompatActivity {
    static private int SPLASH_TIME = 6000;

    // Session Manager Class
  private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Session class instance
        session = new SessionManager(Start.this);

        session.checkLogin();


        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
            //Message = "connecter a Internet 3G ";
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent mainIntent1 = new Intent(Start.this, Login.class);

                    startActivity(mainIntent1);
                    finish();
                }

            }, SPLASH_TIME);


        } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
            //Message = "connecter a Internet WIFI ";
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent mainIntent1 = new Intent(Start.this, Login.class);

                    startActivity(mainIntent1);
                    finish();
                }

            }, SPLASH_TIME);


        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("SVP Activer Internet !");
            alertDialogBuilder.setPositiveButton("Actualiser", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent mainIntent1 = new Intent(Start.this, Login.class);

                    startActivity(mainIntent1);
                    finish();

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }



    }

}
