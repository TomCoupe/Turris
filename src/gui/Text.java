/**
 * @author Team 62
 * 
 * Oliver Legg - sgolegg - 201244658
 *
 */

package gui;

import engine.io.Font;

public class Text {

	public String text;
	public int x;
	public int y;
	public int size;
	private Font font;
	
	public Text(String text, int x, int y, int size)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		
		// Creating the font        
        try {
			font = new Font("./assets/fonts/SEGOEUI.TTF", size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public Font getFont() {
		return font;
	}

	public void setFont(String fontName, int fontStyle, int size)
	{
		try {
			font = new Font(fontName, size);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void draw()
	{
		font.drawText(text, x, y);
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
