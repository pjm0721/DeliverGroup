package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import static maes.tech.intentanim.CustomIntent.customType;
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
    private long backKeyPressedTime = 0;
    private LoginSharedPreferenceUtil util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        util = new LoginSharedPreferenceUtil(this);
        util.setBooleanData("LOGIN",false);
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
    public void To_Change_PW(){
        Intent intent_change_pw=new Intent(getApplicationContext(),ResetPassword.class);
        intent_change_pw.putExtra("email",util.getStringData("ID",null));
        startActivity(intent_change_pw);
        customType(MainActivity.this, "left-to-right");
    }
    @Override
    public void onBackPressed() {
        Toast toast;
        toast = Toast.makeText(this, "초기화", Toast.LENGTH_SHORT);
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "종료 하시겠습니까?", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            //Boolean goToLogin = util11.getBooleanData("AutoLogin", false);
//            if(goToLogin) {
            ActivityCompat.finishAffinity(MainActivity.this);
//            }
            finish();
            toast.cancel();
        }
    }
    public void Logout() {
        LoginSharedPreferenceUtil util11 = new LoginSharedPreferenceUtil(MainActivity.this);
        util11.setBooleanData("AutoLogin", false);
        util11.setStringData("ID", "");
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Login.class);
        ActivityCompat.finishAffinity(MainActivity.this);
        startActivity(intent);
        customType(MainActivity.this, "bottom-to-up");
    }
    public void Group(String name){
        Intent intent3=new Intent(MainActivity.this,GroupListActivity.class);
        intent3.putExtra("dorm_name",name);
        startActivity(intent3);
        customType(MainActivity.this, "left-to-right");
    }
}

