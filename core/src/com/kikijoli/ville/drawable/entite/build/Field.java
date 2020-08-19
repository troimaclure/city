/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.FieldWorking;
import com.kikijoli.ville.automation.None;
import com.kikijoli.ville.shader.WindShader;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Field extends Build {

    private boolean working = false;

    public Field(int srcX, int srcY) {
        super("sprite/Wheat.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
        this.action = new None();
        this.shader = new WindShader(this);
        this.inventory.add(Constantes.WHEAT, 120);
        this.filter = Constantes.EMPTYFILTER;
    }

//    @Override
//    public void draw(SpriteBatch batch) {
////        super.draw(batch); //To change body of generated methods, choose Tools | Templates.
//        batch.draw(this.getTexture(), getX(), getY(), getWidth() / 2, getHeight() / 2);
//        batch.draw(this.getTexture(), getX() + Constantes.TILESIZE, getY(), getWidth() / 2, getHeight() / 2);
//        batch.draw(this.getTexture(), getX(), getY() + Constantes.TILESIZE, getWidth() / 2, getHeight() / 2);
//        batch.draw(this.getTexture(), getX() + Constantes.TILESIZE, getY() + Constantes.TILESIZE, getWidth() / 2, getHeight() / 2);
//    }
    public AbstractAction setWorking(boolean b) {
        this.working = b;
        this.action = this.working ? new FieldWorking(this) : new None();
        return this.action;
    }

    public String getProduct() {
        return Constantes.WHEAT;
    }

    public void addProduct(int i) {
        this.inventory.add(this.getProduct(), i);
    }

    public boolean isWorking() {
        return this.working;
    }

}
