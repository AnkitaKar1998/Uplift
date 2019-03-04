package com.example.uplift;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, IndividualFragment.OnFragmentInteractionListener, NgoFragment.OnFragmentInteractionListener, PrivateFragment.OnFragmentInteractionListener
{

    BottomNavigationView bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        bottomNav = findViewById(R.id.bottom_nav);

        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,new HomeFragment(),null);

        fragmentTransaction.commit();


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.item_home:
                        fragmentTransaction.replace(R.id.container,new HomeFragment(),null).commit();
                        return true;
                    case R.id.item_private:
                        fragmentTransaction.replace(R.id.container,new PrivateFragment(),null).commit();
                        return true;
                    case R.id.item_ngo:
                        fragmentTransaction.replace(R.id.container,new NgoFragment(),null).commit();
                        return true;

                    case R.id.item_individual:
                        fragmentTransaction.replace(R.id.container,new IndividualFragment(),null).commit();
                        return true;
                }
                return false;
            }
        });


    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
