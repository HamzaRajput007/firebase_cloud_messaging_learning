package apps.webscare.firbasecloudmessaging.Acitvities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import apps.webscare.firbasecloudmessaging.R;

public class SendMessage extends AppCompatActivity {

    TextView sendToTextView;
    EditText sendToEditText;
    Button sendToButton;
    ProgressBar progressBar;
    String senderID , message , userID;

    FirebaseFirestore firebaseFirestore ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        firebaseFirestore = FirebaseFirestore.getInstance();

        sendToButton = findViewById(R.id.sendToButtonId);
        sendToEditText = findViewById(R.id.sendToEditTextID);
        sendToTextView = findViewById(R.id.sendToTextViewID);
        progressBar = findViewById(R.id.progressBarID);
        progressBar.setVisibility(View.GONE);
        senderID = FirebaseAuth.getInstance().getUid();
        userID = getIntent().getStringExtra("user_id");
        sendToTextView.setText("Send To " + getIntent().getStringExtra("send_to"));
        sendToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                message = sendToEditText.getText().toString();
                if (!TextUtils.isEmpty(message)){
                    Map<String , Object> notificationMessage = new HashMap<>();
                    notificationMessage.put("message" , message);
                    notificationMessage.put("from" , senderID);

                    firebaseFirestore.collection("Users/" + userID + "/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendMessage.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                            sendToEditText.setText("");
                            progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SendMessage.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else
                    Toast.makeText(SendMessage.this, "Please Write Something!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}