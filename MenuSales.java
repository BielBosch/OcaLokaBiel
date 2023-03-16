package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuSales extends AppCompatActivity {

    Button btn_crear_partida,btn_unir_partida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_crear_unir);

        btn_unir_partida = findViewById(R.id.btn_unir_partida);

        btn_crear_partida = findViewById(R.id.btn_crear_partida);
        btn_crear_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),MenuCrearPartida.class);
                startActivity(i);

            }
        });

        btn_unir_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),MenuUnirsePartida.class);
                startActivity(i);

            }
        });
    }
}