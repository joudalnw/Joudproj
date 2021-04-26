package com.example.Joud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class FB_adapter extends FirebaseRecyclerAdapter<DataModel, FB_adapter.myviewholder>
{
    public FB_adapter(@NonNull FirebaseRecyclerOptions<DataModel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final DataModel model)
    {
       holder.fname.setText(model.getFname());
       holder.lname.setText(model.getLname());
       holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
       //Glide.with(holder.img.getContext()).load(DataModel.getPurl()).into(holder.img);

                    holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                    .setExpanded(true,1100)
                                    .create();

                            View myview=dialogPlus.getHolderView();
                            final EditText phone=myview.findViewById(R.id.uimgurl);
                            final EditText fname=myview.findViewById(R.id.uname);
                            final EditText lname=myview.findViewById(R.id.ucourse);
                            final EditText email=myview.findViewById(R.id.uemail);
                            Button submit=myview.findViewById(R.id.usubmit);

                            phone.setText(model.getPhone());
                            fname.setText(model.getFname());
                            lname.setText(model.getLname());
                            email.setText(model.getEmail());

                            dialogPlus.show();

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Map<String, Object> map=new HashMap<>();
                                        map.put("phone",phone.getText().toString());
                                        map.put("fname",fname.getText().toString());
                                        map.put("email",email.getText().toString());
                                        map.put("lname",lname.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });


                        }
                    });


                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                            builder.setTitle("Delete Panel");
                            builder.setMessage("Delete...?");

                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("users")
                                            .child(getRef(position).getKey()).removeValue();
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.show();
                        }
                    });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
       return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView lname,fname,email,phone;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            phone=(TextView) itemView.findViewById(R.id.phonetext);
            fname=(TextView)itemView.findViewById(R.id.fnametext);
            lname=(TextView)itemView.findViewById(R.id.lnametext);
            email=(TextView)itemView.findViewById(R.id.emailtext);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
