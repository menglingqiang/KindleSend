package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Constant {

	public static ArrayList<String> getConfig()
	{
		// kindleEmail ,sendEmail,pwd
		ArrayList<String> list = new ArrayList<String>();
		File f = new File("sendConfig.txt");
		if(f.exists())
		{
			try {
				BufferedReader br= new BufferedReader(new FileReader(f)); 
				String str = null;
				while((str = br.readLine())!=null)
				{
					list.add(str);
				}
				return list;
			}
			catch (Exception e) {
				return list;
			}
		} 
		return list;
		
	}
}
