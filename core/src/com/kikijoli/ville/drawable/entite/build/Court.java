/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Court extends Build {

    public Court(int srcX, int srcY) {
        super("sprite/court.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

}
