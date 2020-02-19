package com.shuitianyun.kassistant.json;

import com.csmall.report.ReportManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangchao on 2017/4/5.
 */

public class LocalSong {
    private static final String TAG = "LocalSong";
    public String fullPath;
    public String fileName;
    public long createAt;

    public String getId(){
        return fileName;
    }

    public long parseCreateAt(){
        int index = fileName.indexOf(".");
        if(index < 1){
            //没找到？存在命名为tempsave文件
            return 0;
        }
        String s = fileName.substring(0, index);
        try{
            createAt = Long.parseLong(s);
        }catch (NumberFormatException nfe){
            //存在命名为tempsave文件
            return 0;
        }
        return createAt;
    }

    public String displayDisplayCreateAt(){
        return new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss").format(new Date(createAt));
    }
}
