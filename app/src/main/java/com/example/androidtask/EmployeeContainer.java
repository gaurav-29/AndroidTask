package com.example.androidtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class EmployeeContainer extends AppCompatActivity {

    Button btnsave;
    TextView txtdepartment;
    ImageButton btnadd;
    TextInputLayout txtemail1;
    TextInputEditText txtemail;
    TextView lblremove;
    Switch switch1;
    Spinner spinner;
    String[] department = {"Select Department", "Android", "iOS", "Web"};
    String employeeemail;
    int id;
    Context ctx = this;
    ArrayList<Employee> EmployeeList = new ArrayList<Employee>();
    DBAdapter dbAdapter;
    RecyclerView recemployee;
    EmployeeAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_container);

        init();
        FillSpinner();
        HandleEvent();
        GetDataFromTable();
    }

    private void FillSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx,android.R.layout.simple_spinner_item, department);
        spinner.setAdapter(adapter);
    }

    private void HandleEvent()
    {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtdepartment.getText().toString()=="Android" || txtdepartment.getText().toString()=="iOS" ||
                        txtdepartment.getText().toString()=="Web")
                {
                    txtemail1.setVisibility(View.VISIBLE);
                    lblremove.setVisibility(View.VISIBLE);
                    switch1.setVisibility(View.VISIBLE);
                }
                else
                {
                    txtdepartment.setError("Please select department from below");
                    Toast.makeText(ctx,"Please select department",Toast.LENGTH_LONG).show();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String SelectedDepartment = adapterView.getSelectedItem().toString();
                txtdepartment.setText(SelectedDepartment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                employeeemail = txtemail.getText().toString();

                boolean result = dbAdapter.insert_data_android(employeeemail);
                // insert_data() of DBAdaper class will be called from here and response will be returned to this method.
                if (result == true) {
                    Toast.makeText(ctx, "Data successfully inserted in Android Department", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
/*
                if(txtdepartment.getText().toString()=="iOS") {
                    boolean result = dbAdapter.insert_data_ios(employeeemail);
                    // insert_data() of DBAdaper class will be called from here and response will be returned to this method.
                    if (result == true) {
                        Toast.makeText(ctx, "Data successfully inserted in iOS Department", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx, "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }

                if(txtdepartment.getText().toString()=="Web") {
                    boolean result = dbAdapter.insert_data_web(employeeemail);
                    // insert_data() of DBAdaper class will be called from here and response will be returned to this method.
                    if (result == true) {
                        Toast.makeText(ctx, "Data successfully inserted in Web Department", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx, "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }
            }  */
        });
    }

    private void GetDataFromTable()
    {
            Cursor c = dbAdapter.view_data_android();   // This will call view_data_android() method of DBAdapter class.

            if (c != null)     // view_data_android() may return null, so we have to check.

            // Difference between null and 0 :  0 is a numeric value, null is not a numeric value. It is like a blank(empty) cell.
            {
                int size = c.getCount(); //get count method returns how many object(record) cursor has
                if (size == 0) {
                    Toast.makeText(ctx, "No data found", Toast.LENGTH_LONG).show();
                    txtdepartment.setText("");
                } else {
                    while (c.moveToNext() == true)     // this is the library method of Cursor class.
                    {
                        id = c.getInt(c.getColumnIndex("_id"));
                        employeeemail = c.getString(c.getColumnIndex("employee_email1"));
                        Employee emp = new Employee(id, employeeemail);  // from here values of variables will go to Employee.java
                        EmployeeList.add(emp);
                    }

                    adapter = new EmployeeAdapter(EmployeeList, ctx); // from here CourseList and ctx will pass to Constructor of InventoryAdapter.java.(passing data from one java file to another).
                    recemployee.setLayoutManager(new GridLayoutManager(ctx, 1));
                    recemployee.setItemAnimator(new DefaultItemAnimator());
                    recemployee.setAdapter(adapter); //it will call onCreateViewHolder method of InventoryAdapter class

                }
                c.close(); //close cursor. if not closed, it will give error.
            }

     /*   if(txtdepartment.getText().toString()=="iOS") {
            Cursor c = dbAdapter.view_data_ios();
            if (c != null)
            {
                int size = c.getCount(); //get count method returns how many object(record) cursor has
                if (size == 0) {
                    Toast.makeText(ctx, "No data found", Toast.LENGTH_LONG).show();
                    txtdepartment.setText("");
                } else {
                    while (c.moveToNext() == true)     // this is the library method of Cursor class.
                    {
                        id = c.getInt(c.getColumnIndex("_id"));
                        employeeemail = c.getString(c.getColumnIndex("employee_email2"));
                        Employee emp = new Employee(id, employeeemail);  // from here values of variables will go to Employee.java
                        EmployeeList.add(emp);
                    }
                    adapter = new EmployeeAdapter(EmployeeList, ctx); // from here CourseList and ctx will pass to Constructor of InventoryAdapter.java.(passing data from one java file to another).
                    recemployee.setLayoutManager(new GridLayoutManager(ctx, 1));
                    recemployee.setItemAnimator(new DefaultItemAnimator());
                    recemployee.setAdapter(adapter); //it will call onCreateViewHolder method of InventoryAdapter class
                }
                c.close(); //close cursor. if not closed, it will give error.
            }
        }

        if(txtdepartment.getText().toString()=="Web") {
            Cursor c = dbAdapter.view_data_web();
            if (c != null)
            {
                int size = c.getCount(); //get count method returns how many object(record) cursor has
                if (size == 0) {
                    Toast.makeText(ctx, "No data found", Toast.LENGTH_LONG).show();
                    txtdepartment.setText("");
                } else {
                    while (c.moveToNext() == true)     // this is the library method of Cursor class.
                    {
                        id = c.getInt(c.getColumnIndex("_id"));
                        employeeemail = c.getString(c.getColumnIndex("employee_email3"));
                        Employee emp = new Employee(id, employeeemail);  // from here values of variables will go to Employee.java
                        EmployeeList.add(emp);
                    }
                    adapter = new EmployeeAdapter(EmployeeList, ctx); // from here CourseList and ctx will pass to Constructor of InventoryAdapter.java.(passing data from one java file to another).
                    recemployee.setLayoutManager(new GridLayoutManager(ctx, 1));
                    recemployee.setItemAnimator(new DefaultItemAnimator());
                    recemployee.setAdapter(adapter); //it will call onCreateViewHolder method of InventoryAdapter class
                }
                c.close(); //close cursor. if not closed, it will give error.
            }
        }  */
    }


    private void init()
    {
        btnsave = findViewById(R.id.btnsave);
        txtdepartment = findViewById(R.id.txtdepartment);
        btnadd = findViewById(R.id.btnadd);
        txtemail = findViewById(R.id.txtemail);
        txtemail1 = findViewById(R.id.txtemail1);
        lblremove = findViewById(R.id.lblremove);
        switch1 = findViewById(R.id.switch1);
        spinner = findViewById(R.id.spinner);
        recemployee = findViewById(R.id.recemployee);
        dbAdapter = new DBAdapter(ctx,DBAdapter.DATABASE, null, DBAdapter.VERSION);
    }
}
