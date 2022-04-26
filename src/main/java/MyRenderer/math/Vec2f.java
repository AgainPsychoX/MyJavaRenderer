package MyRenderer.math;

public class Vec2f {
	public float x;
	public float y;

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

	static Vec2f fromVec2i(Vec2i i) {
		return new Vec2f(i.x, i.y);
	}

	static Vec2f fromPoints(Vec2f a, Vec2f b) {
		return new Vec2f(a.x - b.x, a.y - b.y);
	}
}
