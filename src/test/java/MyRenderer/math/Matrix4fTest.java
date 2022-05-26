package MyRenderer.math;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Matrix4fTest {
	static final Matrix4f A = new Matrix4f(
		5, 2, 6, 1,
		0, 6, 2, 0,
		3, 8, 1, 4,
		1, 8, 5, 6
	);
	static final Matrix4f B = new Matrix4f(
		7, 5, 8, 0,
		1, 8, 2, 6,
		9, 4, 3, 8,
		5, 3, 7, 9
	);

	@Test void equality() {
		assertTrue(Matrix4f.zeros().equals(Matrix4f.zeros()));
		assertTrue(Matrix4f.ones().equals(Matrix4f.ones()));
		assertTrue(Matrix4f.identity().equals(Matrix4f.identity()));
		assertFalse(A.equals(B));
	}

	@Test void multiply() {
		assertTrue(A.multiplied(B).equals(new Matrix4f(
			96, 68, 69, 69,
			24, 56, 18, 52,
			58, 95, 71, 92,
			90, 107, 81, 142
		)));
	}
}
