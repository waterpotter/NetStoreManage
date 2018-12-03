package com.power.using.test;

import java.io.IOException;
import java.io.InputStream;

public class Test {

	public static void main(String[] args) throws IOException {
		InputStream in1 = Test.class.getClassLoader().getResourceAsStream("testfile1.txt");
		InputStream in2 = Test.class.getClassLoader().getResourceAsStream("testfile1.txt");
		
		int same=0;
		int defrent=0;
				
		
		int c=0;
		//假设in1的文件长度较大
		//长度较大的放到循环中
		while((c=in1.read())!=-1){
			if(c==in2.read()){
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
		in1.close();
		in2.close();

	}

}
