package com.yaohan.postlike.dto;

public class PostLikeV3Response {

    //该视频现在的点赞数量
    Integer count;

    //该用户对该视频现在的点赞状态
    Integer status;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
