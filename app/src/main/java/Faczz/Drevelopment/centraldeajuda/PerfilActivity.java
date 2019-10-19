package Faczz.Drevelopment.centraldeajuda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {

    EditText edtUUID, edtEmail;
    Button btnLogout;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        edtUUID = (EditText) findViewById(R.id.edtUUIDUsuario);
        edtEmail = (EditText) findViewById(R.id.edtEmailUsuario);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseDM.logOut();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = DatabaseDM.getFirebaseAuth();
        user = DatabaseDM.getFirebaseUser();

        if(user == null){
            finish();
        }
        else
        {
            edtEmail.setText(user.getEmail());
            edtUUID.setText(user.getUid());
        }
    }
}
