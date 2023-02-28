package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class IniciGoogle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici_google);

        funcio();

    }

    private void funcio() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.layout_btn_google);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IniciGoogle.this, MenuInici.class));
            }
        });
    }
}