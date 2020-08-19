/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.build.Field;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.shader.BeatShader;
import com.kikijoli.ville.shader.WindShader;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class FieldWorking extends AbstractAction {

    public Field field;

    public FieldWorking(Field field) {
        this.field = field;
    }

    @Override
    public void act() {
        if (Constantes.isBeating()) {
            Vector2 v = new Vector2();
            v = field.getBoundingRectangle().getCenter(v);
            ParticleManager.addParticle("particle/wheat_work.p", v.x, v.y, (float) (0.7f));
//            field.shader = new BeatShader(field, new WindShader(field));
        }
    }

}
