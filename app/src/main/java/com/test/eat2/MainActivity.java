package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Home_Fragment fragment_home;
    Action2_Fragment fragment_action2;
    Action3_Fragment fragment_action3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //프래그먼트 생성
        fragment_home = new Home_Fragment();
        fragment_action2 = new Action2_Fragment();
        fragment_action3 = new Action3_Fragment();

        //제일 처음 띄워줄 뷰를 세팅해줍니다.
        // commit();까지 해줘야 합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();

        //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.action_today: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.action_setting: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_action3).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.action_add: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_action2).commitAllowingStateLoss();
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });
    }
    public void To_Login() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    public void To_Signup(){
        Intent intent = new Intent(MainActivity.this, Signup.class);
        startActivity(intent);
    }
}

