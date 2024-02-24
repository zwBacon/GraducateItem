package com.zw.graducate.controller;

import com.zw.graducate.entity.OrderInfo;
import com.zw.graducate.entity.VideosInfo;
import com.zw.graducate.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2024/2/15 19:27
 */
@RestController
@RequestMapping("/videoInfoController")
@CrossOrigin(origins = {"*", "null"})
public class VideoInfoController {

    @Autowired
    private VideoInfoService service;

    @GetMapping("/selectAllVideoInfos")
    public List<VideosInfo> selectAllVideoInfos(){
        return service.selectAllVideoInfos();
    }

}
