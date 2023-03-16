package cat.dam.biel.ocaloka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MenuPerfil extends AppCompatActivity {

    TextView tvUsername;
    TextView tvRegistrationDate;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_perfil);

        tvUsername = findViewById(R.id.nom_jugador_perfil);
        tvRegistrationDate = findViewById(R.id.tv_data_registre_usuari);
        profileImage = findViewById(R.id.iv_imatge_jugador);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            tvUsername.setText(displayName);

            Date registrationDate = new Date(user.getMetadata().getCreationTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = sdf.format(registrationDate);
            String registrationText = getString(R.string.member_since) + " " + formattedDate;
            tvRegistrationDate.setText(registrationText);
        }
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(profileImage);
        }
    }
}