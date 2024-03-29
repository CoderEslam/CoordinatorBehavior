package com.doubleclick.coordinatorbehavior;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;


class HandleProfileViewBehavior implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.7f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.7f;
    private static final int ALPHA_ANIMATIONS_DURATION = 250;

    private boolean isTheTitleVisible = false;
    private boolean isTheTitleContainerVisible = true;

    private LinearLayout linearLayoutTitle;
    private TextView textViewTitle;

    HandleProfileViewBehavior(View rootView) {

        AppBarLayout appbar =  rootView.findViewById(R.id.appbar);
        linearLayoutTitle =  rootView.findViewById(R.id.llTitle);
        textViewTitle =  rootView.findViewById(R.id.tvToolbarTitle);

        appbar.addOnOffsetChangedListener(this);

        startAlphaAnimation(textViewTitle, 0, View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!isTheTitleVisible) {
                startAlphaAnimation(textViewTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                isTheTitleVisible = true;
            }
        } else {
            if (isTheTitleVisible) {
                startAlphaAnimation(textViewTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                isTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (isTheTitleContainerVisible) {
                startAlphaAnimation(linearLayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                isTheTitleContainerVisible = false;
            }
        } else {

            if (!isTheTitleContainerVisible) {
                startAlphaAnimation(linearLayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                isTheTitleContainerVisible = true;
            }
        }
    }

    private static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
