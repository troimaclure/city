/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.build.Court;
import com.kikijoli.ville.drawable.entite.build.Farm;
import com.kikijoli.ville.drawable.entite.build.Field;
import com.kikijoli.ville.drawable.entite.build.House;
import com.kikijoli.ville.drawable.entite.build.Road;
import com.kikijoli.ville.drawable.entite.build.Castle;
import com.kikijoli.ville.drawable.entite.build.Market;
import com.kikijoli.ville.drawable.entite.decor.Mountains;
import com.kikijoli.ville.drawable.entite.decor.Tree;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import com.kikijoli.ville.ui.UiButton;
import com.kikijoli.ville.util.TextureUtil;
import com.kotcrab.vis.ui.layout.GridGroup;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 *
 * @author troïmaclure
 */
public class StageManager {

    public static Stage stage;
    public static VisWindow window = new VisWindow("Choix des constructions");

    public static Stage getStage() {
        return stage;
    }

    public static void initialize() {
        stage = new Stage();
        stage.addActor(new VisScrollPane(new VisTable(true)));
//        stage.addActor(new VisDialog("coucou")); 

        window.setX(stage.getWidth() - 300);
        window.setHeight(stage.getHeight());
        window.setY(stage.getHeight());
        window.setWidth(300);

        init();
        stage.addActor(window);
    }

    public static void tour() {
        stage.act();
        stage.draw();
    }

    private static void init() {

        GridGroup group = new GridGroup(64, 8);

        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/house-white.png"))), House()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/road-white.png"))), Road()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/farm-white.png"))), Farm()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/field-white.png"))), Field()));

        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/tree-white.png"))), Tree()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/court-white.png"))), Court()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/mountains-white.png"))), Moutains()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/castle-white.png"))), Castle()));

        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/water.png"))), Water()));
        group.addActor(new UiButton(new TextureRegionDrawable(new TextureRegion(TextureUtil.getTexture("sprite/market.png"))), Market()));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.addActor(new VisTextButton("one row"));
        group.addActor(new VisTextButton("grid"));
        group.addActor(new VisTextButton("one column"));
        group.addActor(new VisTextButton("one row"));

        group.setX(50);
        group.setSize(300, stage.getHeight());

        window.add(group);

    }

    private static ClickListener House() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new House(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new House((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Road() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = null;
                RoadManager.currentRoad = new Road(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        RoadManager.click();
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Farm() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Farm(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        Farm f = new Farm((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY());
                        f.addWindmill();
                        EntiteManager.addEntite(f);
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Field() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Field(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Field((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Tree() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Tree(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Tree((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Court() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Court(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Court((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Moutains() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Mountains(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Mountains((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Castle() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Castle(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Castle((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Water() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Water(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Water((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }

    private static ClickListener Market() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EntiteManager.currentBuild = new Market(0, 0);
                GeneralKeyListener.onTouchAction = new AbstractAction() {
                    @Override
                    public void act() {
                        EntiteManager.addEntite(new Market((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
//                        EntiteManager.addEntite(new Water((int) EntiteManager.currentBuild.getX(), (int) EntiteManager.currentBuild.getY()));
                    }
                };
                super.clicked(event, x, y);
            }
        };
    }
}
