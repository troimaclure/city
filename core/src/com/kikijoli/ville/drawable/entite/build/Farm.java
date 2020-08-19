/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.automation.FarmDefault;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Farm extends Build {

    public boolean active;
    ParticleEffect effect;

    public Farm(int srcX, int srcY) {
        super("sprite/windmill.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
        this.action = new FarmDefault(this);

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch); //To change body of generated methods, choose Tools | Templates.
        if (effect != null && effect.isComplete()) {
            addWindmill();
        }
    }

    public void addWindmill() {
        Vector2 v = new Vector2();
        v = getBoundingRectangle().getCenter(v);
        effect = ParticleManager.addParticle("particle/windmill.p", v.x, v.y + 6, 1f);
    }
}
