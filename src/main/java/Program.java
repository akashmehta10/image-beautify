import org.img.beautify.ImgBeautify;

public class Program {
    public static void main(String [] args) {
        for(int i =0; i<10; i++) {
            ImgBeautify.generateImage("/Users/akashmehta/myImg" + i +" .png");
        }

/*        ImgBeautify.generateImage("/Users/akashmehta/myImg2.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg3.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg4.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg5.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg6.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg7.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg8.png");
        ImgBeautify.generateImage("/Users/akashmehta/myImg9.png");*/
        System.out.println("Image Generated");
    }
}
