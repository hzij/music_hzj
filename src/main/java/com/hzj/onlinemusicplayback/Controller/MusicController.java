package com.hzj.onlinemusicplayback.Controller;

import com.hzj.onlinemusicplayback.Mapper.MusicMapper;
import com.hzj.onlinemusicplayback.Service.MusicService;
import com.hzj.onlinemusicplayback.pojo.Music;
import com.hzj.onlinemusicplayback.pojo.User;
import com.hzj.onlinemusicplayback.utils.Constant;
import com.hzj.onlinemusicplayback.utils.ResponseBodyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * PackageName :com.hzj.onlinemusicplayback.Controller
 * ClassName: MusicController
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  14:05
 * @edition 1.0
 */
@RestController
@RequestMapping("/music")
@Slf4j
public class MusicController {
    //获取配置文件的路径
    @Value("${music.local.path}")
    private String SAVE_PATH;
    @Autowired
    private MusicService musicService;
    @Autowired
    private MusicMapper musicMapper;
    /**
     * 上传mp3文件
     * @return
     */
    @PostMapping("/upload")
    @Transactional
    public ResponseBodyMessage<Boolean> insertMusic(@RequestParam String singer,@RequestParam("filename")
    MultipartFile file, HttpServletRequest request, HttpServletResponse response){

        //没有session不创建
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY) == null) {
            System.out.println("没有登录！");
            return new ResponseBodyMessage<>(-1,"没有登录！",false);
        }
        //上传之前获取歌手名和歌名，先查询数据库中是否存在这个版本的歌
        String fileNameAndType = file.getOriginalFilename();//xxx.mp3
        int index = fileNameAndType.lastIndexOf(".");
        String suffix= fileNameAndType.substring(index);
        String title=null;
        title=fileNameAndType.substring(0,index);
        ResponseBodyMessage<Boolean> b = musicService.QuerySongs(singer, title, file);
        System.out.println("b = " + b.getData());
        if (b.getData()){
            //数据库中不存在歌曲，可以文件的上传与数据库的上传
            //判断路径
            String path = SAVE_PATH +"/"+fileNameAndType;
            File file1=new File(path);
            //不存在的化创建
            if (!file1.exists()){
                file1.mkdir();
            }
            try {
                file.transferTo(file1);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseBodyMessage<>(-1,"文件上传失败",false);
            }
            /**
             * 以下是数据库的上传
             * 1.准备数据，url与时间数据难搞
             * 2.调用insertMusic方法（MusicMapper）
             * insert into
             * music(title,singer,time,url,userid)
             * values(#{title},#{singer},#{time},#{url},#{userid})
             */
            //1.准备数据,url、时间数据、userid需要获取
            String url="/music/get?path="+title;//没有后缀
            User user = (User)request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY);
            Integer userid = user.getId();
            //获取当前时间
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sf.format(new Date());
           Boolean musicAddServer = musicMapper.insertMusic(title, singer, time, url, userid);
            if (musicAddServer){
                try {
                    response.sendRedirect("/static/list.html");
                    return new ResponseBodyMessage<Boolean>(0,"歌曲上传成功", true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new  ResponseBodyMessage<>(-1,"歌曲上传失败111",false);
                }
            }
        }
        return new ResponseBodyMessage<>(-1,"歌曲上传失败222",false);
    }
    @GetMapping("/get")
    public ResponseEntity<byte[]> get(String path) {
        System.out.println("sss:"+path);
        File file = new File(SAVE_PATH+"/"+path);
        byte[] a = null;
        try {
            a = Files.readAllBytes(file.toPath());
            if(a == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
        //return ResponseEntity.internalServerError().build();
        //return ResponseEntity.notFound().build();
    }

    /**
     * 模糊查询，与页面列表显示
     * @param musicName
     * @return
     */
    @GetMapping("/findmusic")
    //(required = false)可以不传入参数
    public ResponseBodyMessage<List<Music>> selectFindMusic(@RequestParam(required = false) String musicName){
        List<Music> listMusic;
        if (musicName != null) {
         listMusic = musicMapper.selectAllMusicByName(musicName);
        }else {
          listMusic= musicMapper.selectAllMusic();
        }
    return new ResponseBodyMessage<>(0, "查询到所有歌曲", listMusic);
    }
    @PostMapping("/delete")
    @Transactional
    public ResponseBodyMessage<Boolean> deleteMusic(@RequestParam String id){
        int musicid = Integer.parseInt(id);
        //查询数据库中是否存在这首歌
        Music music = musicMapper.selectMusicById(musicid);
        //进行空值校验
        if (music == null){
            return new ResponseBodyMessage<>(-1,"这首歌曲不存在",false);
        }
        //进行数据库的删除与（确认删除成功）页面回显
        int i = musicMapper.deleteSingleRow(musicid);
        System.out.println("i = " + i);
        if (i == 1) {
            //数据库删除成功，本地文件需要删除，收藏页面的数据也得删除，页面上的数据也得改变
            int index = music.getUrl().lastIndexOf("=");
            String substring = music.getUrl().substring(index+1);
            File file=new File(SAVE_PATH+"/"+substring+".mp3");
            boolean deleteBoolean = file.delete();
            if (deleteBoolean){
                return new ResponseBodyMessage<>(0,"删除服务器音乐成功",true);
            }else {
                return new ResponseBodyMessage<>(-1,"删除音乐失败",false);
            }
        }
        return new ResponseBodyMessage<>(-1,"删除数据库中音乐失败",false);
    }

    @PostMapping("/deleteSel")
    public ResponseBodyMessage<Boolean> deleteSelMusic(@RequestParam("id[]") List<Integer> id){
         int sum = 0;
         //使用循环批量删除
        for(int i=0; i<id.size(); i++){
            Music music = musicMapper.selectMusicById(id.get(i));
            int removeReturn = musicMapper.deleteSingleRow(id.get(i));
            if (removeReturn==1){
                //数据在数据库中删除成功
                //删除服务器下的数据
                int index = music.getUrl().lastIndexOf("=");
                String substring = music.getUrl().substring(index + 1);
                File file=new File(SAVE_PATH+"/"+substring+".mp3");
                if (file.delete()){
                    //数据在服务器上删除成功
                   sum+=removeReturn;
                }else {
                    return new ResponseBodyMessage<>(-1,"音乐在服务器上删除失败",false);
                }
            }else {
                return new ResponseBodyMessage<>(-1,"音乐在数据库中删除失败",true);
            }
        }
        if (id.size()==sum){
            return new ResponseBodyMessage<>(0,"批量删除成功",true);
        }else {
            return new ResponseBodyMessage<>(-1,"批量删除失败",false);
        }
    }
 }
