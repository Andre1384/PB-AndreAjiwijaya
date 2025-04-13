package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText username, password;
    CheckBox checkBoxes;
    Button btLogin;
    TextView forgotPass, signUp;
    FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();

        username = findViewById(R.id.NamaPengguna);
        password = findViewById(R.id.KataSandi);
        checkBoxes = findViewById(R.id.checkboxs);
        btLogin = findViewById(R.id.btnLogin);
        forgotPass = findViewById(R.id.LupaPassword);
        signUp = findViewById(R.id.Daftar);

        btLogin.setOnClickListener(view -> {
            String email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Email dan Password wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(this, "Email belum diverifikasi. Silakan cek email Anda.", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                            }
                        } else {
                            Toast.makeText(this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        signUp.setOnClickListener(view -> {
            Intent a = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(a);
            finish();
        });

        forgotPass.setOnClickListener(view -> {
            Intent a = new Intent(getApplicationContext(), MainActivity4.class);
            startActivity(a);
        });
    }
}
