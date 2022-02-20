package com.example.androidtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter {

    private ArrayList<Employee> EmployeeList;
    private Context ctx;
    DBAdapter dbAdapter;


    public EmployeeAdapter(ArrayList<Employee> employeeList, Context ctx) {
        EmployeeList = employeeList;
        this.ctx = ctx;
        dbAdapter = new DBAdapter(ctx, DBAdapter.DATABASE, null, DBAdapter.VERSION);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View EmployeeRow = inflater.inflate(R.layout.employee_row, null); //this is the layout we want to repeat for each course
        MyWidgetContainer container = new MyWidgetContainer(EmployeeRow);
        return container;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        MyWidgetContainer container = (MyWidgetContainer) holder;
        final Employee CurrentEmployee = EmployeeList.get(position);
        container.txtemail2.setText(CurrentEmployee.getEmployeeemail());
        container.lblremove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Condition[] = {String.valueOf(CurrentEmployee.getId())};
                dbAdapter.delete_data(Condition);  // To delete data from table in database. This will call delete_data() method in DBAdapter class.
                EmployeeList.remove(position);    // To remove specified object from specified position from arraylist (from current screen)
                notifyDataSetChanged();            // To refresh the recyclerview.
                Toast.makeText(ctx, "Employee Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    class MyWidgetContainer extends RecyclerView.ViewHolder     // MyWidgetContainer class must extend RecyclerView.ViewHolder class.
    {
        TextView txtemail2, lblremove2;
        Chip chip;

        public MyWidgetContainer(@NonNull View itemView) {  //this constructor is in parent class (ViewHolder) so we have to implement it cumpulsary here.
            super(itemView);  // this super(itemView) will call parent class constructor.

            txtemail2 = itemView.findViewById(R.id.txtemail2);
            lblremove2 = itemView.findViewById(R.id.lblremove2);
            chip = itemView.findViewById(R.id.chip);
        }
    }
}
