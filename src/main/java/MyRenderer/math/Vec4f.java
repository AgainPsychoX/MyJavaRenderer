package MyRenderer.math;

public class Vec4f extends Vec3f {
	public float w;

	public float get(int i) {
		if (i == 0) return x;
		if (i == 1) return y;
		if (i == 2) return z;
		if (i == 3) return w;
		return 0;
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

	public Vec4f(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}

	public Vec4f normalize() {
		final float a = 1.f / length();
		x *= a;
		y *= a;
		z *= a;
		w *= a;
		return this; // for chaining
	}
	public Vec4f normalized() {
		final float a = 1.f / length();
		return new Vec4f(x * a, y * a, z * a, w * a);
	}

	public Vec4f negate() {
		x *= -1;
		y *= -1;
		z *= -1;
		w *= -1;
		return this; // for chaining
	}
	public Vec4f negated() {
		return new Vec4f(-x, -y, -z, -w);
	}

	public Vec4f add(Vec4f other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		this.w += other.w;
		return this; // for chaining
	}
	public Vec4f added(Vec4f other) {
		return new Vec4f(
			this.x + other.x, 
			this.y + other.y, 
			this.z + other.z, 
			this.w + other.w
		);
	}

	public Vec4f subtract(Vec4f other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		this.w -= other.w;
		return this; // for chaining
	}
	public Vec4f subtracted(Vec4f other) {
		return new Vec4f(
			this.x - other.x, 
			this.y - other.y, 
			this.z - other.z,
			this.w - other.w
		);
	}

	public Vec4f multiply(float value) {
		x *= value;
		y *= value;
		z *= value;
		w *= value;
		return this; // for chaining
	}
	public Vec4f multiplied(float value) {
		return new Vec4f(x * value, y * value, z * value, w * value);
	}

	public float dot(Vec4f other) {
		return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
	}
}
