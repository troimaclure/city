package com.kikijoli.ville.pathfind;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.TimeManager;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import com.kikijoli.ville.util.Constantes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author troïmaclure
 */
public class GridManager {

    public static int COLUMNCOUNT;
    public static int ROWCOUNT;
    public static Tile[][] grid;

    public static void initialize(int columnCount, int rowCount, int size) {
        COLUMNCOUNT = columnCount;
        ROWCOUNT = rowCount;
        grid = new Tile[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                grid[i][j] = new Tile(i, j, i * size, j * size, size, size);
            }
        }
    }

    public static Tile getCaseFor(Rectangle entite) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle())) {
                        return g;
                    }
                }
            }
        }

        return null;
    }

    public static Tile getCaseFor(Circle entite) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle())) {
                        return g;
                    }
                }
            }
        }

        return null;
    }

    public static void setState(String b, Rectangle entite) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle())) {
                        g.state = b;
                    }
                }
            }
        }

    }

    public static void tour() {
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(TimeManager.getTimeColor());
        for (Tile[] object : GridManager.grid) {
            for (Tile tile : object) {
                tile.draw(spriteBatch);
            }
        }
        spriteBatch.setColor(c);
    }

    public static boolean isClearZone(Rectangle entite) {
        for (Tile[] grille1 : grid) {
            for (Tile g : grille1) {
                if (!g.state.equals(Constantes.EMPTYFILTER) && Intersector.overlaps(g.getBoundingRectangle(), entite)) {
                    return false;
                }
            }
        }
        return true;
    }

}
