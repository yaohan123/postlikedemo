package com.yaohan.postlike.service.impl;

import com.yaohan.postlike.config.UserPostLikeTimes;
import com.yaohan.postlike.service.PostLikedService;
import com.yaohan.postlike.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostLikedServiceImpl implements PostLikedService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserPostLikeTimes userPostLikeTimes;

    /**
     * 一、不区分用户，仅考虑点赞
     */
    @Override
    public void postLike(String videoId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_COUNT, videoId, 1);
    }

    /**
     * 二、区分用户，对某视频点赞，每天限制点赞次数。点赞先查是否已经达到上限，达到上限返回true
     */
    @Override
    public boolean postLikeV2(String userId, String videoId) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED_TIMES,userId+"::"+videoId);
        //不为null，且点赞次数达到上限，该操作限制
        if(obj != null && Integer.parseInt(obj.toString()) == userPostLikeTimes.getTimes()){
            return true;
        }
        //否则该操作为点赞
        else {
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_COUNT, videoId, 1);
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_TIMES, userId+"::"+videoId, 1);
        }
        return false;
    }




    /**
     * 三、区分用户，考虑点赞状态，对某视频点赞，先判断该操作是点赞还是取消点赞，若为点赞，点赞数量加1，更改点赞状态为1
     */
    @Override
    public void postLikeV3(String userId, String videoId) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_LIKED_STATUS,userId+"::"+videoId);
        //不为null，且点赞状态值为1，说明之前点赞了，该操作为取消点赞
        if(obj != null && Integer.parseInt(obj.toString())==1){
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_COUNT, videoId, -1);
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_STATUS, userId+"::"+videoId, -1);
        }
        //否则该操作为点赞
        else {
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_COUNT, videoId, 1);
            redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_LIKED_STATUS, userId+"::"+videoId, 1);
        }
    }

    /**
     * 查询某视频的点赞数
     */
    @Override
    public Integer queryLikeCount(String videoId) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_LIKED_COUNT,videoId);
        //避免空指针异常，因为obj可能为空
        if(obj != null){
            return Integer.parseInt(obj.toString());
        }
        else return 0;
    }

    /**
     * 查询某用户对某视频的点赞状态
     */
    @Override
    public Integer queryLikeStatus(String userId, String videoId) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_LIKED_STATUS,userId+"::"+videoId);
        if(obj != null){
            return Integer.parseInt(obj.toString());
        }
        else return 0;
    }

    /**
     * 查询时间间隔内某用户对某视频的点赞次数
     */
    @Override
    public Integer queryUserLikeTimes(String userId, String videoId) {
        Object obj = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED_TIMES,userId+"::"+videoId);
        if(obj != null){
            return Integer.parseInt(obj.toString());
        }
        else return 0;
    }

    //    @Autowired
//    UserLikeRepository likeRepository;
//
//    @Autowired
//    RedisService redisService;
//
//    @Autowired
//    UserService userService;

//    @Override
//    @Transactional
//    public UserLike save(UserLike userLike) {
//        return likeRepository.save(userLike);
//    }
//
//    @Override
//    @Transactional
//    public List<UserLike> saveAll(List<UserLike> list) {
//        return likeRepository.saveAll(list);
//    }
//
//    @Override
//    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
//        return likeRepository.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
//    }
//
//    @Override
//    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
//        return likeRepository.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
//    }
//
//    @Override
//    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
//        return likeRepository.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
//    }
//
//    @Override
//    @Transactional
//    public void transLikedFromRedis2DB() {
//        List<UserLike> list = redisService.getLikedDataFromRedis();
//        for (UserLike like : list) {
//            UserLike ul = getByLikedUserIdAndLikedPostId(like.getLikedUserId(), like.getLikedPostId());
//            if (ul == null){
//                //没有记录，直接存入
//                save(like);
//            }else{
//                //有记录，需要更新
//                ul.setStatus(like.getStatus());
//                save(ul);
//            }
//        }
//    }
//
//    @Override
//    @Transactional
//    public void transLikedCountFromRedis2DB() {
//        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
//        for (LikedCountDTO dto : list) {
//            UserInfo user = userService.findById(dto.getId());
//            //点赞数量属于无关紧要的操作，出错无需抛异常
//            if (user != null){
//                Integer likeNum = user.getLikeNum() + dto.getCount();
//                user.setLikeNum(likeNum);
//                //更新点赞数量
//                userService.updateInfo(user);
//            }
//        }
//    }
}
