package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Partida extends AppCompatActivity {


    private TableLayout Taulell;
    private ImageView add_box1, add_box2, add_box3, add_box4, add_box5, add_box6, add_box7, add_box8, add_box9, add_box10, add_box11, add_box12,
            add_box13, add_box14, add_box15, add_box16, add_box17, add_box18, add_box19, add_box20, add_box21, add_box22, add_box23, add_box24, add_box25;

    private ArrayList<ImageView> lilaPieceList = new ArrayList<>(25);
    private ArrayList<ImageView> verdPieceList = new ArrayList<>(25);
    private ArrayList<ImageView> blauPieceList = new ArrayList<>(25);
    private ArrayList<ImageView> varmellPieceList = new ArrayList<>(25);
    private ArrayList<ImageView> grocPieceList = new ArrayList<>(25);
    private List<Player> players = new ArrayList<>(); // declare the list of players as a class variable
    private HashMap<String, Player> playersMap = new HashMap<>();
    private DatabaseReference mDatabase;
    private ImageView iv_dau;
    private int rollValue;
    private TextView tv_ordre;
    private List<Box> boxes = new ArrayList<>();
    private LinearLayout ll_casella;
    private TextView tvNumeroCasella,tvTitolCasella,tvDescripcioCasella;
    private ImageView ivImatgeCasella;
    private int posicioActualCasella;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);


        //taulell i dau
        Taulell = findViewById(R.id.tl_tauler);
        iv_dau = findViewById(R.id.iv_dau);
        tv_ordre = findViewById(R.id.tv_ordre);

        //casella taulell
        ll_casella = findViewById(R.id.ll_casella);
        tvNumeroCasella = findViewById(R.id.tv_numero_casella);
        tvTitolCasella = findViewById(R.id.tv_titol_casella);
        tvDescripcioCasella = findViewById(R.id.tv_descripcio_casella);
        ivImatgeCasella = findViewById(R.id.iv_imatge_casella);


        //caselles
        add_box1 = findViewById(R.id.add_box1);
        add_box2 = findViewById(R.id.add_box2);
        add_box3 = findViewById(R.id.add_box3);
        add_box4 = findViewById(R.id.add_box4);
        add_box5 = findViewById(R.id.add_box5);
        add_box6 = findViewById(R.id.add_box6);
        add_box7 = findViewById(R.id.add_box7);
        add_box8 = findViewById(R.id.add_box8);
        add_box9 = findViewById(R.id.add_box9);
        add_box10 = findViewById(R.id.add_box10);
        add_box11 = findViewById(R.id.add_box11);
        add_box12 = findViewById(R.id.add_box12);
        add_box13 = findViewById(R.id.add_box13);
        add_box14 = findViewById(R.id.add_box14);
        add_box15 = findViewById(R.id.add_box15);
        add_box16 = findViewById(R.id.add_box16);
        add_box17 = findViewById(R.id.add_box17);
        add_box18 = findViewById(R.id.add_box18);
        add_box19 = findViewById(R.id.add_box19);
        add_box20 = findViewById(R.id.add_box20);
        add_box21 = findViewById(R.id.add_box21);
        add_box22 = findViewById(R.id.add_box22);
        add_box23 = findViewById(R.id.add_box23);
        add_box24 = findViewById(R.id.add_box24);
        add_box25 = findViewById(R.id.add_box25);

        String gameName = getIntent().getStringExtra("nom_partida");

        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
        Query gameQuery = gamesRef.orderByChild("name").equalTo(gameName);

        //carregar taulell
        loadBoard(gameQuery, gamesRef);
        //carregar peces jugadors atotes les caselles invisibles
        loadPieces();
        //carregar peces de la casella iniciañ
        loadPiecesFirstBox();
        //assignar colors designats als jugadors
        loadPlayers(gameName);

        // Wait for the players to finish loading before continuing with onCreate
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { //PARTIDA

                //for (Player player : players) { //recorre els jugadors
                    tirada(gameName);

                    //mostrarProvaCasella(gameQuery);

                //}


            }
        }, 2000); // Wait for 2 seconds (adjust this time as needed)

        //Escolto el Click
        iv_dau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Escoltes la BD
        DatabaseReference gameRef = FirebaseDatabase.getInstance()
                .getReference("games/" + gameName);

        gameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //TODO: Per tots els usuaris actualitzar la posició, quan canvii alguna cosa del joc.
                //Prohibit actualitzar la BD en aquest punt.
                Game game = new Game();
                game.setName(gameName);
                game.setCurrentPlayerIndex(0);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String playerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.e("DEBUG2", "Han canviat les dades del joc " + playerId);

                DatabaseReference playerRef = database.getReference("games/" + gameName + "/players/" + playerId);
                DatabaseReference posicioAnteriorRef = playerRef.child("posicioAnterior");
                DatabaseReference posicioActualRef = playerRef.child("actualPosition");
                Log.e("posicioActualRef",posicioActualRef.toString());
                Player jugadorActual = playersMap.get(playerId);
                Integer actualPosition = dataSnapshot.child("players/"+playerId+"/actualPosition").getValue(Integer.class);
                Log.e("actualPosition: ",actualPosition.toString());

                jugadorActual.setActualPosition(actualPosition);

                posicioActualCasella = actualPosition;
                retrieveBoxForPosition(gameName, actualPosition+1);

                movePiece(jugadorActual);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", "Failed to read game state from Firebase.", databaseError.toException());
            }
        });


    }

    private void tirada(String gameName) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String playerId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference playerRef = database.getReference("games/"+gameName+"/players/" + playerId);
        DatabaseReference posicioAnteriorRef = playerRef.child("posicioAnterior");
        DatabaseReference posicioActualRef = playerRef.child("actualPosition");

        Player player = playersMap.get(playerId);
        int posAnterior = player.getActualPosition();
        player.setActualPosition(player.getActualPosition()+rollDice());
        player.setPosicioAnterior(posAnterior);
        posicioAnteriorRef.setValue(player.getPosicioAnterior());//pujar firebase
        posicioActualRef.setValue(player.getActualPosition());//pujar firebase


    }

    private void loadPiecesFirstBox() {
        lilaPieceList.get(0).setVisibility(View.VISIBLE);
        blauPieceList.get(0).setVisibility(View.VISIBLE);
        grocPieceList.get(0).setVisibility(View.VISIBLE);
        varmellPieceList.get(0).setVisibility(View.VISIBLE);
        verdPieceList.get(0).setVisibility(View.VISIBLE);
    }

    public void loadBoard(Query gameQuery, DatabaseReference gamesRef) {
        gameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Game with the given name found
                    DataSnapshot gameSnapshot = dataSnapshot.getChildren().iterator().next();
                    String gameId = gameSnapshot.getKey();

                    // Now you can retrieve the board reference for this game
                    DatabaseReference boardRef = gamesRef.child(gameId).child("board");

                    // Read the board data from the database
                    boardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                //Carregar les caselles a una llista dobjectes
                                loadBoxes(dataSnapshot);


                            } else {
                                // Board data not found
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle read error
                        }
                    });
                } else {
                    // Game with the given name not found
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle read error
            }
        });

    }

    public void loadBoxes(DataSnapshot ds) {

        HashMap<String, Object> board = (HashMap<String, Object>) ds.getValue();
        //System.out.println("Board: " + board.toString());
        List<Map<String, Object>> boxList = (List<Map<String, Object>>) board.get("boxes");

        boxes = new ArrayList<>();

        for (Map<String, Object> boxMap : boxList) {
            String title = (String) boxMap.get("title");
            String description = (String) boxMap.get("description");
            String imageUrl = (String) boxMap.get("imageUrl");

            Box box = new Box(title, description, imageUrl);

            boxes.add(box);

            //pintar caselles al taulell
            for (int i = 0; i < boxes.size(); i++) {
                String boxImageUrl = boxes.get(i).getImageUrl();
                int resID = getResources().getIdentifier("add_box" + (i + 1), "id", getPackageName());
                ImageView imageView = findViewById(resID);
                Picasso.get().load(boxImageUrl).into(imageView);
            }
        }
    }

    public void loadPieces() {
        // Iterate through all the LinearLayouts ll1 to ll25
        for (int i = 1; i <= 25; i++) {
            String linearLayoutId = "ll" + i;
            LinearLayout linearLayout = findViewById(getResources().getIdentifier(linearLayoutId, "id", getPackageName()));

            initializeImageViewsNull();

            for (int j = 0; j < 5; j++) {
                ImageView imageView = new ImageView(Partida.this);
                int imageViewId = View.generateViewId();
                imageView.setId(imageViewId);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        dpToPx(10),
                        dpToPx(10),
                        1f));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setVisibility(View.GONE);

                switch (j) {
                    case 0:
                        imageView.setImageResource(R.drawable.capolllila);
                        lilaPieceList.add(imageView);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.capollverd);
                        verdPieceList.add(imageView);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.capollblau);
                        blauPieceList.add(imageView);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.capollvarmell);
                        varmellPieceList.add(imageView);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.capollgroc);
                        grocPieceList.add(imageView);
                        break;
                }

                linearLayout.addView(imageView);
            }

        }

    }

    private int dpToPx(int dp) {
        float density = Partida.this.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void initializeImageViewsNull(){
        // Add the ImageView elements
        ImageView lila1 = null, lila2 = null, lila3 = null, lila4 = null, lila5 = null, lila6 = null, lila7 = null, lila8 = null, lila9 = null
                ,lila10 = null, lila11 = null, lila12 = null, lila13 = null, lila14 = null, lila15 = null, lila16 = null, lila17 = null
                ,lila18 = null, lila19 = null, lila20 = null, lila21 = null, lila22 = null, lila23 = null, lila25;
        ImageView verd1 = null, verd2 = null, verd3 = null, verd4 = null, verd5 = null, verd6 = null, verd7 = null, verd8 = null, verd9 = null
                ,verd10 = null, verd11 = null, verd12 = null, verd13 = null, verd14 = null, verd15 = null, verd16 = null, verd17 = null
                ,verd18 = null, verd19 = null, verd20 = null, verd21 = null, verd22 = null, verd23 = null, verd25;
        ImageView blau1 = null, blau2 = null, blau3 = null, blau4 = null, blau5 = null, blau6 = null, blau7 = null, blau8 = null, blau9 = null
                ,blau10 = null, blau11 = null, blau12 = null, blau13 = null, blau14 = null, blau15 = null, blau16 = null, blau17 = null
                ,blau18 = null, blau19 = null, blau20 = null, blau21 = null, blau22 = null, blau23 = null, blau25;
        ImageView groc1 = null, groc2 = null, groc3 = null, groc4 = null, groc5 = null, groc6 = null, groc7 = null, groc8 = null, groc9 = null
                ,groc10 = null, groc11 = null, groc12 = null, groc13 = null, groc14 = null, groc15 = null, groc16 = null, groc17 = null
                ,groc18 = null, groc19 = null, groc20 = null, groc21 = null, groc22 = null, groc23 = null, groc25;
        ImageView varmell1 = null, varmell2 = null, varmell3 = null, varmell4 = null, varmell5 = null, varmell6 = null, varmell7 = null, varmell8 = null, varmell9 = null
                ,varmell10 = null, varmell11 = null, varmell12 = null, varmell13 = null, varmell14 = null, varmell15 = null, varmell16 = null, varmell17 = null
                ,varmell18 = null, varmell19 = null, varmell20 = null, varmell21 = null, varmell22 = null, varmell23 = null, varmell25;

    }

    private void loadPlayers(String gameName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference gamesRef = database.getReference("games");
        DatabaseReference gameRef = gamesRef.child(gameName);
        DatabaseReference playersRef = gameRef.child("players");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                players = new ArrayList<>();
                playersMap = new HashMap<>();
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) playerSnapshot.child("name").getValue();
                    String email = (String) playerSnapshot.child("email").getValue();
                    int actualPosition = Integer.parseInt(playerSnapshot.child("actualPosition").getValue().toString());
                    String color = (String) playerSnapshot.child("color").getValue();

                    Player player = new Player(name, email);
                    player.setPosicioAnterior(0);
                    player.setActualPosition(actualPosition);
                    player.setColor(color);

                    players.add(player);
                    playersMap.put(playerSnapshot.getKey(), player);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }

    private void movePiece(Player player){

        //logica del joc aqui
        switch (player.getColor()) {
            case "Blau":
                blauPieceList.get(player.getPosicioAnterior()).setVisibility(View.INVISIBLE);
                blauPieceList.get(player.getActualPosition()).setVisibility(View.VISIBLE);
                break;
            case "Verd":
                verdPieceList.get(player.getPosicioAnterior()).setVisibility(View.INVISIBLE);
                verdPieceList.get(player.getActualPosition()).setVisibility(View.VISIBLE);
                break;
            case "Varmell":
                varmellPieceList.get(player.getPosicioAnterior()).setVisibility(View.INVISIBLE);
                varmellPieceList.get(player.getActualPosition()).setVisibility(View.VISIBLE);
                break;
            case "Groc":
                grocPieceList.get(player.getPosicioAnterior()).setVisibility(View.INVISIBLE);
                grocPieceList.get(player.getActualPosition()).setVisibility(View.VISIBLE);
                break;
            case "Lila":
                lilaPieceList.get(player.getPosicioAnterior()).setVisibility(View.INVISIBLE);
                lilaPieceList.get(player.getActualPosition()).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private int rollDice() {

        //agergar imatge i valor al dau

        rollValue = Dice.roll();

        switch (rollValue) {
            case 1:
                iv_dau.setImageResource(R.drawable.diceone);
                rollValue = 1;
                break;
            case 2:
                iv_dau.setImageResource(R.drawable.dicetwo);
                rollValue = 2;
                break;
            case 3:
                iv_dau.setImageResource(R.drawable.dicethree);
                rollValue = 3;
                break;
            case 4:
                iv_dau.setImageResource(R.drawable.dicefour);
                rollValue = 4;
                break;
            case 5:
                iv_dau.setImageResource(R.drawable.dicefive);
                rollValue = 5;
                break;
            case 6:
                iv_dau.setImageResource(R.drawable.dicesix);
                rollValue = 6;
                break;
            default:
                break;
        }
        Toast.makeText(Partida.this, rollValue+"", Toast.LENGTH_SHORT).show();
        Log.e("",rollValue+"");
        return rollValue;
    }

    private void retrieveBoxForPosition(String gameName, int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference boardRef = database.getReference("games/" + gameName + "/board");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot boxesSnapshot = dataSnapshot.child("boxes").child(String.valueOf(position));
                if (boxesSnapshot.exists()) {
                    String boxTitle = (String) boxesSnapshot.child("title").getValue();
                    String boxDesc = (String) boxesSnapshot.child("description").getValue();
                    String boxImg = (String) boxesSnapshot.child("imageUrl").getValue();

                    ll_casella.setVisibility(View.VISIBLE);
                    // Set the box image using boxImg
                    Glide.with(getApplicationContext()).load(boxImg).into(ivImatgeCasella);
                    // Set the box title and description
                    tvTitolCasella.setText(boxTitle);
                    tvDescripcioCasella.setText(boxDesc);
                    tvNumeroCasella.setText(String.valueOf(position));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        };

        boardRef.addListenerForSingleValueEvent(valueEventListener);
    }

}