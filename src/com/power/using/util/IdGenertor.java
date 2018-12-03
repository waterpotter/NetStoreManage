package com.power.using.util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenertor {
	public static String genGUID(){
		return new BigInteger(165, new Random()).toString(36).toUpperCase();
	}
	
	public static String genOrdernum(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String sl = df.format(date);
		return sl+System.nanoTime();
	}
}
