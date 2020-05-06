import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.*;
public class GameFrame extends JFrame{
	private int height;
	private int width;
	private PlayerComponent comp;
	private Timer timer;
	private GameMap map;
	private JFrame start;
	private JFrame end;
	
	public GameFrame(int h,int w){
		height = h;
		width = w;
		this.setSize(width,height);
		System.out.println("width:" + width);
		System.out.println("height: " + height);
		this.setTitle("game Frame");
		addComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.getContentPane().setBackground(Color.GREEN);
	}
	public void go(){
		timer.start();
	}
	public void addComponents(){
		comp = new PlayerComponent();
		//map = new GameMap("map.txt");
		//this.add(map);
		this.add(comp);

		timer= new Timer(200,new gameActionListener());
		this.addKeyListener( new MyKeyListener());
		this.setFocusable(true);
	}


	// class towerFrame extends JFrame{
	// 	public towerFrame(){
	// 		this.setSize(1000, 1000);
	// 		this.setTitle("inside the tower");
			
	// 	}
	// }

	class gameActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();
		}
	}
	class MyKeyListener implements KeyListener{
		public void keyReleased(KeyEvent e){}
		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){
			//top = 38, left 37, right = 39, bottom = 40
			//System.out.println(e.getKeyCode());
			if(e.getKeyCode() == 38){
				if(comp.player.y >0){
					comp.movePlayer("up");
				}
			}
			else if(e.getKeyCode() == 37 && comp.player.x !=0){
				comp.movePlayer("left");
			}
			
			else if(e.getKeyCode() == 39 && comp.player.x+comp.player.width < 1000){
				comp.movePlayer("right");
			}
			else{
				if(comp.player.y+comp.player.height < 1000){
					comp.movePlayer("down");
				}
			}

			// if(comp.inTower){
			// 	//
			// }
		}
	}
}