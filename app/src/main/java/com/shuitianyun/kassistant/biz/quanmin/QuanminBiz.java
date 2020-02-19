package com.shuitianyun.kassistant.biz.quanmin;

import com.csmall.log.LogHelper;
import com.shuitianyun.kassistant.json.LocalSong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangchao on 2017/4/5.
 */

public class QuanminBiz {
    private static final java.lang.String TAG = "QuanminBiz";

    public List<LocalSong> getLocalSongFromStorage(){
        List<LocalSong> result = new ArrayList<>();

        String dir = new QuanMinDir().getDirLoalSong();
        LogHelper.d(TAG, "dir:" + dir);
        File fDir = new File(dir);
        File[] list = fDir.listFiles();
        if(list == null || list.length == 0){
            return result;
        }
        for (File file:
             list) {
            LocalSong ls = new LocalSong();
            ls.fullPath  = file.getAbsolutePath();
            ls.fileName = file.getName();
            ls.parseCreateAt();
            result.add(ls);
        }
        LogHelper.d(TAG, "getLocalSongFromStorage:" + result.size());
        return result;
    }
}
