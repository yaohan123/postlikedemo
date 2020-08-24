package com.yaohan.postlike.utils;

public class RedisKeyUtils {

    //保存用户被点赞数量的key
    public static final String MAP_KEY_LIKED_COUNT = "MAP_LIKED_COUNT";
    //保存点赞状态的key
    public static final String MAP_KEY_LIKED_STATUS = "MAP_LIKED_STATUS";
    //保存用户对某视频点赞次数的key
    public static final String MAP_KEY_USER_LIKED_TIMES = "MAP_USER_LIKED_TIMES";


    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param userId
     * @param videoId
     * @return
     */
    public static String getLikedKey(String userId, String videoId){
        StringBuilder builder = new StringBuilder();
        builder.append(userId);
        builder.append("::");
        builder.append(videoId);
        return builder.toString();
    }

}
