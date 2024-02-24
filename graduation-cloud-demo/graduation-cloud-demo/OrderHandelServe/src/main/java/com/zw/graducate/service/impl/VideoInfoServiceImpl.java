package com.zw.graducate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zw.graducate.entity.VideosInfo;
import com.zw.graducate.mapper.VideoInfoMapper;
import com.zw.graducate.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/15 19:26
 */

@Service
@DS("order")
public class VideoInfoServiceImpl implements VideoInfoService {

    @Autowired
    private VideoInfoMapper mapper;

    @Override
    public List<VideosInfo> selectAllVideoInfos() {
        QueryWrapper<VideosInfo> queryWrapper = new QueryWrapper<>();
        List<VideosInfo> videosInfos = mapper.selectList(queryWrapper);
//        结果处理
        for(VideosInfo videosInfo : videosInfos){
            JSONObject videoSrc = new JSONObject();
            videoSrc.put("type","video/mp4");
            videoSrc.put("src",videosInfo.getVideoLink());
            videosInfo.setVideoSrc(videoSrc);
        }
        return videosInfos;
    }
}
