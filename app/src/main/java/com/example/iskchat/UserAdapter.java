package com.example.iskchat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.iskchat.Adapter.Chat;
import com.example.iskchat.Adapter.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mcontext;
    private List<User> mUser;
    private boolean ischat;
    int msg_unRead = 0;
    public  static  String firstUser=" ";

    String lastMessage;
    public UserAdapter(Context mcontext,List<User> mUser,boolean ischat){
        this.mcontext=mcontext;
        this.mUser=mUser;
        this.ischat=ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.user,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {

        final User user=mUser.get(position);


        holder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.man);

        }
        else{
            Glide.with(mcontext).load(user.getImageURL()).into(holder.profile_image);}


        if (ischat) {
            lastMessage(user.getId(),holder.last_msg,holder.unread,holder.last_image);


            if (user.getStatus().equals("online")) {
                //  receiver_state=true;
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);
            } else {
                //   receiver_state=false;
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }
        else {
            //   receiver_state=false;
            holder.last_msg.setVisibility(View.GONE);

            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,MainChatActivity.class);

                intent.putExtra("userid",user.getId());
                mcontext.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username,last_msg,unread;
        public ImageView profile_image,last_image;
        private ImageView img_on;
        private ImageView img_off;



        public ViewHolder(View itemView){
            super(itemView);
            username=itemView.findViewById(R.id.username);
            profile_image=itemView.findViewById(R.id.profile_image);
            img_on=itemView.findViewById(R.id.img_on);
            img_off=itemView.findViewById(R.id.img_off);
            last_msg=itemView.findViewById(R.id.last_msg);
            unread=itemView.findViewById(R.id.unread);
            last_image=itemView.findViewById(R.id.last_image);

        }
    }

    public void lastMessage(final String userid, final TextView last_msg,final TextView unread,final ImageView last_image){
        lastMessage="default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                msg_unRead=0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if ( !chat.equals("seen")&&chat.getReciver().equals(firebaseUser.getUid())&&chat.getSender().equals(userid)){
                        msg_unRead++;
                        unread.setVisibility(View.VISIBLE);
                        unread.setText(Integer.toString(msg_unRead));
                        firstUser=userid;
                    }
                    if(chat.getReciver().equals(firebaseUser.getUid())&&chat.getSender().equals(userid)||
                            chat.getReciver().equals(userid)&&chat.getSender().equals(firebaseUser.getUid())){
                        if (chat.getType().equals("txt")){
                            last_msg.setVisibility(View.VISIBLE);
                        last_image.setVisibility(View.GONE);
                        lastMessage = chat.getMessage();}
                        else if (chat.getType().equals("image")){
                            last_msg.setVisibility(View.GONE);
                            last_image.setVisibility(View.VISIBLE);
                        }

                    }

                }
                switch (lastMessage){
                    case "default"
                            :last_msg.setText("No Message");

                        break;
                    default:
                        last_msg.setText(lastMessage);
                        break;
                }
                lastMessage="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}