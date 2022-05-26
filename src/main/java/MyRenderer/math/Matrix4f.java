package MyRenderer.math;

public class Matrix4f {
	public static final float EPSILON = 1e-7f;

	protected float[][] data;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Matrix4f other) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (Math.abs(this.data[i][j] - other.data[i][j]) >= EPSILON) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public Matrix4f() {
		data = new float[4][4];
	}

	public Matrix4f(Matrix4f other) {
		this.data = other.data.clone();
	}

	public Matrix4f(
		float m00, float m01, float m02, float m03,
		float m10, float m11, float m12, float m13,
		float m20, float m21, float m22, float m23,
		float m30, float m31, float m32, float m33
	) {
		data = new float[4][4];

		this.data[0][0] = m00;
		this.data[0][1] = m01;
		this.data[0][2] = m02;
		this.data[0][3] = m03;

		this.data[1][0] = m10;
		this.data[1][1] = m11;
		this.data[1][2] = m12;
		this.data[1][3] = m13;

		this.data[2][0] = m20;
		this.data[2][1] = m21;
		this.data[2][2] = m22;
		this.data[2][3] = m23;

		this.data[3][0] = m30;
		this.data[3][1] = m31;
		this.data[3][2] = m32;
		this.data[3][3] = m33;
	}

	public static Matrix4f zeros() {
		return new Matrix4f(); // Java creates arrays zero-initialized anyway.
	}

	public static Matrix4f ones() {
		var obj = new Matrix4f();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				obj.data[i][j] = 1;
			}
		}
		return obj;
	}

	public static Matrix4f identity() {
		var obj = new Matrix4f();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				obj.data[i][j] = i == j ? 1 : 0;
			}
		}
		return obj;
	}

	// TODO: scale: https://youtu.be/EOyF1pBbE1Q?t=588
	// TODO: translate: https://youtu.be/EOyF1pBbE1Q?t=710
	// TODO: rotate: https://youtu.be/EOyF1pBbE1Q?t=1312
	// TODO: shearing: https://youtu.be/EOyF1pBbE1Q?t=607

	public Vec4f multiply(final Vec4f worldCoord) {
		float x = this.data[0][0] * worldCoord.x + this.data[0][1] * worldCoord.y + this.data[0][2] * worldCoord.z + this.data[0][3] * worldCoord.w;
		float y = this.data[1][0] * worldCoord.x + this.data[1][1] * worldCoord.y + this.data[1][2] * worldCoord.z + this.data[1][3] * worldCoord.w;
		float z = this.data[2][0] * worldCoord.x + this.data[2][1] * worldCoord.y + this.data[2][2] * worldCoord.z + this.data[2][3] * worldCoord.w;
		float w = this.data[3][0] * worldCoord.x + this.data[3][1] * worldCoord.y + this.data[3][2] * worldCoord.z + this.data[3][3] * worldCoord.w;
		return new Vec4f(x, y, z, w);
	}

	public Vec4f multiply(final Vec3f worldCoord) {
		float x = this.data[0][0] * worldCoord.x + this.data[0][1] * worldCoord.y + this.data[0][2] * worldCoord.z + this.data[0][3];
		float y = this.data[1][0] * worldCoord.x + this.data[1][1] * worldCoord.y + this.data[1][2] * worldCoord.z + this.data[1][3];
		float z = this.data[2][0] * worldCoord.x + this.data[2][1] * worldCoord.y + this.data[2][2] * worldCoord.z + this.data[2][3];
		float w = this.data[3][0] * worldCoord.x + this.data[3][1] * worldCoord.y + this.data[3][2] * worldCoord.z + this.data[3][3];
		return new Vec4f(x, y, z, w);
	}

	public Matrix4f multiply(final Matrix4f m) {
		float m00 = this.data[0][0] * m.data[0][0] + this.data[0][1] * m.data[1][0] +
		            this.data[0][2] * m.data[2][0] + this.data[0][3] * m.data[3][0];
		float m01 = this.data[0][0] * m.data[0][1] + this.data[0][1] * m.data[1][1] +
		            this.data[0][2] * m.data[2][1] + this.data[0][3] * m.data[3][1];
		float m02 = this.data[0][0] * m.data[0][2] + this.data[0][1] * m.data[1][2] +
		            this.data[0][2] * m.data[2][2] + this.data[0][3] * m.data[3][2];
		float m03 = this.data[0][0] * m.data[0][3] + this.data[0][1] * m.data[1][3] +
		            this.data[0][2] * m.data[2][3] + this.data[0][3] * m.data[3][3];

		float m10 = this.data[1][0] * m.data[0][0] + this.data[1][1] * m.data[1][0] +
		            this.data[1][2] * m.data[2][0] + this.data[1][3] * m.data[3][0];
		float m11 = this.data[1][0] * m.data[0][1] + this.data[1][1] * m.data[1][1] +
		            this.data[1][2] * m.data[2][1] + this.data[1][3] * m.data[3][1];
		float m12 = this.data[1][0] * m.data[0][2] + this.data[1][1] * m.data[1][2] +
		            this.data[1][2] * m.data[2][2] + this.data[1][3] * m.data[3][2];
		float m13 = this.data[1][0] * m.data[0][3] + this.data[1][1] * m.data[1][3] +
		            this.data[1][2] * m.data[2][3] + this.data[1][3] * m.data[3][3];

		float m20 = this.data[2][0] * m.data[0][0] + this.data[2][1] * m.data[1][0] +
		            this.data[2][2] * m.data[2][0] + this.data[2][3] * m.data[3][0];
		float m21 = this.data[2][0] * m.data[0][1] + this.data[2][1] * m.data[1][1] +
		            this.data[2][2] * m.data[2][1] + this.data[2][3] * m.data[3][1];
		float m22 = this.data[2][0] * m.data[0][2] + this.data[2][1] * m.data[1][2] +
		            this.data[2][2] * m.data[2][2] + this.data[2][3] * m.data[3][2];
		float m23 = this.data[2][0] * m.data[0][3] + this.data[2][1] * m.data[1][3] +
		            this.data[2][2] * m.data[2][3] + this.data[2][3] * m.data[3][3];

		float m30 = this.data[3][0] * m.data[0][0] + this.data[3][1] * m.data[1][0] +
		            this.data[3][2] * m.data[2][0] + this.data[3][3] * m.data[3][0];
		float m31 = this.data[3][0] * m.data[0][1] + this.data[3][1] * m.data[1][1] +
		            this.data[3][2] * m.data[2][1] + this.data[3][3] * m.data[3][1];
		float m32 = this.data[3][0] * m.data[0][2] + this.data[3][1] * m.data[1][2] +
		            this.data[3][2] * m.data[2][2] + this.data[3][3] * m.data[3][2];
		float m33 = this.data[3][0] * m.data[0][3] + this.data[3][1] * m.data[1][3] +
		            this.data[3][2] * m.data[2][3] + this.data[3][3] * m.data[3][3];

		this.data[0][0] = m00; this.data[0][1] = m01; this.data[0][2] = m02; this.data[0][3] = m03;
		this.data[1][0] = m10; this.data[1][1] = m11; this.data[1][2] = m12; this.data[1][3] = m13;
		this.data[2][0] = m20; this.data[2][1] = m21; this.data[2][2] = m22; this.data[2][3] = m23;
		this.data[3][0] = m30; this.data[3][1] = m31; this.data[3][2] = m32; this.data[3][3] = m33;

		return this; // for chaining
	}

	public Matrix4f multiplied(Matrix4f m) {
		return new Matrix4f(this).multiply(m);
	}

	/**
	 * Prepares camera transform matrix.
	 * @param eye Eye/camera position in the world.
	 * @param direction Normalized direction vector.
	 * @param up View up vector.
	 * @return The prepared matrix.
	 */
	public static Matrix4f camera(final Vec3f eye, final Vec3f direction, final Vec3f up) {
		final Vec3f w = direction.negated();
		final Vec3f u = up.crossed(w).normalize();
		final Vec3f v = w.crossed(u);
		return new Matrix4f(
			u.x, u.y, u.z, 0,
			v.x, v.y, v.z, 0,
			w.x, w.y, w.z, 0,
			0, 0, 0, 1
		).multiply(new Matrix4f(
			1, 0, 0, -eye.x,
			0, 1, 0, -eye.y,
			0, 0, 1, -eye.z,
			0, 0, 0, 1
		));
	}

	/**
	 * Prepares viewport transform matrix.
	 * @param width Width of resulting viewport.
	 * @param height Height of resulting viewport.
	 * @return The prepared matrix.
	 */
	public static Matrix4f viewport(final float width, final float height) {
		return new Matrix4f(
			width / 2, 0, 0, width / 2,
			0, height / 2, 0, height / 2,
			0, 0, 1, 0,
			0, 0, 0, 1
		);
	}

	/**
	 * Prepares orthogonal transform matrix.
	 * @param l left 
	 * @param r right
	 * @param t top
	 * @param b bottom
	 * @param n near
	 * @param f far
	 * @return The prepared matrix.
	 */
	public static Matrix4f orthogonal(
		final float l, final float r, 
		final float t, final float b, 
		final float n, final float f
	) {
		return new Matrix4f(
			2.f / (r - l), 0, 0, -(r + l) / (r - l),
			0, 2.f / (t - b), 0, -(t + b) / (t - b),
			0, 0, 2.f / (n - f), -(n + f) / (n - f),
			0, 0, 0, 1
		);
	}

	/**
	 * Prepares perspective transformation matrix.
	 * @param l left 
	 * @param r right
	 * @param t top
	 * @param b bottom
	 * @param n near
	 * @param f far
	 * @return The prepared matrix.
	 */
	public static Matrix4f perspective(
		final float l, final float r, 
		final float t, final float b, 
		final float n, final float f
	) {
		return new Matrix4f(
			2.f * n / (r - l), 0, (l + r) / (l - r), 0,
			0, 2.f * n / (t - b), (b + t) / (b - t), 0,
			0, 0, (n + f) / (n - f), (n * f * 2) / (f - n),
			0, 0, 1, 0
		);
	}
}
