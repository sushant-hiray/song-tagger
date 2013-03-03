package com.example.songtagger;

public class Option implements Comparable<Option>{
    private String name;
    private String data;
    private String path;
    private Boolean checkedState;
    
    public Option(String n,String d,String p,Boolean c)
    {
        name = n;
        data = d;
        path = p;
        checkedState=c;
    }
    public void setSelected(boolean selected) {
        this.checkedState = selected;
    }
    public String getName()
    {
        return name;
    }
    public String getData()
    {
        return data;
    }
    public String getPath()
    {
        return path;
    }
    public Boolean getChecked()
    {
        return checkedState;
    }
    public void toggle()
    {
        checkedState=!(checkedState);
    }
	@Override
    public int compareTo(Option o) {
        if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase()); 
        else 
            throw new IllegalArgumentException();
    }
}

