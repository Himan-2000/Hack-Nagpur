package com.example.myhealthdoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView qr_codeD;
    BarcodeDetector barcodeDetector;
    Button share;
    ProgressBar cred_progress;
    String scan_code="no";
    String registerPref="bundle",
            loggedIn="logStatus",
            token="token",
            mail="email",
            name="name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        surfaceView = findViewById( R.id.cameraSurface );
        qr_codeD = findViewById( R.id.scannedText );
        share = findViewById( R.id.share_btn );
        cred_progress = findViewById( R.id.cred_progress );
        cred_progress.setVisibility( View.INVISIBLE );
        share.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cred_progress.setVisibility( View.VISIBLE );
                sendCred();
            }
        } );
        barcodeDetector= new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats( Barcode.QR_CODE  )
                .build();
        cameraSource = new CameraSource.Builder( getApplicationContext(),barcodeDetector )
                .setRequestedPreviewSize( 640,480 )
                .setAutoFocusEnabled( true )
                .build();


        surfaceView.getHolder().addCallback( new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED){
                    return;
                }
                try{
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        } );
        barcodeDetector.setProcessor( new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if(qrcode.size()!=0){
                    qr_codeD.post( new Runnable() {
                        @Override
                        public void run() {
                            String qr = String.valueOf( qrcode.valueAt( 0 ) );
                            scan_code = qrcode.valueAt( 0 ).displayValue;
                            qr_codeD.setText( qrcode.valueAt( 0 ).displayValue);
                        }
                    } );
                }
            }
        } );

    }

    private void sendCred() {
        String doc_email,pat_ph;
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://health-care-auto.herokuapp.com/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        QRInterface uai = retrofit.create( QRInterface.class );
        SharedPreferences registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
        doc_email = registerData.getString( mail,null );
        if(!(doc_email.equals( null )) && !(scan_code.equals( "no" )))
        {
            pat_ph = scan_code;
            Call<QRResponseModel> call = uai.getConfirm(new QRModel( doc_email,scan_code));
            call.enqueue( new Callback<QRResponseModel>() {
                @Override
                public void onResponse(Call<QRResponseModel> call, Response<QRResponseModel> response) {
                    if(response.isSuccessful()){
                        String c_phone = response.body().getUpdatedUser().getContact();
                        if(scan_code.equals( c_phone )){
                            cred_progress.setVisibility( View.INVISIBLE );
                            Toast.makeText( MainActivity.this, "Response Submitted!", Toast.LENGTH_SHORT ).show();
                        }
                    }else{
                        cred_progress.setVisibility( View.INVISIBLE );
                        Toast.makeText( MainActivity.this, "Response Unsuccessful", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<QRResponseModel> call, Throwable t) {
                    cred_progress.setVisibility( View.INVISIBLE );
                    Toast.makeText( MainActivity.this, "Response Failed", Toast.LENGTH_SHORT ).show();
                }
            } );
        }else{
            cred_progress.setVisibility( View.INVISIBLE );
            Toast.makeText( this, "Data Missing", Toast.LENGTH_SHORT ).show();
        }



    }
}