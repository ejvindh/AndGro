package com.ejvindh.andgro;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BrowseFiles extends ListActivity {
	private List<String> path = null;
	private TextView myPath;
	private Button choose_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_files);
		// Show the Up button in the action bar.
		Intent intent = getIntent();
		String directory = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		choose_button = findViewById(R.id.button1);
		myPath = findViewById(R.id.path);
        getDir(directory);
	}
	
	private void getDir(final String dirPath) {
    	myPath.setText(String.format("%s%s", getString(R.string.place), dirPath));
		List<String> item = new ArrayList<>();
    	path = new ArrayList<>();
    	File f = new File(dirPath);
    	File[] files = f.listFiles();
		String root = "/";
		if(!dirPath.equals(root)) {
    		item.add(root);
    		path.add(root);
    		item.add("../");
    		path.add(f.getParent());
    	}
		for (File file : files) {
			path.add(file.getPath());
			if (file.isDirectory()) item.add(file.getName() + "/");
			else item.add(file.getName());
		}
    	ArrayAdapter<String> fileList =
				new ArrayAdapter<>(this, R.layout.row, item);
    	setListAdapter(fileList);
    	choose_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent=new Intent();
            	intent.putExtra("RESULT_STRING", dirPath);
            	setResult(RESULT_OK, intent);
            	finish();
            }
        });

    }

 protected void onListItemClick(ListView l, View v, int position, long id) {
	 File file = new File(path.get(position));
	 if (file.isDirectory()) {
		 if(file.canRead()) getDir(path.get(position));
		 else {
			 new AlertDialog.Builder(this)
			 .setIcon(R.drawable.ic_launcher)
			 .setTitle("Ingen adgang til [" + file.getName() + "]")
			 .setPositiveButton("OK", 
					 new DialogInterface.OnClickListener() {
				 @Override
				 
				 public void onClick(DialogInterface dialog, int which) {
					 // Auto-generated method stub
				 }
				 }).show();
			 }
		 }
	 }
 
 }