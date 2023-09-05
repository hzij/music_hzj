package com.hzj.onlinemusicplayback.pojo;

import lombok.Data;

/**
 * PackageName :com.hzj.pojo
 * ClassName: User
 * Description:
 *  User表的实体类
 * @Author 郝紫俊
 * @Create 2023/9/4  16:46
 * @edition 1.0
 */

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
}
