package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.Org;
import top.jayu.oa.entity.User;
import top.jayu.oa.mapper.OrgMapper;
import top.jayu.oa.mapper.UserMapper;

/**
 * Created by 余杰 on 2020/12/18 17:19
 */

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    OrgMapper orgMapper;

    // 是否是科级干部
    public boolean userIfChief(Integer userId){
        Byte aByte = userMapper.ifChief(userId);
        return aByte == 1;
    }

    // 判断是否是部门/单位负责人
    public boolean userIfLeader(Integer userId){
        User user = userMapper.getUserById(userId);
        String orgCode = user.getOrgCode();
        String candidateOrgCodePriv = orgCode.substring(0, 6);
        Integer leaderId = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
        return (int)userId == (int)leaderId;
    }

    // 判断是否是市局负责人
    public boolean userIfCompanyLeader(Integer userId){
        return (int)userId == 2;
    }

}
