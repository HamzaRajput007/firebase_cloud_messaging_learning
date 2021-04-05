package apps.webscare.firbasecloudmessaging.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.net.UnknownServiceException;
import java.util.ArrayList;

import apps.webscare.firbasecloudmessaging.Interfaces.OnClickRecyclerViewItem;
import apps.webscare.firbasecloudmessaging.Models.Users;
import apps.webscare.firbasecloudmessaging.R;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder> {

    ArrayList<Users> usersList ;
    Context mContext;
    OnClickRecyclerViewItem onClickRecyclerViewItem;

    public AllUserAdapter(ArrayList<Users> usersList, Context mContext , OnClickRecyclerViewItem onClickRecyclerViewItem) {
        this.usersList = usersList;
        this.mContext = mContext;
        this.onClickRecyclerViewItem = onClickRecyclerViewItem;
    }

    @NonNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_list_model , parent , false);
        AllUserViewHolder allUserViewHolder = new AllUserViewHolder(view , onClickRecyclerViewItem );
        return allUserViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {
        holder.userName.setText(usersList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userName;
        OnClickRecyclerViewItem onClickRecyclerViewItem;
        public AllUserViewHolder(@NonNull View itemView , OnClickRecyclerViewItem onClickRecyclerViewItem) {
            super(itemView);
            this.onClickRecyclerViewItem = onClickRecyclerViewItem;
            userName = itemView.findViewById(R.id.textViewUserNameID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickRecyclerViewItem.onRecyclerViewItemClicked(getAdapterPosition());
        }
    }


}
