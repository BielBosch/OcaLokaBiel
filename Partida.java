package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Partida extends AppCompatActivity {


     TableLayout Taulell;
     ImageView add_box1, add_box2, add_box3, add_box4, add_box5, add_box6, add_box7, add_box8, add_box9, add_box10, add_box11, add_box12,
            add_box13, add_box14, add_box15, add_box16, add_box17, add_box18, add_box19, add_box20, add_box21, add_box22, add_box23, add_box24, add_box25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);


        //taulell
        Taulell = findViewById(R.id.tl_tauler);

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


        carregarBoard(gameQuery,gamesRef);
    }

    public void carregarBoard(Query gameQuery, DatabaseReference gamesRef) {
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

        HashMap<String, Object> board = (HashMap<String,Object>) ds.getValue();
        //System.out.println("Board: " + board.toString());
        List<Map<String, Object>> boxList = (List<Map<String, Object>>) board.get("boxes");

        List<Box> boxes = new ArrayList<>();

        for (Map<String, Object> boxMap : boxList) {
            String title = (String) boxMap.get("title");
            String description = (String) boxMap.get("description");
            String imageUrl = (String) boxMap.get("imageUrl");

            Box box = new Box(title, description, imageUrl);

            boxes.add(box);

            //pintar caselles al taulell
            for (int i = 0; i < boxes.size(); i++) {
                String boxImageUrl = boxes.get(i).getImageUrl();
                int resID = getResources().getIdentifier("add_box" + (i+1), "id", getPackageName());
                ImageView imageView = findViewById(resID);
                Picasso.get().load(boxImageUrl).into(imageView);
            }
        }
    }

}