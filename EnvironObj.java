import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class EnvironObj{
	BufferedImage image;
	int x;
	String fn;
	int y;
	int height;
	int width;
	boolean walkable;
	public EnvironObj(String fn, int a, int b, boolean walk){
		this.fn = fn;
		x=a*10;
		y=b*5;
		walkable = walk;
		loadOriginalImage(fn);
	}
	public EnvironObj(String fn, int a, int b){
		x=a*10;
		y=b*5;
		walkable = false;
		loadOriginalImage(fn);
	}

	public void loadOriginalImage(String fileName) {
      	try {
        	 image = ImageIO.read(new File(fileName));
        	 height = image.getHeight();
        	 width = image.getWidth();

        	 //making a hit box
        	 //height += height%10;
        	 //width+=width%10;

      	} catch (IOException e) {
        	 e.printStackTrace();
      	}
   	}

   	public String toString(){
   		String toReturn = "";
   		toReturn += fn = ": (" + x + ", " + y + ")";
   		return toReturn;
   	}

   	public void paint(Graphics g) {
      g.drawImage(image, x, y, null);
  	}

}