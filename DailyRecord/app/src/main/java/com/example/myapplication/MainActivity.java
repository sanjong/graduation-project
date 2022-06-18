package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNV;
    static String IP = "13.124.171.62";
    static User user;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (User) this.getIntent().getSerializableExtra("user");
        imageView = (CircleImageView) findViewById(R.id.imagebtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Record.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.navigation_1);

    }



    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (id == R.id.navigation_1) {
            fragment = new FragmentPage1();
        } else if (id == R.id.navigation_2){
            fragment = new FragmentPage2();
        } else if (id == R.id.navigation_3){
            fragment = new FragmentPage3();
        }else {
            fragment = new FragmentPage4();
        }
        if (fragment == null) {
            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.replace(R.id.content_layout, fragment, tag);
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();


    }

}