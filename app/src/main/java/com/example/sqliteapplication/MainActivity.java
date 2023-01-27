package com.example.sqliteapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static DBHelper DB;
    EditText name,contact,Dob;
    Button Insert,Update,Delete,View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        contact=findViewById(R.id.contact);
        Dob=findViewById(R.id.DOB);

        Insert=findViewById(R.id.btnInsert);
        Update=findViewById(R.id.btnUpdate);
        Delete=findViewById(R.id.btnDelete);
        View=findViewById(R.id.btnView);
        DB = new DBHelper(this);

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String nameTXT=name.getText().toString();
                String contactTXT=contact.getText().toString();
                String DobTXT=Dob.getText().toString();

                Boolean checkinsertdata = DB.insertUserData(nameTXT,contactTXT,DobTXT);
                if (checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "New Entry Inserted ", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        Update.setOnClickListener(view ->{
            String nameTXT=name.getText().toString();
            String contactTXT=contact.getText().toString();
            String DobTXT=Dob.getText().toString();

            Boolean checkUpdatedata = DB.updateUserData(nameTXT,contactTXT,DobTXT);
            if (checkUpdatedata==true)
                Toast.makeText(MainActivity.this, "Entry Updated ", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, " Entry Not Updated", Toast.LENGTH_SHORT).show();
        });

        Delete.setOnClickListener(view ->{
            String nameTXT=name.getText().toString();
            Boolean checkdeletedata = DB.deleteUserData(nameTXT);
            if (checkdeletedata==true)
                Toast.makeText(MainActivity.this, "Entry Deleted ", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, " Entry Not Deleted", Toast.LENGTH_SHORT).show();

        });



        View.setOnClickListener(view ->{
            Cursor res = DB.getData();
                if (res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;

                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name :" +res.getString(0)+"\n");
                    buffer.append("contact :" +res.getString(1)+"\n");
                    buffer.append("Date of Birth:" +res.getString(2)+"\n\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
        });


    }
}