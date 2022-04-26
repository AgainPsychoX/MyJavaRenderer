package MyRenderer.math;

public class Vec2i {
	public int x;
	public int y;

	@Override
	public String toString() {
		return x + " " + y;
	}

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Vec2i fromPoints(Vec2i a, Vec2i b) {
		return new Vec2i(a.x - b.x, a.y - b.y);
	}

	public Vec2i translate(Vec2i other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	public Vec2i transformed(Vec2i other) {
		return new Vec2i(this.x + other.x, this.y + other.y);
	}
}
