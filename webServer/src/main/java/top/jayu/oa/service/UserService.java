package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.mapper.UserMapper;

/**
 * Created by 余杰 on 2020/12/18 17:19
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public boolean userIfChief(int userId){
        Byte aByte = userMapper.ifChief(userId);
        return aByte == 1;
    }

}
