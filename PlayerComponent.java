import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
public class PlayerComponent extends JComponent{
	Player player;
	private GameMap map;
	NPC door;
	boolean inTower = false;//change this later bro
	boolean done = false;
	//private Food food;
	private String keyDirection;
	static int RIGHT = 1;
	static int UP = 2;
	static int LEFT = 3;
	static int DOWN = 4;
	PopupFactory pf;

  

	public PlayerComponent() {
		player = new Player("playerr1.png");
		map = new GameMap("map.txt");
		pf = new PopupFactory();
		door = new NPC("door", "door.txt", 80, 40);
	}

	

	public void movePlayer(String a){
		keyDirection = a;
		boolean blocked = false;
		ArrayList<EnvironObj> blocking = touching();

		for(EnvironObj o : blocking){
			if(player.touching(o).equals(keyDirection)){
				//System.out.println("player is touching an object, cant move " + keyDirection);
				blocked = true;	
			}
		}

		if(!blocked || inTower){
			player.move(keyDirection);
			repaint();
		}
		NPC touching = touchNPC();
		if(touching != null && (!inTower || touching.name.equals("witchgirl"))){
			if(player.touching(touching).equals(keyDirection)){
				blocked = true;
			}
			updateDialogue(touching);
			Pop p = new Pop(touching);
			touching.updateNPC();
			//updateDialogue(touching);
			System.out.println("dialogue updated");
			//touching.updateNPC();
		}
	}

	public void updateDialogue(NPC n){
		//ArrayList<NPC> ch = map.characters;
		NPC woofy = map.getNPC("Woofy");
		NPC taffy = map.getNPC("Taffy");
		NPC leo = map.getNPC("leo");
		NPC prof = map.getNPC("ProffessorFeathers");
		NPC tex = map.getNPC("Tex");
		NPC lilly = map.getNPC("lilly");
		NPC tower = map.getNPC("tower");
		NPC witch = map.witch;
		//NPC door = door;
		String name = n.name;
		if(name.equals("Taffy")){
			if(woofy.visited && woofy.numVisited<2){ // phase one
				taffy.updateMsg(1, 0);
				taffy.updateNPC();
			}
			if(woofy.numVisited == 2){
				taffy.updateMsg(2,0);
				taffy.updateNPC();
			}
			if(witch.numVisited == 4){
				taffy.updateMsg(3,0);
			}
		}
		if(name.equals("leo")){
			if(taffy.numVisited == 2 && woofy.numVisited==1){
				leo.updateMsg(1,0);
				leo.updateNPC();
				//n.numVisited++;
			}
			if(woofy.numVisited == 2){
				if(woofy.currC == 2){
					leo.updateMsg(3,0);
				}else{
					leo.updateMsg(2,0);
				}
			}
			if(lilly.numVisited == 4){
				leo.updateMsg(4,0);
			}
		}
		if(name.equals("Woofy")){
			if(leo.numVisited == 2){
				woofy.updateMsg(1,0);
				woofy.updateNPC();
			}
		}
		if(name.equals("ProffessorFeathers")){
			if(woofy.numVisited == 2){
				prof.updateMsg(4, 0);
				player.ready = true;
			}
			if(taffy.numVisited == 4){
				prof.updateMsg(7,0);
			}
		}
		if(name.equals("Tex")){
			if(woofy.numVisited == 1 && leo.numVisited<2){
				tex.updateMsg(1, 0);
			}
			if(prof.numVisited == 1){
				tex.updateMsg(2, 0);
			}
			if(player.ready){
				tex.updateMsg(3,0);
			}
			//when to go to tower

		}
		if(name.equals("lilly")){
			System.out.println("tex visited: " + tex.numVisited);
			if(lilly.numVisited == 2){
				lilly.updateMsg(1,0);
				System.out.println("updatde lilly to beetle talk");
			}
			if(tex.numVisited == 4){
				System.out.println("tex num visited is 4");
				lilly.updateMsg(2,0);
			}
			if(tower.numVisited == 2){
				lilly.updateMsg(3,0);
			}
		}
		if(name.equals("tower")){
			if(player.ready){
				tower.updateMsg(1,0);
				System.out.println(tower.numVisited);
			}
			if(leo.numVisited == 5 && leo.currC == 1){
				inTower = true; 
				tower.updateMsg(2,0);//go to game frame now? or make a nother class in PC
				//Pop p = new Pop(tower, witch);
				// TowerFrame t = new TowerFrame(witch);
				// t.setVisible(true);
			}
		}
		if(name.equals("witchgirl") && witch.numVisited !=0){
			if(witch.numVisited == 4 && witch.currC == 1){
				if(prof.numVisited == 8){
					witch.updateMsg(4,0);
				}else{
						inTower = false;
						player.x = 800;
						player.y = 450;

				}
			}else{
				System.out.println("currRow: " + witch.currRow + " currMsg: " + witch.currMsg);
				witch.updateMsg(witch.currRow+1, 0);
				System.out.println("currRow: " + witch.currRow + " currMsg: " + witch.currMsg);
			}

			if(witch.currRow == 6){
				//go home
				done = true;
			}

			
		}
		
	}



	public class Pop extends JFrame implements ActionListener, KeyListener{
		NPC npc;
		JLabel l;
		Popup p;
		JPanel p2;
		JPanel p3;
		JFrame dialogueF;
		JButton b;
		JButton b2;
		int owl;
		//NPC witch;
		//NPC tower;
		public Pop(NPC t){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			npc = t;
			owl =t.currRow;
			dialogueF = new JFrame(t.name);
			dialogueF.setSize(600, 100);
			dialogueF.setLocation(100,100);
			l = new JLabel(t.currMsg);
			BufferedImage pic = t.image;
			p2 = new JPanel();
			p2.add(l);
			//p2.add(pic);

			p = pf.getPopup(dialogueF, p2, 100, 180);

			dialogueF.show();
			p.show();

			b = new JButton("yes");
			//b.setBounds(90,50,30,30);
			b.addActionListener(this);
			b2 = new JButton("no");
			//b2.setBounds(100,50,20,20);
			b2.addActionListener(this);

			dialogueF.setLayout(new GridLayout(1,3));
			dialogueF.add(b);
			dialogueF.add(b2);
		
		}
		//put this in a new class >

		// public Pop(NPC t, NPC a){
		// 	//so basically we are inside the tower
		// 	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 	dialogueF = new JFrame("Inside the Tower");
		// 	dialogueF.setSize(1000, 1000);
		// 	dialogueF.setLocation(0,0);
		// 	this.getContentPane().setBackground(Color.GRAY);
		// 	p2 = new JPanel();
		// 	p2.add(new JLabel(a.currMsg));
		// 	witch = a;

		// 	p=pf.getPopup(dialogueF, p2, 100, 180);

		// 	dialogueF.show();
		// 	p.show();

		// 	b = new JButton("yes");
		// 	//b.setBounds(90,50,30,30);
		// 	b.addActionListener(this);
		// 	b2 = new JButton("no");
		// 	//b2.setBounds(100,50,20,20);
		// 	b2.addActionListener(this);

		// 	dialogueF.add(b);
		// 	dialogueF.add(b2);

		// }

		public void actionPerformed(ActionEvent e){
			boolean proffessor = false;
			if(npc.name.equals("ProffessorFeathers") && owl <7){
				proffessor = true;
			}
			if(e.getSource() == b){
				dialogueF.remove(b2);
				if(proffessor){
					System.out.println("owl: " + owl);
					int j = 0;
					if(owl==0){
						j=1;
					}else{
						j=0;
					}
					System.out.println(player.ready);
					if(owl != 4 || player.ready==true){
						if(owl == 0 || owl==4){
							j=1;
						}
						//System.out.println("owl: " + owl);
						System.out.println("j is: "+ j);
						npc.updateMsg(owl,j);
						if(owl !=6){
							owl++;
						}
						//System.out.println("just updated owl to be:" + owl);
					}
					//if(owl == 7)
				}else if(!proffessor){
					System.out.println("not professor");
					npc.updateMsg(npc.currRow, 1);
				}
				
				l = new JLabel(npc.currMsg);
				JPanel a = new JPanel();
				a.add(l);
				p.hide();
				Popup oh = pf.getPopup(dialogueF, a, 100, 180);
				
				oh.show();


			}else if(e.getSource() == b2){
				if(proffessor && owl>=0 && owl<4){
					npc.updateMsg(3,1);
				}else if(proffessor && owl>=4){
					npc.updateMsg(npc.currRow, 2);
				}else{
					npc.updateMsg(npc.currRow, 2);
				}
				l = new JLabel(npc.currMsg);
				dialogueF.remove(b);
				//l = new JLabel(npc.currMsg);
				JPanel a = new JPanel();
				a.add(l);
				p.hide();
				Popup oh = pf.getPopup(dialogueF,a, 100, 180);
				oh.show();
			}else{
				p.hide();
				dialogueF.hide();
			}
		}

		public void keyReleased(KeyEvent e){}
		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){
			p.hide();
			dialogueF.hide();

		}



	}



	public ArrayList<EnvironObj> touching(){
		ArrayList<EnvironObj> toReturn = new ArrayList<EnvironObj>();
		ArrayList<EnvironObj> list = map.getOrder();
		//System.out.println("unwalkable objects: " + list);
		for(EnvironObj object : list){
			//System.out.println("checking this object: " + object); //ok so it checks both
			if(!player.touching(object).equals("none")){
				//System.out.println("adding " + object +" to touching list bc they touching : " + player.touching(object));
				toReturn.add(object);
			}
		}

		return toReturn;
	}
	
	public NPC touchNPC(){
		ArrayList<NPC> npcs = map.characters;
		for(NPC n : npcs){
			if(!player.touching(n).equals("none")){
				return n;
			}
		}
		
		if(!player.touching(map.witch).equals("none")){
			return map.witch;
		}
		return null;
	}

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		//so the lowest thing has to come first

		//draw paths/ground objects here
		if(!inTower){
			ArrayList<EnvironObj> ground = map.getWalkables();
			for(int i=0; i<ground.size(); i++){
				EnvironObj temp = ground.get(i);
				g2.drawImage(temp.image, temp.x, temp.y, null);
			}

			//drawing other environ objects
			ArrayList<EnvironObj> objectList = map.getOrder();
			
			for(int i=0; i<objectList.size(); i++){
				//System.out.println("im in my drawing loop");
				EnvironObj temp = objectList.get(i);
				g2.drawImage(temp.image, temp.x, temp.y, null);
				//System.out.println("drew a tree"); 
			}
			//NPCs go here

			ArrayList<NPC> npc = map.characters;
			for(NPC a : npc){
				g2.drawImage(a.image, a.x, a.y, null);
			}
		}else{
			try{
				g2.drawImage(ImageIO.read(new File("intower.png")), 100, 0, null);
				g2.drawImage(map.witch.image, map.witch.x, map.witch.y, null);
				
			}catch (IOException e){
				e.printStackTrace();
			}
			
		}
		//player has to come last
		g2.drawImage(player.image, player.x, player.y, null);


		if(done){
			//end screen
			try{
				g2.drawImage(ImageIO.read(new File("end.png")), 0,0, null);
			}catch( IOException e){
				e.printStackTrace();
			}
			
		}
	} 
}