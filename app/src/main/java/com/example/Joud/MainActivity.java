package com.example.Joud;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText fname,lname, email, phone;
    Button insert, update, delete, view , firebase_button,weather_btn;
    DBHelper DB_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname = findViewById(R.id.f_name);
        lname = findViewById(R.id.l_name);
        email = findViewById(R.id.email_add);
        phone = findViewById(R.id.phone_no);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        weather_btn = findViewById(R.id.weather);
        firebase_button = findViewById(R.id.fb);
        DB_Helper = new DBHelper(this);

        firebase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseActivity();
            }
        });
        weather_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherActivity();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f_nameTXT =fname.getText().toString();
                String l_nameTXT = lname.getText().toString();
                String email_addTXT = email.getText().toString();
                String phone = MainActivity.this.phone.getText().toString();


                Boolean checkinsertdata = DB_Helper.insertuserdata(f_nameTXT,l_nameTXT, email_addTXT, phone);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB_Helper.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "Data Doesn't Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Full Name :"+res.getString(0)+" "+res.getString(1)+"\n");
                    buffer.append("Email Address :"+res.getString(2)+"\n");
                    buffer.append("Phone Number :"+res.getString(3)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Data");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fnameTXT =fname.getText().toString();
                String lnameTXT = lname.getText().toString();
                String emailTXT = email.getText().toString();
                String phoneTXT = phone.getText().toString();

                Boolean checkupdatedata = DB_Helper.updateuserdata(fnameTXT,lnameTXT, emailTXT, phoneTXT);
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, " Data Not Updated", Toast.LENGTH_SHORT).show();
            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneTXT = phone.getText().toString();
                Boolean checkudeletedata = DB_Helper.deletedata(phoneTXT);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
            }        });


    }
    public void FirebaseActivity(){
        Intent intent = new Intent(this, FirebaseMainActivity.class);
        startActivity(intent);
    }
    public void WeatherActivity(){
        Intent intent = new Intent(this, OpenWeather.class);
        startActivity(intent);
    }
}