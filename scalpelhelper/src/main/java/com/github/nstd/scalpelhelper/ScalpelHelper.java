package com.github.nstd.scalpelhelper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import java.util.List;

import rjsv.floatingmenu.animation.enumerators.AnimationType;
import rjsv.floatingmenu.floatingmenubutton.FloatingMenuButton;
import rjsv.floatingmenu.floatingmenubutton.listeners.FloatingMenuStateChangeListener;
import rjsv.floatingmenu.floatingmenubutton.subbutton.SubButton;

/**
 * Created by Nstd on 2017/7/12 0012.
 *
 * scalpel: https://github.com/JakeWharton/scalpel
 * floatingMenu: https://github.com/rjsvieira/floatingMenu
 * icon: http://www.iconfont.cn/collections/detail?spm=a313x.7781069.1998910419.d9df05512&cid=3191
 */

public class ScalpelHelper {

    ScalpelFrameLayout scalpel;
    ScalpelConfig config = new ScalpelConfig();
    FloatingMenuButton floatingMenu;

    public void injectScalpel(Activity activity) {
        FrameLayout fl = new FrameLayout(activity);
        scalpel = new ScalpelFrameLayout(activity);
        ViewGroup contentContainer = (ViewGroup) activity.findViewById(android.R.id.content);
        View v = contentContainer.getChildAt(0);
        contentContainer.removeView(v);
        fl.addView(scalpel);
        scalpel.addView(v);

        floatingMenu = (FloatingMenuButton) LayoutInflater.from(activity).inflate(R.layout.menu, fl, false);
        fl.addView(floatingMenu);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) floatingMenu.getLayoutParams();
        lp.rightMargin = dpToPx(activity, 20);
        lp.bottomMargin = dpToPx(activity, 70);
        floatingMenu.setAlpha(0.3f);

        initSubMenu(floatingMenu);

        floatingMenu.setStartAngle(0)
                .setEndAngle(360)
                .setAnimationType(AnimationType.EXPAND)
                .setRadius(100)
                .setAnchored(false);
        floatingMenu.getAnimationHandler()
                .setOpeningAnimationDuration(500)
                .setClosingAnimationDuration(200)
                .setLagBetweenItems(0)
                .setOpeningInterpolator(new FastOutSlowInInterpolator())
                .setClosingInterpolator(new FastOutLinearInInterpolator())
                .shouldFade(true)
                .shouldScale(true)
                .shouldRotate(false);

        floatingMenu.setStateChangeListener(new FloatingMenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingMenuButton floatingMenuButton) {
                floatingMenu.setAlpha(1);
            }

            @Override
            public void onMenuClosed(FloatingMenuButton floatingMenuButton) {
                floatingMenu.setAlpha(0.3f);
            }
        });

        contentContainer.addView(fl);

    }

    private void initSubMenu(FloatingMenuButton menu) {
        List<SubButton> list = menu.getSubMenuButtons();
        for(SubButton sub : list) {
            sub.getView().setOnClickListener(menuClickListener);
        }
    }

    private View.OnClickListener menuClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.btn_interaction) {
                scalpel.setLayerInteractionEnabled(config.isLayerInteractionEnabled = !config.isLayerInteractionEnabled);
                if (!config.isLayerInteractionEnabled) {
                    floatingMenu.closeMenu();
                }
            } else if (id == R.id.btn_draw_id) {
                scalpel.setDrawIds(config.isDrawId = !config.isDrawId);
            } else if (id == R.id.btn_draw_view) {
                scalpel.setDrawViews(config.isDrawView = !config.isDrawView);
            }
        }
    };

    //dp转换为px
    private int dpToPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
