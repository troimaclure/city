/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.Farm;
import com.kikijoli.ville.drawable.entite.build.Field;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.shader.BeatShader;
import com.kikijoli.ville.shader.WindShader;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class FarmDefault extends AbstractAction {

    public int count = 0;
    public int delayField = 4;
    public Farm farm;
    public ParticleEffect p;

    public FarmDefault(Farm farm) {
        this.farm = farm;
    }

    @Override
    public void act() {
        if (Constantes.isBeating()) {
            this.farm.active = false;
            for (Entite entite : EntiteManager.getEntites()) {
                if (entite instanceof Field && Intersector.overlaps(this.farm.anchor, entite.getBoundingRectangle())) {
                    this.farm.active = true;
                    break;
                }
            }
            if (this.farm.active) {
//                Vector2 v = new Vector2();
//                v = farm.getBoundingRectangle().getCenter(v);
//                p = ParticleManager.addParticle("particle/work.p", v.x + farm.getWidth() / 4, farm.getY() + farm.getHeight(), (float) (0.8f));
                if (count++ == delayField) {
                    count = 0;
                    EntiteManager.getEntites().stream().filter(e -> e instanceof Field && Intersector.overlaps(((Field) e).anchor, this.farm.anchorInfluence)).forEach(e -> {
                        ((Field) e).addProduct(1);
                        e.shader.dispose();
                        e.shader = new BeatShader(e, new WindShader(e));
                    });
                }
            }
        }

    }
}
