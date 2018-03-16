package com.tq.indoormap.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.MapViewListener;
import com.onlylemi.mapview.library.layer.BitmapLayer;
import com.onlylemi.mapview.library.layer.LocationLayer;
import com.tq.imageloader.DefImageLoader;
import com.tq.indoormap.R;
import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.event.LocationEvent;
import com.tq.indoormap.event.ShopEvent;
import com.tq.indoormap.location.LocationManager;
import com.tq.indoormap.map.MarkLayer;
import com.tq.indoormap.model.ShopModel;
import com.tq.indoormap.network.MyResult;
import com.tq.indoormap.ui.activity.ShopDetailActivity;
import com.tq.indoormap.utils.ToastUtils;
import com.tq.simple_retrofit.RxBus;
import com.tq.simple_retrofit.simples.ResultObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by niantuo on 2017/4/2.
 * 首页即地图显示展示界面
 */

public class MapFragment extends BaseFragment implements MapViewListener,
        MarkLayer.MarkIsClickListener, Consumer<ShopEvent>, View.OnClickListener {
    final String TAG = MapFragment.class.getSimpleName();

    @BindView(R.id.mapView)
    MapView mMapView;

    @BindView(R.id.action_location)
    FloatingActionButton mLocationBtn;

    private BitmapLayer mBitmapLayer;

    private LocationLayer locationLayer;

    private MarkLayer markLayer;

    private List<Shop> mShopList;

    Observable<ShopEvent> mShopLocation;

    private Observable<LocationEvent> mLocObservable;

    private LocationManager mLocationManager;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_map_container_layout;
    }

    @Override
    protected void initView(View view) {

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getAssets().open("map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMapView.loadMap(bitmap);

        mMapView.setMapViewListener(this);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mShopLocation = RxBus.getBus().register(ShopEvent.class);
        mShopLocation.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        mLocObservable = RxBus.getBus().register(LocationEvent.class);
        mLocObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locationEventConsumer);

        mLocationManager = LocationManager.create(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();
        mLocationManager = LocationManager.create(getContext());
    }

    private void initMarkLayer() {
        showLoadingDialog();
        new ShopModel()
                .loadAll(new ResultObserver<MyResult<List<Shop>>>() {
                    @Override
                    protected void onSuccess(int requestCode, MyResult<List<Shop>> data) {
                        if (data.isSuccess() && data.getData() != null) {
                            mShopList = data.getData();
                            List<PointF> marks = new ArrayList<>();
                            List<String> markName = new ArrayList<>();
                            for (Shop shop : mShopList) {
                                marks.add(shop.getPoint());
                                markName.add(shop.getName());
                            }

                            markLayer = new MarkLayer(mMapView, data.getData());
                            markLayer.setListener(MapFragment.this);
                            mMapView.addLayer(markLayer);
                            mMapView.refresh();
                            mLocationManager.getLocation();
                        } else {
                            ToastUtils.toast(data.getMsg());
                        }
                        hideLoadingDialog();
                    }

                    @Override
                    protected void onError(int requestCode, int errCode, String errMsg) {
                        hideLoadingDialog();
                        ToastUtils.toast(errMsg);
                    }
                });
    }

    private void initLocationLayer() {
        if (locationLayer == null) {
            locationLayer = new LocationLayer(mMapView);
            locationLayer.setCompassIndicatorCircleRotateDegree(60);
            locationLayer.setCompassIndicatorArrowRotateDegree(-30);
            locationLayer.setOpenCompass(true);
            locationLayer.setCompassRadius(18);
        }
    }

    @Override
    public void markIsClick(final Shop shop) {
        if (shop == null) {
            Toast.makeText(getContext(), "未找到该店铺", Toast.LENGTH_SHORT).show();
            return;
        }

        new MaterialDialog.Builder(getContext())
                .content("查看 " + shop.getName() + " 详情？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ShopDetailActivity.startAction(getContext(), shop);
                    }
                })
                .show();
    }


    @Override
    public void onMapLoadSuccess() {
        Log.d(TAG, "onMapLoadSuccess: ");
        initMarkLayer();
        initLocationLayer();
        mMapView.addLayer(locationLayer);
        mMapView.addLayer(markLayer);
        mMapView.refresh();
    }

    @Override
    public void onMapLoadFail() {
        Log.d(TAG, "onMapLoadFail: ");
    }


    @OnClick(R.id.action_location)
    @Override
    public void onClick(View v) {
        mLocationManager.getLocation();
        mLocationBtn.setEnabled(false);
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim_repeat);
        mLocationBtn.startAnimation(a);
    }

    /**
     * 用户在店铺详情界面，点击查看位置，讲调用该方法
     *
     * @param shopEvent
     * @throws Exception
     */
    @Override
    public void accept(ShopEvent shopEvent) throws Exception {

        final Shop shop = shopEvent.getShop();

        Log.d(TAG, "accept: " + shop);

        Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        Bitmap bitmap = DefImageLoader.getDefault().loadBitmapSync(shop.getIcon(), 60);

                        if (bitmap == null) {
                            bitmap = DefImageLoader.getDefault().loadBitmapSync("drawable://" + R.drawable.def_image, 60);
                        }

                        boolean flag = mBitmapLayer == null;

                        if (mBitmapLayer == null) {
                            mBitmapLayer = new BitmapLayer(mMapView, bitmap);
                        } else {
                            mBitmapLayer.setBitmap(bitmap);
                        }

                        mBitmapLayer.setLocation(shop.getPoint());
                        mBitmapLayer.setAutoScale(false);
                        e.onNext(flag);
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean flag) throws Exception {
                        mBitmapLayer.setOnBitmapClickListener(new BitmapLayer.OnBitmapClickListener() {
                            @Override
                            public void onBitmapClick(BitmapLayer layer) {
                                Toast.makeText(getContext(), shop.getName(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (flag) {
                            mMapView.addLayer(mBitmapLayer);
                        }
                        mMapView.refresh();
                    }
                });

    }


    private Consumer<LocationEvent> locationEventConsumer
            = new Consumer<LocationEvent>() {
        @Override
        public void accept(LocationEvent locationEvent) throws Exception {

            Log.d(TAG, "accept: " + locationEvent.getPointF());
            Log.d(TAG, "accept: " + locationEvent.getRadio());
            mLocationBtn.setEnabled(true);
            mLocationBtn.clearAnimation();

            if (locationEvent == null || locationEvent.getPointF() == null) {
                Log.d(TAG, "accept: " + "定位失败");
                return;
            }
            locationLayer.setCurrentPosition(locationEvent.getPointF());
            locationLayer.setCompassRadius(locationEvent.getRadio());
            mMapView.refresh();
        }
    };


    @Override
    public void onDestroy() {
        RxBus.getBus().unregister(ShopEvent.class, mShopLocation);
        RxBus.getBus().unregister(LocationEvent.class, mLocObservable);
        super.onDestroy();
    }
}
