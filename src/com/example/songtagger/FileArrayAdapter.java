package com.example.songtagger;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<Option>{

	private Context c;
	private List<Option>items;
	private final Activity context;
	
	public FileArrayAdapter(Activity context,List<Option> objects) {
		super(context, R.layout.file_view, objects);
		c = context;
		this.context=context;
		items = objects;
	}
	
	public Option getItem(int i)
	 {
		 return items.get(i);
	 }
	static class ViewHolder {
		protected ImageView image;
        protected TextView text;
        protected CheckBox checkbox;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder viewHolder = null;
	        if (convertView == null) {
	            LayoutInflater inflator = context.getLayoutInflater();
	            convertView = inflator.inflate(R.layout.file_view, null);
	            viewHolder = new ViewHolder();
	            viewHolder.text = (TextView) convertView.findViewById(R.id.TextView01);
	            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox);
	            viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
	            
	            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	                
	                @Override
	                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
	                    items.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
	                }
	            });
	            convertView.setTag(viewHolder);
	            convertView.setTag(R.id.TextView01, viewHolder.text);
	            convertView.setTag(R.id.checkBox, viewHolder.checkbox);
	            convertView.setTag(R.id.icon,viewHolder.image);
	            }
	        else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        viewHolder.checkbox.setTag(position); // This line is important.
	        
	        viewHolder.text.setText(items.get(position).getName());
	        viewHolder.checkbox.setChecked(items.get(position).getChecked());
	        //Now set the appropriate icons
	        if (items.get(position).getData()=="File"){
	        	viewHolder.image.setImageResource(R.drawable.ic_launcher);
	        }
	        else if (items.get(position).getData()=="Folder"){
	        	viewHolder.image.setImageResource(R.drawable.ic_folder);
	        }
	        else if (items.get(position).getData()=="Parent"){
	        	viewHolder.image.setImageResource(R.drawable.back);
	        }
	        
	        return convertView;
	}
            
            

}
