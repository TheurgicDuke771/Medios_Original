package com.amigos.android.medios;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

/**
 * Created by Arijit on 07-07-2017.
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.first_slide_background)
                        .buttonsColor(R.color.first_slide_buttons)
                        .image(R.drawable.img_love_music)
                        .title("Do you love Music?")
                        .description("Try Medios")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("But before that, we need a small help");
                    }
                }, "Let's start"));
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.second_slide_background)
                        .buttonsColor(R.color.second_slide_background)
                        .neededPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE})
                        .image(R.drawable.img_equipment)
                        .title("We need some permissions")
                        .description("to play music")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("Yup! That's all we need");
                    }
                }, "Ok then?"));

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.third_slide_background)
                .buttonsColor(R.color.third_slide_background)
                .title("That's it")
                .description("Would you like to try?")
                .build());

    }
    @Override
    public void onFinish() {
        super.onFinish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
