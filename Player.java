import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;


public class Player{
	private String name;
	private Color color;
	boolean ready;
	BufferedImage image; //we could animate
	int x;
	int y;
	boolean key;

	int height;
	int width;

	public Player(String fn){
		loadOriginalImage(fn);
		//body = new Rectangle(10, 10, 5, 5);
		x=250;
		y=500;

		height = 110;
		width = 60;
		ready = false;
		key = false;
		System.out.println("width player: " + width + "\n height player: " + height);
	}

	 public BufferedImage getImage() {
      return image;
   }

   public void paint(Graphics g) {
      g.drawImage(image, x, y, null);
   }

   

   public void loadOriginalImage(String fileName) {
      try {
         image = ImageIO.read(new File(fileName));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

	public void move(String key){
		if(key.equals("left")){
			x-=10;
			//repaint();
		}
		if(key.equals("right")){
			//**
			x += 10;
			//repaint();
		}
		if(key.equals("down")){
			y +=10;
			//repaint();
		}
		if(key.equals("up")){
			y -=10;
			//repaint();
		}
	}

	public String touching(EnvironObj a){
		//okay so our player moves in steps of ten, 
		if((x<=a.x+a.width && x>a.x) && ((y<a.y+a.height && y>a.y) || (y+height <a.y+a.height && y+height>a.y))){ //WORKS
			System.out.println("touching left");
			return "left";
		}else if((x+width >= a.x && x+width<a.x + a.width) && ((y<a.y+a.height && y>a.y) || (y+height <a.y+a.height && y+height>a.y))){//and either the top or the bottom y is in between the object's y's{  //works
			return "right";
		}else if((y <= a.y+a.height && y>=a.y) && ((x<a.x+a.width && x>=a.x) || (x+width<a.x+a.width && x+width>a.x))){ //doesnt work
			System.out.println("touching up :(");
			return "up";
		}else if((y+height >= a.y && y+height<=a.y+ a.height) && ((x<a.x+a.width && x>=a.x) || (x+width<a.x+a.width && x+width >a.x))){//works beautifully and perfectly
			return "down";
		}

		//System.out.println("not touching anyone");

		return "none";
	}

	public String touching(NPC a){
		//okay so our player moves in steps of ten, 
		if((x<=a.x+a.width && x>=a.x) && ((y<a.y+a.height && y>a.y) || (y+height <=a.y+a.height && y+height>a.y))){ //WORKS
			System.out.println("touching left");
			return "left";
		}else if((x+width >= a.x && x+width<a.x + a.width) && ((y<a.y+a.height && y>a.y) || (y+height <a.y+a.height && y+height>a.y))){//and either the top or the bottom y is in between the object's y's{  //works
			return "right";
		}else if((y <= a.y+a.height && y>=a.y) && ((x<a.x+a.width && x>=a.x) || (x+width<a.x+a.width && x+width>a.x))){ //doesnt work
			System.out.println("touching up :(");
			return "up";
		}else if((y+height >= a.y && y+height<=a.y+ a.height) && ((x<a.x+a.width && x>=a.x) || (x+width<a.x+a.width && x+width >a.x))){//works beautifully and perfectly
			return "down";
		}

		//System.out.println("not touching anyone");

		return "none";
	}

}
