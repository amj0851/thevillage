import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.io.*;
//import 

public class GameMap extends JComponent{
	//how we gonna draw a tree 
	private ArrayList<ArrayList<String>> map;
	private ArrayList<ArrayList<EnvironObj>> mapObj;
	private ArrayList<EnvironObj> objectList;
	private ArrayList<EnvironObj> walkables;
	private EnvironObj temp;
	private NPC np;
	NPC witch;
	ArrayList<NPC> characters;

	public GameMap(String fn){
		//read in a text file, put it all in the array
		map = new ArrayList<ArrayList<String>>();
		mapObj = new ArrayList<ArrayList<EnvironObj>>();
		objectList = new ArrayList<EnvironObj>();
		walkables = new ArrayList<EnvironObj>();
		characters = new ArrayList<NPC>();
		witch = new NPC("witchgirl", "witchgirl.txt", 50, 10);

		
			ReadInFile(fn);		
			makeMap();
	}
	public NPC getNPC(String name){
		for(NPC n : characters){
			if(n.name.equals(name)){
				return n;
			}
		}

		return null;
	}

	public void ReadInFile(String fn){
		//System.out.println(fn);
		File file = new File(fn);
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
		
			String st = br.readLine();
			while(st != null){
				//System.out.println(st);
				String[] line = st.split(",");
				map.add(makeList(line));
				//System.out.println("map so far: " + map);
				st= br.readLine();
			}

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//okay now read it in 
	public void makeMap(){
		int m =1;
		//EnvironObj temp = new EnvironObj(null, 0, 0);
		for(int i=0; i<map.size(); i++){
			for(int j =0; j<map.get(i).size(); j++){
				String o = map.get(i).get(j);
				//System.out.println("grabbing o from map, o = " + o);
				if(o.equals("t")){
					temp = new EnvironObj("treee.png", j+m, i*20);
					objectList.add(temp);
					m+=10;
					//System.out.println("objectList: " + objectList);
				}else if(o.equals("p")){
					temp = new EnvironObj("path.png", j+m, i*20, true);
					//dont need to add to obj list bc its always in back
					walkables.add(temp);

				}else if(o.equals("h")){
					temp = new EnvironObj("house.png", j+m, i*20);
					objectList.add(temp);
				}else if(o.equals("g")){
					m+=10;
				}
				else if(!o.equals("g")){
					String fn = o +".txt";
					System.out.println("filename for NPC: " + fn);
					np = new NPC(o, fn, j+m, i*20);
					m+=10;
					characters.add(np);
				}
			}
			m=0;
		}
	}

	public ArrayList<String> makeList(String[] a){
		ArrayList<String> toReturn = new ArrayList<String>();
		for(String s : a){
			toReturn.add(s);
		}
		return toReturn;

	}

	public ArrayList<ArrayList<EnvironObj>> getObjects(){
		return mapObj;
	}

	public ArrayList<EnvironObj> getOrder(){
		return objectList;
	}

	public ArrayList<EnvironObj> getWalkables(){
		return walkables;
	}

}