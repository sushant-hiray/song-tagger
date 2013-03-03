package com.example.songtagger;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectedSongs extends ListActivity {
	ArrayList<String> namelist=null;
	ArrayList<String> addlist=null;
	ArrayAdapter<String> adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selected_songs);
		
		Bundle b=this.getIntent().getExtras();
		namelist=b.getStringArrayList("name");
		addlist=b.getStringArrayList("path");
		ListView lv = (ListView)findViewById(android.R.id.list);
//		TextView v= (TextView) this.findViewById(R.id.dummy);
//		v.setText(namelist.get(0));
		adapter= new ArrayAdapter<String>(this,
		 
		        android.R.layout.simple_list_item_1,
		 
		        namelist);
		 
		   lv.setAdapter(adapter);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selected_songs, menu);
		return true;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String path = addlist.get(position);
		//display in short period of time
//		Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
		Intent myIntent = new Intent(this.getBaseContext(), EditDetails.class);
		
		Bundle b=new Bundle();
		b.putString("path", path);
		myIntent.putExtras(b);
		this.startActivity(myIntent);
	}

}
