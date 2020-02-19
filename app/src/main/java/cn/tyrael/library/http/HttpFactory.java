package cn.tyrael.library.http;

import okhttp3.OkHttpClient;

/**
 * @author Administrator
 *
 */
public class HttpFactory {
	public static HttpAdapter getDefault(){
		HttpAdapter httpAdapter = new HttpAdapter();
		httpAdapter.setClient(new OkHttpClient());
		return httpAdapter;
	}
}
