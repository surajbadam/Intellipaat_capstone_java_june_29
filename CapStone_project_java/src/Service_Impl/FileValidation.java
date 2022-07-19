package Service_Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import Exceptions.CurlyBraceMisMatchException;
import Exceptions.KeywordException;
import Exceptions.NotAValidSignatureException;

public class FileValidation {
	public static void main(String args[]) throws IOException, KeywordException {
		Set<String> hs=new HashSet<>();
        hs.add("class");hs.add("do");hs.add("for");hs.add("if");hs.add("else");
        hs.add("public");hs.add("private");hs.add("protected");hs.add("void");
        hs.add("int");hs.add("boolean");hs.add("while");hs.add("throws");
        hs.add("final");hs.add("long");hs.add("float");hs.add("double");
        hs.add("char");hs.add("static");hs.add("main");hs.add("extends");
        hs.add("finally");hs.add("try");hs.add("catch");hs.add("new");
        
        File f=new File("C:\\Users\\sbadam\\Desktop\\Example.java");
        FileWriter fw=new FileWriter(f);

        //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String str=readProgram();
        String[] split_str=str.split("\n");
        try {
			fileRead(fw,split_str,hs,f);
		} catch (KeywordException | IOException | CurlyBraceMisMatchException | NotAValidSignatureException e) {
			System.out.println(e.getMessage());
		}
        
	}
	
	private static void fileRead(FileWriter fw,String[] str,Set<String> hs,File f) throws KeywordException, IOException, CurlyBraceMisMatchException, NotAValidSignatureException {
		int count_open=0,count_close=0;
		boolean main_check=false;
		for(String curr_line:str) {
			if(curr_line.equals("public static void main(String args[]")) {
				fw.write(curr_line);
				main_check=true;
			}
			String[] split_each=curr_line.split("\\s");
			
			for(int i=0;i<split_each.length;i++)
			{
				if(split_each[i].equals("public")&&hs.contains(split_each[i]))
				{
					if(split_each[i+1].equals("class")&&hs.contains(split_each[i+2])) {
						throw new KeywordException("class name can't be same as reserved keywords");
					}
					else if(split_each[i+2].equals(f.getName().substring(0, f.getName().indexOf(".")-1))){
						fileWrite(split_each);
					}
						
				}
				if(split_each[i].equals("{"))
					count_open++;
				if(split_each[i].equals("}"))
					count_close++;
				
			}
		}
		if(count_open!=count_close)
			throw new CurlyBraceMisMatchException("Curlybraces are mismatched");
		if(main_check==false)
			throw new NotAValidSignatureException("main method has not a valid signature");
		
	}

	private static void fileWrite(String[] split_each) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\sbadam\\Desktop\\Example.java"));
		for(String each:split_each) {
			bw.write(each);
		}
		bw.close();
	}

	private static String readProgram() {
		return """
        		public class Example {
                public static void main(String args[]) 
		{
                   System.out.println("Hello World"); 
                }
            }
    		""";
	}
}
