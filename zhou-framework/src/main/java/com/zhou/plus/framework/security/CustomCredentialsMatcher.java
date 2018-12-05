package com.zhou.plus.framework.security;


import com.zhou.plus.framework.utils.CryptoUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 密码匹配器，验证密码是否正确
 * @author bone
 * @version 2017-07-27
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String plainPassword = String.valueOf(usernamePasswordToken.getPassword());
        String encryptPassword = (String)info.getCredentials();
        return CryptoUtils.validatePassword(plainPassword, encryptPassword);
    }

}
