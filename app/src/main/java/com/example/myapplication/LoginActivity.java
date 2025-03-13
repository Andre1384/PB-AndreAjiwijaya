package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity { //page Login

    TextInputEditText username, password;
    CheckBox checkBoxes;
    Button btLogin;
    TextView forgotPass, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.NamaPengguna);
        password = findViewById(R.id.KataSandi);
        checkBoxes = findViewById(R.id.checkboxs);
        btLogin = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.LupaPassword);
        signUp = findViewById(R.id.Daftar);

        btLogin.setOnClickListener(view -> {
            Intent a = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(a);
            finish();
        });

        signUp.setOnClickListener(view -> {
            Intent a = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(a);
            finish();
        });
    }
}