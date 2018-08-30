package com.mine.microservices.spring.cloud.binder.http;


import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName MessageReceiverController
 * @Description TODO
 * @Author 刘海飞
 * @Date 2018/8/30 15:18
 * @Version 1.0
 **/
@RestController
public class MessageReceiverController implements Controller {

    public static final String ENDPOINT_URI="/message/receive";


    private MessageChannel messageChannel;

    @PostMapping(ENDPOINT_URI)
    public String receive(HttpServletRequest request) throws IOException {
        //请求内容
        InputStream inputStream=request.getInputStream();
        byte[] requestBody=(byte[]) StreamUtils.copyToByteArray(inputStream);
        //写入 messageChannel
        messageChannel.send(new GenericMessage<Object>(requestBody));

        return "success";
    }


    public void setMessageChannel(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
