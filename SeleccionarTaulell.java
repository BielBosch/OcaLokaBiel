package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeleccionarTaulell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Define a reference to your Firebase Realtime Database
    private DatabaseReference mDatabase;
    TextView tv_spinner_taulers;
    // Define a list to hold the board names
    private final List<String> nomTaulers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_taulell);


        tv_spinner_taulers = findViewById(R.id.tv_spinner_taulers);
        // Get a reference to your Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Query the "boards" node in the database to get the board names
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

        String boardName = nomTaulers.get(position);
        tv_spinner_taulers.setText(boardName);
        Toast.makeText(this,"Tauler "+nomTaulers.get(position)+" seleccionat",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}