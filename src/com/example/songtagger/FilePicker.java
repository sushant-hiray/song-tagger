package com.example.songtagger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class FilePicker extends ListActivity {

	 private static final Boolean FALSE = null;
	private File currentDir;
	 private FileArrayAdapter adapter;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.filepickerlayout);
	        ListView lv = (ListView)findViewById(android.R.id.list);
	        lv.setAdapter(adapter);
	        currentDir = new File(Environment.getExternalStorageDirectory().getPath());
	        fill(currentDir);
	    }
	    //-----------------------------------------------------------------------
	    private void fill(File f)
	    {
	        File[]dirs = f.listFiles();
	         this.setTitle("Current Dir: "+f.getName());
	         List<Option>dir = new ArrayList<Option>();
	         List<Option>fls = new ArrayList<Option>();
	         try{
	             for(File ff: dirs)
	             {
	                if(ff.isDirectory())
	                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath(),false));
	                else
	                {
	                	String filename=ff.getName();
	                	String ext = filename.substring(filename.lastIndexOf('.')+1, filename.length());
	                    if(ext.equals("mp3") || ext.equals("wav"))fls.add(new Option(ff.getName(),"File",ff.getAbsolutePath(),false));
	                }
	             }
	         }catch(Exception e)
	         {
	             
	         }
	         Collections.sort(dir);
	         Collections.sort(fls);
	         //jugaad for OK button for now -- add a item to list of that name
//	         fls.add(new Option("OK OK ","done","shortcut",false));
	         
	         dir.addAll(fls);
	         if(!f.getName().equalsIgnoreCase("sdcard"))
	             dir.add(0,new Option("..","Parent",f.getParent(),false));
	         	 
	       
	         
	         adapter = new FileArrayAdapter(FilePicker.this,dir);
			 this.setListAdapter(adapter);


	    }
	    
	    @Override
		protected void onListItemClick(ListView l, View v, int position, long id){
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			Option o = adapter.getItem(position);
			if(o.getData().equalsIgnoreCase("folder")||
						o.getData().equalsIgnoreCase("parent")){
					currentDir = new File(o.getPath());
					fill(currentDir);
			}
//			else if(o.getData().equalsIgnoreCase("done")){
//				int size=adapter.getCount();
//				StringBuffer output=new StringBuffer();
//				for(int i=0;i<size;i++){
//					Option opt = adapter.getItem(i);
//					if(opt.getData()=="File" ){
//						String x="F ";
//						if (opt.getChecked())
//						output.append(opt.getName() + '\n') ;
//					}
//				}
//				Toast.makeText(this,output, Toast.LENGTH_LONG).show();
//			}
			else
			{
				onFileClick(o,v);
			}
		}
	    public void onPicked(View v){
	    	int size=adapter.getCount();
	    	ArrayList<String> namelist= new ArrayList<String>();
	    	ArrayList<String> addlist=new ArrayList<String>();
			StringBuffer output=new StringBuffer();
			for(int i=0;i<size;i++){
				Option opt = adapter.getItem(i);
				if(opt.getData()=="File" ){
					String x="F ";
					if (opt.getChecked()){
						namelist.add(opt.getName());
						addlist.add(opt.getPath());
					}
				}
			}
//			Toast.makeText(this,output, Toast.LENGTH_LONG).show();
//			Intent myIntent = new Intent(this.getBaseContext(), SelectedSongs.class);
//			
			Bundle b=new Bundle();
			b.putStringArrayList("name", namelist);
			b.putStringArrayList("path", addlist);
			Intent i=new Intent(this.getBaseContext(), SelectedSongs.class);
			i.putExtras(b);
			this.startActivity(i);
//			at the receiving end
//			Bundle b=this.getIntent().getExtras();
//			String[] array=b.getStringArray(key);
	    }
	    private void onFileClick(Option o,View v)
	    {
	    	CheckBox cb = (CheckBox)v.findViewById(R.id.checkBox);
	    //	Toast.makeText(this, "File Clicked: "+o.getName(), Toast.LENGTH_SHORT).show();
	    	
	    	String x;
	    	if(o.getChecked())x="true";
	    	else x="false";
	    	Log.d("state is.....................",x);
	    	
	    	o.toggle();//MAIN OP
	    	
	    	if(o.getChecked())x="true";
	    	else x="false";
	    	Log.d("After state is.....................",x);
	    	cb.toggle();
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
