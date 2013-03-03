package com.example.songtagger;

import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1_1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditDetails extends Activity {
	ID3v1_1 tag=null;
	MP3File mp3file =null;
	EditText Title;
	EditText Artist;
	EditText Album;
	EditText Year;
	EditText Genre;
	EditText Track;
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
		try {
			mp3file = new MP3File(sourceFile);
			tag = (ID3v1_1) mp3file.getID3v1Tag();
			
			//set initial metadetails to corr edittexts
			Artist.setText(tag.getArtist());
			Album.setText(check(tag.getAlbum()));
			Year.setText(tag.getYear());
//			Genre.setText(tag.getGenre());
//			Track.setText(tag.getTrack());
			Title.setText(tag.getTitle());
			
//			tag.setYearReleased("2008");
			mp3file.save();		
			Log.d("tag info ............", tag.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		finish();
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
