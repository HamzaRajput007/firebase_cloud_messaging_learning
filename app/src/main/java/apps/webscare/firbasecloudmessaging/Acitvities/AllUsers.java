package apps.webscare.firbasecloudmessaging.Acitvities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

import apps.webscare.firbasecloudmessaging.Adapters.AllUserAdapter;
import apps.webscare.firbasecloudmessaging.Interfaces.OnClickRecyclerViewItem;
import apps.webscare.firbasecloudmessaging.Models.Users;
import apps.webscare.firbasecloudmessaging.R;

public class AllUsers extends AppCompatActivity implements OnClickRecyclerViewItem {
    FirebaseFirestore firebaseFirestore;
    ArrayList<Users> usersList;

    RecyclerView recyclerView ;
    AllUserAdapter allUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        recyclerView = findViewById(R.id.recyclerViewAllUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        usersList.clear();
        firebaseFirestore.collection("Users").addSnapshotListener( this , new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String user_id = doc.getDocument().getId();
                        Users user = doc.getDocument().toObject(Users.class).withId(user_id);
                        usersList.add(user);
                    }
                }
                allUserAdapter = new AllUserAdapter(usersList , AllUsers.this , AllUsers.this);
                recyclerView.setAdapter(allUserAdapter);
            }
        });
    }

    @Override
    public void onRecyclerViewItemClicked(int position) {
        Log.d("UserID", usersList.get(position).userId);
        Intent toSendActivity = new Intent(AllUsers.this , SendMessage.class);
        toSendActivity.putExtra("send_to" , usersList.get(position).getName());
        toSendActivity.putExtra("user_id" , usersList.get(position).userId);
        startActivity(toSendActivity);
    }
}