package com.example.myhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class ReportActivity extends AppCompatActivity {

    Button btn_upload,btn_choose,btn_load;
    final static int PICK_PDF_CODE = 2342;
    private Uri filePath;
    private StorageReference storageReference;
    EditText editText;
    TextView responseT;
    TextInputEditText report_title;
    String r_title;
    String title=null,url=null,cont=null;
    RecyclerView recyclerView;
    Report_adapter report_adapter;
    ProgressBar rl_progress,rul_progress;
    String registerPref="bundle",
            phonePrefs="phone";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_report );
        btn_choose = findViewById( R.id.choose_file );
        btn_upload = findViewById( R.id.upload_file );
        report_title = findViewById( R.id.edit_title );
        rl_progress = findViewById( R.id.reportLoad_progress );
        rul_progress = findViewById( R.id.reportLoad_progress2 );
        rul_progress.setVisibility( View.INVISIBLE );
        SharedPreferences ph= getSharedPreferences( registerPref,Context.MODE_PRIVATE );
        cont = ph.getString( phonePrefs, "8850356911");
        storageReference = FirebaseStorage.getInstance().getReference();
        btn_choose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent choose = new Intent();
                choose.setType( "application/pdf" );
                choose.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult(Intent.createChooser(choose, "Select Picture"), PICK_PDF_CODE);
            }
        } );
        btn_upload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rul_progress.setVisibility( View.VISIBLE );
                r_title = report_title.getText().toString();
                r_title = "pdf/"+r_title;
                Toast.makeText( ReportActivity.this, "File Selected, Click Upload!", Toast.LENGTH_LONG ).show();
                StorageReference pdfRef = storageReference.child(r_title);
                UploadTask uploadTask = pdfRef.putFile( filePath );

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return pdfRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String downloadUri = task.getResult().toString();
                            Toast.makeText( ReportActivity.this, downloadUri, Toast.LENGTH_SHORT ).show();
                            sendFile(downloadUri);
                        } else {
                            // Handle failures
                            // ...
                            Toast.makeText( ReportActivity.this, "No", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });

            }
        } );
        reloadReports();



    }

    private void reloadReports() {
        recyclerView = findViewById( R.id.recycler_r );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        Retrofit retrofit_l = new Retrofit.Builder()
                .baseUrl( "https://health-care-auto.herokuapp.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        MedicalInterface uai = retrofit_l.create( MedicalInterface.class );


        Call<MedicalResponse> call = uai.getRecords(new MedicalInput(cont));
        call.enqueue( new Callback<MedicalResponse>() {
            @Override
            public void onResponse(Call<MedicalResponse> call, Response<MedicalResponse> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject( new Gson().toJson( response.body() ) );
                        String bufferR = "",bufferP="";
                        int i=0;
                        int reportN = response.body().getNoOfReports();
                        int prescriptionN = response.body().getNoOfPrescriptions();
                        String[] reports_t = new String[reportN];
                        String[] reports_f = new String[reportN];
                        String[] reports_d = new String[reportN];

                        JSONArray jsonArrayR = jsonObject.getJSONArray( "userReports" );
                        for(i=0;i<reportN;i++){
                            JSONObject jsonObject1 = jsonArrayR.getJSONObject( i );
                            reports_t[i] = jsonObject1.getString( "title" );
                            reports_f[i] = jsonObject1.getString( "file" );
                            reports_d[i] = jsonObject1.getString( "date" );
                            //bufferR = bufferR + title + " "+file+"\n"+date+"\n";
                        }

                        report_adapter = new Report_adapter( ReportActivity.this,reportN,prescriptionN,reports_t,reports_f,reports_d );
                        recyclerView.setAdapter( report_adapter );
                        rl_progress.setVisibility( View.INVISIBLE );
                        //responseT.setText( bufferP );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText( ReportActivity.this, "No reports found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<MedicalResponse> call, Throwable t) {

            }
        } );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            //Toast.makeText( this, "File Path: "+filePath.toString(), Toast.LENGTH_SHORT ).show();
        }
    }

    public void sendFile(String dUrl){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://health-care-auto.herokuapp.com/")
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        ReportAPIInterface uai = retrofit.create( ReportAPIInterface.class );
        url = dUrl;
        title = report_title.getText().toString();
        Call<ReportResponseModel> call = uai.getConfirmation(new ReportModel(title,url,cont));
        call.enqueue( new Callback<ReportResponseModel>() {
            @Override
            public void onResponse(Call<ReportResponseModel> call, Response<ReportResponseModel> response) {
                if(response.isSuccessful()) {
                    String title1=null;
                    title1 = response.body().getTitle();
                    if(title1.equals( title )){
                        Toast.makeText( ReportActivity.this, "Backend Synced", Toast.LENGTH_SHORT ).show();
                        rl_progress.setVisibility( View.VISIBLE );
                        reloadReports();
                    }
                }else{
                    Toast.makeText( ReportActivity.this, "Response Unsuccess! "+response.errorBody().toString(), Toast.LENGTH_LONG ).show();
                    report_title.setText( response.errorBody().toString() );
                }
            }

            @Override
            public void onFailure(Call<ReportResponseModel> call, Throwable t) {
                Toast.makeText( ReportActivity.this, "Naah, try harder!", Toast.LENGTH_LONG ).show();
            }
        } );
        rul_progress.setVisibility( View.INVISIBLE );
    }

}