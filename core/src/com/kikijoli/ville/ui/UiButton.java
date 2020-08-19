/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.ui;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.widget.VisImageButton;

/**
 *
 * @author troïmaclure
 */
public class UiButton extends VisImageButton {

    public UiButton(Drawable imageUp, ClickListener click) {
        super(imageUp);
        this.addListener(click);
    }

}
