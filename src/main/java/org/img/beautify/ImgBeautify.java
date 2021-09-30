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

    public static int[] getRedBase() {
        int r = (int) (Math.random() * 50); //red
        int g = 220 + (int) (Math.random() * ((255 - 220) + 1)); //green
        int b = 180 + (int) (Math.random() * ((255 - 180) + 1)); //blue
        int a = (int) (Math.random() * 255); //alpha
        return new int[]{r, g, b, a};
    }

    public static int[] getGreenBase() {
        int r = 180 + (int) (Math.random() * ((255 - 180) + 1)); //red
        int g = (int) (Math.random() * 50); //green
        int b = 220 + (int) (Math.random() * ((255 - 220) + 1)); //blue
        int a = (int) (Math.random() * 255); //alpha
        return new int[]{r, g, b, a};
    }

    public static int[] getBlueBase() {
        int r = 220 + (int) (Math.random() * ((255 - 220) + 1)); //red
        int g = 180 + (int) (Math.random() * ((255 - 180) + 1)); //green
        int b = (int) (Math.random() * 50); //blue
        int a = (int) (Math.random() * 255); //alpha
        return new int[]{r, g, b, a};
    }

    public static void generateImage(String fileLocation) {
        int width = 1024;
        int height = 768;
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //file object
        File f = null;
        Color c = null;
        //Min + (int)(Math.random() * ((Max - Min) + 1))

        int colorBase = (int) (Math.random() * 3);
        System.out.println("Random Color Base: " + colorBase);
        int rIndex = 0, gIndiex = 0, bIndex = 0;
        int []colorBaseArr;

        if(colorBase == 0) {
            rIndex = 1;
            gIndiex = -1;
            bIndex = -1;
            colorBaseArr = getRedBase();
        }
        else if(colorBase == 1) {
            rIndex = -1;
            gIndiex = 1;
            bIndex = -1;
            colorBaseArr = getGreenBase();
        }
        else {
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
        System.out.println("R: " + r);
        System.out.println("G: " + g);
        System.out.println("B: " + b);
        c = new Color(r, g, b, a);
        int RGB = c.getRGB();
        for (int y = 0; y < height; y+=1) {
            if(y % 1.5 ==0.0) {
                if(colorBase == 0) {
                    if(r < 254) {
                        r = r + rIndex;
                    }
                    if(g > 0) {
                        g = g + gIndiex;
                    }
                    if(b > 0) {
                        b = b + bIndex;
                    }
                }
                else if(colorBase == 1) {
                    if(r > 0) {
                        r = r + rIndex;
                    }
                    if(g < 254) {
                        g = g + gIndiex;
                    }
                    if(b > 0) {
                        b = b + bIndex;
                    }
                }
                else {
                    if(r > 0) {
                        r = r + rIndex;
                    }
                    if(g > 0) {
                        g = g + gIndiex;
                    }
                    if(b < 254) {
                        b = b + bIndex;
                    }
                }
                c = new Color(r, g, b, a);
                RGB = c.getRGB();
            }
            for (int x = 0; x < width; x+=1) {
                img.setRGB(x, y, RGB);
            }
        }

/*        int a1 = (int) (Math.random() * 256); //alpha
        int r1 = (int) (Math.random() * 256); //red
        int g1 = (int) (Math.random() * 256); //green
        int b1 = (int) (Math.random() * 256); //blue
        Color c1 = new Color(r1, g1, b1, a1);
        int RGB = c1.getRGB();
        //create random image pixel by pixel
        for (int y = 0; y < height; y+=(int) (Math.random() * 5)) {
            for (int x = 0; x < width; x+=(int) (Math.random() * 5)) {
                img.setRGB(x, y, new Color(r1, g1, b1, a1).getRGB());
            }
        }*/


        /*for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(x*x + y*y + 102400 - 640*x -320*y == 0) {
                    img.setRGB(Math.round(x), Math.round(y), c.getBlue());
                }
            }
        }*/


        //write image
        try {
            f = new File(fileLocation);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void generateXML(String imageFileLocation, String folderName, String imageName, String xmlFileLocation) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement =
                    doc.createElementNS("http://dv.newbay.com/ns/1.0", "files");
            //append root element to document
            doc.appendChild(rootElement);

            String sha256Checksum = getSHA256Checksum(imageFileLocation);
            int fileSize = ImgBeautify.getFileSize(imageFileLocation);
            //append first child element to root element
            rootElement.appendChild(getFile(doc, "/" + folderName, imageName, String.valueOf(fileSize), sha256Checksum, "image/png"));

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            //StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(xmlFileLocation));

            //write data
            //transformer.transform(source, console);
            transformer.transform(source, file);
            //System.out.println("DONE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getFile(Document doc, String parentPath, String name, String size, String checksum, String systemAttribute) {
        Element file = doc.createElement("file");
        //set id attribute
        //file.setAttribute("id", id);
        //create name element
        file.appendChild(getFileElements(doc, file, "parentPath", parentPath));
        //create age element
        file.appendChild(getFileElements(doc, file, "name", name));
        //create role element
        file.appendChild(getFileElements(doc, file, "size", size));
        //create gender element
        file.appendChild(getFileElements(doc, file, "checksum", checksum));
        file.appendChild(getFileElementsWithAttribute(doc, file, "systemAttribute", systemAttribute, "name", "Mime-Type"));
        return file;
    }


    //utility method to create text node
    private static Node getFileElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    private static Node getFileElementsWithAttribute(Document doc, Element element, String name, String value, String attributeName, String attributeValue) {
        Element node = doc.createElement(name);
        node.setAttribute(attributeName, attributeValue);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static void deletFile(String filePath) {
        try {
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSHA256Checksum(String fileLocation) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(fileLocation);
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        ;
        byte[] mdbytes = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static int getFileSize(String fileLocation) throws IOException, NoSuchAlgorithmException {
        File file = new File(fileLocation);
        int bytes = 0;
        if (file.exists()) {
            bytes = (int) file.length();
        }
        return bytes;
    }
}