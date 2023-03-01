package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuDispositius extends AppCompatActivity {

    Button btn_jugarVarisDispositius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dispositius);

        btn_jugarVarisDispositius = findViewById(R.id.btn_jugar_varis_dispositius);


        btn_jugarVarisDispositius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuDispositius.this, MenuCrearPartida.class));
            }
        });

    }
}