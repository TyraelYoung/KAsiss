package wang.tyrael.os.baidu;

import java.io.File;
import java.util.UUID;

import com.baidubce.BceClientException;
import com.baidubce.services.bos.BosClient;
import com.csmall.log.LogHelper;

import cn.tyrael.library.file.FilePathParser;

/**
 * 为上层提供简单的接口
 */
public class BosBiz {
	private static final String TAG = "BosBiz";
	public final BosClient bosClient = BosApi.getClient();

	/**
	 * 防止服务器文件重名
	 * @param localPath
	 * @param bosDir
	 * @return
	 */
	public String putObjectWithRandomName(String localPath, String bosDir){
		File file = new File(localPath);
		FilePathParser pSrc = new FilePathParser(localPath);
		FilePathParser pDest = new FilePathParser(bosDir);

		String bucket = pDest.getFirstDir();

		UUID uuid = UUID.randomUUID();
		String filename = uuid +"."+ pSrc.getExt();
		String key = pDest.excludeFirstDir() + "/" + filename;
		try {
			bosClient.putObject(bucket, key, file);
		}catch (BceClientException e){
			// 没有网络会有这个崩溃
			LogHelper.w(TAG, "请检查网络", e);
			return null;
		}
		String url = String.format(Const.URL, bucket, key);
		LogHelper.d(TAG, "url:" + url);
		return url;
	}
	
	/**
	 * 将本地文件上传到bos
	 * @param localPath
	 * @param bosDir 第一段作为bucket，方法内会自动分析并且拼上文件名 kassistant/localSong
	 */
	public String putObject(String localPath, String bosDir){
		File file = new File(localPath);
		
		FilePathParser p = new FilePathParser(bosDir);
		String bucket = p.getFirstDir();
		
		String filename = new FilePathParser(localPath).getFileName();
		String key = p.excludeFirstDir() + "/" + filename;
		try {
			bosClient.putObject(bucket, key, file);
		}catch (BceClientException e){
			// 没有网络会有这个崩溃
            LogHelper.w(TAG, "请检查网络", e);
            return null;
		}
		String url = String.format(Const.URL, bucket, key);
        LogHelper.d(TAG, "url:" + url);
		return url;
	}
}
