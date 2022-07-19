package Service_Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import Exceptions.CurlyBraceMisMatchException;
import Exceptions.FileNameException;
import Exceptions.KeywordException;

public class FileValidation{

	public void fileRead(File f,String str, HashSet<String> hs) throws FileNotFoundException, IOException, CurlyBraceMisMatchException, FileNameException, KeywordException {
		BufferedReader br =new BufferedReader(new FileReader(f));
		BufferedReader br1 =new BufferedReader(new FileReader(f));
    	String s;
    	String split_each[];String fname=f.getName();
    	boolean curlyflag=false;
    	boolean filename=false;
    	boolean argflag=true;
    	boolean classflag=true;
   int c_open=0; int c_close=0;
    	while((s=br.readLine())!=null) {
    			split_each = s.split(" ");
    				for(int i=0;i<split_each.length;i++) {
    					if(split_each[i].equals("{"))
    						c_open++;
    					if(split_each[i].equals("}"))
    						c_close++;
    					if(split_each[i].equals("String") ) {	
    						if(hs.contains(split_each[i+1])) {
    							argflag=false;
    						throw new KeywordException("Arguments for main should not be keyword ");
    						}
    					}
    					if(split_each[i].equals("class"))
    					{
    						if(hs.contains(split_each[i+1])) {
    							classflag=false;
    							throw new KeywordException("Class name should not be keyword ");
    						}
    						if(split_each[i+1].equals(fname.substring(0,fname.length()-5 )))
    						{
    							
    							filename=true;
    						}
    						else
    							throw new FileNameException("Filename and class name not matched");
    					}				
    				}
    	}
    	
    	if(c_open==c_close) {
    		
    		curlyflag=true;
    	}
    	else {
    		throw new CurlyBraceMisMatchException("Curly braces are mismatched");
    	}
   
		if(curlyflag && filename && argflag && classflag) {
			System.out.println("ClassName and File Name are same");
			System.out.println("No curly braces are mismatched");
			System.out.println("No word in program has the reserved keyword");
			System.out.println("");
		while((s=br1.readLine())!=null) {
			System.out.println(s); 
			
		}	
		fiWrite(str);
		}
		br.close();
		br1.close();
		}
	
    public void fileWrite(String str) throws IOException {
  		BufferedWriter fw = new BufferedWriter(new FileWriter("C:\\Users\\sbadam\\Desktop\\test.java"));
  		fw.write(str);
  		fw.close();
  	}
    
    public void fiWrite(String str) throws IOException {
  		BufferedWriter fw1 = new BufferedWriter(new FileWriter("C:\\Users\\sbadam\\Desktop\\test1.java"));
  		fw1.write(str);
  		fw1.close();
  	}


}