package apps.webscare.firbasecloudmessaging.Acitvities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

import apps.webscare.firbasecloudmessaging.Models.Users;
import apps.webscare.firbasecloudmessaging.R;

public class Home extends AppCompatActivity {

    ImageView profilePictureImageView;
    TextView usernameTextView , allUsersTextView;
    Button allUsersBtn;
    String Uid ;

    ArrayList<Users> usersList;

    FirebaseAuth mAuth ;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profilePictureImageView = findViewById(R.id.profileImageViewHomeId);
        usernameTextView = findViewById(R.id.usernameTextViewid);
    allUsersBtn = findViewById(R.id.allUsersBtnID);
    allUsersTextView = findViewById(R.id.allUsersTextViewID);
    usersList = new ArrayList<>();
    allUsersBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent toAllUsers = new Intent(Home.this , AllUsers.class);
            startActivity(toAllUsers);

        }
    });
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Uid = mAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("Users").document(Uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String image = documentSnapshot.getString("image");
                String userName = documentSnapshot.getString("name");
                Glide.with(profilePictureImageView).load(image).into(profilePictureImageView);
                usernameTextView.setText(userName);
            }
        });
    }
}