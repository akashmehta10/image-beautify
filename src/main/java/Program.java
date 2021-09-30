import org.img.beautify.ImgBeautify;

public class Program {
    public static void main(String [] args) {
        for(int i =0; i<10; i++) {
            ImgBeautify.generateImage("/Users/akashmehta/myImg" + i +" .png");
        }
        System.out.println("Image Generated");
    }
}
