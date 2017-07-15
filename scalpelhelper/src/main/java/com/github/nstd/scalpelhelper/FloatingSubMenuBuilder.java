package com.github.nstd.scalpelhelper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rjsv.floatingmenu.floatingmenubutton.FloatingMenuButton;
import rjsv.floatingmenu.floatingmenubutton.subbutton.FloatingSubButton;

/**
 * Created by Nstd on 2017/7/15 0015.
 */

public class FloatingSubMenuBuilder {

    private Context mContext;
    private List<FloatingSubButton> mMenuItems;

    public FloatingSubMenuBuilder(Context context){
        this.mContext = context;
        mMenuItems = new ArrayList<>();
    }

    public FloatingSubMenuBuilder add(int iconResId, SubMenuAction action) {
        add(iconResId, null, action);
        return this;
    }

    public FloatingSubMenuBuilder add(Drawable iconDrawable, SubMenuAction action) {
        add(iconDrawable, null, action);
        return this;
    }

    public FloatingSubMenuBuilder add(int iconResId, int size, SubMenuAction action) {
        add(iconResId, size, null, action);
        return this;
    }

    public FloatingSubMenuBuilder add(Drawable iconDrawable, int size, SubMenuAction action) {
        add(iconDrawable, size, null, action);
        return this;
    }

    public FloatingSubMenuBuilder add(int iconResId, Object tag, SubMenuAction action) {
        add(mContext.getResources().getDrawable(iconResId), tag, action);
        return this;
    }

    public FloatingSubMenuBuilder add(Drawable iconDrawable, Object tag, SubMenuAction action) {
        add(iconDrawable, -1, tag, action);
        return this;
    }

    public FloatingSubMenuBuilder add(int iconResId, int size, Object tag, SubMenuAction action) {
        add(mContext.getResources().getDrawable(iconResId), size, tag, action);
        return this;
    }

    public FloatingSubMenuBuilder add(Drawable iconDrawable, int size, Object tag, SubMenuAction action) {
        FloatingSubButton subButton = new FloatingSubButton(mContext);
        ColorStateList colors = new ColorStateList(new int[][] {
                {-android.R.attr.state_enabled},
                {android.R.attr.state_enabled, android.R.attr.state_pressed},

        }, new int[] {
                0x4c000000,
                0x99000000,
        });
        subButton.setBackgroundDrawable(tintDrawable(iconDrawable.mutate(), colors));
        if(size > 0) {
            subButton.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        }
        subButton.setTag(R.id.floatingSubMenuTag, tag);
        subButton.setOnClickListener(new SubMenuClickListener(action));
        mMenuItems.add(subButton);
        return this;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.DST_OUT);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public void build(FloatingMenuButton menu) {
        for(int i = 0, size = mMenuItems.size(); i < size; i++) {
            menu.addFloatingSubButton(mMenuItems.get(i), mMenuItems.get(i).getLayoutParams());
        }
    }

    private class SubMenuClickListener implements View.OnClickListener {

        SubMenuAction mAction;

        public SubMenuClickListener(SubMenuAction action) {
            mAction = action;
        }

        @Override
        public void onClick(View view) {
            if(mAction == null) return;
            Object tag = view.getTag(R.id.floatingSubMenuTag);
            mAction.onClick(view, tag);
        }
    }
}
