package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    private TextView editProfile, changePassword, aboutApp, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        editProfile = findViewById(R.id.setting_edit_profile);
        changePassword = findViewById(R.id.setting_change_password);
        aboutApp = findViewById(R.id.setting_about);
        logoutBtn = findViewById(R.id.setting_logout);

        // Edit Profil
        editProfile.setOnClickListener(view -> {
            // Nanti arahkan ke activity EditProfile
            Toast.makeText(this, "Edit Profil diklik", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, EditProfileActivity.class));
        });

        // Ganti Password
        changePassword.setOnClickListener(view -> {
            // Nanti arahkan ke activity GantiPassword
            Toast.makeText(this, "Ganti Password diklik", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, ChangePasswordActivity.class));
        });

        // Tentang Aplikasi
        aboutApp.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Tentang Aplikasi")
                    .setMessage("Aplikasi ini dibuat oleh Yoga Natadisastro.\nVersi 1.0")
                    .setPositiveButton("Tutup", null)
                    .show();
        });

        // Logout
        logoutBtn.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi Logout")
                    .setMessage("Apakah kamu yakin ingin keluar?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(this, "Berhasil keluar", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }
}
