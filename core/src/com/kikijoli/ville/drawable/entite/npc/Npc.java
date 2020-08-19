/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.House;
import com.kikijoli.ville.nested.Inventory;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author troïmaclure
 */
public class Npc extends Entite {

    public Inventory inventory;
    public int maxWeight = 3;
    public House home;

    public Npc(int srcX, int srcY) {

        super("sprite/npc.png", srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        this.inventory = new Inventory(this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (!inventory.isEmpty()) {
            batch.draw(TextureUtil.getTexture("sprite/bag.png"), this.getX() + this.getWidth() - Constantes.TILESIZE / 3, this.getY(), Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        }
    }
}
