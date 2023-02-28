package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MenuCrearPartida extends AppCompatActivity {

    Spinner spinner,spinner2,spinner3,spinner4,spinner5;
    String[] fitxesNoms;
    int[] fitxesIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);

        // Initialize the spinner
        spinner = findViewById(R.id.spinner_fitxes);
        spinner2 = findViewById(R.id.spinner_fitxes2);
        spinner3 = findViewById(R.id.spinner_fitxes3);
        spinner4 = findViewById(R.id.spinner_fitxes4);
        spinner5 = findViewById(R.id.spinner_fitxes5);



        fitxesNoms = getResources().getStringArray(R.array.spinner_items);
        fitxesIcons = new int[]{R.drawable.capollblau, R.drawable.capollverd, R.drawable.capollgroc, R.drawable.capolllila,R.drawable.capollvarmell};
        ItemAdapter adapter = new ItemAdapter(this, fitxesNoms, fitxesIcons);

        //Assign adapters to the spinners
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);

    }
}
