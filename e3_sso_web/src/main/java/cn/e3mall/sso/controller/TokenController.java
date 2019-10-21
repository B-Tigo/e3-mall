package cn.e3mall.sso.controller;

import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.common.pojo.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根据Token取用户信息Controller
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /*@RequestMapping(value = "/user/token/{token}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE *//*"application/json;charset=utf-8"*//*)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        E3Result result = tokenService.getUserByToken(token);
        //相应结果之前，判断是否为jsonp请求
        if (StringUtils.isEmpty(callback)) {
            //把结果封装成一个js语句响应
            return callback+"("+ JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }*/
    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        E3Result result = tokenService.getUserByToken(token);
        //相应结果之前，判断是否为jsonp请求
        if (!StringUtils.isEmpty(callback)) {
            //把结果封装成一个js语句响应
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }
}
