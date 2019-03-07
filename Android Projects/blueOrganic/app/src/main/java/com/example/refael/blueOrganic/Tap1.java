package com.example.refael.blueOrganic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import com.example.refael.blueOrganic.R;
import com.example.refael.blueOrganic.Utils.DatabaseHelper;

import java.util.ArrayList;


public class Tap1 extends AppCompatActivity {
    public static final String PREFS_NAME = "Hygrometer";
    static public int flag_ID_name;
    private Switch swHygrometer, swOpenTap;
    DatabaseHelper myDB;
    ListAdapter listAdapter;
    private Button addnewprogaram;
    private ListView list;
    final ArrayList<String> theList = new ArrayList<>();
    final ArrayList<Integer> listOfHour = new ArrayList<>();
    final ArrayList<Integer> listOfMin = new ArrayList<>();
    final ArrayList<Integer> listOfEndHour = new ArrayList<>();
    final ArrayList<Integer> listOfEndMin = new ArrayList<>();
    final ArrayList<Integer> listOfDuration = new ArrayList<>();
    final ArrayList<String> listOfDays = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap1);
        SetUpUIViews();

        SharedPreferences prefs = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        flag_ID_name = prefs.getInt("myInt", 1); // 1 is default

        swOpenTap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    swOpenTap.setText("Open Tap ON");
                }else{
                    swOpenTap.setText("Open Tap OFF");
                }
            }
        });

        /*check what we choose before... (switch)*/
        swHygrometer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
        boolean isChecked) {

            if(isChecked){
                swHygrometer.setText("Hygrometer ON");
            }else{
                swHygrometer.setText("Hygrometer OFF");
            }
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("switch_key", isChecked);
            editor.commit();
            }
        });

        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        Cursor data = myDB.getListContents();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(/*Name --> */ data.getString(1)+"\n" +
                        "Start Time: " +data.getString(2) +
                        ":" + data.getString(3) + "\n"
                        +"Duration: " + data.getString(4) + "\n" +
                        "Days" + ": " +data.getString(5));
                listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                list.setAdapter(listAdapter);
                listOfHour.add(Integer.parseInt(data.getString(2)));
                listOfMin.add(Integer.parseInt(data.getString(3)));
                listOfEndHour.add(Integer.parseInt(data.getString(2)));
                listOfEndMin.add(Integer.parseInt(data.getString(3)));
                listOfDuration.add(Integer.parseInt(data.getString(4)));
                listOfDays.add(data.getString(5));
            }
            getTimeOfTap();
        }
        //Goto Program
        addnewprogaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityProgram();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                PopupMenu popupMenu = new PopupMenu(Tap1.this, list);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Edit")){
                            //Toast.makeText(getBaseContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                            goToUpdateProgramActivity(String.valueOf(id));
                        }
                        if (item.getTitle().equals("Delete")){
                            //Toast.makeText(getBaseContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                            DeleteProgramName(String.valueOf(id));
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    // go back to Home Page...
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile_menu_btn:
                startActivity(new Intent(this, HomeActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void SetUpUIViews() {
        addnewprogaram = findViewById(R.id.btnAddNewProgram);
        list = (ListView)findViewById(R.id.listProgram);
        swHygrometer = (Switch) findViewById(R.id.switch_hygrometer);
        swOpenTap = (Switch) findViewById(R.id.switch_OpenTap);
        //save the choice we are choose..(switch)
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean silent = settings.getBoolean("switch_key", false);
        swHygrometer.setChecked(silent);
        if(silent)swHygrometer.setTextSize(25); // set 25sp displayed text size of Switch
    }
    //move to program activity
    public void openActivityProgram(){
        Intent intent = new Intent(this, Program.class);
        intent.putExtra("flag_ID_Name", flag_ID_name);
        intent.putIntegerArrayListExtra("listOfEndHour", listOfEndHour);
        intent.putIntegerArrayListExtra("listOfEndMin", listOfEndMin);
        intent.putIntegerArrayListExtra("listOfHour", listOfHour);
        intent.putIntegerArrayListExtra("listOfMin", listOfMin);
        intent.putExtra("DAYS_SELECT", listOfDays);
        startActivity(intent);
    }
    //move to Updateprogram activity
    public void goToUpdateProgramActivity(String Id) {
        Intent goToUpdate = new Intent(this, UpdateProgramActivity.class);
        // get the id for edit..
        int getIDsql = Integer.parseInt(Id);
        // use id for update the activity
        myDB.getNameProgram(Id);
        Cursor data = myDB.getListContents();
        if (data.getCount() == 0) { /* Checked if is any program to edit... */
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            data.moveToPosition(getIDsql); /* get the string from sql */
            Toast.makeText(getBaseContext(),"Your choice edit " + data.getString(1), Toast.LENGTH_LONG).show();
            // update of the program
            goToUpdate.putExtra("PROGRAM_ID", Id);
            goToUpdate.putExtra("PROGRAM_NAME", data.getString(1));
            goToUpdate.putExtra("START_HOUR", data.getString(2));
            goToUpdate.putExtra("START_MIN", data.getString(3));
            goToUpdate.putExtra("DURATION", data.getString(4));
            goToUpdate.putExtra("DAYS_SELECT", data.getString(5));
            // list for check update
            goToUpdate.putIntegerArrayListExtra("listOfEndHour", listOfEndHour);
            goToUpdate.putIntegerArrayListExtra("listOfEndMin", listOfEndMin);
            goToUpdate.putIntegerArrayListExtra("listOfHour", listOfHour);
            goToUpdate.putIntegerArrayListExtra("listOfMin", listOfMin);
            goToUpdate.putExtra("DAYS_SELECT", listOfDays);
            myDB.close();
            startActivity(goToUpdate);

        }
    }
    //Delete program By ID && Name
    public void DeleteProgramName(String Id) {
        Intent RefreshTap = new Intent(this, Tap1.class);
        int getIDsql = Integer.parseInt(Id);
        // use id for update the activity
        myDB.getNameProgram(Id);
        Cursor data = myDB.getListContents();
        if (data.getCount() == 0) { /* Checked if is any program to edit... */
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            data.moveToPosition(getIDsql); /* get the string from sql */
            Toast.makeText(getBaseContext(),"Your choice delete " + data.getString(1), Toast.LENGTH_LONG).show();
            myDB.deleteName(data.getString(1));
            startActivity(RefreshTap);
        }
    }
    //getTimeOfTap --> take time
    public void getTimeOfTap(){
        int countHour = 0;
        for(int i=0; i<listOfDuration.size();i++){
            while (listOfDuration.get(i) != 0)
            {
                while (listOfDuration.get(i) > 59){
                    listOfEndHour.set(i, listOfEndHour.get(i)+1);
                    listOfDuration.set(i, listOfDuration.get(i)%60);
                }
                listOfEndMin.set(i, listOfEndMin.get(i)+listOfDuration.get(i));
                listOfDuration.set(i, 0);
            }
            while (listOfEndMin.get(i)>59){
                    listOfEndHour.set(i, listOfEndHour.get(i)+1);
                    listOfEndMin.set(i, listOfEndMin.get(i)%60);
            }

            while (listOfEndHour.get(i)>23){
                listOfEndHour.set(i, countHour);
                countHour++;
            }
            countHour = 0;
        }
    }

}