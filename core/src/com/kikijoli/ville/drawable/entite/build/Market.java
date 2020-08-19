/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.kikijoli.ville.automation.MarketWorking;
import com.kikijoli.ville.nested.Inventory;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Market extends Build {

    public boolean active;
    int count = 250;

    public Market(int srcX, int srcY) {
        super("sprite/market.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

    public boolean checkInventory() {
        count--;
        if (count == 0) {
            count = 250;
            return false;
        }
        return true;
    }

}
