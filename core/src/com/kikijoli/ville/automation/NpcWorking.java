/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class NpcWorking extends AbstractAction {

    public Npc npc;

    public NpcWorking(Npc npc) {
        this.npc = npc;
    }

    @Override
    public void act() {
        if (Constantes.isBeating()) {
//            npc.shader = new BeatShader(npc, null);
        }
    }

}
