package com.mygdx.game.scenes.ProgressBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

/**
 * Progress bar which reassembles the behaviour of the loading bar (with left and right borders).
 *
 * @author serhiy
 */
public class Stamina extends ProgressBar {

    public Stamina(int width, int height) {
        super(0, 10, 1, false, new ProgressBarStyle());
        getStyle().background = Utils.getColoredDrawable(width, height, Color.RED);
        getStyle().knob = Utils.getColoredDrawable(0, height, Color.RED);
        getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.YELLOW);
        setWidth(width);
        setHeight(height);

        setValue(10);

        setAnimateDuration(1);
    }
}