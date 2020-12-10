package com.example.apiproject;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Student> students;
    private String url;

    public CustomAdapter(Context context, ArrayList<Student> students,String url) {
        this.context = context;
        this.students = students;
        this.inflater=LayoutInflater.from(context);
        this.url=url;
    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item,parent,false);
        return new CViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, int position) {
        Student student=students.get(position);
        System.out.println(student);
        holder.tvName.setText(student.getName());
        holder.tvClass.setText(student.getClassName());
        holder.tvGender.setText(student.getGender());
        holder.tvAge.setText(student.getAge()+"");
        holder.btnEdit.setOnClickListener(e->{
            Dialog dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_edit);
            int width= WindowManager.LayoutParams.MATCH_PARENT;
            int height=WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
            dialog.show();
            EditText etName=dialog.findViewById(R.id.etName);
            EditText etAge=dialog.findViewById(R.id.etAge);
            EditText etClass=dialog.findViewById(R.id.etClass);
            TextView tvID=dialog.findViewById(R.id.tvID);
            RadioButton rdMale=dialog.findViewById(R.id.rdMale);
            RadioButton rdFemale=dialog.findViewById(R.id.rdFemale);
            Button btnDone=dialog.findViewById(R.id.btnDone);
            tvID.setText(student.getId());
            etName.setText(student.getName());
            etAge.setText(student.getAge()+"");
            etClass.setText(student.getClassName());

            try {
                btnDone.setOnClickListener(ec->{
                    String name=etName.getText().toString().trim();
//                String age=etAge.getText().toString().trim();
                    int age =Integer.parseInt(etAge.getText().toString().trim());
                    String className=etClass.getText().toString().trim();
                    String gender="Male";
                    if(rdFemale.isChecked())
                        gender="Female";
                    student.setName(name);
                    student.setAge(age);
                    student.setClassName(className);
                    student.setGender(gender);
                    updateStudent(student);
                    dialog.dismiss();
                });

            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        holder.btnDelete.setOnClickListener(e->{
            deleteStudent(student,position);
        });
    }

    public  void addStudent(Student student) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                rs->{
                    Toast.makeText(context,"Successfull",Toast.LENGTH_LONG).show();
                    students.add(student);
                    notifyItemInserted(students.size()-1);
                },
                err->{
                    Toast.makeText(context,"Insert fail",Toast.LENGTH_LONG).show();
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("name",student.getName());
                params.put("gender",student.getGender());
                params.put("age",student.getAge()+"");
                params.put("class",student.getClassName());
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void updateStudent(Student student){
        StringRequest stringRequest=new StringRequest(Request.Method.PUT,url+"/"+student.getId(),
                rs->{
                    Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                },
                err->{
                    Toast.makeText(context, "Update Fail!", Toast.LENGTH_SHORT).show();
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params=new HashMap<>();
                params.put("name",student.getName());
                params.put("gender",student.getGender());
                params.put("age",student.getAge()+"");
                params.put("class",student.getClassName());
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private void deleteStudent(Student student,int position){

        StringRequest stringRequest =new StringRequest(Request.Method.DELETE,url+"/"+student.getId(),
                rs->{
                    Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
                    students.remove(position);
                    notifyItemChanged(position);
                    notifyItemRangeChanged(position,students.size());
                },
                err->{
                    Toast.makeText(context, "Delete Fail", Toast.LENGTH_SHORT).show();
                });
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class CViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvClass,tvGender,tvAge;
        ImageButton btnEdit,btnDelete;
        CustomAdapter customAdapter;
        public CViewHolder(@NonNull View itemView, CustomAdapter customAdapter) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvTen);
            tvClass=itemView.findViewById(R.id.tvLop);
            tvAge=itemView.findViewById(R.id.tvTuoi);
            tvGender=itemView.findViewById(R.id.tvGioiTinh);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            this.customAdapter=customAdapter;
        }
    }
}
