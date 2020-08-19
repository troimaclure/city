package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.Build;
import com.kikijoli.ville.drawable.entite.build.Farm;
import com.kikijoli.ville.drawable.entite.build.Field;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import static com.kikijoli.ville.maps.Tmap.worldCoordinates;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.shader.ClickShader;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class EntiteManager {

    public static ArrayList<Entite> entites = new ArrayList<>();
    public static Entite currentBuild;

    public static ArrayList<Entite> getEntites() {
        return (ArrayList<Entite>) entites.clone();
    }

    public static void addEntite(Entite entite) {
        if (!GridManager.isClearZone(entite.getBoundingRectangle())) {
            return;
        }
        GridManager.setState(entite.filter, entite.getBoundingRectangle());
        entites.add(entite);
    }

    public static void tour() {
        Color c = spriteBatch.getColor();
        Color time = TimeManager.getTimeTextureColor();

        entites.stream().forEach((Entite entite) -> {
            spriteBatch.setColor(time);
            spriteBatch.setShader(entite.shader);

            if (entite.visible) {
                entite.draw(spriteBatch);
            }
            if (entite.action != null) {
                entite.action.act();
            }
            if (entite.buisiness != null) {
                entite.buisiness.act();
            }
            spriteBatch.setShader(ShaderManager.defaultShader);
        });
        if (EntiteManager.currentBuild != null) {
            Tile t = GridManager.getCaseFor(new Rectangle(worldCoordinates.x, worldCoordinates.y, 1, 1));
            if (t != null) {
                EntiteManager.currentBuild.setPosition(t.getX(), t.getY());
                spriteBatch.setColor(TimeManager.getTimeTextureColor().r, TimeManager.getTimeTextureColor().g, TimeManager.getTimeTextureColor().b, 0.2f);
                EntiteManager.currentBuild.draw(spriteBatch);
            }
        }
        spriteBatch.setColor(c);
    }

    public static void clickHover() {
        Rectangle r = new Rectangle(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y, 1, 1);
        for (Entite entite : getEntites()) {
            if (entite.getBoundingRectangle().overlaps(r)) {
                if (!(entite.shader instanceof ClickShader) && entite instanceof Build) {
                    entite.shader = new ClickShader(entite, entite.shader);
                }
            }
        }
    }

    public static void rightClick() {
        if (EntiteManager.currentBuild == null) {
            Rectangle r = new Rectangle(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y, 1, 1);
            for (Entite entite : getEntites()) {
                if (entite.getBoundingRectangle().overlaps(r)) {
                    entites.remove(entite);
                    GridManager.setState(Constantes.EMPTYFILTER, entite.getBoundingRectangle());
                    break;
                }
            }
        } else {
            EntiteManager.currentBuild = null;
        }

    }

    public static Build lookForNearest(Npc source, Class<? extends Build> aClass) {
        ArrayList<Build> get = new ArrayList<>();
        getEntites().stream().filter((entite) -> (entite.getClass() == aClass)).forEachOrdered((entite) -> {
            get.add((Build) entite);
        });
        Build target = null;
        int max = 5000;
        for (Build build : get) {
            ArrayList<Tile> path = PathFinderManager.getPath(source, build.anchor, Constantes.NPCFILTERBUILD);;
            if (path != null && path.size() < max) {
                target = build;
                max = path.size();
            }
        }
        return target;
    }

    public static Field getBestField(Farm farm) {
        Field f = null;
        for (Entite entite : getEntites()) {
            if (entite instanceof Field && !((Field) entite).isWorking() && entite.getBoundingRectangle().overlaps(farm.anchorInfluence)) {
                Field current = (Field) entite;
                if (f == null) {
                    f = current;
                } else if (current.inventory.products.get(current.getProduct()) > f.inventory.products.get(f.getProduct())) {
                    f = (Field) entite;
                }
            }
        }
        return f;
    }

}
