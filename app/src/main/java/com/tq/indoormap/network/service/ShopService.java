package com.tq.indoormap.network.service;

import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.network.MyResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by niantuo on 2017/4/4.
 */

public interface ShopService {

    @GET("api/shop/list")
    Observable<MyResult<List<Shop>>> getShopList(@Query("pageIndex")int pageIndex,
                                                 @Query("pageSize")int pageSize);

    @GET("api/shop/search")
    Observable<MyResult<List<Shop>>> search(@Query("key")String key);

    @GET("api/shop/getByType")
    Observable<MyResult<List<Shop>>> searchByType(@Query("type")String type);

}
