/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.decor;

import com.kikijoli.ville.manager.ShaderManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Water extends Decor {

    public Water(int srcX, int srcY) {
        super("sprite/water.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.shader = ShaderManager.waveShader;
        this.filter = Constantes.WATERFILTER;
    }

}
