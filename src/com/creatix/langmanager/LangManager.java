package com.creatix.langmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LangManager {
	
	protected File langFile;
	
	protected HashMap<String, String> lang;
	
	public LangManager(String filePath) throws IOException, URISyntaxException{
		this.setLangFile(filePath);
		this.lang = new HashMap<String, String>();
		System.out.println(langFile.getAbsolutePath()+" "+langFile.exists());
		if(!langFile.exists())
			new IOException("Lang file not fount on '"+langFile.toURI()+"'").printStackTrace();
		handle();
	}
	
	public LangManager setLangFile(String name) throws URISyntaxException{
		if(!name.endsWith(".lang"))
			name += ".lang";
		System.out.println(name);
		File file = new File(this.getClass().getResource(name).toURI());
		this.langFile = file;
		return this;
	}
	public String readLangFile(){
		String ret = "";
		try{
			BufferedReader read  = FileUtils.createReader(langFile);
			String tmp;
		     while((tmp = read.readLine()) != null){
		    	 ret += tmp;
		     }		
		}catch(IOException e){
			e.printStackTrace();
		}
		return ret;
	}
	public void handle() throws IOException{
		String full = readLangFile();
		if(full.length() == 0)
			return;
		String[] lines = full.split(";");
		if(lines.length == 0)
			return;
		
		for(String str : lines){
			String[] b = str.split("=");
			if(b.length != 2)
				continue;
			lang.put(b[0],b[1]);
		}	
	}
	public String getLocalizedName(String key){
		if(!lang.containsKey(key))
			return key;
		return lang.get(key);
	}
	
	
}
