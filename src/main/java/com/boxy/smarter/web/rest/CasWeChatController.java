package com.boxy.smarter.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.boxy.smarter.web.rest.utils.HttpUtils;
import com.boxy.smarter.web.rest.utils.WeChatUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/cas/wechat")
public class CasWeChatController {
    private final Logger log = LoggerFactory.getLogger(CasWeChatController.class);

    @Value("${wechat.cp.corpid}")
    private String corpid;

    @Value("${wechat.cp.corpsecret}")
    private String corpsecret;

    @Value("${wechat.cp.redirect_uri}")
    private String redirectURI;

    @RequestMapping("/entry")
    public String weChatEntry(HttpServletRequest request, HttpServletResponse response) {
        log.debug("WeChat Entry corpid: {} redirectURI: {}", this.corpid, this.redirectURI);

        String redirect_uri = "";
        try {
            redirect_uri = URLEncoder.encode(this.redirectURI, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("WeChat Entry redirect_uri error: {}", e);
        }

        String oauthUrl = WeChatUtils.WECHAT_OAUTH_URL
            .replace("CORPID", this.corpid)
            .replace("REDIRECT_URI", redirect_uri);

        log.debug("WeChat Entry oauthUrl: {}", oauthUrl);
        return "redirect:" + oauthUrl;
    }

    @RequestMapping("/oauth")
    public String weChatOauth(
        @RequestParam("url") String url,
        @RequestParam("code") String code,
        HttpServletRequest request, HttpServletResponse response) {
        log.debug("WeChat oauth corpid: {}, corpsecret: {}", this.corpid, this.corpsecret);
        log.debug("WeChat oauth url: {} code: {}", url, code);

        String token = WeChatUtils.getWechatCpAccessToken(this.corpid, this.corpsecret);
        log.debug("WeChat oauth token: {}", token);

        String userId = "";
        if (!StringUtils.isEmpty(token)) {
            String userInfoUrl = WeChatUtils.WECHAT_USERINFO_URL
                .replace("ACCESS_TOKEN", token)
                .replace("CODE", code);
            JSONObject userInfo = HttpUtils.request(userInfoUrl, "GET", null);
            log.debug("WeChat oauth userInfo: {}", userInfo);

            if (!StringUtils.isEmpty(userInfo)) {
                userId = String.valueOf(userInfo.get("UserId"));

                if (userId == null && userInfo.containsKey("userId")) {
                    userId = userInfo.getString("userId");
                }
            }

            log.debug("WeChat oauth userId: {}", userId);
        }

        String redirect_uri = url.replace("USERID", Base64.encodeBase64String(userId.getBytes()));
        log.info("WeChat redirect_uri: {}", redirect_uri);
        return "redirect:" + redirectURI;
    }
}
