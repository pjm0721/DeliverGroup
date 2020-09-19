package com.test.eat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Signup2 extends AppCompatActivity {
    String MAJOR;
    String YEAR;
    String EMAIL;
    String NAME;
    EditText id;
    EditText password;
    EditText password_chk;
    EditText phone;
    EditText nick;
    Button join;
    RadioGroup Signup2_grp;
    int scs=0;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        Intent intent=getIntent();
        Signup2_grp = (RadioGroup) findViewById(R.id.signup2_grp);
        MAJOR=intent.getStringExtra("major");
        YEAR=intent.getStringExtra("year");
        EMAIL=intent.getStringExtra("email");
        NAME=intent.getStringExtra("name");
        id=(EditText)findViewById(R.id.signup2_id);
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scs=0;
                // 입력되는 텍스트에 변화가 있을 때
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
            }
        });
        password=(EditText)findViewById(R.id.signup2_password);
        password_chk=(EditText)findViewById(R.id.signup2_password_chk);
        phone=(EditText)findViewById(R.id.signup2_phone);
        nick=(EditText)findViewById(R.id.signup2_nick);
        join=(Button)findViewById(R.id.signup2_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ID = id.getText().toString();
                final String PASSWORD = password.getText().toString();
                final String PASSWORD_CHK = password_chk.getText().toString();
                final String PHONE = phone.getText().toString();
                final String NICK = nick.getText().toString();
                int Rid = Signup2_grp.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(Rid);
                final String who = rb.getText().toString();
                idchk(ID);
                if (scs != 2)
                    Toast.makeText(Signup2.this, "중복확인을 부탁드립니다", Toast.LENGTH_SHORT).show();
                else if (PASSWORD.length() < 8 || PASSWORD.length() > 12) {
                    Toast.makeText(Signup2.this, "비밀번호는 8자 이상 12자 이하로 입력해주세요", Toast.LENGTH_SHORT).show();
                    password.setText(null);
                } else if (PASSWORD_CHK.equals(PASSWORD) == false) {
                    Toast.makeText(Signup2.this, "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                    password_chk.setText(null);
                } else if (PHONE.length() != 10 && PHONE.length() != 11) {
                    Toast.makeText(Signup2.this, "전화번호를 제대로 입력해주세요", Toast.LENGTH_SHORT).show();
                }   else if (TextUtils.isEmpty(NICK) == true){
                    Toast.makeText(Signup2.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("id", ID);
                    user.put("password", PASSWORD);
                    user.put("학번",MAJOR);
                    user.put("입학년도",YEAR);
                    user.put("이름", NAME);
                    user.put("phone",PHONE);
                    user.put("Email",EMAIL);
                    user.put("닉네임", NICK);
                    user.put("성별",who);
                    db.collection("USER").document(ID).set(user);
                }
            }
        });
    }
    public void idchk(final String ID) {
        scs = 0;
        String pattern = "^[a-zA-Z가-힣0-9]{3,10}$";
        if (ID.length() < 3 || ID.length() > 10) {
            Toast.makeText(this, "아이디는 3자 이상 10자 이하로 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Pattern.matches(pattern, ID) == false) {
            Toast.makeText(this, "사용할 수 없는 아이디 형식입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("USER")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(ID)==true) {
                                    scs = 1;
                                }
                            }
                        } else {
//                             Log.w("LoginActivity.java", "Error getting documents.", task.getException());
                        }
                        if (scs == 0) scs = 2;
                    }
                });
    }
}