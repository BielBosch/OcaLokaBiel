package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GaleriaCaselles extends AppCompatActivity {

    private List<Box> boxList;
    private GridAdapterCasella adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_caselles);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        boxList = new ArrayList<>();
        adapter = new GridAdapterCasella(boxList);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference boxesRef = database.getReference("boxes");

        List<Box> boxes = new ArrayList<>();

        boxesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot boxSnapshot : dataSnapshot.getChildren()) {
                    String title = boxSnapshot.child("title").getValue(String.class);
                    String description = boxSnapshot.child("description").getValue(String.class);
                    String imageUrl = boxSnapshot.child("imageUrl").getValue(String.class);

                    if (title != null && description != null && imageUrl != null) {
                        Box box = new Box(title, description, imageUrl);
                        boxes.add(box);
                    } else {
                        Log.e("GaleriaCaselles", "One or more fields for a box were null");
                    }
                }

                adapter.boxList.addAll(boxes);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("GaleriaCaselles", "Firebase database error: " + databaseError.getMessage());
            }
        });


    }
}