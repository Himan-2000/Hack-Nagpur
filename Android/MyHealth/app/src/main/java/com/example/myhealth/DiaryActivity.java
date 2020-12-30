package com.example.myhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiaryActivity extends AppCompatActivity {

    TextInputEditText startDate,endDate;
    Button sendDate;
    ProgressBar date_progress;
    String startD=null,endD=null;
    String registerPref="bundle",
            token="token",
            id="id";
    WebView webView;
    SharedPreferences registerData;
    ImageButton pick1,pick2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_diary );
        startDate = findViewById( R.id.edit_startD );
        endDate = findViewById( R.id.edit_endD );
        sendDate =findViewById( R.id.sendDate );
        startD = startDate.getText().toString();
        endD=endDate.getText().toString();
        date_progress = findViewById( R.id.date_progress );
        date_progress.setVisibility( View.INVISIBLE );

        pick1 = findViewById( R.id.pick_1 );
        pick2 = findViewById( R.id.pick_2 );

        pick1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear,mMonth,mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DiaryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                startD = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                                startDate.setText(startD);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        } );

        pick2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear,mMonth,mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DiaryActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                endD = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                                endDate.setText(endD);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        } );

        String url = "https://securehealth.netlify.app/diary/5f25665f970f6b00177ac54a";
        webView = findViewById( R.id.diary_view );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.loadUrl( url );

        sendDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_progress.setVisibility( View.VISIBLE );
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl( "https://health-care-auto.herokuapp.com/" )
                        .addConverterFactory( GsonConverterFactory.create() )
                        .build();
                registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
                String ids = registerData.getString( id,null );
                if( !(startD.equals( null )) && !(endD.equals( null )) && !(ids.equals( null ))  ){
                    DateInterface uai = retrofit.create( DateInterface.class );
                    Call<DateResponseModel> call = uai.getRecords(new DateModel(startD,endD,ids));
                    call.enqueue( new Callback<DateResponseModel>() {
                        @Override
                        public void onResponse(Call<DateResponseModel> call, Response<DateResponseModel> response) {
                            if(response.isSuccessful()){
                                if(ids.equals( response.body().getClientData().getUserId() )){
                                    Toast.makeText( DiaryActivity.this, "Dates Sent!", Toast.LENGTH_LONG ).show();
                                }else{
                                    Toast.makeText( DiaryActivity.this, "IDs didnt match!", Toast.LENGTH_SHORT ).show();
                                }
                                date_progress.setVisibility( View.INVISIBLE );
                            }else{
                                Toast.makeText( DiaryActivity.this, "Response Unsuccessful", Toast.LENGTH_SHORT ).show();
                                date_progress.setVisibility( View.INVISIBLE );
                            }
                        }

                        @Override
                        public void onFailure(Call<DateResponseModel> call, Throwable t) {
                            Toast.makeText( DiaryActivity.this, "Response Failure", Toast.LENGTH_SHORT ).show();
                            date_progress.setVisibility( View.INVISIBLE );
                        }
                    } );
                }else{
                    Toast.makeText( DiaryActivity.this, "Date/ID Missing", Toast.LENGTH_LONG ).show();
                    date_progress.setVisibility( View.INVISIBLE );
                }

            }
        } );
    }
}