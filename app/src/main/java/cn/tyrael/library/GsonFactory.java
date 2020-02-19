package cn.tyrael.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
	public static Gson newGsonDHE(){
		//noinspection UnnecessaryLocalVariable
		Gson gs = new GsonBuilder()
			    .disableHtmlEscaping()
			    .create();	
		return gs;
	}
}
