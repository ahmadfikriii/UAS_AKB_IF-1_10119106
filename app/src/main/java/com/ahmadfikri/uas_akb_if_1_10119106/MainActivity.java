package com.ahmadfikri.uas_akb_if_1_10119106;

/*
NIM     : 10119106
Nama    : Ahmad Fikri Maulana
Kelas   : IF-1/S1/VI
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.MenuItem;


import com.ahmadfikri.uas_akb_if_1_10119106.fragment.FragmentHome;
import com.ahmadfikri.uas_akb_if_1_10119106.fragment.FragmentInformation;
import com.ahmadfikri.uas_akb_if_1_10119106.fragment.FragmentProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseUser firebaseUser;
    private TextView txtFullname;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFullname = findViewById(R.id.txtFullname);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null){
            txtFullname.setText(firebaseUser.getDisplayName());
        }else {
            txtFullname.setText("Login Gagal");
        }
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginRegisterPageActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
                break;

            case R.id.nav_information:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentInformation()).commit();
                break;

            case R.id.nav_logout:
                logout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}