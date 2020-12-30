package com.example.myhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class AppointmentActivity extends AppCompatActivity {

    WebView webView;
    Button payBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_appointment );
        payBtn = findViewById( R.id.pay_btn );
        String url = "https://calendly.com/hethgala/appointment";
        webView = findViewById( R.id.appoint_view );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.loadUrl( url );

        payBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl( "https://securehealth.netlify.app/payment" );
            }
        } );

    }
}