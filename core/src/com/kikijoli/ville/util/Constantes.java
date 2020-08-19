/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.util;

/**
 *
 * @author troïmaclure
 */
public class Constantes {

    public static int TILESIZE = 32;
    public static int SCREENWIDTH = 1600;
    public static int SCREENHEIGHT = 900;
    public static String ROADFILTER = "1";
    public static String BUILDFILTER = "2";
    public static String EMPTYFILTER = "0";
    public static String WATERFILTER = "3";
    public static String NPCFILTERBUILD = Constantes.EMPTYFILTER + ROADFILTER;
    public static String WHEAT = "Wheat";
    public static int BEAT = 0;
    public static int DELAY = 100;

    public static boolean isBeating() {
        return BEAT >= DELAY;
    }
}
