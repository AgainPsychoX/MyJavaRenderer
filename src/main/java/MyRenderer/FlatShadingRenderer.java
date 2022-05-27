package MyRenderer;

import java.util.concurrent.ThreadLocalRandom;

import MyRenderer.math.*;

public class FlatShadingRenderer extends Renderer {
	// Vec3f cameraEye = new Vec3f(0f, 0, 1f);
	// Vec3f cameraEye = new Vec3f(1f, 0, 0f);
	// Vec3f cameraEye = new Vec3f(0.5f, 0, 0.5f);
	Vec3f cameraEye = new Vec3f(1f, 1f, 1f);
	// Vec3f cameraDirection = new Vec3f(0f, 0, -1f).normalize();
	// Vec3f cameraDirection = new Vec3f(-1f, 0, 0f).normalize();
	// Vec3f cameraDirection = new Vec3f(-0.5f, 0, -0.5f).normalize();
	Vec3f cameraDirection = new Vec3f(-1f, -1f, -1f).normalize();
	Vec3f cameraUp = new Vec3f(0, 1, 0);

	Vec3f diffuseLightColor = new Vec3f(255, 255, 255);

	public FlatShadingRenderer(String filename, int width, int height) {
		super(filename, width, height);
		matrix = Matrix4f.viewport(width, height)
			.multiply(Matrix4f.perspective(-1, 1, 1, -1, -1, 1))
			// .multiply(Matrix4f.orthogonal (-1, 1, 1, -1, -1, 1))
			// .multiply(Matrix4f.orthogonal(-1, 1, 1, -1, -1, 1))
			.multiply(Matrix4f.camera(cameraEye, cameraDirection, cameraUp))
		;
	}

	Matrix4f matrix;

	public void render(Model model) {
		int count = model.facesCount();
		for (int i = 0; i < count; i++) {
			final Vec3i face = model.getFace(i);
			final Vec3f normal = model.getFaceNormal(i);
			// final float facing = normal.dot(cameraEye.normalized());
			final float facing = normal.dot(cameraDirection.negated());
			if (facing < 0) {
				continue;
			}

			// final int color = 0xFFFFFFFF;
			// final int color = randomColor();
			final int color = normal.multiplied(255).toVec3i().toColorARGB();
			// final int color = diffuseLightColor.multiplied(facing).toVec3i().toColorARGB();

			final Vec3f[] screenCoords = new Vec3f[3];
			for (int j = 0; j < 3; j++) {
				final Vec3f worldCoord = model.getVertex(face.get(j));
				final Vec4f v = matrix.multiply(worldCoord);
				screenCoords[j] = new Vec3f(v.x / v.w, v.y / v.w, v.z / v.w);
				// screenCoords[j] = new Vec3f(v.x / v.w, v.y / v.w, v.z);
			}
			drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], color);
		}
	}

	private int randomColor() {
		return ThreadLocalRandom.current().nextInt(0, 0x00ffffff) | 0xff000000;
	}
}
