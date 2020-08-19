/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.build.Road;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.shapeRenderer;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import static com.kikijoli.ville.maps.Tmap.worldCoordinates;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class RoadManager {

    public static ArrayList<Road> roads = new ArrayList<>();
    public static ArrayList<Water> waters = new ArrayList<>();
    public static Road currentRoad;
    public static Road startRoad;
    public static ArrayList<Tile> currentPath = new ArrayList<>();

    public static ArrayList<Road> getRoads() {
        return (ArrayList<Road>) roads.clone();
    }

    public static void addRoads(ArrayList<Road> roads) {
        RoadManager.roads.addAll(roads);
        for (Road road : roads) {
            GridManager.setState(Constantes.ROADFILTER, road);
        }
        calculateRoad(roads);
    }

    public static void tour() {
        if (currentRoad != null) {
            Tile t = GridManager.getCaseFor(new Rectangle(worldCoordinates.x, worldCoordinates.y, 1, 1));
            if (t != null) {
                currentRoad.setPosition(t.getX(), t.getY());
            }

        }
        for (Road road : getRoads()) {
            road.draw(Tmap.shapeRenderer);
        }
        drawCurrentRoad();
    }

    public static void drawWater() {
        for (Water entite : getWaters()) {
            ShaderProgram shader = spriteBatch.getShader();
            if (entite.shader != null) {
                spriteBatch.setShader(entite.shader);
            }
            entite.draw(spriteBatch);
            entite.action.act();
            spriteBatch.setShader(shader);
        }
    }

    private static void drawCurrentRoad() {
        shapeRenderer.setColor(new Color(204 / 255f, 153 / 255f, 102 / 255f, 1));
        if (RoadManager.startRoad != null) {
            shapeRenderer.circle(RoadManager.startRoad.anchor.x, RoadManager.startRoad.anchor.y, RoadManager.startRoad.anchor.radius / 2);
            Tile t = GridManager.getCaseFor(new Rectangle(worldCoordinates.x, worldCoordinates.y, 1, 1));
            if (t != null) {
                Vector2 v = new Vector2();
                v = t.getBoundingRectangle().getCenter(v);
                shapeRenderer.circle(v.x, v.y, RoadManager.startRoad.anchor.radius / 2);
            }
        }
        if (RoadManager.currentPath != null && !RoadManager.currentPath.isEmpty() && RoadManager.currentPath.size() > 2) {
            Vector2[] dataSet = new Vector2[RoadManager.currentPath.size()];
            for (int i = 0; i < RoadManager.currentPath.size(); i++) {
                Vector2 v = new Vector2();
                v = RoadManager.currentPath.get(i).getBoundingRectangle().getCenter(v);
                Vector2 point = new Vector2(v.x, v.y);
                dataSet[i] = point;
            }
            int k = 100; //increase k for more fidelity to the spline
            Vector2[] points = new Vector2[k];

            CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<>(dataSet, false);
            for (int i = 0; i < k; ++i) {
                points[i] = new Vector2();
                myCatmull.valueAt(points[i], ((float) i) / ((float) k - 1));
            }
            /*render()*/

            for (int i = 0; i < k - 1; ++i) {
                Vector2 v = myCatmull.valueAt(points[i], ((float) i) / ((float) k - 1));
                Vector2 v2 = myCatmull.valueAt(points[i + 1], ((float) (i + 1)) / ((float) k - 1));
                drawDottedLine(shapeRenderer, 1, v.x, v.y, v2.x, v2.y);
            }
        }
    }

    public static void calculateRoad(ArrayList<Road> roads) {
        for (Road road : getRoads()) {

            for (Road r : getRoads()) {
                if (r == road) {
                    continue;
                }
                if (r.anchor.overlaps(road.anchor)) {
                    road.right = road.right || road.getX() < r.getX();
                    road.left = road.left || road.getX() > r.getX();
                    road.bottom = road.bottom || road.getY() > r.getY();
                    road.top = road.top || road.getY() < r.getY();
                }
            }
        }
    }

    public static ArrayList<Road> getRoadsOverlap(Road aThis) {
        ArrayList<Road> roads = new ArrayList<>();
        for (Road road : getRoads()) {
            if (road != aThis) {
                if (aThis.anchor.overlaps(road.anchor)) {
                    roads.add(road);
                }
            }
        }
        System.out.println(roads.size());
        return roads;
    }

    public static void click() {
        if (RoadManager.startRoad == null) {
            RoadManager.startRoad = new Road((int) RoadManager.currentRoad.getX(), (int) RoadManager.currentRoad.getY());
        } else {
            ArrayList<Tile> tiles = PathFinderManager.getPath(RoadManager.startRoad, RoadManager.currentRoad, Constantes.EMPTYFILTER + Constantes.ROADFILTER);
            if (tiles == null) {
                return;
            }
            ArrayList<Road> roads = new ArrayList<>();
            tiles.forEach((t) -> {
                Road road = new Road((int) t.getX(), (int) t.getY());
                roads.add(road);
                GridManager.setState(Constantes.ROADFILTER, road);
            });
            RoadManager.addRoads(roads);
            RoadManager.startRoad = null;
            RoadManager.currentPath = new ArrayList<>();
        }
    }

    private static void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, float x1, float y1, float x2, float y2) {

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for (int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.circle(x1 + vec2.x, y1 + vec2.y, 5);
        }

    }

    static void addWater(Water water) {
        waters.add(water);
        GridManager.setState(Constantes.WATERFILTER, water.getBoundingRectangle());
    }

    public static ArrayList<Water> getWaters() {
        return (ArrayList<Water>) waters.clone();
    }

    public static void rightClick() {
        RoadManager.startRoad = null;
        RoadManager.currentPath = new ArrayList<>();
    }
}
