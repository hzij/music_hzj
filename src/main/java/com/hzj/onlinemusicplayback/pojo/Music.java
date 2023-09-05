package com.hzj.onlinemusicplayback.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.onlinemusicplayback.pojo
 * ClassName: music
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/5  14:03
 * @edition 1.0
 */
@Data
public class Music {
    private int id;
    private String title;
    private String singer;
    private String url;
    private String time;
    private int userid;
}
