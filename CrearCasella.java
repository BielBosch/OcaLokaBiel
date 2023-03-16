package cat.dam.biel.ocaloka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class CrearCasella extends AppCompatActivity {

    private DatabaseReference database;
    private StorageReference storage;
    private Uri selectedImageUri;
    private ImageView iv_select_image;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_casella);

        // Initialize Firebase database and storage references
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();
        iv_select_image = findViewById(R.id.iv_select_image);
        // Get reference to the select image button and set onClickListener

        iv_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to open gallery app to select an image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        Button btnSaveCasella = findViewById(R.id.btn_guardar_casella);
        btnSaveCasella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read user input from EditText views
                EditText etTitol = findViewById(R.id.et_titol_casella);
                EditText etDescripcio = findViewById(R.id.et_descripcio_casella);
                String titol = etTitol.getText().toString().trim();
                String descripcio = etDescripcio.getText().toString().trim();

                // Check if the required fields are filled in and an image is selected
                if (titol.isEmpty() || descripcio.isEmpty() || selectedImageUri == null) {
                    Toast.makeText(CrearCasella.this, "Si us plau, ompliu tots els camps i seleccioneu una imatge", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Generate a random UUID for the new Box object
                String uuid = UUID.randomUUID().toString();

                // Create a new Box object with the user input and the generated UUID
                Box novaCasella = new Box(titol, descripcio, "");

                // Get a reference to the image file in Firebase storage
                StorageReference imageRef = storage.child("images").child(uuid + ".jpg");

                // Upload the selected image to Firebase storage and get the download URL
                UploadTask uploadTask = imageRef.putFile(selectedImageUri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL for the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Set the image URL for the new Box object
                                novaCasella.setImageUrl(uri.toString());

                                // Save the new Box object to the Firebase database
                                database.child("boxes").child(uuid).setValue(novaCasella);

                                // Display a success message to the user and return to the MainActivity
                                Toast.makeText(CrearCasella.this, "Casella guardada correctament", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CrearCasella.this, CrearTaulellCaselles.class);
                                startActivity(intent);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Display an error message to the user if the upload fails
                        Toast.makeText(CrearCasella.this, "Error guardant la casella", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            selectedImageUri = data.getData();

            // Set the selected image as the source of the ImageView
            iv_select_image.setImageURI(selectedImageUri);
        }
    }
}