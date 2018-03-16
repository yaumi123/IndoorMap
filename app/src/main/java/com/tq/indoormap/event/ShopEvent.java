package com.tq.indoormap.event;

import com.tq.indoormap.entity.Shop;

/**
 * Created by niantuo on 2017/4/2.
 */

public class ShopEvent {

    public static ShopEvent create(Shop shop) {
        return new ShopEvent().setShop(shop);
    }

    private Shop shop;


    public ShopEvent() {
        super();
    }

    public ShopEvent setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public Shop getShop() {
        return shop;
    }
}
