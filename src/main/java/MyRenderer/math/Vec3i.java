package MyRenderer.math;

public class Vec3i {
	public int x;
	public int y;
	public int z;

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}

	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int toColorARGB() {
		return toColorARGB((byte) 0xFF);
	}
	public int toColorARGB(byte alpha) {
		return alpha << 24 | ((x & 0xFF) << 16) | ((y & 0xFF) << 8) | (z & 0xFF);
	}
}
