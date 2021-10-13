import org.img.beautify.ImgBeautify;

public class Program {
    public static void main(String [] args) {
        //for(int i =0; i<100; i++) {
            //ImgBeautify.generateImageGradient("/Users/akashmehta/myImg" + i +" .png");
        //}


        //int randomPattern = (int) (Math.random() * 3);
        //int randomPattern = 3;
        //System.out.println(randomPattern);
        for(int i =0; i<10; i++) {
            String fileLocation = "/Users/akashmehta/myImg000" + i + ".png";
            int randomPattern = (int) (Math.random() * 7);
            //int randomPattern = 5;
            if (randomPattern == 0) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.HORIZONTALGRADIENTPLAIN);
            } else if (randomPattern == 1) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.HORIZONTALGRADIENTSHAPES);
            } else if (randomPattern == 2) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.HORIZONTALGRADIENTCIRCLES);
            } else if (randomPattern == 3) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.HORIZONTALGRADIENTELLIPSES);
            } else if (randomPattern == 4) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.VERTICALGRADIENTPLAIN);
            } else if (randomPattern == 5) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.GRIDRUSTIC);
            } else if (randomPattern == 6) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.HORIZONRALRUSTIC);
            } else if (randomPattern == 7) {
                ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.VERTICALRUSTIC);
            }
            System.out.println("Image Generated");
        }


    }
}
