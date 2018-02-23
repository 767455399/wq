package com.example.hasee.wq.modle;

import java.util.List;

/**
 * Created by wangqing on 2018/2/23.
 */

public class NailRobotModle {

    /**
     * msgtype : text
     * text : {"content":"我就是我, 是不一样的烟火"}
     * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
     */

    public String msgtype;
    public TextBean text;
    public AtBean at;

    public static class TextBean {
        /**
         * content : 我就是我, 是不一样的烟火
         */

        public String content;
    }

    public static class AtBean {
        /**
         * atMobiles : ["156xxxx8827","189xxxx8325"]
         * isAtAll : false
         */

        public boolean isAtAll;
        public List<String> atMobiles;
    }
}
