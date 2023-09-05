package com.hzj.onlinemusicplayback.Service;

import com.hzj.onlinemusicplayback.pojo.Music;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import org.springframework.web.multipart.MultipartFile;

/**
 * PackageName :com.hzj.onlinemusicplayback.Service
 * ClassName: MusicService
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  14:50
 * @edition 1.0
 */
public interface MusicService {
    //上传文件之前查询是否文件重复
    ResponseBodyMessage<Boolean> QuerySongs(String title, String singer, MultipartFile file);
}
