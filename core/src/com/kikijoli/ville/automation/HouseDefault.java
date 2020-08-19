/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.build.House;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.TimeManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class HouseDefault extends AbstractAction {

    public int count = 0, go = 300;
    public House house;

    public HouseDefault(House house) {
        this.house = house;
    }

    @Override
    public void act() {
        count++;
        if (count == go) {
            go = 2500 + (int) (Math.random() * 1000);
            count = 0;
            Vector2 v = new Vector2();
            v = house.getBoundingRectangle().getCenter(v);
            ParticleManager.addParticle("particle/round_smoke.p", v.x + house.getWidth() / 4, house.getY() + house.getHeight(), (float) (0.4f + Math.random() * 0.5f));
        }
        if (Constantes.isBeating() && TimeManager.hour == 0 && TimeManager.minute == 0) {
            house.inventory.getTypes(true).stream().forEach(e -> {
                house.inventory.remove(e, 2);
            });
        }
    }

}
