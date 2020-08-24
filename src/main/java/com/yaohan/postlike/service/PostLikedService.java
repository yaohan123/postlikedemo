package com.yaohan.postlike.service;

import com.yaohan.postlike.domain.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostLikedService {

    /**
     * 一、不区分用户，仅考虑点赞
     */
    void postLike(String videoId);


    /**
     * 二、区分用户，考虑点赞状态，对某视频点赞，先判断该操作是点赞还是取消点赞，若为点赞，点赞数量加1，更改点赞状态为1
     */
    void postLikeV2(String userId, String videoId);


    /**
     * 查询某视频的点赞数
     */
    Integer queryLikeCount(String videoId);

    /**
     * 查询某用户对某视频的点赞状态
     */
    Integer queryLikeStatus(String userId, String videoId);


//    /**
//     * 保存点赞记录,即增加一条记录到用户点赞表UserLike
//     * @param userLike
//     * @return
//     */
//    UserLike save(UserLike userLike);
//
//    /**
//     * 批量保存或修改
//     * @param list
//     */
//    List<UserLike> saveAll(List<UserLike> list);
//
//
//    /**
//     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
//     * @param likedUserId 被点赞人的id
//     * @param pageable
//     * @return
//     */
//    Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable);
//
//    /**
//     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
//     * @param likedPostId
//     * @param pageable
//     * @return
//     */
//    Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable);
//
//    /**
//     * 通过被点赞人和点赞人id查询是否存在点赞记录
//     * @param likedUserId
//     * @param likedPostId
//     * @return
//     */
//    UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId);
//
//    /**
//     * 将Redis里的点赞数据存入数据库中
//     */
//    void transLikedFromRedis2DB();
//
//    /**
//     * 将Redis中的点赞数量数据存入数据库
//     */
//    void transLikedCountFromRedis2DB();

}
