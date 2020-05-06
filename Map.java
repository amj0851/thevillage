import java.awt.Color;
import java.awt.Graphics2D;
public class Map{
	private int clear = 0;
	private int blocked = 1;
	private int npc = 2;

	private int width = 15;
	private int height = 15;
	private int tileSize = 20;

	private int[][] data = new int[width][height];

	public Map(){
		data[0][0] = 1;
		data[1][0] = 1;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = 0;
		data[1][4] = 0;
		data[1][5] = 1;
		data[2][5] = 0;
		data[0][1] = 2;
	}
	public void paint(Graphics2D g) {
		// loop through all the tiles in the map rendering them

		// based on whether they block or not

		for (int x=0;x<width;x++) {
			for (int y=0;y<height;y++) {
				
				// so if the cell is blocks, draw a light grey block

				// otherwise use a dark gray

				g.setColor(Color.darkGray);
				if (data[x][y] == blocked) {
					g.setColor(Color.gray);
				}
				
				// draw the rectangle with a dark outline

				g.fillRect(x*tileSize,y*tileSize,tileSize,tileSize);
				g.setColor(g.getColor().darker());
				g.drawRect(x*tileSize,y*tileSize,tileSize,tileSize);
			}
		}
	}
}