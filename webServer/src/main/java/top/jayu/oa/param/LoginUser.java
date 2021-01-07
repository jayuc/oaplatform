package top.jayu.oa.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUser extends BaseParam {

    private Integer userId;

    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String originPassword;
}
