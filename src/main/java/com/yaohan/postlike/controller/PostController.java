package com.yaohan.postlike.controller;

import com.yaohan.postlike.dto.PostLikeV2Response;
import com.yaohan.postlike.dto.PostLikeV3Response;
import com.yaohan.postlike.service.PostLikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @Autowired
    PostLikedService postLikedService;

    /**
     * 点赞1：不分用户无限对某视频点赞，返回最新的点赞数量
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/postLike", method = RequestMethod.POST)
    @ResponseBody
    public Integer postLike(@RequestParam(value = "videoId",required = true) String videoId){
        postLikedService.postLike(videoId);
        return postLikedService.queryLikeCount(videoId);
    }

    /**
     * 点赞2：区分用户对某视频点赞，每天有最多点赞次数，返回最新的点赞数量，以及是否达到次数限制提示
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/postLikeV2", method = RequestMethod.POST)
    @ResponseBody
    public PostLikeV2Response PostLikeV2(@RequestParam(value = "userId",required = true) String userId,
                                         @RequestParam(value = "videoId",required = true) String videoId){
        PostLikeV2Response response = new PostLikeV2Response();
        boolean flag = postLikedService.postLikeV2(userId, videoId);
        Integer times = postLikedService.queryUserLikeTimes(userId,videoId);
        response.setUserLikeTimes(times);
        response.setMax(flag);
        return response;
    }

    /**
     * 点赞3：区分用户对某视频点赞，区分点赞与未点赞状态，返回最新的点赞数量，以及点赞状态
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/postLikeV3", method = RequestMethod.POST)
    @ResponseBody
    public PostLikeV3Response PostLikeV3(@RequestParam(value = "userId",required = true) String userId,
                                         @RequestParam(value = "videoId",required = true) String videoId){
        PostLikeV3Response response = new PostLikeV3Response();
        postLikedService.postLikeV3(userId, videoId);
        Integer count = postLikedService.queryLikeCount(videoId);
        Integer status = postLikedService.queryLikeStatus(userId,videoId);
        response.setCount(count);
        response.setStatus(status);
        return response;
    }


    @RequestMapping(value = "/queryLikeCount", method = RequestMethod.GET)
    @ResponseBody
    public String queryLikeCount(@RequestParam(value = "videoId",required = true) String videoId){
        Integer likeCount = postLikedService.queryLikeCount(videoId);
        System.out.println(likeCount.toString());
        return "postLikeSuccess";
    }


    //不限制
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    //限制三次
    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2(){
        return "index2";
    }

    //限制状态
    @RequestMapping(value = "/index3", method = RequestMethod.GET)
    public String index3(){
        return "index3";
    }



}
