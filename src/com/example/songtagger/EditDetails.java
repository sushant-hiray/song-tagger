package com.example.songtagger;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditDetails extends Activity {
	ID3v1_1 tag=null;
	MP3File mp3file =null;
	EditText Title;
	EditText Artist;
	EditText Album;
	EditText Year;
	EditText Genre;
	EditText Track;
	String x="0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_details);
		
		Bundle b=this.getIntent().getExtras();
		String path=b.getString("path");
		
		//get edittexts
		Title=(EditText) this.findViewById(R.id.title);
		Artist=(EditText) this.findViewById(R.id.artist);
		Album=(EditText) this.findViewById(R.id.album);
		Year=(EditText) this.findViewById(R.id.year);
		
		File sourceFile=new File(path);
		Log.d("inside ....................................","before try");
		try {
			Log.d("inside ....................................","just entered try");
			if(sourceFile==null){Log.d("inside ....................................","sourcefile is null");}
			try {
				mp3file = new MP3File(sourceFile);
				tag = (ID3v1_1) mp3file.getID3v1Tag();
				Log.d("inside ....................................","after tag");

				//set initial metadetails to corr edittexts
				Artist.setText(check(tag.getArtist()));
				Album.setText(check(tag.getAlbum()));
				Year.setText(check(tag.getYear()));
//				Genre.setText(tag.getGenre());
//				Track.setText(tag.getTrack());
				Title.setText(check(tag.getTitle()));
				Log.d("inside ....................................","after settings");
//				tag.setYearReleased("2008");
				mp3file.save();		
				Log.d("tag info ............", tag.toString());
				x="1";
			}
			catch(java.io.IOException e){
				Log.d("inside ....................................","error reading mp3");
				Toast.makeText(this.getApplicationContext(), "sorry couln't read the tag",Toast.LENGTH_SHORT).show();
			}
			catch(TagException e){
				Log.d("inside ....................................","tag exception");
				Toast.makeText(this.getApplicationContext(), "sorry couln't read the tag",Toast.LENGTH_SHORT).show();
			}
			finally{
				Log.d("inside ....................................","unknown exception");
//				Toast.makeText(this.getBaseContext(), "sorry couln't read the tag",Toast.LENGTH_SHORT).show();
			}
//			Log.d("inside ....................................","after mp3");
			
			
		}
		
		finally{
			Log.d("inside ....................................","last exception");
			Intent returnIntent = new Intent();
			 if(x=="0"){returnIntent.putExtra("result","0");
			 	setResult(RESULT_OK,returnIntent);
			 	finish();
			 	System.exit(1);
			 }
			 
		}
		
	}
	
	public void Save(View v){
		tag.setYearReleased(Year.getEditableText().toString());
		tag.setTitle(Title.getEditableText().toString());
		tag.setAlbum(Album.getEditableText().toString());
		tag.setArtist(this.Artist.getEditableText().toString());
		try {
			mp3file.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent returnIntent = new Intent();
		 returnIntent.putExtra("result","1");
		 Log.d("return intent good ..........",returnIntent.getStringExtra("result"));
		 setResult(RESULT_OK,returnIntent);     
		 finish();
		 System.exit(1);
	}
	String check(String x){
		if(x==null) return "";
		else return x;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_details, menu);
		return true;
	}

}
