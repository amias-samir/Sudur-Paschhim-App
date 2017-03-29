package com.naxa.nepal.sudurpaschimanchal.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.naxa.nepal.sudurpaschimanchal.model.Constants;


public class Fragment_YouTube extends YouTubePlayerSupportFragment {
    private FragmentActivity myContext;
    public static Fragment_YouTube newInstance(String url) {

        Fragment_YouTube f = new Fragment_YouTube();

        Bundle b = new Bundle();
        b.putString("url", url);

        f.setArguments(b);
        f.init();

        return f;
    }

    private void init() {

        initialize(Constants.YOUTUBE_DEVELOPER_KEY, new OnInitializedListener() {

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) { }

            @Override
            public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.cueVideo(getArguments().getString("url"));
                }
            }
        });
    }
}

