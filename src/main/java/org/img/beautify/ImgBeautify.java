package org.img.beautify;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

public class ImgBeautify {

    public enum Patterns {
        HORIZONTALGRADIENTPLAIN,
        VERTICALGRADIENTPLAIN,
        HORIZONTALGRADIENTCIRCLES,
        HORIZONTALGRADIENTELLIPSES,
        HORIZONTALGRADIENTSHAPES,
        GRIDRUSTIC,
        HORIZONRALRUSTIC,
        VERTICALRUSTIC
    }


    public static int[] getRedBase() {
        int r = (int) (Math.random() * 100); //red
        int g = 100 + (int) (Math.random() * ((255 - 100) + 1)); //green
        int b = 100 + (int) (Math.random() * ((255 - 100) + 1)); //blue
        int a = (int) (Math.random() * 255); //alpha
        return new int[]{r, g, b, a};
    }

    public static int[] getGreenBase() {
        int r = 100 + (int) (Math.random() * ((255 - 100) + 1)); //red
        int g = (int) (Math.random() * 100); //green
        int b = 100 + (int) (Math.random() * ((255 - 100) + 1)); //blue
        int a = (int) (Math.random() * 255); //alpha
        return new int[]{r, g, b, a};
    }

    public static int[] getBlueBase() {
        int r = 100 + (int) (Math.random() * ((255 - 100) + 1)); //red
        int g = 100 + (int) (Math.random() * ((255 - 100) + 1)); //green
        int b = (int) (Math.random() * 100); //blue
        int a = (int) (Math.random() * 255); //alpha
        return new int[]{r, g, b, a};
    }

    public static void generateHorizontalGradient(String fileLocation, Patterns pattern) {
        int width = 1024;
        int height = 768;
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        Color c = null;
        //Min + (int)(Math.random() * ((Max - Min) + 1))

        int colorBase = (int) (Math.random() * 3);
        //System.out.println("Random Color Base: " + colorBase);
        int rIndex = 0, gIndiex = 0, bIndex = 0;
        int[] colorBaseArr;

        if (colorBase == 0) {
            rIndex = 1;
            gIndiex = -1;
            bIndex = -1;
            colorBaseArr = getRedBase();
        } else if (colorBase == 1) {
            rIndex = -1;
            gIndiex = 1;
            bIndex = -1;
            colorBaseArr = getGreenBase();
        } else {
            rIndex = -1;
            gIndiex = -1;
            bIndex = 1;
            colorBaseArr = getBlueBase();
        }

        //int []redBaseColors = getRedBase();
        int r = colorBaseArr[0];
        int g = colorBaseArr[1];
        int b = colorBaseArr[2];
        int a = colorBaseArr[3];
        //System.out.println("R: " + r);
        //System.out.println("G: " + g);
        //System.out.println("B: " + b);
        c = new Color(r, g, b, a);
        int RGB = c.getRGB();
        for (int y = 0; y < height; y += 1) {
            if (y % 1.5 == 0.0) {
                if (colorBase == 0) {
                    if (r < 254) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else if (colorBase == 1) {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g < 254) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b < 254) {
                        b = b + bIndex;
                    }
                }
                c = new Color(r, g, b, a);
                RGB = c.getRGB();
            }
            for (int x = 0; x < width; x += 1) {
                img.setRGB(x, y, RGB);
            }
        }
        //System.out.println(Patterns.GRADIENTSHAPES.toString());
        //System.out.println(pattern.toString());
        if (pattern.equals(Patterns.HORIZONTALGRADIENTSHAPES)) {
            System.out.println(pattern.toString());
            int randomShapesCount = 0;
            while (randomShapesCount < 300) {
                insertRandomCircle(img);
                insertRandomEllipse(img);
                randomShapesCount++;
            }
        }

        else if (pattern.equals(Patterns.HORIZONTALGRADIENTCIRCLES)) {
            System.out.println(pattern.toString());
            int randomShapesCount = 0;
            while (randomShapesCount < 600) {
                insertRandomCircle(img);
                randomShapesCount++;
            }
        }

        else if (pattern.equals(Patterns.HORIZONTALGRADIENTELLIPSES)) {
            System.out.println(pattern.toString());
            int randomShapesCount = 0;
            while (randomShapesCount < 600) {
                insertRandomEllipse(img);
                randomShapesCount++;
            }
        }

        //write image
        try {
            f = new File(fileLocation);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void generateVerticalGradient(String fileLocation) {
        int width = 1024;
        int height = 768;
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        Color c = null;
        //Min + (int)(Math.random() * ((Max - Min) + 1))

        int colorBase = (int) (Math.random() * 3);
        //System.out.println("Random Color Base: " + colorBase);
        int rIndex = 0, gIndiex = 0, bIndex = 0;
        int[] colorBaseArr;

        if (colorBase == 0) {
            rIndex = 1;
            gIndiex = -1;
            bIndex = -1;
            colorBaseArr = getRedBase();
        } else if (colorBase == 1) {
            rIndex = -1;
            gIndiex = 1;
            bIndex = -1;
            colorBaseArr = getGreenBase();
        } else {
            rIndex = -1;
            gIndiex = -1;
            bIndex = 1;
            colorBaseArr = getBlueBase();
        }

        //int []redBaseColors = getRedBase();
        int r = colorBaseArr[0];
        int g = colorBaseArr[1];
        int b = colorBaseArr[2];
        int a = colorBaseArr[3];
        //System.out.println("R: " + r);
        //System.out.println("G: " + g);
        //System.out.println("B: " + b);
        c = new Color(r, g, b, a);
        int RGB = c.getRGB();
        for (int x = 0; x < width; x += 1) {
            if (x % 1.5 == 0.0) {
                if (colorBase == 0) {
                    if (r < 254) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else if (colorBase == 1) {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g < 254) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b < 254) {
                        b = b + bIndex;
                    }
                }
                c = new Color(r, g, b, a);
                RGB = c.getRGB();
            }
            for (int y = height - 1; y >= 0; y--) {
                img.setRGB(x, y, RGB);
            }
        }
        //write image
        try {
            f = new File(fileLocation);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    public static void insertRandomCircle(BufferedImage img) {
        int width = 1024;
        int height = 768;
        // c1
        int c1 = 0 + (int) (Math.random() * ((1024 - 0) + 1));
        // c2
        int c2 = 0 + (int) (Math.random() * ((768 - 0) + 1));
        // r
        int radius = 50 + (int) (Math.random() * ((900 - 50) + 1));


        int colorBase = (int) (Math.random() * 3);
        //System.out.println("Random Color Base: " + colorBase);
        int rIndex = 0, gIndiex = 0, bIndex = 0;
        int[] colorBaseArr;

        if (colorBase == 0) {
            rIndex = 1;
            gIndiex = -1;
            bIndex = -1;
            colorBaseArr = getRedBase();
        } else if (colorBase == 1) {
            rIndex = -1;
            gIndiex = 1;
            bIndex = -1;
            colorBaseArr = getGreenBase();
        } else {
            rIndex = -1;
            gIndiex = -1;
            bIndex = 1;
            colorBaseArr = getBlueBase();
        }

        //int []redBaseColors = getRedBase();
        int r = colorBaseArr[0];
        int g = colorBaseArr[1];
        int b = colorBaseArr[2];
        int a = colorBaseArr[3];
        Color c = new Color(r, g, b, a);
        int RGB = c.getRGB();
        for (int y = 0; y < height; y += 1) {
            if (y % 1.5 == 0.0) {
                if (colorBase == 0) {
                    if (r < 254) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else if (colorBase == 1) {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g < 254) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b < 254) {
                        b = b + bIndex;
                    }
                }
                c = new Color(r, g, b, a);
                RGB = c.getRGB();
            }
            for (int x = 0; x < width; x += 1) {
                if (Math.pow((x - c1), 2) + Math.pow((y - c2), 2) - radius < 0) {
                    img.setRGB(Math.round(x), Math.round(y), RGB);
                }
                //img.setRGB(x, y, RGB);
            }
        }
    }

    public static void insertRandomEllipse(BufferedImage img) {
        int width = 1024;
        int height = 768;
        // c1
        int h = 0 + (int) (Math.random() * ((1024 - 0) + 1));
        // c2
        int k = 0 + (int) (Math.random() * ((768 - 0) + 1));
        // r
        int asq = 450 + (int) (Math.random() * ((900 - 450) + 1));
        int bsq = 1 + (int) (Math.random() * ((449 - 1) + 1));

        int colorBase = (int) (Math.random() * 3);
        //System.out.println("Random Color Base: " + colorBase);
        int rIndex = 0, gIndiex = 0, bIndex = 0;
        int[] colorBaseArr;

        if (colorBase == 0) {
            rIndex = 1;
            gIndiex = -1;
            bIndex = -1;
            colorBaseArr = getRedBase();
        } else if (colorBase == 1) {
            rIndex = -1;
            gIndiex = 1;
            bIndex = -1;
            colorBaseArr = getGreenBase();
        } else {
            rIndex = -1;
            gIndiex = -1;
            bIndex = 1;
            colorBaseArr = getBlueBase();
        }

        //int []redBaseColors = getRedBase();
        int r = colorBaseArr[0];
        int g = colorBaseArr[1];
        int b = colorBaseArr[2];
        int a = colorBaseArr[3];
        Color c = new Color(r, g, b, a);
        int RGB = c.getRGB();
        for (int y = 0; y < height; y += 1) {
            if (y % 1.5 == 0.0) {
                if (colorBase == 0) {
                    if (r < 254) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else if (colorBase == 1) {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g < 254) {
                        g = g + gIndiex;
                    }
                    if (b > 0) {
                        b = b + bIndex;
                    }
                } else {
                    if (r > 0) {
                        r = r + rIndex;
                    }
                    if (g > 0) {
                        g = g + gIndiex;
                    }
                    if (b < 254) {
                        b = b + bIndex;
                    }
                }
                c = new Color(r, g, b, a);
                RGB = c.getRGB();
            }
            for (int x = 0; x < width; x += 1) {
                if ((Math.pow((x - h), 2))/b + (Math.pow((y - k), 2))/a - 1 < 0) {
                    img.setRGB(Math.round(x), Math.round(y), RGB);
                }
                //img.setRGB(x, y, RGB);
            }
        }
    }

    public static void generateGridRusticImage(String fileLocation) {
        int width = 1024;
        int height = 768;
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        Color c = null;
        //Min + (int)(Math.random() * ((Max - Min) + 1))
        int a1 = (int) (Math.random() * 256); //alpha
        int r1 = (int) (Math.random() * 127); //red
        int g1 = (int) (Math.random() * 127); //green
        int b1 = (int) (Math.random() * 127); //blue
        Color c1 = new Color(r1, g1, b1, a1);
        int RGB = c1.getRGB();
        //create random image pixel by pixel
        for (int y = 0; y < height; y += (int) (Math.random() * 3)) {
            for (int x = 0; x < width; x += (int) (Math.random() * 3)) {
                img.setRGB(x, y, new Color(r1, g1, b1, a1).getRGB());
            }
        }
        a1 = (int) (Math.random() * 256); //alpha
        r1 = 128 + (int) (Math.random() * ((255 - 128) + 1)); //red
        g1 = 128 + (int) (Math.random() * ((255 - 128) + 1)); //green
        b1 = 128 + (int) (Math.random() * ((255 - 128) + 1)); //blue

        for (int x = 0; x < width; x += (int) (Math.random() * 3)) {
            for (int y = height - 1; y >= 0; y -= (int) (Math.random() * 3)) {
                img.setRGB(x, y, new Color(r1, g1, b1, a1).getRGB());
            }

        }

        //write image
        try {
            f = new File(fileLocation);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void generateVerticalRusticImage(String fileLocation) {
        int width = 1024;
        int height = 768;
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        Color c = null;
        //Min + (int)(Math.random() * ((Max - Min) + 1))
        int a1 = (int) (Math.random() * 256); //alpha
        int r1 = (int) (Math.random() * 127); //red
        int g1 = (int) (Math.random() * 127); //green
        int b1 = (int) (Math.random() * 127); //blue
        Color c1 = new Color(r1, g1, b1, a1);
        int RGB = c1.getRGB();
        //create random image pixel by pixel
        for (int x = 0; x < width; x += (int) (Math.random() * 3)) {
            for (int y = height - 1; y >= 0; y -= (int) (Math.random() * 3)) {
                img.setRGB(x, y, new Color(r1, g1, b1, a1).getRGB());
            }
        }
        //write image
        try {
            f = new File(fileLocation);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void generateHorizontalRusticImage(String fileLocation) {
        int width = 1024;
        int height = 768;
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        Color c = null;
        //Min + (int)(Math.random() * ((Max - Min) + 1))
        int a1 = (int) (Math.random() * 256); //alpha
        int r1 = (int) (Math.random() * 127); //red
        int g1 = (int) (Math.random() * 127); //green
        int b1 = (int) (Math.random() * 127); //blue
        Color c1 = new Color(r1, g1, b1, a1);
        int RGB = c1.getRGB();
        //create random image pixel by pixel
        for (int y = 0; y < height; y += (int) (Math.random() * 3)) {
            for (int x = 0; x < width; x += (int) (Math.random() * 3)) {
                img.setRGB(x, y, new Color(r1, g1, b1, a1).getRGB());
            }
        }
        //write image
        try {
            f = new File(fileLocation);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void generateImage(String fileLocation, Patterns pattern) {
        if (pattern.equals(Patterns.HORIZONTALGRADIENTPLAIN) || pattern.equals(Patterns.HORIZONTALGRADIENTSHAPES) ||
        pattern.equals(Patterns.HORIZONTALGRADIENTCIRCLES) || pattern.equals(Patterns.HORIZONTALGRADIENTELLIPSES)) {
            generateHorizontalGradient(fileLocation, pattern);
        } else if (pattern.equals(Patterns.GRIDRUSTIC)) {
            generateGridRusticImage(fileLocation);
        } else if (pattern.equals(Patterns.VERTICALGRADIENTPLAIN)) {
            generateVerticalGradient(fileLocation);
        } else if (pattern.equals(Patterns.HORIZONRALRUSTIC)) {
            generateHorizontalRusticImage(fileLocation);
        } else if (pattern.equals(Patterns.VERTICALRUSTIC)) {
            generateVerticalRusticImage(fileLocation);
        }
    }
}