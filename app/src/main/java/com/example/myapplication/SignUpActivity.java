package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText username, password, email, nim;
    Button btSign;
    FirebaseAuth auth;
    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance(); // Inisialisasi FirebaseAuth

        username = findViewById(R.id.nameUser);
        email = findViewById(R.id.emailUser);
        password = findViewById(R.id.pwUser);
        nim = findViewById(R.id.nimUser);
        btSign = findViewById(R.id.btnSignup);

        btSign.setOnClickListener(view -> {
            String nama = username.getText().toString().trim();
            String em = email.getText().toString().trim();
            String pw = password.getText().toString().trim();
            String nm = nim.getText().toString().trim();

            if (TextUtils.isEmpty(nama)) {
                username.setError("Masukkan Username!");
                username.requestFocus();
            } else if (TextUtils.isEmpty(em)) {
                email.setError("Masukkan Email!");
                email.requestFocus();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
                email.setError("Format Email Salah!");
                email.requestFocus();
            } else if (TextUtils.isEmpty(pw)) {
                password.setError("Masukkan Password!");
                password.requestFocus();
            } else if (pw.length() < 6) {
                password.setError("Password minimal 6 karakter!");
                password.requestFocus();
            } else if (TextUtils.isEmpty(nm)) {
                nim.setError("Masukkan NIM!");
                nim.requestFocus();
            } else {
                // Panggil metode registerUser()
                registerUser(nama, em, pw, nm);
            }
        });
    }

    private void registerUser(String nama, String em, String pw, String nm) {
        auth.createUserWithEmailAndPassword(em, pw).addOnCompleteListener(SignUpActivity.this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser fUser = auth.getCurrentUser();
                if (fUser != null) {
                    String uid = fUser.getUid();

                    UserDetails userDetails = new UserDetails(uid, nama, em, nm);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.child(uid).setValue(userDetails).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            fUser.sendEmailVerification().addOnCompleteListener(task2 -> {
                                if (task2.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Verifikasi email telah dikirim!", Toast.LENGTH_LONG).show();
                                }
                            });

                            Toast.makeText(SignUpActivity.this, "Akun berhasil dibuat!", Toast.LENGTH_LONG).show();

                            // Pindah ke LoginActivity
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Gagal menyimpan data user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(SignUpActivity.this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error: ", task.getException());
            }
        });
    }
}
