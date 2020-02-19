package com.shuitianyun.kassistant.biz;

import com.shuitianyun.kassistant.json.LocalSong;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地歌曲，后续应该 有很多逻辑需要处理。
 * Created by wangchao on 2017/4/6.
 */

public class LocalSongBiz {
    private static LocalSongBiz sLocalSongBiz = new LocalSongBiz();

    public static LocalSongBiz getInstance() {
        return sLocalSongBiz;
    }

    //临时方案
    private Map<String, LocalSong> map = new HashMap<>();

    public LocalSong get(String id){
        return map.get(id);
    }

    public void put(LocalSong localSong){
        map.put(localSong.getId(), localSong);
    }
}
