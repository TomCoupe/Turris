package main;

import org.lwjgl.glfw.GLFW;
import engine.io.Window;

/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */
public class Main {
	public static Window window;
	
	public static void main(String[] args) {
		// Setting the states:
		final int MAIN_MENU = 0;
		final int PLAYING = 1;
		int state = MAIN_MENU;
		
		// Setting up window settings
		int WIDTH = 800;              // Screen Width
		int HEIGHT = 600;             // Screen Height
		int FPS = 60;                 // Max Frame Rate
		String windowName = "Turris"; // Name of the window
		
		// Creates the game window
		window = new Window(WIDTH, HEIGHT, FPS, windowName);
		window.create();
		
		// Creates the main menu
		Main_menu.create();
		
		// While the windows isn't closed print to the screen
		while (!window.closed()) {
			if (window.isUpdating()) {
				window.clear();  // Clears the previous frame
				window.update(); // Start update
				
				// Organises the updating within the states
				switch (state){
				
					case MAIN_MENU : 
						Main_menu.update();
						Main_menu.draw();
						break;
						
					case PLAYING :

						break;
				}
				printMouseCoordsOnClick();
				
				// Finish update
				window.swapBuffers();
			}
		}
	}
	
	public static void printMouseCoordsOnClick() {
		if (window.isMousePressed(window.LEFT_MOUSE))
			System.out.println("("+window.getMouseX()
			+", "+window.getMouseY()+")");
	}
}
