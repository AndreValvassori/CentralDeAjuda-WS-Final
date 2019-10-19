package Faczz.Drevelopment.centraldeajuda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText edtLogin,edtSenha;
    private Button btnLogin;
    private TextView txtCadastro;

    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        auth = DatabaseDM.getFirebaseAuth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // cria os componentes da tela
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        txtCadastro = (TextView) findViewById(R.id.textCadastro);

        // função para clicar no text de cadastro;
        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(getApplicationContext(),CadastroActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtLogin.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();
                login(email,senha);
            }
        });

    }

    private void login(String email, String senha){
        auth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Usuário conectado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this,AvisosGeralActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"E-mail ou senha incorretos!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
