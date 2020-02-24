package edu.unicauca.patacore.view;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.unicauca.patacore.R;
import edu.unicauca.patacore.MainMenuActivity;


//implements View.OnClickListener
public class LoginActivity extends AppCompatActivity{


    //Declara varables
    Button loginButton;
    ToggleButton toggleButton;
    EditText usernameEditText;
    EditText passwordEditText;
    ProgressBar loadingProgressBar;
    private Boolean usuario = false;

    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Controles
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        //loginButton.setOnClickListener(this)

    }

    private boolean validateForm() {
        boolean valid = true;

        String email = usernameEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            usernameEditText.setError("Campo requerido.");
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Campo requerido");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            usuario = true;

                        } else {
                            Toast.makeText(LoginActivity.this, "Autenticación fallida.",
                                    Toast.LENGTH_SHORT).show();
                            usuario = false;
                        }
                    }
                });
    }


    public void goMenuPrincipal(View view){
        signIn(usernameEditText.getText().toString() + "@patacore.com", passwordEditText.getText().toString());
        if (usuario){
            Intent intent = new Intent(this, ContainerActivity.class);
            startActivity(intent);
        }
    }






 /*    @Override
   public void onClick(View view) {
        //loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(usernameEditText.getText().toString(),
                passwordEditText.getText().toString());
        goMenuPrincipal(view);

    }*/
}
