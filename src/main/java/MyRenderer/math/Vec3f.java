package MyRenderer.math;

public class Vec3f extends Vec2f {
	public float z;

	public float get(int i) {
		return i == 0 ? x : i == 1 ? y : i == 2 ? z : 0;
	}

	public float length() {
		return (float) Math.sqrt((x * x + y * y + z * z));
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Vec3f other)
			return Math.abs(this.x - other.x) < EPSILON 
				&& Math.abs(this.y - other.y) < EPSILON
				&& Math.abs(this.z - other.z) < EPSILON
			;
		if (o instanceof Vec3i other)
			return Math.abs(this.x - other.x) < EPSILON 
				&& Math.abs(this.y - other.y) < EPSILON
				&& Math.abs(this.z - other.z) < EPSILON
			;
		if (o instanceof Vec2f other)
			return Math.abs(this.x - other.x) < EPSILON 
				&& Math.abs(this.y - other.y) < EPSILON
				&& this.z == 0
			;
		if (o instanceof Vec2i other)
			return Math.abs(this.x - other.x) < EPSILON 
				&& Math.abs(this.y - other.y) < EPSILON
				&& this.z == 0
			;
		return false;
	}

	public Vec3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}

	public static Vec3f fromVec3i(Vec3i i) {
		return new Vec3f(i.x, i.y, i.z);
	}

	public Vec3f normalize() {
		final float a = 1.f / length();
		x *= a;
		y *= a;
		z *= a;
		return this; // for chaining
	}
	public Vec3f normalized() {
		final float a = 1.f / length();
		return new Vec3f(x * a, y * a, z * a);
	}

	public Vec3f translate(Vec3f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this; // for chaining
	}
	public Vec3f transformed(Vec3f other) {
		return new Vec3f(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	public float dot(Vec3f other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	public Vec3f crossed(Vec3f other) {
		return new Vec3f(
			this.y * other.z - this.z * other.y,
			this.z * other.x - this.x * other.z,
			this.x * other.y - this.y * other.x
		);
	}

	public static Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
		final var ab = Vec2f.fromPoints(A, B);
		final var ac = Vec2f.fromPoints(A, C);
		final var pa = Vec2f.fromPoints(P, A);
		final var v1 = new Vec3f(ab.x, ac.x, pa.x);
		final var v2 = new Vec3f(ab.y, ac.y, pa.y);
		final var cross = v1.crossed(v2);
		final var uv = new Vec2f(cross.x / cross.z, cross.y / cross.z);
		return new Vec3f(uv.x, uv.y, 1 - uv.x - uv.y);
	}
}
