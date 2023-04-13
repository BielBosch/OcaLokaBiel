package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MenuCrearPartida extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView jugador1_name;
    private TextView jugador2_name;
    private TextView jugador3_name;
    private TextView jugador4_name;
    private TextView jugador5_name;

    Spinner spinner, spinner2, spinner3, spinner4, spinner5;
    String[] fitxesNoms;
    int[] fitxesIcons;
    Button btn_jugar_crearpartida;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);

        String gameName = getIntent().getStringExtra("nom_partida");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_jugar_crearpartida = findViewById(R.id.btn_jugar_crearpartida);

        //initialize names
        jugador1_name = findViewById(R.id.jugador1_name);
        jugador2_name = findViewById(R.id.jugador2_name);
        jugador3_name = findViewById(R.id.jugador3_name);
        jugador4_name = findViewById(R.id.jugador4_name);
        jugador5_name = findViewById(R.id.jugador5_name);


        // Initialize the spinner
        spinner = findViewById(R.id.spinner_fitxes);
        spinner2 = findViewById(R.id.spinner_fitxes2);
        spinner3 = findViewById(R.id.spinner_fitxes3);
        spinner4 = findViewById(R.id.spinner_fitxes4);
        spinner5 = findViewById(R.id.spinner_fitxes5);

        fitxesNoms = getResources().getStringArray(R.array.spinner_items);
        fitxesIcons = new int[]{R.drawable.capollblau, R.drawable.capollverd, R.drawable.capollgroc, R.drawable.capolllila, R.drawable.capollvarmell};
        ItemAdapter adapter = new ItemAdapter(this, fitxesNoms, fitxesIcons);

        //Assign adapters to the spinners
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        String name = getIntent().getStringExtra("nom_partida");
        updatePlayerNameFromFirebase();


        btn_jugar_crearpartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarTriarFitxes();

            }
        });
    }

    private void comprovarTriarFitxes() {
        // create an array to store the selected items
        String[] selectedItems = new String[]{
                (String) spinner.getSelectedItem(),
                (String) spinner2.getSelectedItem(),
                (String) spinner3.getSelectedItem(),
                (String) spinner4.getSelectedItem(),
                (String) spinner5.getSelectedItem()
        };

        // controlar fitxes duplicades
        Set<String> set = new HashSet<>(Arrays.asList(selectedItems));
        if (set.size() == selectedItems.length) {
            // fitxa ben triada, canviem de activitys
            // Update "Jugador 1", "Jugador 2", etc. with usernames of players
            for (int i = 0; i < selectedItems.length; i++) {
                mDatabase.child("jugador" + (i + 1)).setValue(selectedItems[i]);
            }
            //startActivity(new Intent(MenuCrearPartida.this, SeleccionarTaulell.class));
        } else {
            // fitxa duplicada
            Toast.makeText(MenuCrearPartida.this, "Cada jugador ha de triar una fitxa diferent", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePlayerNameFromFirebase() {
        // Get the current user from Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        String gameName = getIntent().getStringExtra("nom_partida");
        // Get a reference to the "players" node in Firebase Realtime Database
        DatabaseReference playersRef = mDatabase.child("games").child("nomPartida").child("players").child(userId);

        Toast.makeText(MenuCrearPartida.this, userId + "  PLAYERNAME", Toast.LENGTH_SHORT).show();

        // Add a ValueEventListener to listen for changes in the player's name
        playersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the player's name from the DataSnapshot
                String playerName = dataSnapshot.child("name").getValue(String.class);
                Toast.makeText(MenuCrearPartida.this, playerName + "  PLAYERNAME", Toast.LENGTH_SHORT).show();

                // Update the TextView for player's name with the retrieved value
                jugador1_name.setText(playerName);
                Toast.makeText(MenuCrearPartida.this,gameName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", "Failed to read player's name from Firebase.", databaseError.toException());
            }
        });
    }
}
