package MyRenderer;

import java.util.concurrent.ThreadLocalRandom;

import MyRenderer.math.Matrix4f;
import MyRenderer.math.Vec3f;
import MyRenderer.math.Vec3i;
import MyRenderer.math.Vec4f;

public class Renderer3D extends Renderer {
	// protected Vec3f cameraEye = new Vec3f(0f, 0, 1f);
	// protected Vec3f directionToCamera = new Vec3f(0f, 0, 1f);
	protected Vec3f cameraEye = new Vec3f(2f, 2, 2f);
	protected Vec3f directionToCamera = new Vec3f(2f, 2, 2f).normalize();
	protected Vec3f cameraUp = new Vec3f(0, 1, 0);

	public Vec3f getCameraEye() {
		return cameraEye;
	}
	public void setCameraEye(Vec3f cameraEye) {
		this.cameraEye = cameraEye;
		prepareMatrix();
	}

	public Vec3f getDirectionToCamera() {
		return directionToCamera;
	}
	public void setDirectionToCamera(Vec3f directionToCamera) {
		this.directionToCamera = directionToCamera;
		prepareMatrix();
	}

	public Vec3f getCameraUp() {
		return cameraUp;
	}
	public void setCameraUp(Vec3f cameraUp) {
		this.cameraUp = cameraUp;
		prepareMatrix();
	}

	public void setupCamera(final Vec3f eye, final Vec3f lookAt) {
		cameraEye = eye;
		directionToCamera = eye.subtracted(lookAt).normalize();
		prepareMatrix();
	}

	Vec3f diffuseLightColor = new Vec3f(255, 255, 255);

	public Renderer3D(String filename, int width, int height) {
		super(filename, width, height);

		// Initial matrixes
		viewportMatrix = Matrix4f.viewport(width, height);
		//perspectiveMatrix = Matrix4f.orthogonal();
		perspectiveMatrix = Matrix4f.perspective();
		prepareMatrix();
	}

	protected Matrix4f viewportMatrix;
	protected Matrix4f perspectiveMatrix;

	public void setViewportMatrix(Matrix4f viewportMatrix) {
		this.viewportMatrix = viewportMatrix;
		prepareMatrix();
	}

	public void setPerspectiveMatrix(Matrix4f perspectiveMatrix) {
		this.perspectiveMatrix = perspectiveMatrix;
		prepareMatrix();
	}

	protected Matrix4f matrix;

	protected void prepareMatrix() {
		matrix = viewportMatrix.multiplied(perspectiveMatrix).multiply(Matrix4f.camera(cameraEye, directionToCamera, cameraUp));
	}

	public Vec3f worldToScreen(final Vec3f worldCoord) {
		final Vec4f v = matrix.multiply(worldCoord);
		return new Vec3f(v.x / v.w, v.y / v.w, v.z / v.w);
	}

	public void render(Model model) {
		int count = model.facesCount();
		for (int i = 0; i < count; i++) {
			final Vec3i face = model.getFace(i);
			final Vec3f normal = model.getFaceNormal(i);
			final float facing = normal.dot(directionToCamera);
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
				screenCoords[j] = worldToScreen(worldCoord);
			}
			drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], color);
		}
	}

	protected int randomColor() {
		return ThreadLocalRandom.current().nextInt(0, 0x00ffffff) | 0xff000000;
	}
}
