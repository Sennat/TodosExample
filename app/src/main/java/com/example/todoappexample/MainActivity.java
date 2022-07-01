package com.example.todoappexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    // Global variables
    private final String TODOS_STATE = "todos_state";
    private EditText  etInput;
    private TextView total;
    private Button btnSubmit;
    private ListView lv_todos;
    private ImageButton btn_plus;
    private TextView mdl_text;
    private String shareText;

    // Need adapter -> need layout
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_layout);

        if (savedInstanceState != null) {
            shareText = savedInstanceState.getString(TODOS_STATE);
            mdl_text.setText(shareText);
        }

        etInput = findViewById(R.id.et_input);
        total = findViewById(R.id.total);
        btnSubmit = findViewById(R.id.btn_submit);
        lv_todos = findViewById(R.id.lv_todos);
        btn_plus = findViewById(R.id.btn_plus);
        mdl_text = findViewById(R.id.mdl_text);

        // Context and the id of the layout
        // Context -> current state of our application
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lv_todos.setAdapter(listAdapter);
        btnSubmit.setOnClickListener(this);
        btn_plus.setOnClickListener(view -> {
           mdl_text.setText(etInput.getText().toString());
            etInput.getText().clear();
        });

        /* Remove todos items */
        removeTodos();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //if (outState != null) {
            //outState.putParcelable(TODOS_STATE, (Parcelable) listAdapter);
        //}
        outState.putString(TODOS_STATE, shareText);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //ArrayAdapter<String> savedState = savedInstanceState.getParcelable(TODOS_STATE);
        //if (savedState != null) {
            //listAdapter = savedState;
        //}
        shareText = savedInstanceState.getString(TODOS_STATE);
    }

    @Override
    public void onClick(View view) {
        String input = etInput.getText().toString();
        // == -> check the value
        //=== -> check the references
        if(input.equals("")) {
            Toast.makeText(this, "Input can't be empty!!", Toast.LENGTH_LONG).show();
        } else {
           addTodos(input);
           //etInput.setText("");
           etInput.getText().clear();
        }
    }

    /**
     * Add todos
     * @param input
     */
    private void addTodos(String input) {
        if(isDuplicate(input)) {
            listAdapter.add(input);
        } else {
            Toast.makeText(this, "Duplicated items not allowed!!", Toast.LENGTH_LONG).show();
        }

        /* Get size of todos */
        getSizeOfTodos();

        /* Sort todos by alphabet */
        sortTodos();
    }

    /**
     * Check duplicated todos items
     * @param item
     * @return
     */
    private boolean isDuplicate(String item) {
        //listAdapter.
        return true;
    }

    /**
     * Sort todos items
     */
    private void sortTodos() {
        listAdapter.sort(new Comparator<String>() {
            @Override
            public int compare(String st1, String st2) {
                return st1.compareToIgnoreCase(st2);
            }
        });
    }

    /**
     * Remove todos item from the list
     */
    private void removeTodos() {
        lv_todos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                listAdapter.remove(listAdapter.getItem(position));
                /* Get size of todos */
                getSizeOfTodos();
            }
        });
    }

    /**
     * Get size of todos
     *  */
    private void getSizeOfTodos() {
        total.setText("Total user(s): " + listAdapter.getCount() + " items");
    }

}