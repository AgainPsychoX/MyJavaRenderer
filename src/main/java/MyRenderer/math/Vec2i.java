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

	static Vec2i fromPoints(Vec2i a, Vec2i b) {
		return new Vec2i(a.x - b.x, a.y - b.y);
	}
}
