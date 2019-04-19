package main;

import engine.io.Window;
import playing.Playing;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Main {
	public static Window window;
	
	// Setting the states:
	public static final int MAIN_MENU = 0;
	public static final int PLAYING = 1;
	public static int state = MAIN_MENU;
	
	public static void main(String[] args) {
		
		// Setting up window settings
		int width = 800;              // Screen Width
		int height = 600;             // Screen Height
		int fps = 60;                 // Max Frame Rate
		boolean vsync = false;        // Vsync settings
		String windowName = "Turris"; // Name of the window
		
		// Creates the game window
		window = new Window(width, height, fps, vsync, windowName);
		window.setIcon("TurrisIcon.png");
		window.create();
		
		// Creates the main menu
		Main_menu.create();
		
		
		// While the windows isn't closed print to the screen
		while (!window.closed()) {
			window.clear();  // Clears the previous frame
			window.update(); // Start update
			//System.out.println(window.getDelta());
			double dt = window.getDelta();
			// Organises the updating within the states
			switch (state){
			
				case MAIN_MENU : 
					Main_menu.update(dt);
					Main_menu.draw();
					break;
					
				case PLAYING :
					Playing.update();
					Playing.draw();
					break;
					
			}
			
			// Finish update
			window.swapBuffers();
		}
	}
	
	public static void printMouseCoordsOnClick() {
		if (window.isMousePressed(window.LEFT_MOUSE))
			System.out.println("("+window.getMouseX()
			+", "+window.getMouseY()+")");
	}
}
