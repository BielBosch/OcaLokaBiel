package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;

public class MenuSales extends AppCompatActivity {

    Button btn_crear_partida,btn_unir_partida,btn_crear_partida_entrar_lobby;
    LinearLayout linear_layout_entrar_lobby;
    EditText et_nom_partida;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_crear_unir);

        et_nom_partida = findViewById(R.id.et_nom_partida);
        btn_unir_partida = findViewById(R.id.btn_unir_partida);
        btn_crear_partida_entrar_lobby = findViewById(R.id.btn_crear_partida_entrar_lobby);
        linear_layout_entrar_lobby = findViewById(R.id.linear_layout_entrar_lobby);

        btn_crear_partida = findViewById(R.id.btn_crear_partida);
        btn_crear_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            btn_crear_partida.setVisibility(View.GONE);
            btn_unir_partida.setVisibility(View.GONE);
            linear_layout_entrar_lobby.setVisibility(View.VISIBLE);


            }
        });

        btn_unir_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),MenuUnirsePartida.class);
                startActivity(i);

            }
        });

        btn_crear_partida_entrar_lobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_nom_partida.getText().toString();
                DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
                gamesRef.child(name).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            // Controla si existeix el nom que estem posant a la partida ja es el nom duna altre partida
                            Toast.makeText(MenuSales.this, "Ja existeix una partida amb aquest nom", Toast.LENGTH_SHORT).show();
                        } else {
                            //  Si no crea la partida i li assigna el nom
                            Game game = new Game(name);
                            gamesRef.child(name).setValue(game);
                            Intent i = new Intent(MenuSales.this, SeleccionarTaulell.class);
                            String nom_partida = name;
                            i.putExtra("nom_partida", nom_partida);
                            startActivity(i);

                            }
                    } else {
                        Toast.makeText(MenuSales.this, "Error al obtenir dades de la partida", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}