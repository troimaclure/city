/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.build.Market;
import com.kikijoli.ville.manager.ParticleManager;

/**
 *
 * @author troïmaclure
 */
public class MarketWorking extends AbstractAction {

    public int count = 200, go = 200;
    public Market market;
    public ParticleEffect p;

    public MarketWorking(Market market) {
        this.market = market;
    }

    @Override
    public void act() {

        if (count == go) {
            count = 0;
            Vector2 v = new Vector2();
            v = market.getBoundingRectangle().getCenter(v);
            p = ParticleManager.addParticle("particle/money.p", v.x + market.getWidth() / 4, market.getY() + market.getHeight(), (float) (0.8f));
        }
        count++;
    }
}
