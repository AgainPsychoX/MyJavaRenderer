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

	public static final float EPSILON = 1e-7f;

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

	public Vec2f add(Vec2f other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	public Vec2f added(Vec2f other) {
		return new Vec2f(this.x + other.x, this.y + other.y);
	}

	static public class BBox {
		public float xMin;
		public float xMax;
		public float yMin;
		public float yMax;

		public BBox(Vec2f initial) {
			xMin = initial.x;
			xMax = initial.x;
			yMin = initial.y;
			yMax = initial.y;
		}

		public BBox add(Vec2f next) {
			xMin = Math.min(xMin, next.x);
			xMax = Math.max(xMax, next.x);
			yMin = Math.min(yMin, next.y);
			yMax = Math.max(yMax, next.y);
			return this; // for chaining
		}

		public BBox limit(float xMin, float xMax, float yMin, float yMax) {
			this.xMin = Math.max(this.xMin, xMin);
			this.xMax = Math.min(this.xMax, xMax);
			this.yMin = Math.max(this.yMin, yMin);
			this.yMax = Math.min(this.yMax, yMax);
			return this; // for chaining
		}
	}
}
