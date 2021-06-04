package com.snkz.appcontact.recyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snkz.appcontact.R;
import com.snkz.appcontact.model.Contact;
import com.snkz.appcontact.sqlite.SQLiteContactHelper;

import java.util.ArrayList;

public class RecyclerViewBirthday  extends RecyclerView.Adapter<RecyclerViewBirthday.ViewHolder> {

    private SQLiteContactHelper sqLiteContactHelper;
    private ArrayList <Contact> arrContact;

    public RecyclerViewBirthday() {
        this.arrContact = arrContact;
    }

    public void setArrContact(ArrayList<Contact> arrContact) {
        this.arrContact = arrContact;
    }

    @NonNull
    @Override
    public RecyclerViewBirthday.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_birthday, parent, false);
        return new RecyclerViewBirthday.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBirthday.ViewHolder holder, int position) {
        Contact contact = arrContact.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(),0, contact.getAvatar().length);
        holder.imgAvatar.setImageBitmap(bitmap);
        holder.tvName.setText("💥 HPBD to " +contact.getName() + " !!! 🎉");
        holder.tvSendMessage.setText("👉 Touch to send happy message 💌");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteContactHelper sqLiteContactHelper = new SQLiteContactHelper(holder.itemView.getContext());
                String message = "Happy birhtday to "+contact.getName().trim()+ " !! 🎉🤗";
                String name = contact.getName().toString().trim();
                String phoneNumber = sqLiteContactHelper.getPhoneByName(name);
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"+phoneNumber));
                intent.putExtra("sms_body",message);
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
        TextView tvName, tvSendMessage;
        ImageView imgAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_namebirthday);
            tvSendMessage = itemView.findViewById(R.id.tv_messagebirthday);
            imgAvatar = itemView.findViewById(R.id.img_avatarbirthday);
        }
    }
}
