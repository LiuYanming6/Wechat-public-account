package com.liuming.mywx.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface WechatService {
    String processRequest(HttpServletRequest request);
}
