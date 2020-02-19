package com.shuitianyun.kassistant.biz.quanmin;

import android.os.Environment;

import java.io.File;

/**
 * Created by wangchao on 2017/4/5.
 */

public class QuanMinDir {
    private static final String BASE_DIR = "/Android/data/com.tencent.karaoke/files";
    private static final String LOCAL_SONG_DIR = BASE_DIR + "/localSong";

    private File getDirExternal(){
        return Environment.getExternalStorageDirectory();
    }

    public String getDirLoalSong(){
        return getDirExternal().getAbsolutePath() + LOCAL_SONG_DIR;
    }

}
