package com.liuming.mywx.controller;

import com.liuming.mywx.service.WechatService;
import com.liuming.mywx.service.WechatServiceImpl;
import com.liuming.mywx.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WeChatController {
    Logger loger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatService wechatService;

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     * <p>
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(value = "wechat")
    public String validate(@RequestParam("signature") String signature,
                           @RequestParam(value = "timestamp") String timestamp,
                           @RequestParam(value = "nonce") String nonce,
                           @RequestParam(value = "echostr") String echostr) {
        loger.info("validate");
        return WeChatUtil.checkSignature(signature, timestamp, nonce) ? echostr : null;
    }

    /**
     * 此处是处理微信服务器的消息转发的
     */
    @PostMapping(value = "wechat")
    public String processMsg(HttpServletRequest request) {
        // 调用核心服务类接收处理请求
        return wechatService.processRequest(request);
    }

    @GetMapping("test")
    public String test() {
        loger.info("test");
        return "test success";
    }
}
