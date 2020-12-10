package com.example.apiproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Student> students;
    private RecyclerView rclStudent;
    private CustomAdapter customAdapter;
    private Button btnAdd;
    private final String url="http://5fd0658a1f237400166316e6.mockapi.io/myApi/Student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        students=new ArrayList<>();
        rclStudent=findViewById(R.id.rclStudent);
        btnAdd=findViewById(R.id.btnAdd);

        customAdapter=new CustomAdapter(MainActivity.this,students,url);
        rclStudent.setAdapter(customAdapter);
        rclStudent.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        initData();

        btnAdd.setOnClickListener(e->{
            Dialog dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_edit);
            int height= WindowManager.LayoutParams.WRAP_CONTENT;
            int width=WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width,height);
            dialog.show();
            EditText etName=dialog.findViewById(R.id.etName);
            EditText etAge=dialog.findViewById(R.id.etAge);
            EditText etClass=dialog.findViewById(R.id.etClass);
            RadioButton rdMale=dialog.findViewById(R.id.rdMale);
            RadioButton rdFemale=dialog.findViewById(R.id.rdFemale);
            Button btnDone=dialog.findViewById(R.id.btnDone);

            try {
                btnDone.setOnClickListener(ec->{
                    String name=etName.getText().toString().trim();
                    int age =Integer.parseInt(etAge.getText().toString().trim());
                    String className=etClass.getText().toString().trim();
                    String gender="Male";
                    if(rdFemale.isChecked())
                        gender="Female";
                    Student student=new Student(name,gender,className,age);
                    customAdapter.addStudent(student);
                    dialog.dismiss();
                });

            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }



    private void initData(){

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest =new JsonArrayRequest(Request.Method.GET,url,null
                ,res->{
            try{
                for (int i=0;i<res.length();i++){
                    JSONObject object=res.getJSONObject(i);
                    String ten=object.getString("name");
                    String lop=object.getString("class");
                    String gt=object.getString("gender");
                    int tuoi=object.getInt("age");
                    String id=object.getString("id");
                    Student student=new Student(id,ten,gt,lop,tuoi);
                    students.add(student);
                    customAdapter.notifyDataSetChanged();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },err->{
//            tvShow.setText(err.getMessage());
        });
        requestQueue.add(arrayRequest);
    }
}