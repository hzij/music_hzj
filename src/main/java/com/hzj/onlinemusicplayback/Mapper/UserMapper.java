package com.hzj.onlinemusicplayback.Mapper;

import com.hzj.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * PackageName :com.hzj.Mapper
 * ClassName: UserMapper
 * Description:
 *
 * @Author 郝紫俊
 * @Create 2023/9/4  16:49
 * @edition 1.0
 */
@Mapper
public interface UserMapper {
    User login(String username);
}
