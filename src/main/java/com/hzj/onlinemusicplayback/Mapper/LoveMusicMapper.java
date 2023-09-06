package com.hzj.onlinemusicplayback.Mapper;

import com.hzj.onlinemusicplayback.pojo.LoveMusic;
import com.hzj.onlinemusicplayback.pojo.Music;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * PackageName :com.hzj.onlinemusicplayback.Mapper
 * ClassName: LoveMusicMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/6  16:22
 * @edition 1.0
 */
@Mapper
public interface LoveMusicMapper {
    /**
     * 通过id查询是否收藏过这个音乐
     * @param userid
     * @param musicid
     * @return
     */
   Music selectLoveById(int userid,int musicid);

    /**
     *没有收藏过的话 插入数据
     * @param userid
     * @param musicid
     * @return
     */
    Boolean insertLove(int userid,int musicid);

    /**
     * 通过id查询用户收藏的歌曲，显示在收藏页面
     * @param userid
     * @return
     */
    List<Music> selectCollectMusicById(int userid);

    /**
     * 通过名称模糊查询收藏的歌曲
     * @param title
     * @param userid
     * @return
     */
    List<Music> selectCollectMusic(String title,int userid);
}
