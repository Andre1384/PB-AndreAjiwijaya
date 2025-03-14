package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private TextView textName, textEmail, textNIM;
    private ImageView profileImage;
    private Button btnLogout;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textNIM = findViewById(R.id.textNIM);
        profileImage= findViewById(R.id.prfImage);
        btnLogout = findViewById(R.id.btnLogout);

        if (user != null) {
            getUserData(user.getUid());
        } else {
            Toast.makeText(this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnLogout.setOnClickListener(view -> {
            auth.signOut();
            Toast.makeText(ProfileActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void getUserData(String uid) {
        reference.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String email = snapshot.child("userEmail").getValue(String.class);
                    String nim = snapshot.child("userNIM").getValue(String.class);

                    textName.setText(username);
                    textEmail.setText(email);
                    textNIM.setText("NIM: " + nim);
                } else {
                    Toast.makeText(ProfileActivity.this, "Data user tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProfileActivity.this, "Gagal mengambil data user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
