/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.maps;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kikijoli.ville.business.FarmerBusiness;
import com.kikijoli.ville.business.MerchantBusiness;
import com.kikijoli.ville.business.PersonBusinessShopping;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.Build;
import com.kikijoli.ville.drawable.entite.build.House;
import com.kikijoli.ville.drawable.entite.npc.Npc;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import com.kikijoli.ville.manager.CameraManager;
import static com.kikijoli.ville.manager.CameraManager.camera;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.RoadManager;
import com.kikijoli.ville.manager.ShaderManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.manager.TimeManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class Tmap implements Screen {

    public static World world;
    public static RayHandler ray;
    public static ArrayList<Light> lights = new ArrayList<>();
    public static ShapeRenderer shapeRenderer;
    public static SpriteBatch spriteBatch;
    public static Vector3 worldCoordinates = new Vector3();
    public static Stage stage;
    public static FPSLogger fps;

    public Tmap() {
        GridManager.initialize(100, 100, Constantes.TILESIZE);
    }

    @Override
    public void show() {
        fps = new FPSLogger();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        StageManager.initialize();
        Gdx.input.setInputProcessor(new InputMultiplexer(StageManager.getStage(), new GeneralKeyListener()));

        CameraManager.initialize(Constantes.TILESIZE * 15, Constantes.TILESIZE * 10);
        test();

    }

    @Override
    public void render(float delta) {
        fps.log();

        ShaderManager.step();
        Constantes.BEAT += 1;
//        getRay().setAmbientLight(Color.BLUE);
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        CameraManager.tour();

        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        background();
        road();
        water();

//        getRay().setAmbientLight(Color.BLACK);
//        getRay().setCombinedMatrix(camera.combined,
//                camera.position.x, camera.position.y,
//                camera.viewportWidth * camera.zoom,
//                camera.viewportHeight * camera.zoom);
//        getRay().updateAndRender();
        spriteBatch.begin();
        MessageManager.drawIndicators();
        EntiteManager.tour();
        ParticleManager.tour(delta);

        spriteBatch.flush();
        spriteBatch.end();

//        drawAnchor();
        StageManager.tour();
        if (Constantes.isBeating()) {
            TimeManager.step();
            Constantes.BEAT = 0;
            Gdx.graphics.setTitle(TimeManager.getCurrentDay().name + " (" + TimeManager.hour + ":" + TimeManager.minute + ")");
        }
    }

    private void drawAnchor() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Entite entite : EntiteManager.getEntites()) {
            if (entite instanceof Build) {
                Build e = (Build) entite;
                shapeRenderer.circle(e.anchor.x, e.anchor.y, e.anchor.radius);
            }
        }
        shapeRenderer.flush();
        shapeRenderer.end();
    }

    private void road() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        RoadManager.tour();
        shapeRenderer.flush();
        shapeRenderer.end();
    }

    private void water() {
        spriteBatch.begin();
        RoadManager.drawWater();
        spriteBatch.flush();
        spriteBatch.end();
    }

    private void background() {
        //        if (ShaderManager.invertColor != null) {
//            spriteBatch.setShader(ShaderManager.invertColor);
//        }
        spriteBatch.begin();
        GridManager.tour();
        spriteBatch.flush();
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    public static RayHandler getRay() {
        if (ray == null) {
            ray = new RayHandler(getWorld());
//            ray.setAmbientLight(new Color(0.5f, 0.5f, 0.5f, 1.0f));
        }
        return ray;
    }

    public static World getWorld() {
        if (world == null) {
            world = new World(new Vector2(0, 0), true);

        }
        return world;
    }

    private void test() {

        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();
        addPeasant();

        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();
        addMerchant();

        for (int i = 0; i < 200; i++) {
            addPerson();
        }
    }

    private void addPeasant() {

        Npc p = new Npc(Constantes.TILESIZE, Constantes.TILESIZE);
        p.buisiness = new FarmerBusiness(p);
        EntiteManager.addEntite(p);
    }

    private void addMerchant() {
        Npc m = new Npc(Constantes.TILESIZE, Constantes.TILESIZE);
        m.buisiness = new MerchantBusiness(m);
        EntiteManager.addEntite(m);
    }

    private void addPerson() {
        Npc p = new Npc(Constantes.TILESIZE, Constantes.TILESIZE);
        House home = new House(Constantes.TILESIZE * (int) (Math.random() * 100), Constantes.TILESIZE * (int) (Math.random() * 100));
        p.home = home;
        p.buisiness = new PersonBusinessShopping(p);
        EntiteManager.addEntite(home);
        EntiteManager.addEntite(p);
    }

    private void addPerson(int i) {
        Npc p = new Npc(Constantes.TILESIZE, Constantes.TILESIZE);
        House home = new House(Constantes.TILESIZE * i, Constantes.TILESIZE * 2);
        p.home = home;
        p.action = new PersonBusinessShopping(p);
        EntiteManager.addEntite(home);
        EntiteManager.addEntite(p);
    }
}
