/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.kikijoli.ville.automation.HouseDefault;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class House extends Build {

    public House(int srcX, int srcY) {
        super("sprite/house.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.action = new HouseDefault(this);
    }

}
