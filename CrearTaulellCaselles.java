package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CrearTaulellCaselles extends AppCompatActivity {

    Button btn_crear_casella,btn_crear_taulell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caselles_taulell_unir);

        btn_crear_taulell = findViewById(R.id.btn_crear_taulell);
        btn_crear_casella = findViewById(R.id.btn_crear_casella);

        btn_crear_taulell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),CrearTaulell.class);
                startActivity(i);

            }
        });

        btn_crear_casella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),CrearCasella.class);
                startActivity(i);

            }
        });
    }
}