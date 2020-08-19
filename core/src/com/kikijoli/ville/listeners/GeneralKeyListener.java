/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.manager.CameraManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.PathFinderManager;
import com.kikijoli.ville.manager.RoadManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class GeneralKeyListener extends InputAdapter {

    public static boolean KeyLeft, KeyUp, KeyDown, KeyRight;
    public static AbstractAction onTouchAction;
    private boolean dragged;
    private int buttonDown;
    private float oldX = 0;

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Keys.LEFT:
                KeyLeft = true;
                break;
            case Keys.UP:
                KeyUp = true;
                break;
            case Keys.DOWN:
                KeyDown = true;
                break;
            case Keys.RIGHT:
                KeyRight = true;
                break;
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Keys.LEFT:
                KeyLeft = false;
                break;
            case Keys.UP:
                KeyUp = false;
                break;
            case Keys.DOWN:
                KeyDown = false;
                break;
            case Keys.RIGHT:
                KeyRight = false;
                break;
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean scrolled(int amount) {
        if ((CameraManager.camera.zoom + ((float) amount / 5) > 0.5f && CameraManager.camera.zoom + ((float) amount / 5) < 6.5f)) {
            CameraManager.camera.zoom += ((float) amount / 5);
            CameraManager.camera.update();
        }
        return super.scrolled(amount);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        EntiteManager.clickHover();

        buttonDown = button;
        return super.touchDown(screenX, screenY, pointer, button); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        oldX = 0;
        if (button == Input.Buttons.RIGHT) {
            onTouchAction = null;
            EntiteManager.rightClick();
            RoadManager.rightClick();
        } else if (onTouchAction != null && !dragged && button == Input.Buttons.LEFT) {
            onTouchAction.act();
        }
        dragged = false;
        return super.touchDown(screenX, screenY, pointer, button); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragged = true;
        float x = Gdx.input.getDeltaX();
        float y = Gdx.input.getDeltaY();

        if (buttonDown == Input.Buttons.LEFT) {
            CameraManager.camera.position.add(x * (-CameraManager.camera.zoom), y * (CameraManager.camera.zoom), 0);
            CameraManager.camera.update();
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (RoadManager.startRoad != null) {
            ArrayList<Tile> tiles = PathFinderManager.getPath(RoadManager.startRoad, RoadManager.currentRoad, Constantes.EMPTYFILTER + Constantes.ROADFILTER);
            if (tiles != null && tiles.size() > 1) {
                RoadManager.currentPath = tiles;
            }
        }
        return super.mouseMoved(screenX, screenY);
    }

}
