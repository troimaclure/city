/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.kikijoli.ville.nested.Inventory;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Castle extends Build {


    public Castle(int srcX, int srcY) {
        super("sprite/castle.png", srcX, srcY, Constantes.TILESIZE * 3, Constantes.TILESIZE * 3);
    }

}
