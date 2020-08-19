/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class TimeManager {

    public static int hour = 0;
    public static int minute;
    private static Color colorDay = null;
    private static Color colorDayTexture = null;
    private static Color expected = Color.BLACK;
    private static Color expectedTexture = Color.WHITE;
    public final static ArrayList<ColorDay> colors = new ArrayList<ColorDay>();
    public final static ArrayList<ColorDay> colorsTexture = new ArrayList<ColorDay>();
    public final static ArrayList<Day> days = new ArrayList<Day>();
    public static Day currentDay;

    static {

        days.add(new Day("Monday", 0));
        days.add(new Day("Tuesday", 1));
        days.add(new Day("Wednesday", 2));
        days.add(new Day("Thursday", 3));
        days.add(new Day("Friday", 4));
        days.add(new Day("Saturday", 5));
        days.add(new Day("Sunday", 6));

        colors.add(new ColorDay(Color.valueOf("06363f"), 2, 0));
        colors.add(new ColorDay(Color.valueOf("0b1544"), 3, 0));
        colors.add(new ColorDay(Color.valueOf("0e1d63"), 4, 0));
        colors.add(new ColorDay(Color.valueOf("86c4d8"), 5, 0));
        colors.add(new ColorDay(Color.valueOf("d5edf2"), 6, 0));
        colors.add(new ColorDay(Color.WHITE, 7, 0));
        colors.add(new ColorDay(Color.valueOf("f2ad71"), 18, 0));
        colors.add(new ColorDay(Color.valueOf("c0392b"), 19, 30));
        colors.add(new ColorDay(Color.valueOf("104566"), 20, 0));
        colors.add(new ColorDay(Color.valueOf("34495e"), 21, 0));
        colors.add(new ColorDay(Color.BLACK, 23, 0));

        colorsTexture.add(new ColorDay(Color.valueOf("b1d0d6"), 2, 0));
        colorsTexture.add(new ColorDay(Color.valueOf("919bc6"), 3, 0));
        colorsTexture.add(new ColorDay(Color.valueOf("848cb2"), 4, 0));
        colorsTexture.add(new ColorDay(Color.BLACK, 5, 0));
        colorsTexture.add(new ColorDay(Color.BLACK, 6, 0));
        colorsTexture.add(new ColorDay(Color.BLACK, 7, 0));
        colorsTexture.add(new ColorDay(Color.WHITE, 23, 0));
    }

    public static void step() {
        minute += 10;
        if (minute == 60) {
            minute = 0;
            hour += 1;
        }
        if (hour == 24) {
            hour = 0;
            nextDay();
        }
    }

    public static Color getTimeColor() {
        if (colorDay == null) {
            colorDay = getCurrentFromHour();
        }

        getColor();

        colorDay.r += colorDay.r > expected.r ? (-0.001f) : colorDay.r < expected.r ? 0.001f : 0.00f;
        colorDay.g += colorDay.g > expected.g ? (-0.001f) : colorDay.g < expected.g ? 0.001f : 0.00f;
        colorDay.b += colorDay.b > expected.b ? (-0.001f) : colorDay.b < expected.b ? 0.001f : 0.00f;
        return colorDay;
    }

    private static Color getCurrentFromHour() {
        for (ColorDay color : colors) {
            if (hour > color.hour || hour == color.hour) {
                return color.color;
            }
        }
        return colors.get(0).color;
    }

    private static Color getCurrentFromHourTexture() {
        for (ColorDay color : colorsTexture) {
            if (hour > color.hour || hour == color.hour) {
                return color.color;
            }
        }
        return colorsTexture.get(0).color;
    }

    private static void getColor() {

        for (ColorDay color : colors) {
            if (color.hour == hour && color.minute == minute) {
                expected = color.color;
            }
        }
    }

    private static void getColorTexture() {

        for (ColorDay color : colorsTexture) {
            if (color.hour == hour && color.minute == minute) {
                expectedTexture = color.color;
            }
        }

    }

    public static Color getTimeTextureColor() {
        if (colorDayTexture == null) {
            colorDayTexture = getCurrentFromHourTexture();
        }

        getColorTexture();

        colorDayTexture.r += colorDayTexture.r > expectedTexture.r ? (-0.00005f) : colorDayTexture.r < expectedTexture.r ? 0.00005f : 0.0f;
        colorDayTexture.g += colorDayTexture.g > expectedTexture.g ? (-0.00005f) : colorDayTexture.g < expectedTexture.g ? 0.00005f : 0.0f;
        colorDayTexture.b += colorDayTexture.b > expectedTexture.b ? (-0.00005f) : colorDayTexture.b < expectedTexture.b ? 0.00005f : 0.0f;
        return colorDayTexture;
    }

    private static void nextDay() {
        getCurrentDay();
        currentDay = currentDay.index == days.size() - 1 ? days.get(0) : days.get(currentDay.index + 1);
    }

    public static Day getCurrentDay() {
        if (currentDay == null) {
            currentDay = days.get(0);
        }
        return currentDay;
    }

    public static class Day {

        public String name;
        public int index;

        public Day(String name, int index) {
            this.name = name;
            this.index = index;
        }

    }

    private static class ColorDay {

        Color color;
        int hour, minute;

        public ColorDay(Color color, int hour, int minute) {
            this.color = color;
            this.hour = hour;
            this.minute = minute;
        }
    }
}
