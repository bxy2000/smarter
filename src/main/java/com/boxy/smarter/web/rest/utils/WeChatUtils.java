package com.boxy.smarter.web.rest.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class WeChatUtils {
    private final static Logger log = LoggerFactory.getLogger(WeChatUtils.class);

    // access_token获取地址
    public final static String WECHAT_CP_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=SECRECT";

    // 根据appid获取code
    public final static String WECHAT_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&agentid=AGENTID&state=STATE#wechat_redirect";

    // 根据access_token和code获取用户基本信息
    public final static String WECHAT_USERINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

    public static String getWechatCpAccessToken(String corpid, String corpsecret) {
        String requestUrl = WECHAT_CP_ACCESS_TOKEN
            .replace("CORPID", corpid)
            .replace("SECRECT", corpsecret);

        JSONObject json = HttpUtils.request(requestUrl, "GET", null);

        String token = "";
        if(json != null) {
            try {
                token = json.getString("access_token");

                String message = String.format("获取token成功 access toke: %s, expires_in: %s", token, json.getString("expires_in"));
                log.debug(message);
            } catch(Exception e) {
                String error = String.format("获取token失败 errcode: %s ,errmsg: %s", json.getInteger("errcode"),json.getString("errmsg"));
                log.error(error);
            }
        }

        return token;
    }
}
