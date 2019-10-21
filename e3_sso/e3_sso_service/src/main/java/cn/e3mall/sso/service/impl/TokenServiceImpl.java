package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.common.pojo.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 根据Token取用户信息
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:" + token);
        //取不到用户信息，登录已过期，返回登录过期
        if (StringUtils.isEmpty(json)) {
            return E3Result.build(201, "用户登录过期！");
        }
        //取到用户信息，更新过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //返回结果，E3Result其中包含TbUser对象
        TbUser user = JsonUtils.jsonToPojo(json,TbUser.class);
        return E3Result.ok(user);
    }
}
