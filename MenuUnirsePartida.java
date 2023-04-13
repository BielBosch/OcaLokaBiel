package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MenuUnirsePartida extends AppCompatActivity {

    private EditText et_nom_partida_unir;
    private Button btn_unir_partida;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_unirse_partida);

        et_nom_partida_unir = findViewById(R.id.et_nom_partida_unir);
        btn_unir_partida = findViewById(R.id.btn_unir_partida);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_unir_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom_partida = et_nom_partida_unir.getText().toString().trim();
                unirPartida(nom_partida);
            }
        });
    }

    private void unirPartida(String nom_partida) {
        DatabaseReference partidaRef = mDatabase.child("games").child(nom_partida);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String nom_jugador = user.getDisplayName();
        Player jugador = new Player(nom_jugador);

        partidaRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot partidaSnapshot = task.getResult();
                if (partidaSnapshot.exists()) {
                    // Game matched correctly, add user to player list
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference playerListRef = partidaRef.child("players");
                    HashMap<String, Object> jugadorData = new HashMap<>();
                    jugadorData.put("name", jugador.getName());
                    jugadorData.put("actualPosition", jugador.getActualPosition());
                    jugadorData.put("isOwner", jugador.isOwner());
                    jugadorData.put("color", jugador.getColor());
                    playerListRef.child(uid).setValue(jugadorData); // Add user to player list

                    // Go to lobby
                    Intent intent = new Intent(MenuUnirsePartida.this, MenuCrearPartida.class);
                    intent.putExtra("nom_partida", nom_partida);
                    startActivity(intent);
                } else {
                    // Game not found, show toast
                    Toast.makeText(MenuUnirsePartida.this, "Partida no trobada", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Error getting game data, show toast
                Toast.makeText(MenuUnirsePartida.this, "Error al obtenir dades de la partida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}