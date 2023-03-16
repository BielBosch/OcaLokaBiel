package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CrearTaulell extends AppCompatActivity {
    private ImageView clickedBox;
    private  Board board;
    ImageView add_box1,add_box2,add_box3,add_box4,add_box5,add_box6,add_box7,add_box8,add_box9,add_box10,add_box11,add_box12,
            add_box13,add_box14,add_box15,add_box16,add_box17,add_box18,add_box19,add_box20,add_box21,add_box22,add_box23,add_box24,add_box25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_taulell);

        board = new Board(); // create a new Board object

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
        add_box14= findViewById(R.id.add_box14);
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


            addBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    clickedBox = addBox;
                    Intent i = new Intent(getApplicationContext(),GaleriaCaselles.class);
                startActivityForResult(i, 100);

            }
        });
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            // Get the result data
            String result = data.getStringExtra("result");

            // Do something with the result data
            Toast.makeText(this, "Result: " + clickedBox, Toast.LENGTH_SHORT).show();

            String imageUrl = data.getStringExtra("result");

            // Carregar l'imatge a la casella seleccionada
            Glide.with(this)
                    .load(imageUrl)
                    .into(clickedBox);

        }
    }
}