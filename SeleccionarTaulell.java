package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeleccionarTaulell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Define a reference to your Firebase Realtime Database
    private DatabaseReference mDatabase;
    TextView tv_spinner_taulers;
    Button btn_crear_sala;
    // Define a list to hold the board names
    private final List<String> nomTaulers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_taulell);

        btn_crear_sala = findViewById(R.id.btn_crear_sala);
        tv_spinner_taulers = findViewById(R.id.tv_spinner_taulers);
        // Get a reference to your Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Query the "boards" node in the database to get the board names

        btn_crear_sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),MenuCrearPartida.class);
                i.putExtra("nom_partida",
                        getIntent().getStringExtra("nom_partida"));
                startActivity(i);
                // Unirse a la partida que acabem de crear
                String nom_partida = getIntent().getStringExtra("nom_partida");
                unirPartida(nom_partida);
            }
        });

        mDatabase.child("boards").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot boardSnapshot : dataSnapshot.getChildren()) {
                    String boardName = boardSnapshot.child("name").getValue(String.class);
                    nomTaulers.add(boardName);
                }

                // Once you have fetched the board names, create the adapter and set it on the spinner
                Spinner spinner_taulers = findViewById(R.id.spinner_taulers);
                spinner_taulers.setOnItemSelectedListener(SeleccionarTaulell.this);

                ItemAdapterBoards adapter = new ItemAdapterBoards(SeleccionarTaulell.this, nomTaulers.toArray(new String[0]));
                spinner_taulers.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the fetch
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        final String boardName = nomTaulers.get(position);
        tv_spinner_taulers.setText(boardName);
        Toast.makeText(this,"Tauler "+nomTaulers.get(position)+" seleccionat",Toast.LENGTH_SHORT).show();

        // Get the name of the game from the intent
        String nom_partida = getIntent().getStringExtra("nom_partida");

        // Query the "boards" node in the database to get the selected board
        mDatabase.child("boards").orderByChild("name").equalTo(boardName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the board data and update the game object in Firebase with it
                    Board selectedBoard = null;
                    for (DataSnapshot boardSnapshot : dataSnapshot.getChildren()) {
                        selectedBoard = boardSnapshot.getValue(Board.class);
                        break;
                    }
                    if (selectedBoard != null) {
                        DatabaseReference gameRef = mDatabase.child("games").child(nom_partida);
                        gameRef.child("board").setValue(selectedBoard);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the fetch
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    // Partida trobada correctament, afegir el jugador a la partida
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference playerListRef = partidaRef.child("players");
                    HashMap<String, Object> jugadorData = new HashMap<>();
                    jugadorData.put("name", jugador.getName());
                    jugadorData.put("actualPosition", jugador.getActualPosition());
                    jugadorData.put("isOwner", jugador.isOwner());
                    jugadorData.put("color", jugador.getColor());
                    playerListRef.child(uid).setValue(jugadorData); // Add player data to player list

                    // Anar al lobby
                    Intent intent = new Intent(SeleccionarTaulell.this, MenuCrearPartida.class);
                    intent.putExtra("nom_partida", nom_partida);
                    startActivity(intent);
                } else {
                    // Partida no trobada Toast
                    Toast.makeText(SeleccionarTaulell.this, "Partida no trobada", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Toast derror
                Toast.makeText(SeleccionarTaulell.this, "Error al obtenir dades de la partida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}