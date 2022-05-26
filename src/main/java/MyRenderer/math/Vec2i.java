package MyRenderer.math;

public class Vec2i {
	public int x;
	public int y;

	public int get(int i) {
		return i == 0 ? x : i == 1 ? y : 0;
	}

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

	public Vec2i add(Vec2i other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	public Vec2i added(Vec2i other) {
		return new Vec2i(this.x + other.x, this.y + other.y);
	}

	static public class BBox {
		public int xMin;
		public int xMax;
		public int yMin;
		public int yMax;

		public BBox(Vec2i initial) {
			xMin = initial.x;
			xMax = initial.x;
			yMin = initial.y;
			yMax = initial.y;
		}

		public BBox add(Vec2i next) {
			xMin = Math.min(xMin, next.x);
			xMax = Math.max(xMax, next.x);
			yMin = Math.min(yMin, next.y);
			yMax = Math.max(yMax, next.y);
			return this; // for chaining
		}

		public BBox limit(int xMin, int xMax, int yMin, int yMax) {
			this.xMin = Math.max(this.xMin, xMin);
			this.xMax = Math.min(this.xMax, xMax);
			this.yMin = Math.max(this.yMin, yMin);
			this.yMax = Math.min(this.yMax, yMax);
			return this; // for chaining
		}
	}
}
