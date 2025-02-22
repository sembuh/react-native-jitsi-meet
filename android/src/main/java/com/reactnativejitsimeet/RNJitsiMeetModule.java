package com.reactnativejitsimeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

@ReactModule(name = RNJitsiMeetModule.MODULE_NAME)
public class RNJitsiMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNJitsiMeetModule";
    private IRNJitsiMeetViewReference mJitsiMeetViewReference;
    private ReactApplicationContext mReactContext;

    public RNJitsiMeetModule(ReactApplicationContext reactContext, IRNJitsiMeetViewReference jitsiMeetViewReference) {
        super(reactContext);
        mJitsiMeetViewReference = jitsiMeetViewReference;
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("JitsiMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(false)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mJitsiMeetViewReference.getJitsiMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJitsiMeetViewReference.getJitsiMeetView() != null) {
                    mJitsiMeetViewReference.getJitsiMeetView().leave();
                }
            }
        });
    }

    @ReactMethod
    public void muteAudio() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent muteBroadcastIntent = new Intent("org.jitsi.meet.SET_AUDIO_MUTED");
                muteBroadcastIntent.putExtra("muted", true);
                LocalBroadcastManager.getInstance(mReactContext).sendBroadcast(muteBroadcastIntent);
            }
        });
    }

    @ReactMethod
    public void unmuteAudio() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent muteBroadcastIntent = new Intent("org.jitsi.meet.SET_AUDIO_MUTED");
                muteBroadcastIntent.putExtra("muted", false);
                LocalBroadcastManager.getInstance(mReactContext).sendBroadcast(muteBroadcastIntent);
            }
        });
    }

    @ReactMethod
    public void muteVideo() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent muteBroadcastIntent = new Intent("org.jitsi.meet.SET_VIDEO_MUTED");
                muteBroadcastIntent.putExtra("muted", true);
                LocalBroadcastManager.getInstance(mReactContext).sendBroadcast(muteBroadcastIntent);
            }
        });
    }

    @ReactMethod
    public void unmuteVideo() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent muteBroadcastIntent = new Intent("org.jitsi.meet.SET_VIDEO_MUTED");
                muteBroadcastIntent.putExtra("muted", false);
                LocalBroadcastManager.getInstance(mReactContext).sendBroadcast(muteBroadcastIntent);
            }
        });
    }
}
