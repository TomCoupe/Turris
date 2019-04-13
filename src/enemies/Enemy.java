package enemies;

import gui.Texture;
import main.Main;
import playing.Grid;
import playing.Playing;

import java.util.ArrayList;
import engine.io.Node;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 * Thomas Coupe - sgtcoupe - 
 *
 */
public abstract class Enemy {
	protected int healthpoints;
	protected float x, y, w, h, scale, speed, xvel, yvel, end_time;
	private float time = 90000000;
	protected Texture texture;
	private int t_num = 0; // Texture number
	private int prev_t_num = 0;
	private String path;
	
	// The center of the enemy
	private float cx;
	private float cy;
	
	// Used for path finding
	private static Node head;
	private Node target;
	private float dest_x, dest_y, grid_size;
	public boolean reached = false;
	public float direction;
	public Enemy(String path, float x, float y, float grid_size) {
		this.x = x;
		this.y = y;
		this.speed = 1;
		this.xvel = 0;
		this.yvel = 0;
		this.scale = grid_size / 100;
		this.path = path;
		this.texture = new Texture(path+t_num+".png", x, y, scale, scale);
		this.w = texture.getWidth();
		this.h = texture.getHeight();
		this.end_time = 0;
		this.target = head;
		this.grid_size = grid_size;
		this.direction = (float) Math.toDegrees(Math.atan2(dest_y-cy, dest_x-cx));
	}
	
	public void update(){
        if (target == null) {
    		// Defining the destination positions
    		dest_x = (float) Playing.grid.finish_x + (grid_size / 2);
    		dest_y = (float) Playing.grid.finish_y + (grid_size / 2);
        }
        else {
    		// Defining the destination positions
    		dest_x = (float) (target.x * grid_size)+(grid_size / 2);
    		dest_y = (float) (target.y * grid_size)+(grid_size / 2);
        }
        
		physics();
		
        // Checks the distance from the enemy and the target destination
        double dist = Math.sqrt(Math.pow(dest_x-cx,2) + Math.pow(dest_y-cy,2));
        
        // If the enemy is there, move to the next destination
        if ( dist < 5 && dist > -5) {
        	if (target == null) {
        		reached = true;
        	}
        	else {
            	target = target.next;
        	}
        }
	}
	
	public void physics() {
        // Gets the center of the enemy
		cx = x+(w/2);
		cy = y+(h/2);
		
		float t = (float) Math.toDegrees(Math.atan2(dest_y-cy, dest_x-cx));
    	
		direction = t;
        
        // Sends the enemy in the correct direction given the right speed
        float direction_x = (float) Math.cos(direction * Math.PI / 180);
        float direction_y = (float) Math.sin(direction * Math.PI / 180);
        
        // Moves the enemy
        xvel = (speed * direction_x);
        yvel = (speed * direction_y);
        
        x += xvel;
        y += yvel;
	}

	public void draw() {
		
		// Walking animation
		if (System.nanoTime() > end_time) {
			if (t_num == 0) {
				if (prev_t_num == 1){
					t_num = 2;
					prev_t_num = 2;
				}
				else{
					t_num = 1;
					prev_t_num = 1;
				}
			}
			else{
				t_num = 0;
			}
			
			end_time = System.nanoTime()+time;
			texture.setTexture(path+t_num+".png");
		}
        
        // Sets the rotation of the enemy so they're facing 
        float rotation = (float) Math.atan2( dest_x-cx, dest_y-cy);

        // Moves the enemy
		texture.setX(x);
		texture.setY(y);
		
		// Draw the enemy (at a rotation too)
		texture.draw(-((float) Math.toDegrees(rotation)));
	}
	
	// finds the path for the enemies to go.
	public static void find_path(int starting_x, int starting_y) {
		head = new Node(starting_x, starting_y);
		Node current = head;
		current.next = head;
		Node previous = head;
		while (current.next != null) {
			previous = current;
			current = current.next;
			//System.out.println("("+current.x+", "+ current.y + ")");
			current.next = find_neighbour(previous, current);
		}
	}
	
	// finds the next relevant path
	private static Node find_neighbour(Node previous, Node current) {
		int x = current.x;
		int y = current.y;
		
		String right = Playing.grid.getTile(x+1, y);
		String left  = Playing.grid.getTile(x-1, y);
		String down  = Playing.grid.getTile(x, y+1);
		String up    = Playing.grid.getTile(x, y-1);
		
		// Is the path to the right feasible?
		if ((   right.equals(Grid.ACROSS) || 
				right.equals(Grid.BOTTOM_TO_LEFT) || 
				right.equals(Grid.TOP_TO_LEFT)) && 
				!(previous.x == x+1  && previous.y == y)) {
			return new Node(x+1, y);
		}
		
		// Is the path below feasible?
		if ((   down.equals(Grid.DOWN) || 
				down.equals(Grid.TOP_TO_RIGHT) || 
				down.equals(Grid.TOP_TO_LEFT)) && 
				!(previous.x == x  && previous.y == y+1)) {
			return new Node(x, y+1);
		}
		
		// Is the path above feasible?
		if ((   up.equals(Grid.DOWN) || 
				up.equals(Grid.BOTTOM_TO_RIGHT) || 
				up.equals(Grid.BOTTOM_TO_LEFT)) && 
				!(previous.x == x  && previous.y == y-1)) {
			return new Node(x, y-1);
		}
		
		// Is the path to the left feasible?
		if ((   left.equals(Grid.ACROSS) ||
				left.equals(Grid.BOTTOM_TO_RIGHT) ||
				left.equals(Grid.TOP_TO_RIGHT)) && 
				!(previous.x == x-1  && previous.y == y)) {
			return new Node(x-1, y);
		}
		return null;
	}
}
