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
import com.kikijoli.ville.automation.MarketWorking;
import com.kikijoli.ville.automation.NpcWorking;
import com.kikijoli.ville.drawable.entite.build.Castle;
import com.kikijoli.ville.drawable.entite.build.Market;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.TimeManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author troïmaclure
 */
public class MerchantBusiness extends AbstractBusiness {

    private final Npc npc;
    private Market market;
    private Castle castle;
    private Vector2 oldPosition = new Vector2();

    public MerchantBusiness(Npc npc) {
        this.npc = npc;
    }

    @Override
    public AbstractAction getDefault() {
        return new SearchMarket();
    }

    @Override
    public Texture getTexture() {
        return TextureUtil.getTexture("sprite/merchant.png");
    }

    @Override
    public boolean isWorking() {
        return TimeManager.hour < 8 && TimeManager.hour > 2;
    }

    private class BuyInCastle extends AbstractAction {

        @Override
        public void act() {
            if (Constantes.isBeating()) {
                castle.inventory.getTypes(true).stream().forEach((type) -> {
                    castle.inventory.give(npc.inventory, type, 1);
                });
                if (npc.inventory.isFull(npc.maxWeight) || castle.inventory.isEmpty()) {
                    actions.remove("NpcWorking");
                    npc.setPosition(oldPosition.x, oldPosition.y);
                    castle = null;
                    market = null;
                    current = null;
                }
            }
        }
    }

    private class SearchCastle extends AbstractAction {

        @Override
        public void act() {
            if (castle == null) {
                castle = (Castle) EntiteManager.lookForNearest(npc, Castle.class);
                if (castle != null) {
                    actions.put("GoToCastle", new GoTo(npc, castle.anchor));
                }
            } else if (Intersector.overlaps(castle.anchor, npc.getBoundingRectangle())) {
                actions.remove("GoToCastle");
                oldPosition = npc.getBoundingRectangle().getPosition(oldPosition);
                Vector2 center = new Vector2();
                center = castle.getBoundingRectangle().getCenter(center);
                npc.setPosition(center.x - npc.getWidth() / 2, center.y - npc.getHeight() / 2);
                actions.put("NpcWorking", new NpcWorking(npc));
                current = new BuyInCastle();
            }

        }
    }

    private class SellInMarket extends AbstractAction {

        @Override
        public void act() {

            if (!market.checkInventory() && isWorking()) {
                actions.remove("MarketWorking");
                market.active = false;
                npc.setPosition(oldPosition.x, oldPosition.y);
                current = new SearchCastle();
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
                    oldPosition = npc.getBoundingRectangle().getPosition(oldPosition);
                    Vector2 center = new Vector2();
                    center = market.getBoundingRectangle().getCenter(center);
                    npc.setPosition(center.x - npc.getWidth() / 2, center.y - npc.getHeight() / 2);
                    market.inventory.addAll(npc.inventory);
                    npc.inventory.clear();
                    market.active = true;
                    actions.put("MarketWorking", new MarketWorking(market));
                    current = new SellInMarket();
                }
            }
        }

    }

}
