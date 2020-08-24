package com.yaohan.postlike.dto;

public class PostLikeV2Response {

    //该用户对该视频现在的点赞数量
    Integer userLikeTimes;

    //今日该用户对该视频的点赞次数是否达到上限
    boolean max;

    public Integer getUserLikeTimes() {
        return userLikeTimes;
    }

    public void setUserLikeTimes(Integer userLikeTimes) {
        this.userLikeTimes = userLikeTimes;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }
}
