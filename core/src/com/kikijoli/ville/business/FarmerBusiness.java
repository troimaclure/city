/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.automation.GoTo;
import com.kikijoli.ville.automation.NpcWorking;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.build.Castle;
import com.kikijoli.ville.drawable.entite.build.Farm;
import com.kikijoli.ville.drawable.entite.build.Field;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.TimeManager;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author troïmaclure
 */
public class FarmerBusiness extends AbstractBusiness {

    private final Npc npc;
    private Farm farm;
    private Field field;
    private Castle castle;
    private Vector2 oldPosition = new Vector2();

    public FarmerBusiness(Npc npc) {
        this.npc = npc;
    }

    @Override
    public AbstractAction getDefault() {
        return new SearchFarm();
    }

    @Override
    public Texture getTexture() {
        return TextureUtil.getTexture("sprite/farmer.png");
    }

    @Override
    public boolean isWorking() {
        return TimeManager.hour > 2 && TimeManager.hour < 9;
    }

    private class WorkingFarm extends AbstractAction {

        @Override
        public void act() {
            if (field == null) {
                field = EntiteManager.getBestField(farm);
                if (field != null) {
                    oldPosition = npc.getBoundingRectangle().getPosition(oldPosition);
                    Vector2 centerField = new Vector2();
                    centerField = field.getBoundingRectangle().getCenter(centerField);
                    npc.setPosition(centerField.x - npc.getWidth() / 2, centerField.y - npc.getHeight() / 2);
                    actions.put("FieldWorking", field.setWorking(true));
                    actions.put("NpcWorking", new NpcWorking(npc));
                }
            } else if (field != null) {

                field.inventory.getTypes(true).stream().forEach((type) -> {
                    field.inventory.give(npc.inventory, type, 1);
                });
                if (npc.inventory.isFull(npc.maxWeight) || field.inventory.isEmpty()) {
                    actions.remove("NpcWorking");
                    actions.remove("FieldWorking");
                    field.setWorking(false);
                    npc.setPosition(oldPosition.x, oldPosition.y);
                    field = null;
                    farm = null;
                    current = new SearchCastle();
                }
            }
        }
    }

    private class PutInCastle extends AbstractAction {

        @Override
        public void act() {
            npc.inventory.getTypes(true).stream().forEach((type) -> {
                npc.inventory.give(castle.inventory, type, 1);
            });
            if (npc.inventory.isEmpty()) {
                actions.remove("NpcWorking");
                npc.setPosition(oldPosition.x, oldPosition.y);
                castle = null;
                current = null;
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
                current = new PutInCastle();
            }

        }
    }

    private class SearchFarm extends AbstractAction {

        @Override
        public void act() {
            if (isWorking()) {
                npc.visible = true;
                if (farm == null) {
                    farm = (Farm) EntiteManager.lookForNearest(npc, Farm.class);
                    if (farm != null) {
                        actions.put("GoToFarm", new GoTo(npc, farm.anchor));
                    }
                } else if (farm != null) {
                    if (Intersector.overlaps(farm.anchor, npc.getBoundingRectangle())) {
                        actions.remove("GoToFarm");
                        current = new WorkingFarm();
                    }
                }
            } else {
                npc.visible = false;
            }

        }

    }

}
