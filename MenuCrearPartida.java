package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MenuCrearPartida extends AppCompatActivity {

    Spinner spinner,spinner2,spinner3,spinner4,spinner5;
    String[] fitxesNoms;
    int[] fitxesIcons;
    Button btn_jugar_crearpartida;

    Animation scaleUp,scaleDown;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);

        btn_jugar_crearpartida = findViewById(R.id.btn_jugar_crearpartida);

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


        btn_jugar_crearpartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItem() != null && spinner2.getSelectedItem() != null &&
                        spinner3.getSelectedItem() != null && spinner4.getSelectedItem() != null && spinner5.getSelectedItem() != null) {
                    startActivity(new Intent(MenuCrearPartida.this, SeleccionarTaulell.class));
                } else {
                    Toast.makeText(MenuCrearPartida.this, "Selecciona una fitxa per a cada jugador", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}