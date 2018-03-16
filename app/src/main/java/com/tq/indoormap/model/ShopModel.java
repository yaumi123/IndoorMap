package com.tq.indoormap.model;

import com.tq.indoormap.db.GreenDaoUtils;
import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.greenDao.ShopDao;
import com.tq.indoormap.network.API;
import com.tq.indoormap.network.MyResult;
import com.tq.indoormap.network.service.ShopService;
import com.tq.simple_retrofit.BaseHttpClient;
import com.tq.simple_retrofit.simples.ResultObserver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by niantuo on 2017/4/2.
 */

public class ShopModel extends BaseHttpClient<ShopService> {

    public ShopModel() {
        super(ShopService.class, API.HOST_API);
    }


    public void loadRecommended(int index, ResultObserver<MyResult<List<Shop>>> observer) {

        service().getShopList(index, 1000)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void loadAll(ResultObserver<MyResult<List<Shop>>> observer) {

        service().getShopList(0, 1000)
                .flatMap(new Function<MyResult<List<Shop>>, ObservableSource<MyResult<List<Shop>>>>() {
                    @Override
                    public ObservableSource<MyResult<List<Shop>>> apply(final MyResult<List<Shop>> listMyResult) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<MyResult<List<Shop>>>() {
                            @Override
                            public void subscribe(ObservableEmitter<MyResult<List<Shop>>> emitter) throws Exception {
                                if (listMyResult.isSuccess()){
                                    saveToLocal(listMyResult.getData());
                                }
                                emitter.onNext(listMyResult);
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    public void loadByType(final String type, ResultObserver<MyResult<List<Shop>>> observer) {
        service().searchByType(type)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void searchByKey(String type, ResultObserver<MyResult<List<Shop>>> observer) {
        service().search(type)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }


    private void saveToLocal(List<Shop> shopList) {
        if (shopList == null) return;
        ShopDao dao = GreenDaoUtils.getSingleTon().getSession().getShopDao();
        dao.insertOrReplaceInTx(shopList);
    }


}
