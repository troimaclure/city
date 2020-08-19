/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.automation.GoTo;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.NpcWorking;
import com.kikijoli.ville.drawable.entite.build.Market;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.TimeManager;
import com.kikijoli.ville.shader.BeatShader;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author troïmaclure
 */
public class PersonBusinessShopping extends AbstractBusiness {

    private final Npc npc;
    private Market market;
    private Vector2 oldPosition = new Vector2();

    public PersonBusinessShopping(Npc npc) {
        this.npc = npc;
    }

    @Override
    public AbstractAction getDefault() {
        return new GoToHome();
    }

    @Override
    public Texture getTexture() {
        return TextureUtil.getTexture("sprite/npc.png");
    }

    @Override
    public boolean isWorking() {
        return TimeManager.hour > 8 && TimeManager.hour < 16;
    }

    private class PutAtHome extends AbstractAction {

        @Override
        public void act() {

            npc.inventory.getTypes(true).stream().forEach((type) -> {
                npc.inventory.give(npc.home.inventory, type, 1);
            });
            if (npc.inventory.isEmpty() && Constantes.isBeating()) {
                if (isWorking() && npc.home.inventory.isEmpty()) {
                    npc.visible = true;
                    current = new SearchMarket();
                }
            }
        }
    }

    private class GoToHome extends AbstractAction {

        @Override
        public void act() {

            if (npc.home == null) {
                return;
            }
            if (!actions.containsKey("GoToHome")) {
                actions.put("GoToHome", new GoTo(npc, npc.home.anchor));
            }
            if (Intersector.overlaps(npc.home.anchor, npc.getBoundingRectangle())) {
                actions.remove("GoToHome");
                npc.visible = false;
                npc.home.shader = new BeatShader(npc.home, null);
                current = new PutAtHome();
            }
        }
    }

    private class BuyMarket extends AbstractAction {

        @Override
        public void act() {
            if (!actions.containsKey("NpcWorking")) {
                actions.put("NpcWorking", new NpcWorking(npc));
            }
            if (market != null && Constantes.isBeating()) {
                for (String type : market.inventory.getTypes(true)) {
                    market.inventory.give(npc.inventory, type, 1);
                    if (npc.inventory.isFull(npc.maxWeight)) {
                        break;
                    }
                }
                if (market.inventory.isEmpty() || npc.inventory.isFull(npc.maxWeight)) {
                    npc.setPosition(oldPosition.x, oldPosition.y);
                    actions.remove("NpcWorking");
                    current = new GoToHome();
                    market = null;
                }
            }
        }
    }

    private class SearchMarket extends AbstractAction {

        @Override
        public void act() {
            if (market == null) {
                market = (Market) EntiteManager.lookForNearest(npc, Market.class);
                if (market != null) {
                    actions.put("GoToMarket", new GoTo(npc, market.anchor));
                }
            } else if (market != null) {
                if (Intersector.overlaps(market.anchor, npc.getBoundingRectangle())) {
                    actions.remove("GoToMarket");
                    current = new BuyMarket();
                    oldPosition = new Vector2(npc.getX(), npc.getY());
                    npc.setPosition((float) (market.getX() + market.getWidth() / 10 + Math.random() * (market.getWidth() - market.getWidth() / 5)), market.getY());
                }
            }
        }
    }
}
