package cn.tyrael.library.http;

public class UrlUtil {
	private static final String TAG = "UrlUtil";

	public static String getFileName(String url){
		int last = url.lastIndexOf("/");
		//noinspection UnnecessaryLocalVariable
		String name = url.substring(last + 1);
		//LogAdapter.d(TAG, name);
		return name;
	}
}
