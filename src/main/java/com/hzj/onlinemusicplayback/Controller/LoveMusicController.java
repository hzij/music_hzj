package com.hzj.onlinemusicplayback.Controller;

import com.hzj.onlinemusicplayback.Mapper.LoveMusicMapper;
import com.hzj.onlinemusicplayback.Mapper.MusicMapper;
import com.hzj.onlinemusicplayback.pojo.LoveMusic;
import com.hzj.onlinemusicplayback.pojo.Music;
import com.hzj.onlinemusicplayback.pojo.User;
import com.hzj.onlinemusicplayback.utils.Constant;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PackageName :com.hzj.onlinemusicplayback.Controller
 * ClassName: LoveMusicController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/6  16:24
 * @edition 1.0
 */
@RestController
@RequestMapping("/lovemusic")
public class LoveMusicController {
    @Autowired
    private LoveMusicMapper loveMusicMapper;
    @Autowired
    private MusicMapper musicMapper;
    @PostMapping("/likeMusic")
    public ResponseBodyMessage<Boolean> loveMusic(@RequestParam String id , HttpServletRequest request){
        //没有session不创建
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("没有登录！");
            return new ResponseBodyMessage<>(-1,"没有登录！",false);
        }
        int musicid = Integer.parseInt(id);
        User user = (User)request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY);
        Integer userid = user.getId();
        //判断是否添加过喜欢
        Music music = loveMusicMapper.selectLoveById(userid, musicid);
        if (music!=null){
            return new ResponseBodyMessage<>(-1,"该用户点赞过该音乐",false);
        }else {
            Boolean aBoolean = loveMusicMapper.insertLove(userid, musicid);
            if (aBoolean){
                return new ResponseBodyMessage<>(0,"点赞音乐成功",true);
            }else{
                return new ResponseBodyMessage<>(-1,"点赞音乐失败",false);
            }
        }
    }
    @GetMapping("/findlovemusic")
    public ResponseBodyMessage<List<Music>>  findCollectMusic(@RequestParam(required = false) String musicName,HttpServletRequest request
            ,HttpServletResponse response ) throws IOException {
        //没有session不创建
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("没有登录！");
            return new ResponseBodyMessage<>(-1,"没有登录！",null);
        }
        User user = (User) request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY);
        Integer userid = user.getId();
        List<Music> music;
        if(musicName != null) {
            music = loveMusicMapper.selectCollectMusic(musicName, userid);
        }else {
            music = loveMusicMapper.selectCollectMusicById(userid);
        }
        return new ResponseBodyMessage<>(0, "查询到了所有的收藏的音乐", music);
    }

}
