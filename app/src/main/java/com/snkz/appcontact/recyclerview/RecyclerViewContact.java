package com.snkz.appcontact.recyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snkz.appcontact.R;
import com.snkz.appcontact.activity.EditContactActivity;
import com.snkz.appcontact.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecyclerViewContact extends RecyclerView.Adapter<RecyclerViewContact.ViewHolder> {
    private ArrayList<Contact> arrContact;

    public RecyclerViewContact() {
        this.arrContact = arrContact;
    }

    public void setArrContact(ArrayList<Contact> arrContact) {
        this.arrContact = arrContact;
    }

    @NonNull
    @Override
    public RecyclerViewContact.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(arrContact, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Contact contact = arrContact.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(),0, contact.getAvatar().length);
        holder.imgAvatar.setImageBitmap(bitmap);
        holder.tvName.setText(contact.getName());
        holder.getTvSection().setText(contact.getName().substring(0,1));

        if(position > 0){
            int i = position - 1;
            if (i < arrContact.size() && contact.getName().substring(0,1).equals(arrContact.get(i).getName().substring(0,1))){
                holder.getTvSection().setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditContactActivity.class);
                intent.putExtra("name", String.valueOf(contact.getName()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrContact!=null){
            return arrContact.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSection;
        ImageView imgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_namecontact);
            tvSection = itemView.findViewById(R.id.tv_section);
            imgAvatar = itemView.findViewById(R.id.img_avatarcontact);
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvSection() {
            return tvSection;
        }

        public ImageView getImgAvatar() {
            return imgAvatar;
        }
    }
}
