package com.smage.engine.utilities;

import javafx.scene.paint.Paint;

public class Color {
    public static Paint
            RED = Paint.valueOf("#ff0000"), GREEN = Paint.valueOf("#00ff00"), BLUE = Paint.valueOf("#0000ff"), YELLOW = Paint.valueOf("#fff700"),
            ORANGE = Paint.valueOf("#FFA500"), PURPLE = Paint.valueOf("#A020F0"), GOLD = Paint.valueOf("#FFD700"),
            GREY = Paint.valueOf("#808080"), BLACK = Paint.valueOf("#000000"), WHITE = Paint.valueOf("#ffffff");

    public static Paint mixColors(String colorOne, String colorTwo) {
        //Converts all Hexadecimal values into base 10 RGB values for the distance formula
        int colorOneRed = Integer.valueOf(colorOne.substring( 1, 3 ), 16 );
        int colorOneGreen = Integer.valueOf(colorOne.substring( 3, 5 ), 16 );
        int colorOneBlue = Integer.valueOf(colorOne.substring( 5, 7 ), 16 );

        int colorTwoRed = Integer.valueOf(colorTwo.substring( 1, 3 ), 16 );
        int colorTwoGreen = Integer.valueOf(colorTwo.substring( 3, 5 ), 16 );
        int colorTwoBlue = Integer.valueOf(colorTwo.substring( 5, 7 ), 16 );

        //The mixture in between two colors is the difference between the red, green, and blue
        //channels of a color divided by two
        String hex = "#" + decToHex(colorTwoRed + (colorOneRed - colorTwoRed)/2) + decToHex(colorTwoGreen +
                (colorOneGreen - colorTwoGreen)/2) + decToHex(colorTwoBlue + (colorOneBlue - colorTwoBlue)/2);

        return Paint.valueOf(hex);
    }

    public static String decToHex(int number) {
        int hexNumber = 0x0, p = 1;
        while (number != 0) {
            hexNumber = hexNumber + p * (number % 16);
            number = number / 16;
            p = p * 16;
        }
        return Integer.toHexString(hexNumber);
    }
}
