import org.img.beautify.ImgBeautify;

public class Program {
    public static void main(String [] args) {
        //for(int i =0; i<10; i++) {
        //    ImgBeautify.generateImageGradient("/Users/akashmehta/myImg" + i +" .png");
        //}
        String fileLocation = "/Users/akashmehta/myImg0.png";

        //int randomPattern = (int) (Math.random() * 3);
        int randomPattern = 2;
        System.out.println(randomPattern);

        if(randomPattern == 0) {
            ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.GRADIENTPLAIN);
        }
        else if(randomPattern == 1) {
            ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.GRADIENTSHAPES);
        }
        else {
            ImgBeautify.generateImage(fileLocation, ImgBeautify.Patterns.RUSTIC);
        }

        System.out.println("Image Generated");
    }
}
