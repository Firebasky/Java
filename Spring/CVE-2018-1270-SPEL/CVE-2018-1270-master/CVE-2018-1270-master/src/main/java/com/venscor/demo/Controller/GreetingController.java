package com.venscor.demo.Controller;

import com.venscor.demo.Bean.Greeting;
import com.venscor.demo.Bean.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * @ClassName GreetingController
 * @Description TODO
 * @Author wangyu89
 * @Create Time 2018/12/13 22:39
 * @Version 1.0
 */
@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
