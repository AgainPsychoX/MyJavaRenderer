package MyRenderer;

import java.util.Arrays;

public class ZBuffer {
	protected final int width;
	protected final float[] buffer;
	
	/**
	 * Tries to set new depth for given pixel.
	 * @param x 
	 * @param y
	 * @param value
	 * @return true if closer (should apply color).
	 */
	public boolean set(int x, int y, float value) {
		final float old = buffer[width * y + x];
		if (value <= old) {
			buffer[width * y + x] = value;
			return true;
		}
		return false;
	}

	public float get(int x, int y) {
		return buffer[width * y + x];
	}
	
	public ZBuffer(int width, int height) {
		this.width = width;
		buffer = new float[height * width];
		Arrays.fill(buffer, Float.POSITIVE_INFINITY);
	}
}
