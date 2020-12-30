package com.example.myhealthdoctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity_Doctor extends AppCompatActivity {

    TextInputEditText log_email,log_password;
    Button sign_in;
    TextView register_link;
    String registerPref="bundle",
            loggedIn="logStatus",
            token="token",
    mail="email",
    name="name";
    boolean logStatus=false;
    String inp_email=null,inp_password=null,tokenIn=null,db_name=null,db_ph=null,db_id=null;
    SharedPreferences registerData=null;
    ProgressBar sign_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

        registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
        logStatus = registerData.getBoolean( loggedIn,false );
        if(logStatus){
            Intent home = new Intent(LoginActivity_Doctor.this,MainActivity.class);
            startActivity( home );
            this.finish();
        }else{
            setContentView( R.layout.activity_login_doctor );
            log_email = findViewById( R.id.edit_email_login );
            log_password = findViewById( R.id.edit_pass_login );
            sign_in = findViewById( R.id.sign_in );

            sign_progress = findViewById( R.id.signin_progressBar );
            sign_progress.setVisibility( View.INVISIBLE );

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
                            tokenIn = response.body().getToken();
                            db_name = response.body().getUser().getName();
                            db_ph = response.body().getUser().getContact();
                            db_id = response.body().getUser().getEmail();
                            Toast.makeText( LoginActivity_Doctor.this, "Token: "+tokenIn+"\n Name: "+db_name+" \n Phone: "+db_ph, Toast.LENGTH_LONG ).show();
                            if(inp_email.equals( db_id )) {
                                registerData = getSharedPreferences( registerPref, Context.MODE_PRIVATE );
                                //registerData.edit().putBoolean( loggedIn, true ).apply();
                                registerData.edit().putString( token,tokenIn ).apply();
                                registerData.edit().putString( name,db_name ).apply();
                                registerData.edit().putString( mail,db_id ).apply();
                                sign_progress.setVisibility( View.INVISIBLE );
                                Toast.makeText( LoginActivity_Doctor.this, "Name "+db_name+"\nEmail: "+db_id , Toast.LENGTH_SHORT ).show();
                                Intent home = new Intent( LoginActivity_Doctor.this, MainActivity.class );
                                startActivity( home );
                                LoginActivity_Doctor.this.finish();
                            }
                            else{
                                sign_progress.setVisibility( View.INVISIBLE );
                                Toast.makeText( LoginActivity_Doctor.this, "Token Null", Toast.LENGTH_SHORT ).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            sign_progress.setVisibility( View.INVISIBLE );
                            Toast.makeText( LoginActivity_Doctor.this, "Couldn't fetch response", Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }
            } );
        }

    }
}