package com.example.myhealth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    Button btn_reports;

    RecyclerView recyclerView;
    Prescription_adapter prescription_adapter;
    ProgressBar p_progress;
    ListView listView,listView2;
    TextView name,prof_link;
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
    String buffer="",nameV="",ageV="",emailV="",phoneV="",ep1V="",ep2V="",ep1N="",ep2N="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_profile, null );
        listView = v.findViewById( R.id.list_qr_code );
        listView2 = v.findViewById( R.id.list_report );
        name = v.findViewById( R.id.home_name_disp );
        prof_link = v.findViewById( R.id.view_profile );
        p_progress = v.findViewById( R.id.prescription_progress );
        p_progress.setVisibility( View.VISIBLE );
        SharedPreferences data = getContext().getSharedPreferences( registerPref, Context.MODE_PRIVATE );
        nameV = data.getString( namePref,"No Information Available" );
        name.setText( nameV );
        ageV = data.getString( agePrefs,"No Information Available" );
        emailV = data.getString( emailPrefs,"No Information Available" );
        phoneV = data.getString( phonePrefs,"No Information Available" );
        ep1N = data.getString( ephone1PrefsN,"No Name Available" );
        ep1V = data.getString( ephone1Prefs,"No Information Available" );
        ep2N = data.getString( ephone2PrefsN,"No Name Available" );
        ep2V = data.getString( ephone2Prefs,"No Information Available" );
        buffer = "Name: "+nameV+"\n"+"Age: "+ageV+"\n"+"Email: "+emailV+"\n"+"Phone: "+phoneV+"\n"+"Emergency Contact 1: "+ep1N+": "+ep1V+"\n"+"Emergency Contact 2: "+ep1N+": "+ep1V;
        prof_link.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Your data with us..")
                        .setMessage(buffer)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        } );

        ArrayList<String> arrayList = new ArrayList<>(  );
        arrayList.add( "     Generate QR Code" );
        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter( arrayAdapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent qr = new Intent( getContext(),QRActivity.class );
                startActivity( qr );
            }
        } );

        ArrayList<String> arrayList2 = new ArrayList<>(  );
        arrayList2.add( "     View or add Reports" );
        ArrayAdapter<String> arrayAdapter2 = new
                ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1,arrayList2);
        listView2.setAdapter( arrayAdapter2 );
        listView2.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent reports = new Intent(getContext(),ReportActivity.class);
                startActivity( reports );
            }
        } );

        recyclerView = v.findViewById( R.id.recycler_1 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "https://health-care-auto.herokuapp.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        MedicalInterface uai = retrofit.create( MedicalInterface.class );
        Call<MedicalResponse> call = uai.getRecords(new MedicalInput(phoneV));
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

                        String[] p_link = new String[prescriptionN];
                        String[] p_doc = new String[prescriptionN];
                        String[] p_date = new String[prescriptionN];
                        String[] p_special = new String[prescriptionN];

                        JSONArray jsonArrayP = jsonObject.getJSONArray( "userPrescriptions" );
                        for(i=0;i<prescriptionN;i++){
                            JSONObject jsonObject1 = jsonArrayP.getJSONObject( i );
                            p_link[i] = jsonObject1.getString( "details" );
                            JSONObject jsonObject2 = jsonObject1.getJSONObject( "doctor" );
                            p_doc[i] = jsonObject2.getString( "name" );
                            p_date[i] = jsonObject2.getString( "date" );
                            JSONArray jsonArraySp = jsonObject2.getJSONArray( "Specialization" );
                            p_special[i] = jsonArraySp.getString( 0 );

                            prescription_adapter = new Prescription_adapter(v, getContext(),reportN,prescriptionN,p_link,p_doc,p_date,p_special );
                            recyclerView.setAdapter( prescription_adapter );
                            p_progress.setVisibility( View.INVISIBLE );
                            //bufferP = bufferP + details + "\n" + docName + "\n" + docDate + "\n" + special +"\n";
                        }
                        //responseT.setText( bufferP );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    p_progress.setVisibility( View.INVISIBLE );
                    Toast.makeText( getContext(), "No prescriptions found!", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<MedicalResponse> call, Throwable t) {
                p_progress.setVisibility( View.INVISIBLE );
            }
        } );

        return v;
    }
}