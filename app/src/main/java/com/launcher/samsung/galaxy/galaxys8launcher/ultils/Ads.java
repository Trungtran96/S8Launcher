package com.launcher.samsung.galaxy.galaxys8launcher.ultils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.znanotech.zah;


/**
 * Created by binhnk on 6/20/2017.
 */

public class Ads {
    private static final String base64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvO5GoYx6QTldox312coZZ+XMQqPhwLCqhGzsUGw3oJuUp+F0sbh9oMCjDhTR5JB59AE0CzdRIgHS48EgEul6O475pLOMwEPTbF0kgT98jfL696YCv1K2M3zH6mlbhjAIbLDNTxs1kQGpGvxurHu9mHT5cp8DWo1J75FcaI/VgafBBNvptCkQxnHyyEGp/QPLk/7hPRXN+jlBlw+3Ao6RAQjRb0RMr9K6+DIl1+f2kyZkMElnDOJubynH62HMQNK6lGQ5j28TOTtfzh4fzydUlITATAcl5bLNIRp4+t4uRtVFh/YuKRHDAlGeoh85Tez8ze+Nabljpt9+I/O+4jCnzwIDAQAB";
    public static String idVideoAds = "ca-app-pub-6312398807284059/9950623399";

    private static InterstitialAd interstitialAd;

    public interface OnAdsListener {
        void onError();

        void onAdLoaded();

        void onAdOpened();

        void onAdClose();
    }

    public interface RewardedVideoListener {
        void onRewarded(@NonNull RewardItem rewardItem);

        void onRewardedVideoAdClosed();

        void onRewardedVideoAdFailedToLoad(int i);

        void onRewardedVideoAdLeftApplication();

        void onRewardedVideoAdLoaded();

        void onRewardedVideoAdOpened();

        void onRewardedVideoStarted();
    }

    public static void initBanner(Activity activity, final RelativeLayout relativeLayout, final OnAdsListener onAdsListener) {
        try {
            if (com.znanotech.zu.s(activity) && !TextUtils.isEmpty(zah.getBannerAds(activity))) {
                final AdView adView = new AdView(activity);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(zah.getBannerAds(activity));
                AdRequest adRequest = (new AdRequest.Builder()).addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int i) {
                        onAdsListener.onError();
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdLoaded() {
                        onAdsListener.onAdLoaded();
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdOpened() {
                        onAdsListener.onAdOpened();
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdClosed() {
                        onAdsListener.onAdClose();
                        super.onAdClosed();
                    }
                });
                adView.loadAd(adRequest);

                RelativeLayout.LayoutParams rLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                rLParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
                relativeLayout.addView(adView, rLParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBannerAds(final Activity mActivity, final RelativeLayout layoutAds) {
        Ads.initBanner(mActivity, layoutAds, new OnAdsListener() {
            @Override
            public void onError() {
                layoutAds.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                layoutAds.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdOpened() {
                layoutAds.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClose() {
                layoutAds.setVisibility(View.GONE);
                loadBannerAds(mActivity, layoutAds);
            }
        });
    }

    public static void largeBanner(Activity activity, RelativeLayout relativeLayout) {
        if (com.znanotech.zu.s(activity) && !TextUtils.isEmpty(zah.getBannerAds(activity))) {
            AdView adView = new AdView(activity);
            adView.setAdSize(AdSize.LARGE_BANNER);
            adView.setAdUnitId(zah.getBannerAds(activity));
            AdRequest adRequest = (new AdRequest.Builder()).addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView.loadAd(adRequest);

            RelativeLayout.LayoutParams rLParams =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rLParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 1);
            relativeLayout.addView(adView, rLParams);
        }
    }

    public static void loadFullScreenAds(Context context) {
        try {
            if (com.znanotech.zu.s(context) && !TextUtils.isEmpty(zah.getInterAdsId(context))) {
                interstitialAd = new InterstitialAd(context);
                interstitialAd.setAdUnitId(zah.getInterAdsId(context));
                AdRequest adRequest = (new AdRequest.Builder()).build();
                interstitialAd.loadAd(adRequest);
                interstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initVideoAds(Context mContext, @NonNull RewardedVideoAd mRewardedVideoAd, final RewardedVideoListener mListener) {
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded(RewardItem rewardItem) {
                // khi người dùng xem video quá thời gian tối thiểu
                mListener.onRewarded(rewardItem);
            }

            @Override
            public void onRewardedVideoAdClosed() {
                // khi người dùng close video
                mListener.onRewardedVideoAdClosed();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                // khi video gặp lỗi khi load
                mListener.onRewardedVideoAdFailedToLoad(i);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                // khi click vào quảng cáo
                mListener.onRewardedVideoAdLeftApplication();
            }

            @Override
            public void onRewardedVideoAdLoaded() {
                // khi video đã được load xong
                mListener.onRewardedVideoAdLoaded();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                // khi video được mở
                mListener.onRewardedVideoAdOpened();
            }

            @Override
            public void onRewardedVideoStarted() {
                // khi video bắt đầu phát
                mListener.onRewardedVideoStarted();
            }
        });
        mRewardedVideoAd.loadAd(idVideoAds, new AdRequest.Builder().build());
    }

    private static void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}
