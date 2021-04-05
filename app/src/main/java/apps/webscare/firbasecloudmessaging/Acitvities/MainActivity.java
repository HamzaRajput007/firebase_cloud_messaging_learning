package apps.webscare.firbasecloudmessaging.Acitvities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import apps.webscare.firbasecloudmessaging.R;

public class MainActivity extends AppCompatActivity {

    StorageReference mStore;
    FirebaseAuth mAuth;
    FirebaseFirestore mFireStore;
    EditText nameEditText, emailEditText, passwordEditText;
    Button signUpBtn, toLoginBtn;
    String email, pass, name;
    Uri imageUri;
    ImageView profileImageView;
    int PICK_IMAGE = 1;
    FirebaseUser firebaseUser;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
        profileImageView.setImageURI(imageUri);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(this, "User Not Logged In", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User Logged in successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseStorage.getInstance().getReference().child("images");
        mFireStore = FirebaseFirestore.getInstance();

        nameEditText = findViewById(R.id.nameTbId);
        emailEditText = findViewById(R.id.emailTbId);
        passwordEditText = findViewById(R.id.passwordTbId);
        signUpBtn = findViewById(R.id.signUpBtnId);
        toLoginBtn = findViewById(R.id.toLoginBtnId);
        profileImageView = findViewById(R.id.profileImageViewID);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPickImage = new Intent();
                toPickImage.setType("image/*");
                toPickImage.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(toPickImage, "Select Picture"), PICK_IMAGE);
            }
        });



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {

                    mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String user_id = mAuth.getCurrentUser().getUid();
                                StorageReference user_profile = mStore.child(user_id + ".jpg");
                                user_profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            name = nameEditText.getText().toString();
                                            pass = passwordEditText.getText().toString();
                                            email = emailEditText.getText().toString();
                                            Map<String, Object> userMap = new HashMap<>();
                                            userMap.put("name", name);
//                                            userMap.put("image" , imageUri);
                                            mFireStore.collection("Users").document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent toHome = new Intent(MainActivity.this, Home.class);
                                                    startActivity(toHome);
                                                    finish();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(MainActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(MainActivity.this, "Task Unsuccessful : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("Error", " : " + task.getException().getMessage()  );
                            }
                        }
                    });
                }
            }
        });

    }
}