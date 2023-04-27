package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MenuCrearPartida extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView jugador1_name;
    private TextView jugador2_name;
    private TextView jugador3_name;
    private TextView jugador4_name;
    private TextView jugador5_name;
    private TextView tv_waiting_players;
    private LinearLayout linear_layout_jugadors;
    private Button btn_jugar_crearpartida;
    private ProgressBar loadingProgressBar;
    private ImageView iv_imatge_jugador1,iv_imatge_jugador2,iv_imatge_jugador3,iv_imatge_jugador4,iv_imatge_jugador5;
    private ImageView creuJugador2,creuJugador3,creuJugador4,creuJugador5;
    private DatabaseReference playersRef;
    private String gameName;

    Spinner spinner, spinner2, spinner3, spinner4, spinner5;
    String[] fitxesNoms;
    int[] fitxesIcons;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);
        playersRef = FirebaseDatabase.getInstance().getReference().child("players");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn_jugar_crearpartida = findViewById(R.id.btn_jugar_crearpartida);

        //initialize names
        jugador1_name = findViewById(R.id.jugador1_name);
        jugador2_name = findViewById(R.id.jugador2_name);
        jugador3_name = findViewById(R.id.jugador3_name);
        jugador4_name = findViewById(R.id.jugador4_name);
        jugador5_name = findViewById(R.id.jugador5_name);

        //initialize imageviews

        iv_imatge_jugador1 = findViewById(R.id.iv_imatge_jugador1);
        iv_imatge_jugador2 = findViewById(R.id.iv_imatge_jugador2);
        iv_imatge_jugador3 = findViewById(R.id.iv_imatge_jugador3);
        iv_imatge_jugador4 = findViewById(R.id.iv_imatge_jugador4);
        iv_imatge_jugador5 = findViewById(R.id.iv_imatge_jugador5);

        //creus
        creuJugador2 = findViewById(R.id.iv_imatge_icona2);
        creuJugador3 = findViewById(R.id.iv_imatge_icona3);
        creuJugador4 = findViewById(R.id.iv_imatge_icona4);
        creuJugador5 = findViewById(R.id.iv_imatge_icona5);


        //initialize layouts
        tv_waiting_players = findViewById(R.id.tv_waiting_players);
        linear_layout_jugadors = findViewById(R.id.linear_layout_jugadors);
        btn_jugar_crearpartida = findViewById(R.id.btn_jugar_crearpartida);

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

        loadingProgressBar = findViewById(R.id.loadingProgressBar);


        afegirNomJugador();

        btn_jugar_crearpartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprovarTriarFitxes();


            }
        });

        creuJugador2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esborrarJugador();
            }
        });
    }

    private void comprovarTriarFitxes() {

        String gameName = getIntent().getStringExtra("nom_partida");
        DatabaseReference gameRef = mDatabase.child("games").child(gameName);
        //spinner
        String[] selectedItems = new String[]{
                (String) spinner.getSelectedItem(),
                (String) spinner2.getSelectedItem(),
                (String) spinner3.getSelectedItem(),
                (String) spinner4.getSelectedItem(),
                (String) spinner5.getSelectedItem()
        };
        // Attach a listener to the game node to retrieve the player IDs of all players in the game
        gameRef.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> playerIds = new ArrayList<>();
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String playerId = playerSnapshot.getKey();
                    playerIds.add(playerId);
                }
                // controlar fitxes duplicades
                Set<String> set = new HashSet<>(Arrays.asList(selectedItems));

                if (set.size() == selectedItems.length) {
                    // assginar colors
                    for (int i = 0; i < playerIds.size() ; i++) {

                        //recorre les posicions del firebase i la llista de jugadors
                        DatabaseReference playerRef = mDatabase.child("games").child(getIntent().getStringExtra("nom_partida")).child("players").child(playerIds.get(i));
                        playerRef.child("color").setValue(selectedItems[i]); //primer spinner

                    }
                    //seguent pantalla
                    Intent intent = new Intent(MenuCrearPartida.this, Partida.class);
                    intent.putExtra("nom_partida", gameName);
                    startActivity(intent);

                } else {
                    // fitxa duplicada
                    Toast.makeText(MenuCrearPartida.this, "Cada jugador ha de triar una fitxa diferent", Toast.LENGTH_SHORT).show();


                    //BORRARO DESPRES ES PER FER PROVES
                    Intent intent = new Intent(MenuCrearPartida.this, Partida.class);
                    intent.putExtra("nom_partida", gameName);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });
    }


    /*
    private void afegirNolmJugador() {
        String gameName = getIntent().getStringExtra("nom_partida");
        DatabaseReference playersRef = mDatabase.child("games").child(gameName).child("players");
        DatabaseReference gameRef = mDatabase.child("games").child(gameName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> playerIds = new ArrayList<>();
                    for (DataSnapshot playerSnapshot : dataSnapshot.child("players").getChildren()) {
                        String playerId = playerSnapshot.getKey();
                        playerIds.add(playerId);
                    }

                    playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                ArrayList<String> playerNames = new ArrayList<>();
                                boolean isOwner = false;

                                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                                    String playerName = playerSnapshot.child("name").getValue(String.class);
                                    boolean isCreator = playerSnapshot.child("isOwner").getValue(Boolean.class);

                                    System.out.println("KEY OWNER"+playerSnapshot.getKey());
                                    System.out.println("USER ID"+userId);


                                    if (playerSnapshot.getKey().equals(userId) && isCreator) {
                                        // Check if the player is the creator based on isOwner field
                                        // Set the player's name in the TextView for creator
                                        System.out.println("DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE DINTRE");


                                        jugador1_name.setText(playerName.substring(0, Math.min(playerName.length(), 8)));
                                        //pasar la foto de perfil
                                        posarFotoPerfilOwner(user, iv_imatge_jugador1);
                                        isOwner = true;

                                    } else {
                                        playerNames.add(playerName);
                                    }
                                }

                                System.out.println("playerNames.size()  "+playerNames.size() + playerNames);
                                System.out.println("playerIDS  "+playerIds.size() + playerIds);
                                System.out.println(isOwner);
                                if (isOwner) {
                                    // Update the TextViews with player names for other players
                                        if (playerNames.size() >= 1) {
                                            jugador2_name.setText(playerNames.get(0).substring(0, Math.min(playerNames.get(0).length(), 8)));
                                            posarFotoPerfil(playerIds.get(0), iv_imatge_jugador2);
                                            System.out.println("1");
                                        }
                                        if (playerNames.size() >= 2) {
                                            jugador3_name.setText(playerNames.get(1).substring(0, Math.min(playerNames.get(1).length(), 8)));
                                            posarFotoPerfil(playerIds.get(1), iv_imatge_jugador3);
                                            System.out.println("2");
                                        }
                                        if (playerNames.size() >= 3) {
                                            jugador4_name.setText(playerNames.get(2).substring(0, Math.min(playerNames.get(2).length(), 8)));
                                            posarFotoPerfil(playerIds.get(2), iv_imatge_jugador4);
                                        }
                                        if (playerNames.size() >= 4) {
                                            jugador5_name.setText(playerNames.get(3).substring(0, Math.min(playerNames.get(3).length(), 8)));
                                            posarFotoPerfil(playerIds.get(3), iv_imatge_jugador5);
                                        }

                                    vistaOwner();
                                } else {
                                    vistaJugadorNormal(playerNames);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("FirebaseError", "Failed to read player names from Firebase.", databaseError.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", "Failed to read player names from Firebase.", databaseError.toException());
            }
        });
    }
*/

    private void afegirNomJugador() {


        gameName = getIntent().getStringExtra("nom_partida");
        DatabaseReference playersRef = mDatabase.child("games").child(gameName).child("players");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();



        playersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> playerNames = new ArrayList<>();
                    boolean isOwner = false;

                    for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                        String playerName = playerSnapshot.child("name").getValue(String.class);
                        boolean isCreator = playerSnapshot.child("isOwner").getValue(Boolean.class);

                        if (playerSnapshot.getKey().equals(userId) && isCreator) {
                            // Check if the player is the creator based on isOwner field
                            // Set the player's name in the TextView for creator
                            jugador1_name.setText(playerName.substring(0, Math.min(playerName.length(), 8)));
                            //pasar la foto de perfil
                            posarFotoPerfilOwner(user,iv_imatge_jugador1);

                            isOwner = true;
                        } else {
                            playerNames.add(playerName);
                        }
                    }

                    if (isOwner) {
                        // Update the TextViews with player names for other players
                        if (!playerNames.isEmpty()) {
                            if (playerNames.size() >= 1) {
                                jugador2_name.setText(playerNames.get(0).substring(0, Math.min(playerNames.get(0).length(), 8)));

                            }
                            if (playerNames.size() >= 2) {
                                jugador3_name.setText(playerNames.get(1).substring(0, Math.min(playerNames.get(1).length(), 8)));

                            }
                            if (playerNames.size() >= 3) {
                                jugador4_name.setText(playerNames.get(2).substring(0, Math.min(playerNames.get(2).length(), 8)));
                            }
                            if (playerNames.size() >= 4) {
                                jugador5_name.setText(playerNames.get(3).substring(0, Math.min(playerNames.get(3).length(), 8)));

                            }
                        }
                        vistaOwner();
                    } else {

                        vistaJugadorNormal(playerNames);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", "Failed to read player names from Firebase.", databaseError.toException());
            }
        });
    }

    public void vistaOwner(){
        // Hide the waiting players TextView and progress bar
        tv_waiting_players.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.GONE);
        // Show the lobby players LinearLayout
        btn_jugar_crearpartida.setVisibility(View.VISIBLE);
        linear_layout_jugadors.setVisibility(View.VISIBLE);
    }

    public void vistaJugadorNormal(ArrayList<String> playerNames){
        // Show the waiting players TextView and progress bar
        tv_waiting_players.setText("Esperant a l'anfitrió : " + playerNames.size() +"/5");
        tv_waiting_players.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
        loadingProgressBar.setIndeterminate(true);
        btn_jugar_crearpartida.setVisibility(View.GONE);
        linear_layout_jugadors.setVisibility(View.GONE);
    }

    public void posarFotoPerfilOwner (FirebaseUser user, ImageView iv_imatge) {
        Uri photoUrl = user.getPhotoUrl();
        if (photoUrl != null) {
            Glide.with(getApplicationContext())
                    .load(photoUrl)
                    .into(iv_imatge);
        }
    }

    /*
    public void posarFotoPerfil(String userId, ImageView iv_imatge) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String currentUserId = user.getUid();
            if (currentUserId.equals(userId)) {
                Uri photoUrl = user.getPhotoUrl();
                if (photoUrl != null) {
                    Glide.with(getApplicationContext())
                            .load(photoUrl)
                            .into(iv_imatge);
                }
            }
        }
    }
*/

    public void esborrarJugador() {
        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("prova").child("players");
        String playerKey = "97VnnUI5XyUYZ7Vi2NCZpUTK0Fo1"; // replace with the actual key of the player you want to remove
        playersRef.child(playerKey).removeValue();
        Toast.makeText(MenuCrearPartida.this, " clicked" + playersRef, Toast.LENGTH_SHORT).show();

    }

}
