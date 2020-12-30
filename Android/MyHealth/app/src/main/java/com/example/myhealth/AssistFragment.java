package com.example.myhealth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssistFragment extends Fragment implements AdapterView.OnItemSelectedListener{



    ListView listView,listView2,listView3;
    Spinner contacts;
    ImageButton call;
    String registerPref="bundle",
            ephone1PrefsN="ePhone1N",
            ephone2PrefsN="ePhone2N",
            ephone1Prefs="ePhone1",
            ephone2Prefs="ePhone2";
    String num1=null,num2=null,name1=null,name2=null,select=null,selectN="Name Sample";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_assist, null );
        call = v.findViewById( R.id.call_phone );
        contacts = v.findViewById( R.id.contact_select );
        listView = v.findViewById( R.id.list_diary );
        listView2 = v.findViewById( R.id.list_appointment );
        ArrayList<String> arrayList = new ArrayList<>(  );
        arrayList.add( "     View or share your diary" );
        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter( arrayAdapter );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent d = new Intent( getContext(),DiaryActivity.class );
                startActivity( d );
            }
        } );

        ArrayList<String> arrayList2 = new ArrayList<>(  );
        arrayList2.add( "     Schedule an appointment" );
        ArrayAdapter<String> arrayAdapter2 = new
                ArrayAdapter<String>(v.getContext(),android.R.layout.simple_list_item_1,arrayList2);
        listView2.setAdapter( arrayAdapter2 );
        listView2.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a = new Intent(getContext(),AppointmentActivity.class);
                startActivity( a );
            }
        } );

        contacts.setOnItemSelectedListener(this);

        SharedPreferences data = getContext().getSharedPreferences( registerPref,Context.MODE_PRIVATE );
        num1 = data.getString( ephone1Prefs,null );
        num2 = data.getString( ephone2Prefs,null );
        name1 = data.getString( ephone1PrefsN,null );
        name2 = data.getString( ephone2PrefsN,null );
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Contact");
        categories.add("Ambulance: 102");
        if(name1 == null || name2 == null){
            categories.add(num1);
            categories.add( num2 );
        } else if (!num1.equals( null ) && !num2.equals( null ) && !name1.equals( null ) && !name2.equals( null )){
            categories.add(name1+": "+num1);
            categories.add(name2+": "+num2);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        contacts.setAdapter(dataAdapter);

        call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if(select!=null) {
                    String mod = "tel:"  + select; // concatenating the number with prefix 'tel:'
                    callIntent.setData( Uri.parse(mod));
                    startActivity(callIntent);
                } else{
                    Toast.makeText( getContext(), "Error in parsing phone no", Toast.LENGTH_LONG ).show();
                }

            }
        } );

        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(position == 1)
        {
            select = "102";
            selectN = "Ambulance";
        }else if(position == 2){
            select = num1;
            selectN = name1;

        }else if(position == 3){
            select = num2;
            selectN = name2;
        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected " + selectN, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}


