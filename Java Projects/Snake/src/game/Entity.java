package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Entity {
	private int x, y, size;
	public Entity(int size) {
		this.size = size;
	}
	
	public int GetX() {return x;}
	public int GetY() {return y;}
	public void SetX(int x) {this.x = x;}
	public void SetY(int y) {this.y = y;}
	public void SetPosition(int x, int y) {
		this.x = x; this.y = y;}
	
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public Rectangle getBound() {
		return new Rectangle(x, y, size, size);
	}
	public boolean isCollsion(Entity o) {
		if (o == this) return false;
		return getBound().intersects(o.getBound());
	}
	
	public void render(Graphics2D g2d) {
		g2d.fillRect(x + 1, y + 1, size - 2, size -2);
	}
}

