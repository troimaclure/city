/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.manager.PathFinderManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class GoTo extends AbstractAction {

    private final Npc entite;
    private final Circle target;
    private ArrayList<Tile> path = null;
    private Vector2 goal = new Vector2();
    private int index = 0;
    private AbstractShader old;

    public GoTo(Npc entite, Circle target) {

        this.entite = entite;
        this.target = target;
        old = entite.shader;
    }

    @Override
    public void act() {
        if (checkPath()) {
            getGoal();
            goToGoal();
            checkGoal();
        }
    }

    private boolean checkPath() {

        if (Constantes.isBeating()) {
            if (path == null) {
                path = PathFinderManager.getPath(entite, target, Constantes.NPCFILTERBUILD);

                if (path == null) { //if again empty
                    System.out.println("NO PATH");
//                    entite.action = new None();
                    return false;
                }
            }
            return true;
        }
        return path != null;
    }

    private void getGoal() {
        if (index < path.size()) {
            goal = path.get(index).getBoundingRectangle().getCenter(goal);
        }
    }

    private void goToGoal() {

        if (entite.shader != null && !(entite.shader instanceof WalkShader)) {
            System.out.println(entite.shader);
        }
        if (!(entite.shader instanceof WalkShader)) {
            entite.shader = new WalkShader(entite);
        }
        Vector2 center = new Vector2();
        center = entite.getBoundingRectangle().getCenter(center);
        entite.setX(entite.getX() + (goal.x < center.x ? (-1) : center.x == goal.x ? 0 : 1));
        entite.setY(entite.getY() + (goal.y < entite.getY() ? (-1) : entite.getY() == goal.y ? 0 : 1));
        if (Intersector.overlaps(target, entite.getBoundingRectangle())) {
            entite.shader.dispose();
            entite.shader = old;
        }
    }

    private void checkGoal() {
        if (entite.getBoundingRectangle().contains(goal)) {
            index++;
        }

    }

}
