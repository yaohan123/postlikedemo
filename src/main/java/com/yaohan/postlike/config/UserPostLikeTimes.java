package com.yaohan.postlike.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 第二种点赞方式，用户对某视频的点赞次数限制
 */
@Component("userPostLike")
public class UserPostLikeTimes {

    @Value("${userPostLike.intervalTime}")
    private double intervalTime;

    @Value("${userPostLike.times}")
    private int times;

    public double getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(double intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
