package MyRenderer.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vec3fTest {
	@SuppressWarnings("unlikely-arg-type")
	@Test void equality() {
		assertTrue(new Vec3f(1, 2, 3).equals(new Vec3f(1, 2, 3)));
		assertFalse(new Vec3f(1, 2, 3).equals(new Vec3f(0, 0, 0)));

		assertTrue(new Vec3f(1, 2, 3).equals(new Vec3i(1, 2, 3)));
		assertFalse(new Vec3f(1, 2, 3).equals(new Vec3i(0, 0, 0)));

		assertTrue(
			new Vec3f(
				123456.789f, 
				0.1f + 0.2f, // != 0.3f, but vectors should be equal, as difference is less than given epsilon.
				123f + Vec3f.EPSILON / 2
			).equals(new Vec3f(
				123456789f / 1000, 
				0.3f,
				123f
			))
		);
	}

	@Test void crossProduct() {
		assertTrue(
			new Vec3f(1, 2, 3)
				.crossed(new Vec3f(4, 5, 6))
				.equals(new Vec3f(-3, 6, -3))
		);
	}
}
