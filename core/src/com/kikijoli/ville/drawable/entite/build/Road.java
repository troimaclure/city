/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.interfaces.IDrawShape;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Road extends Rectangle implements IDrawShape {

    public boolean right;
    public boolean left;
    public boolean bottom;
    public boolean top;
    public Circle anchor;
    public Vector2 center;

    public Road(int srcX, int srcY) {
        super(srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        center = new Vector2();
        center = this.getCenter(center);
        this.anchor = new Circle(center, (float) Math.sqrt(width * width + height * height) / 2);
    }

    public Road() {
        super(0, 0, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    @Override
    public void draw(ShapeRenderer batch) {
//        batch.setColor(Color.GRAY);
//
//        int size = Constantes.TILESIZE / 4;
//        batch.circle(this.anchor.x, this.anchor.y, this.anchor.radius / 6);
//        if (right) {
//            batch.rect(center.x, center.y - size / 2, width / 2, size);
//        }
//        if (left) {
//            batch.rect(x, center.y - size / 2, width / 2, size);
//        }
//        if (top) {
//            batch.rect(center.x - size / 2, center.y, size, height / 2);
//        }
//        if (bottom) {
//            batch.rect(center.x - size / 2, y, size, height / 2);
//        }
        batch.setColor(new Color(255 / 255f, 204 / 255f, 153 / 255f, 1));
        int size = Constantes.TILESIZE / 8;
        batch.circle(this.anchor.x, this.anchor.y, this.anchor.radius / 12);
        if (right) {
            batch.rect(center.x, center.y - size / 2, width / 2, size);
        }
        if (left) {
            batch.rect(x, center.y - size / 2, width / 2, size);
        }
        if (top) {
            batch.rect(center.x - size / 2, center.y, size, height / 2);
        }
        if (bottom) {
            batch.rect(center.x - size / 2, y, size, height / 2);
        }

    }

}
