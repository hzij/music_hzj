package com.hzj.onlinemusicplayback.Service.impl;

import com.hzj.onlinemusicplayback.Mapper.MusicMapper;
import com.hzj.onlinemusicplayback.Service.MusicService;
import com.hzj.onlinemusicplayback.pojo.Music;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * PackageName :com.hzj.onlinemusicplayback.Service.impl
 * ClassName: MusicServiceImpl
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  14:50
 * @edition 1.0
 */
@Service
@Slf4j
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicMapper musicMapper;
    //上传文件之前查询是否文件重复
    @Override
     public  ResponseBodyMessage<Boolean> QuerySongs(String title,String singer,MultipartFile file){
        //查询数据库获得数据
         ResponseBodyMessage<Music> musicMessage = musicMapper.QuerySongs(title,singer);
         log.info("musicMessage:{}",musicMessage);
         //判断歌曲与对应歌手是否存在
             if (musicMessage!=null) {
                 return  new  ResponseBodyMessage<>(-1,"歌曲已经存在",false);
             }
        return new  ResponseBodyMessage<>(0,"歌曲可以添加",true);
     }

}
