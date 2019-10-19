package Faczz.Drevelopment.centraldeajuda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import static Faczz.Drevelopment.centraldeajuda.DatabaseDM.getFirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnCadastro;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Define componentes da tela.
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        btnCadastro = (Button) findViewById(R.id.btnCadastrar);

        // Define funções onclick

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();
                CriarUser(email,senha);
            }
        });
    }

    public void CriarUser(String email,String senha){
            auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(CadastroActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Erro de cadastro!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = DatabaseDM.getFirebaseAuth();
    }
}
