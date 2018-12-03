package com.power.using.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test3 {

	public static void main(String[] args) throws IOException {
		File file1 = new File("src\\testfile1.txt");
		File file2 = new File("src\\testfile2.txt");
		System.out.println("file1:"+file1.length());
		System.out.println("file2:"+file2.length());
		
		FileReader f1=new FileReader(file1);
		FileReader f2=new FileReader(file2);
		
		BufferedReader b1 = new BufferedReader(f1);
		BufferedReader b2 = new BufferedReader(f2);
		
		
		int same=0;
		int defrent=0;
				
		
		String c=null;
		//假设in1的文件长度较大
		//长度较大的放到循环中
		while((c=b1.readLine())!=null){
			if(c.equals(b2.readLine())){
				same++;
			}else{
				defrent++;
			}
		}
		System.out.println(same);
		System.out.println(defrent);
		System.out.println(defrent+same);
		if(same!=0&&defrent!=0){
			float per=(float) (1.0*same /(defrent+same));
			System.out.println("相同率"+per*100+"%");
			float def=(float) (1.0*defrent /(defrent+same));
			System.out.println("不同率"+def*100+"%");
		}else{
			System.out.println("相同率100%");
		}
		f1.close();
		f2.close();

	}

}
