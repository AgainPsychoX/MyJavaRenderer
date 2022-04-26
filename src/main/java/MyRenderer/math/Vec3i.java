package MyRenderer.math;

public class Vec3i extends Vec2i {
	public int z;

	public int get(int i) {
		return i == 0 ? x : i == 1 ? y : i == 2 ? z : 0;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}

	public Vec3i(int x, int y, int z) {
		super(x, y);
		this.z = z;
	}

	public int toColorARGB() {
		return toColorARGB((byte) 0xFF);
	}
	public int toColorARGB(byte alpha) {
		return alpha << 24 | ((x & 0xFF) << 16) | ((y & 0xFF) << 8) | (z & 0xFF);
	}

	public Vec3i translate(Vec3i other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	public Vec3i translated(Vec3i other) {
		return new Vec3i(this.x + other.x, this.y + other.y, this.z + other.z);
	}
}
