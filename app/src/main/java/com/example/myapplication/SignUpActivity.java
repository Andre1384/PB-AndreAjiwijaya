package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText nameUser, emailUser, pwUser, nimUser;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameUser = findViewById(R.id.nameUser);
        emailUser = findViewById(R.id.emailUser);
        pwUser = findViewById(R.id.pwUser);
        nimUser = findViewById(R.id.nimUser);
        btnSignup = findViewById(R.id.btnSignup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        btnSignup.setOnClickListener(v -> signUpUser());
    }

    private void signUpUser() {
        String name = nameUser.getText().toString().trim();
        String email = emailUser.getText().toString().trim();
        String password = pwUser.getText().toString().trim();
        String nim = nimUser.getText().toString().trim();

        // Validasi field kosong
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || nim.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi panjang password
        if (password.length() < 6) {
            Toast.makeText(this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proses buat akun
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Kirim verifikasi email
                            user.sendEmailVerification().addOnCompleteListener(verifyTask -> {
                                if (verifyTask.isSuccessful()) {
                                    Toast.makeText(this, "Verifikasi email telah dikirim ke " + user.getEmail(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "Gagal mengirim verifikasi email.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            // Simpan data ke Realtime Database
                            String uid = user.getUid();
                            User userData = new User(name, email, nim);
                            mDatabase.child(uid).setValue(userData)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(this, "Akun berhasil dibuat. Silakan cek email untuk verifikasi.", Toast.LENGTH_LONG).show();
                                            // Redirect ke Login
                                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Gagal menyimpan data user.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Gagal membuat akun: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // Kelas model user
    public static class User {
        public String name, email, nim;

        public User() {} // Diperlukan oleh Firebase

        public User(String name, String email, String nim) {
            this.name = name;
            this.email = email;
            this.nim = nim;
        }
    }
}
