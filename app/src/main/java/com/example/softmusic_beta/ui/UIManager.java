package com.example.softmusic_beta.ui;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.example.softmusic_beta.MainActivity;
import com.example.softmusic_beta.R;
import com.example.softmusic_beta.views.panels.RootMediaPlayerPanel;
import com.example.softmusic_beta.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private static UIManager instance;

    private final MainActivity m_vMainActivity;

    private MultiSlidingUpPanelLayout m_vMultiSlidingPanel;

    private MediaPlayerManager m_vMediaPlayerManager;
    public UIManager(MainActivity activity) {
        instance = this;

        this.m_vMainActivity = activity;
        onCreate();

        this.m_vMediaPlayerManager = new MediaPlayerManager(this.m_vMainActivity, getCallback());
        this.m_vMediaPlayerManager.onStart();
    }

    public MediaController.Callback getCallback() {
        return new MediaController.Callback() {

            @Override
            public void onPlaybackStateChanged(@Nullable PlaybackState state) {
                UIManager.this.m_vMultiSlidingPanel.getAdapter().getItem(RootMediaPlayerPanel.class).onPlaybackStateChanged(state);
            }

            @Override
            public void onMetadataChanged(@Nullable MediaMetadata metadata) {
                UIManager.this.m_vMultiSlidingPanel.getAdapter().getItem(RootMediaPlayerPanel.class).onMetadataChanged(metadata);
            }
        };
    }

    public void onCreate() {
        m_vMultiSlidingPanel = findViewById(R.id.root_sliding_up_panel);

        List<Class<?>> items = new ArrayList<>();


        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);

        m_vMultiSlidingPanel.setPanelStateListener(new PanelStateListener(m_vMultiSlidingPanel));

        m_vMultiSlidingPanel.setAdapter(new Adapter(this.m_vMainActivity, items));
    }

    public static UIManager getInstance() {
        return instance;
    }

    public MediaPlayerManager getMediaPlayerManager() {
        return this.m_vMediaPlayerManager;
    }

    public <T extends android.view.View> T findViewById(@IdRes int id) {
        return this.m_vMainActivity.findViewById(id);

    }
}
