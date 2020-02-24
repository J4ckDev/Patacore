package edu.unicauca.patacore.view;

import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import edu.unicauca.patacore.R;

public class LoginActivity extends AppCompatActivity{


    //Declara varables
    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Controles
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

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
        progressDialog.setMessage("Realizando autenticación");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Autenticación Exitosa.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication(), ContainerActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginActivity.this, "Autenticación fallida. Credenciales no válidas.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    public void goMenuPrincipal(View view){
        signIn(usernameEditText.getText().toString().trim() + "@patacore.com", passwordEditText.getText().toString().trim());

    }

}
