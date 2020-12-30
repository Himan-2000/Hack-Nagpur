package com.example.myhealth;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRActivity extends AppCompatActivity {

    ImageView disp_code;
    Button gen_code;
    Bitmap qrBits;
    QRGEncoder qrgEncoder;
    String t;
    public static final int CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_qr_generator );
        disp_code = findViewById( R.id.disp_qr );
        disp_code.setVisibility( View.INVISIBLE );
        gen_code = findViewById( R.id.generate );

        gen_code.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayQR();
            }
        } );

    }
        public void displayQR(){
    //        code = inp_code.getText().toString();
            String registerPref="bundle",
                    loggedIn="logStatus",
                    token="token",
                    namePref="name",
                    agePrefs="age",
                    emailPrefs="email",
                    phonePrefs="phone";
            SharedPreferences sharedPreferences = getSharedPreferences( registerPref,Context.MODE_PRIVATE );
            String phoneS = sharedPreferences.getString( phonePrefs,null );
            if(!(phoneS.equals( null ))){
                qrgEncoder = new QRGEncoder( phoneS,null, QRGContents.Type.TEXT,1000 );
                try {
                    qrBits = qrgEncoder.encodeAsBitmap();
                    dispQR.start();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText( this, "Phone No Missing!", Toast.LENGTH_SHORT ).show();
            }


    }
    Thread dispQR = new Thread(  ){
        @Override
        public void run() {
            super.run();

            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    disp_code.setImageBitmap( qrBits );
                    disp_code.setVisibility( View.VISIBLE );
                    //Toast.makeText( QRGenerator.this, t, Toast.LENGTH_SHORT ).show();
                }
            } );

        }
    };

}