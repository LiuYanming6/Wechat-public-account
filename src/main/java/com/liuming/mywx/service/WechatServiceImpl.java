package com.liuming.mywx.service;

import com.liuming.mywx.domain.Article;
import com.liuming.mywx.util.WeChatConstant;
import com.liuming.mywx.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private FeignUtils feignUtils;

//    @Autowired
//    private RedisUtils redisUtils;

    @Override
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;

        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get(WeChatConstant.MsgType);
            String msg = null;

            if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_TEXT)) {
                msg = requestMap.get(WeChatConstant.Content);
                logger.info("processRequest" + msgType +":"+ msg);
                if (msg != null && msg.length() < 2) {
                    List<Article> articleList = new ArrayList<>();

//                    Article article = new Article();
//                    article.setTitle("照片墙");
//                    article.setDescription("阿狸照片墙");
//                    article.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
//                    article.setUrl("http://changhaiwx.pagekite.me/page/photowall");
//                    articleList.add(article);

                    Article article = new Article();
                    article.setTitle("百度");
                    article.setDescription("百度一下");
                    article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                    article.setUrl("http://www.baidu.com");
                    articleList.add(article);

                    respXml = WeChatUtil.sendArticleMsg(requestMap, articleList);
                }
            }// 图片消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get(WeChatConstant.Event);
                // 关注
                if (eventType.equals(WeChatConstant.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                }
                // 取消关注
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            msg = msg == null ? "不知道您在干嘛" : msg;
            if (respXml == null) {
                respXml = WeChatUtil.sendTextMsg(requestMap, msg);
            }
            logger.info(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
