import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class NPC{
	BufferedImage image;
	boolean visited;
	int numVisited;
	int x;
	int y;
	String name;
	int height;
	int width;
	ArrayList<ArrayList<String>> dialogues;
	String currMsg;
	int currRow;
	int currC;

	public NPC(String name, String dialogueFn, int a, int b){
		x=a*10;
		y=b*5;
		this.name = name;
		String fn = name + ".png";
		loadOriginalImage(fn);
		dialogues = new ArrayList<ArrayList<String>>();
		dialogues.add(new ArrayList<String>());
		

		readDialogue(dialogueFn);

		currMsg = dialogues.get(0).get(0);
		currRow = 0;
		currC =0;
		//System.out.println("dialogues:" + dialogues.get(0).get(0));
	}


	public void updateNPC(){
		if(visited == false){
			visited = true;
			numVisited++;
		}
		numVisited = currRow+1;
	}

	public void updateMsg(int r, int c){
		currMsg = dialogues.get(r).get(c);
		currRow = r;
		currC = c;
	}

	public void readDialogue(String fn){
		File file = new File(fn);
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
		
			String st = br.readLine();
			int i=0;
			while(st != null){
				String[] line = st.split("//");
				for(String s : line){
					//System.out.println("reading dialogue., adding " + s);
					dialogues.add(new ArrayList<String>());
					dialogues.get(i).add(s);
				}
				i++;
				st= br.readLine();
			}

		}catch(IOException e){
			e.printStackTrace();
		}
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
	
}