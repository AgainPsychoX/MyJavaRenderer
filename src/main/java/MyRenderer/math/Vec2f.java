package MyRenderer.math;

public class Vec2f {
	public float x;
	public float y;

	public float get(int i) {
		return i == 0 ? x : i == 1 ? y : 0;
	}

	@Override
	public String toString() {
		return x + " " + y;
	}

	public static final float EPSILON = 1e-9f;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Vec2f other)
			return Math.abs(this.x - other.x) < EPSILON 
				&& Math.abs(this.y - other.y) < EPSILON
			;
		if (o instanceof Vec2i other)
			return Math.abs(this.x - other.x) < EPSILON 
				&& Math.abs(this.y - other.y) < EPSILON
			;
		return false;
	}

	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static Vec2f fromVec2i(Vec2i i) {
		return new Vec2f(i.x, i.y);
	}
	public Vec2i toVec2i() {
		return new Vec2i(Math.round(x), Math.round(y));
	}

	public static Vec2f fromPoints(Vec2f a, Vec2f b) {
		return new Vec2f(a.x - b.x, a.y - b.y);
	}

	public Vec2f translate(Vec2f other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	public Vec2f transformed(Vec2f other) {
		return new Vec2f(this.x + other.x, this.y + other.y);
	}
}
