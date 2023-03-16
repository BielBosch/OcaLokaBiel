package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MenuInici extends AppCompatActivity {

    Button crearTaulell,sortir,jugar,perfil;

    //animacions
    Animation scaleUp,scaleDown;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inici);

        //buttons
        crearTaulell = findViewById(R.id.btn_crear_taulell);
        sortir = findViewById(R.id.btn_sortir);
        jugar = findViewById(R.id.btn_jugar);
        perfil = findViewById(R.id.btn_perfil);

        //animacions
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        jugar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    jugar.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    jugar.startAnimation(scaleDown);
                    startActivity(new Intent(MenuInici.this, MenuDispositius.class));
                    return false;
                }
                return true;
            }
        });

        crearTaulell.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    crearTaulell.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    crearTaulell.startAnimation(scaleDown);
                    startActivity(new Intent(MenuInici.this, CrearTaulellCaselles.class));
                    return false;
                }
                return true;
            }
        });

        perfil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    perfil.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    perfil.startAnimation(scaleDown);
                    startActivity(new Intent(MenuInici.this, MenuPerfil.class));
                    return false;
                }
                return true;
            }
        });

        sortir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    sortir.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sortir.startAnimation(scaleDown);
                    finish();
                    return false;
                }
                return true;
            }
        });
    }
}