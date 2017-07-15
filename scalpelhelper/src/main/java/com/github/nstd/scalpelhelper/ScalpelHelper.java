package com.github.nstd.scalpelhelper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import rjsv.floatingmenu.animation.enumerators.AnimationType;
import rjsv.floatingmenu.floatingmenubutton.FloatingMenuButton;
import rjsv.floatingmenu.floatingmenubutton.listeners.FloatingMenuStateChangeListener;

/**
 * Created by Nstd on 2017/7/12 0012.
 *
 * scalpel: https://github.com/JakeWharton/scalpel
 * floatingMenu: https://github.com/rjsvieira/floatingMenu
 * icon: http://www.iconfont.cn/collections/detail?spm=a313x.7781069.1998910419.d9df05512&cid=3191
 */

public class ScalpelHelper {

    private ScalpelFrameLayout scalpel;
    private ScalpelConfig config = new ScalpelConfig();
    private FloatingMenuButton floatingMenu;

    public void injectScalpel(final Activity activity) {
        final ViewGroup contentContainer = (ViewGroup) activity.findViewById(android.R.id.content);

        contentContainer.post(new Runnable() {
            @Override
            public void run() {
                if(activity == null) return;
                Context context = contentContainer.getContext();

                FrameLayout menuContainer = new FrameLayout(activity);
                scalpel = new ScalpelFrameLayout(activity);
                View v = contentContainer.getChildAt(0);
                if(v != null) {
                    contentContainer.removeView(v);
                    scalpel.addView(v);
                }
                menuContainer.addView(scalpel);

                floatingMenu = new FloatingMenuButton(context);
                floatingMenu.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_tools));
                int menuSize = dpToPx(context, 40);
                FrameLayout.LayoutParams menuLP = new FrameLayout.LayoutParams(menuSize, menuSize);
                menuLP.rightMargin = dpToPx(activity, 20);
                menuLP.bottomMargin = dpToPx(activity, 70);
                menuLP.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                menuContainer.addView(floatingMenu, menuLP);

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

                int subMenuSize = dpToPx(context, 50);

                new FloatingSubMenuBuilder(context)
                        .add(R.drawable.btn_layers, subMenuSize, new SubMenuAction() {
                            @Override
                            public void onClick(View view, Object tag) {
                                scalpel.setLayerInteractionEnabled(config.isLayerInteractionEnabled = !config.isLayerInteractionEnabled);
                                if (!config.isLayerInteractionEnabled) {
                                    floatingMenu.closeMenu();
                                }
                            }
                        })
                        .add(R.drawable.btn_lines, subMenuSize, new SubMenuAction() {
                            @Override
                            public void onClick(View view, Object tag) {
                                scalpel.setDrawViews(config.isDrawView = !config.isDrawView);
                            }
                        })
                        .add(R.drawable.btn_ids, subMenuSize, new SubMenuAction() {
                            @Override
                            public void onClick(View view, Object tag) {
                                scalpel.setDrawIds(config.isDrawId = !config.isDrawId);
                            }
                        })
                        .build(floatingMenu);

                contentContainer.addView(menuContainer);
            }
        });


    }

    //dp转换为px
    private int dpToPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
