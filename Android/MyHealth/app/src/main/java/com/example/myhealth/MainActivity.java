package com.example.myhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        BottomNavigationView bottomNav = findViewById( R.id.bottomNav );
        bottomNav.setOnNavigationItemSelectedListener( navListener );
        loadFragment( new HealthFragment() );
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_health:
                    loadFragment( new HealthFragment() );
                    break;
                case R.id.action_profile:
                    loadFragment( new ProfileFragment() );
                    break;
                case R.id.action_assist:
                    loadFragment( new AssistFragment() );
                    break;

            }
            return true;
        }
    };

    public void loadFragment(Fragment frag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container,frag,"My_Fragment");
        ft.addToBackStack(null);
        ft.commit();
    }
}