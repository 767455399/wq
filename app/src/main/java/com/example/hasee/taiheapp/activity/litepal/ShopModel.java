package com.example.hasee.taiheapp.activity.litepal;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by wangqing on 2018/3/28.
 */

public class ShopModel extends DataSupport{
    /**
     * userId : 100
     * goodsList : [{"id":1,"goodsId":"100","price":3.5},{"id":2,"goodsId":"101","price":4}]
     */
    @SerializedName("userId")
    public String userId;
    /**
     * id : 1
     * goodsId : 100
     * price : 3.5
     */
    @SerializedName("goodsList")
    public List<GoodsListBean> goodsList;


    public static class GoodsListBean {
        public int id;
        public String link;
        public double price;
        public String name;


    }
}
