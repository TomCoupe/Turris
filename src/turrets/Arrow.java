package turrets;

import enemies.Enemy;
import main.Main;

public class Arrow {

	public float x, y;
	private int damage;
	private boolean destroy = false;
	private int radius = 5;
	private int speed = 5;
	private float xvel;
	private float yvel;
	// Target stats
	private float tx, ty;
	public Arrow(float x, float y, float tx, float ty, int damage) {
		this.x = x;
		this.y = y;
		this.damage = damage;
		
		this.tx = tx;
		this.ty = ty;
    	
		float direction = (float) Math.toDegrees(Math.atan2(ty-y, tx-x));
        
        // Sends the enemy in the correct direction given the right speed
        float direction_x = (float) Math.cos(direction * Math.PI / 180);
        float direction_y = (float) Math.sin(direction * Math.PI / 180);
        
        // Moves the enemy
        xvel = (speed * direction_x);
        yvel = (speed * direction_y);
	}
	
	public void update() {
		if (x < 0 || y < 0 || x > Main.window.getWidth() || y > Main.window.getHeight()) {
			destroy = true;
		}
//		if (Math.abs(x - tx) < (tr+radius) && Math.abs(y - ty) < (tr+radius))
//		{
//			destroy = true;
//		}
        
        x += xvel;
        y += yvel;
		
	}
	
	public void draw() {
		Main.window.setColour(0,0,0,1);
		Main.window.circle(true, x, y, radius, 16);
	}

}
