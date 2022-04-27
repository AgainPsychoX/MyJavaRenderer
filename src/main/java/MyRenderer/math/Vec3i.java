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
		return toColorARGB(0xFF);
	}
	public int toColorARGB(int alpha) {
		return (alpha & 0xFF) << 24 
			| (Math.min(Math.max(x, 0), 255) << 16) 
			| (Math.min(Math.max(y, 0), 255) << 8) 
			|  Math.min(Math.max(z, 0), 255)
		;
	}

	public Vec3i add(Vec3i other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	public Vec3i added(Vec3i other) {
		return new Vec3i(this.x + other.x, this.y + other.y, this.z + other.z);
	}
}
