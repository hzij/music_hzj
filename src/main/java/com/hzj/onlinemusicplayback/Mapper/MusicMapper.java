package com.hzj.onlinemusicplayback.Mapper;

import com.hzj.onlinemusicplayback.pojo.Music;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * PackageName :com.hzj.onlinemusicplayback.Mapper
 * ClassName: MusicMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  14:13
 * @edition 1.0
 */
@Mapper
public interface MusicMapper {
    //上传文件之前查询是否文件重复
    ResponseBodyMessage<Music> QuerySongs(String title,String singer);

    //上传的歌曲添加到数据库中
    Boolean insertMusic(String title,String singer,String time,String url,Integer userid);

 }
