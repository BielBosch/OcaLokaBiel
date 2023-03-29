package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CrearTaulell extends AppCompatActivity {
    private ImageView clickedBox;
    private Board board;
    private Button btnGuardar;
    private int count = 0; // initialize count outside the loop
    private final Map<Integer, ImageView> boxMap = new LinkedHashMap<>();

    EditText et_guardar_taulell;
    LinearLayout ll_guardar_taulell;
    ImageView add_box1, add_box2, add_box3, add_box4, add_box5, add_box6, add_box7, add_box8, add_box9, add_box10, add_box11, add_box12,
            add_box13, add_box14, add_box15, add_box16, add_box17, add_box18, add_box19, add_box20, add_box21, add_box22, add_box23, add_box24, add_box25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_taulell);

        //creacio del tauler
        board = new Board(); // create a new Board object

        et_guardar_taulell = findViewById(R.id.et_guardar_taulell);
        ll_guardar_taulell = findViewById(R.id.ll_guardar_taulell);
        btnGuardar = findViewById(R.id.btn_guardar_taulell);

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

        //creacio dels 25 onclick listeners
        for (int i = 1; i <= 25; i++) {
            int resId = getResources().getIdentifier("add_box" + i, "id", getPackageName());
            ImageView addBox = findViewById(resId);
            boxMap.put(i, addBox);

            addBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedBox = addBox;
                    int boxNumber = Integer.parseInt(clickedBox.getResources().getResourceEntryName(clickedBox.getId()).replace("add_box", "")); //agafo string add_box i li gafo el numero
                    clickedBox.setTag(boxNumber - 1); //menys x perque el contador va per 1 per davant

                    Intent i = new Intent(getApplicationContext(), GaleriaCaselles.class);
                    startActivityForResult(i, 100);
                }
            });
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = et_guardar_taulell.getText().toString();
                board.setName(name);

                DatabaseReference boardsRef = FirebaseDatabase.getInstance().getReference("boards");
                String boardId = boardsRef.push().getKey();
                DatabaseReference boardRef = boardsRef.child(boardId);
                boardRef.setValue(board)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(CrearTaulell.this, "Taulell "+board.getName()+" guardat correctament! ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CrearTaulell.this, CrearTaulellCaselles.class);
                                startActivity(intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CrearTaulell.this, "Error al guardar el taulell! " + e, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            String imageUrl = data.getStringExtra("result");
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");

            int index = Integer.parseInt(clickedBox.getTag().toString());

            // Carregar l'imatge a la casella seleccionada
            Glide.with(this)
                    .load(imageUrl)
                    .error(R.drawable.errorimage)
                    .into(clickedBox);


            Box selectedBox = board.getBox(index);
            //passar valors de la casella
            selectedBox.setImageUrl(imageUrl);
            selectedBox.setTitle(title);
            selectedBox.setDescription(description);
            selectedBox.setFilled(true);

            board.setBox(index, selectedBox);//afegir casella a larray de caselles

            Toast.makeText(this, "Casella afegida correctament! ", Toast.LENGTH_SHORT).show();

            Map<String, Object> boardData = new HashMap<>();
            boardData.put("name", board.getName());

            List<Map<String, Object>> boxList = new ArrayList<>();
            for (Box box : board.getBoxes()) {
                Map<String, Object> boxMap = new HashMap<>();
                boxMap.put("title", box.getTitle());
                boxMap.put("description", box.getDescription());
                boxMap.put("imageUrl", box.getImageUrl());
                boxMap.put("filled", box.isFilled());
                boxList.add(boxMap);
            }

            boardData.put("boxes", boxList);

            if (board.getNumFilledBoxes() == 2) { //guardar tauler al firebase i mostar boto

                Toast.makeText(this, String.valueOf(board.getNumFilledBoxes()), Toast.LENGTH_SHORT).show();
                ll_guardar_taulell.setVisibility(View.VISIBLE);
            }
        }
    }
}