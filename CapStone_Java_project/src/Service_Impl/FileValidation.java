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
import Exceptions.NotAValidSignatureException;

public class FileValidation{

	public void fileRead(File f,String str, HashSet<String> hs) throws FileNotFoundException, IOException, CurlyBraceMisMatchException, FileNameException, KeywordException, NotAValidSignatureException {
		BufferedReader bufferedreader =new BufferedReader(new FileReader(f));
		BufferedReader bufferedreader1 =new BufferedReader(new FileReader(f));
    	String s;
    	String split_each[];String fname=f.getName();
    	boolean curlyflag=false;
    	boolean filename=false;
    	boolean argflag=false;
    	boolean classflag=false;
    	boolean mainflag=false;
   int c_open=0; int c_close=0;
    	while((s=bufferedreader.readLine())!=null) {
    		if(s.equals("public static void main ( String args [ ] )"))
    		{
    			mainflag=true;
    		}
    			split_each = s.split(" ");
    				for(int i=0;i<split_each.length;i++) {
    					if(split_each[i].equals("{"))
    						c_open++;
    					if(split_each[i].equals("}"))
    						c_close++;
    					if(split_each[i].equals("String") ) {	
    						if(hs.contains(split_each[i+1])) {
    							argflag=true;
    						throw new KeywordException("Arguments for main should not be keyword ");
    						}
    					}
    					if(split_each[i].equals("class"))
    					{
    						if(hs.contains(split_each[i+1])) {
    							classflag=true;
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
    	if(mainflag=false)
    		throw new NotAValidSignatureException("Main method is not having a valid Signature");
    		
		if(curlyflag && filename && !argflag && !classflag) {
			System.out.println("ClassName and File Name are same");
			System.out.println("No curly braces are mismatched");
			System.out.println("No word in program has the reserved keyword");
			System.out.println("");
		while((s=bufferedreader1.readLine())!=null) {
			System.out.println(s); 
			
		}	
		fileCopyWrite(str);
		}
		bufferedreader.close();
		bufferedreader1.close();
		}
	
    public void fileWrite(String str) throws IOException {
  		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\sbadam\\Desktop\\test.java"));
  		bw.write(str);
  		bw.close();
  	}
    
    public void fileCopyWrite(String str) throws IOException {
  		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\sbadam\\Desktop\\test1.java"));
  		bw.write(str);
  		bw.close();
  	}


}