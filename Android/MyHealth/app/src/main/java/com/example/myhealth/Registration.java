package com.example.myhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Registration extends AppCompatActivity {

    TextInputEditText u_name,u_age,u_email,u_password,u_phone,u_ephone1,u_ephone2;
    String str_name,str_age,str_email,str_password,str_phone,str_ephone1,str_ephone1_name,str_ephone2,str_ephone2_name;
    Button u_register;
    ImageButton cp1,cp2;
    int mode=-1;
    private static final int RESULT_PICK_CONTACT=1;
    String phoneNo = null;
    String name = null;
    String tokenIn = null;
    String registerPref="bundle",
    loggedIn="logStatus",
    token="token",
            id="id",
    namePref="name",
    agePrefs="age",
    emailPrefs="email",
    phonePrefs="phone",
    ephone1PrefsN="ePhone1N",
    ephone2PrefsN="ePhone2N",
    ephone1Prefs="ePhone1",
    ephone2Prefs="ePhone2";
    SharedPreferences registerData=null;
    ProgressBar save_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView( R.layout.activity_registration );
        super.onCreate( savedInstanceState );
        u_name = findViewById( R.id.edit_name );
        u_age = findViewById( R.id.edit_age );
        u_email = findViewById( R.id.edit_email );
        u_password = findViewById( R.id.edit_upass );
        u_phone = findViewById( R.id.edit_uphone );
        u_ephone1 = findViewById( R.id.edit_ephone1 );
        cp1 = findViewById( R.id.contact_1 );
        cp2 = findViewById( R.id.contact_2 );
        u_ephone2 = findViewById( R.id.edit_ephone2 );
        u_register = findViewById( R.id.btn_save );
        str_name = u_name.getText().toString();
        str_age = u_age.getText().toString();
        str_email = u_email.getText().toString();
        str_password = u_password.getText().toString();
        str_phone = u_phone.getText().toString();
        save_progress = findViewById( R.id.save_progressBar );
        save_progress.setVisibility( View.INVISIBLE );
        registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
        cp1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContactPicker();
                mode=1;
            }
        } );
        cp2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContactPicker();
                mode=2;
            }
        } );
        /*u_ephone1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContactPicker();
                u_ephone1.setText( name+" : "+phoneNo );
                str_ephone1 = phoneNo;
                str_ephone1_name = name;
            }
        } );
        u_ephone2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContactPicker();
                u_ephone2.setText( name+" : "+phoneNo );
                str_ephone2 = phoneNo;
                str_ephone2_name = name;
            }
        } );*/
        u_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_progress.setVisibility( View.VISIBLE );

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://health-care-auto.herokuapp.com/")
                        .addConverterFactory( GsonConverterFactory.create())
                        .build();

                ArrayList<String> arrayList = new ArrayList<>(  );
                if(!str_ephone1.equals( null ) && !str_ephone2.equals( null )){

                    arrayList.add(str_ephone1 );
                    arrayList.add(str_ephone2 );
                } else {

                    arrayList.add( "8850356911" );
                    arrayList.add( "8850356911" );
                }
                UserAPIInterface uai = retrofit.create( UserAPIInterface.class );
                str_name = u_name.getText().toString();
                str_age = u_age.getText().toString();
                str_email = u_email.getText().toString();
                str_password = u_password.getText().toString();
                str_phone = u_phone.getText().toString();
                Call<UserResponse> call = uai.getUserToken(new UserInput( str_name,str_age,str_email,str_password,str_phone,arrayList));
                //Call<UserResponse> call = uai.getUserToken(new UserInput( "Mihir","22","mihir@health.com","pass","0987654321"));
                call.enqueue( new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        //if(response.isSuccessful()) {
                            save_progress.setVisibility( View.INVISIBLE );
                            tokenIn = response.body().getToken();
                            Toast.makeText( Registration.this, "Token: "+tokenIn, Toast.LENGTH_SHORT ).show();
                            if(!tokenIn.equals( null )) {
                                registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
                                registerData.edit().putBoolean( loggedIn, true ).apply();
                                registerData.edit().putString( token,tokenIn ).apply();
                                registerData.edit().putString( id,response.body().getUser().get_id() ).apply();
                                registerData.edit().putString( namePref, str_name ).apply();
                                registerData.edit().putString( agePrefs, str_age ).apply();
                                registerData.edit().putString( emailPrefs, str_email ).apply();
                                registerData.edit().putString( phonePrefs, str_phone ).apply();
                                registerData.edit().putString( ephone1PrefsN, str_ephone1_name ).apply();
                                registerData.edit().putString( ephone1Prefs, str_ephone1 ).apply();
                                registerData.edit().putString( ephone2PrefsN, str_ephone2_name ).apply();
                                registerData.edit().putString( ephone2Prefs, str_ephone2 ).apply();
                                save_progress.setVisibility( View.INVISIBLE );

                                Intent home = new Intent( Registration.this, MainActivity.class );
                                startActivity( home );
                                Registration.this.finish();
                            }
                            else{
                                save_progress.setVisibility( View.INVISIBLE );
                                Toast.makeText( Registration.this, "Token Null", Toast.LENGTH_SHORT ).show();
                            }

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        save_progress.setVisibility( View.INVISIBLE );
                        Toast.makeText( Registration.this, "Couldn't fetch response", Toast.LENGTH_SHORT ).show();
                    }
                } );


            }
        } );
    }
    public void loadContactPicker(){
        Intent contactPickerIntent = new Intent( Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        //contactPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case (RESULT_PICK_CONTACT):
                    Cursor cursor = null;
                    try {
                        Uri uri = data.getData();
                        cursor = getContentResolver().query( uri, null, null, null, null );
                        cursor.moveToFirst();
                        int phoneIndex = cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER );
                        int nameIndex = cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME );
                        phoneNo = cursor.getString( phoneIndex );
                        name = cursor.getString( nameIndex );
                        if(mode ==1){
                            str_ephone1 = phoneNo;
                            str_ephone1_name = name;
                            u_ephone1.setText( name+" : "+phoneNo );
                            mode=-1;
                        }else if(mode == 2){
                            str_ephone2 = phoneNo;
                            str_ephone2_name = name;
                            u_ephone2.setText( name+" : "+phoneNo );
                            mode=-1;
                        }
                        Log.e( "Name Contact number is", name + "," + phoneNo );

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            Log.e( "Failed", "Not able to pick contact" );
        }
    }
}