package com.example.myhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText log_email,log_password;
    Button sign_in;
    TextView register_link;
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

    boolean logStatus=false;
    String inp_email=null,inp_password=null,tokenIn=null,db_name=null,db_ph=null,db_id;
    SharedPreferences registerData=null;
    ProgressBar sign_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
        logStatus = registerData.getBoolean( loggedIn,false );
        if(logStatus){
            Intent home = new Intent(LoginActivity.this,MainActivity.class);
            startActivity( home );
            this.finish();
        }else{
            setContentView( R.layout.activity_login );
            log_email = findViewById( R.id.edit_email_login );
            log_password = findViewById( R.id.edit_pass_login );
            sign_in = findViewById( R.id.sign_in );
            register_link = findViewById( R.id.create_acc );
            sign_progress = findViewById( R.id.signin_progressBar );
            sign_progress.setVisibility( View.INVISIBLE );
            register_link.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent register = new Intent(LoginActivity.this, Registration.class);
                    startActivity( register );
                }
            } );
            sign_in.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sign_progress.setVisibility( View.VISIBLE );

                    Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("https://health-care-auto.herokuapp.com/")
                            .addConverterFactory( GsonConverterFactory.create())
                            .build();

                    UserIDAPIInterface uai = retrofit.create( UserIDAPIInterface.class );
                    inp_email = log_email.getText().toString();
                    inp_password = log_password.getText().toString();

                    Call<UserResponse> call = uai.getUserIDToken(new UserIDInput( inp_email,inp_password));
                    //Call<UserResponse> call = uai.getUserToken(new UserInput( "Mihir","22","mihir@health.com","pass","0987654321"));
                    call.enqueue( new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            //if(response.isSuccessful()) {
                            sign_progress.setVisibility( View.INVISIBLE );
                            try {
                                JSONObject jsonObject = new JSONObject( new Gson().toJson( response.body() ) );
                                tokenIn = response.body().getToken();
                                db_name = response.body().getUser().getName();
                                db_ph = response.body().getUser().getContact();
                                db_id = response.body().getUser().get_id();
                                Toast.makeText( LoginActivity.this, "Token: "+tokenIn+"\n Name: "+db_name+" \n Phone: "+db_ph, Toast.LENGTH_LONG ).show();
                                if(!tokenIn.equals( null )) {
                                    registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );

                                    registerData.edit().putBoolean( loggedIn, true ).apply();
                                    registerData.edit().putString( token,tokenIn ).apply();
                                    registerData.edit().putString( id,db_id ).apply();

                                    registerData.edit().putString( namePref, db_name ).apply();
                                    registerData.edit().putString( agePrefs, response.body().getUser().getAge() ).apply();
                                    registerData.edit().putString( emailPrefs, response.body().getUser().getEmail() ).apply();
                                    registerData.edit().putString( phonePrefs, response.body().getUser().getContact() ).apply();

                                    JSONObject jsonObject1 = jsonObject.getJSONObject( "user" );
                                    JSONArray jsonArray = jsonObject1.getJSONArray( "emergencyContact" );

                                    registerData.edit().putString( ephone1Prefs, jsonArray.getString( 0 ) ).apply();
                                    registerData.edit().putString( ephone2Prefs, jsonArray.getString( 0 ) ).apply();

                                    sign_progress.setVisibility( View.INVISIBLE );

                                    Intent home = new Intent( LoginActivity.this, MainActivity.class );
                                    Log.d( "Home","Home here" );
                                    startActivity( home );
                                    LoginActivity.this.finish();
                                }
                                else{
                                    sign_progress.setVisibility( View.INVISIBLE );
                                    Toast.makeText( LoginActivity.this, "Token Null", Toast.LENGTH_SHORT ).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            sign_progress.setVisibility( View.INVISIBLE );
                            Toast.makeText( LoginActivity.this, "Couldn't fetch response", Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }
            } );
        }

    }
}