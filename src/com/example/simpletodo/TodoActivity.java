package com.example.simpletodo;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;


public class TodoActivity extends Activity {
	ListView lvItems;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();	
		lvItems = (ListView) findViewById(R.id.lvItems);		
		items = new ArrayList<String>();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		items.add("First item");
		items.add("Second item");
		setupListViewListener();
    }

    private void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){
    		@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
    	});
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    public void addTodoItem(View v) {
		EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
	}    

private void readItems() {
	File filesDir = getFilesDir();
	File todoFile = new File(filesDir, "todo.txt");
	try {
		items = new ArrayList<String>(FileUtils.readLines(todoFile));
	} catch (IOException e) {
		items = new ArrayList<String>();
		e.printStackTrace();
	}
}

private void saveItems() {
	File filesDir = getFilesDir();
	File todoFile = new File(filesDir, "todo.txt");
	try {
		FileUtils.writeLines(todoFile, items);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}