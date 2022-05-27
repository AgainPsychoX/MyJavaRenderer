package MyRenderer;

import MyRenderer.math.*;

public class FlatShadingRenderer extends Renderer3D {
	public FlatShadingRenderer(String filename, int width, int height) {
		super(filename, width, height);
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
			// final int color = normal.multiplied(255).toVec3i().toColorARGB();
			final int color = diffuseLightColor.multiplied(facing).toVec3i().toColorARGB();

			final Vec3f[] screenCoords = new Vec3f[3];
			for (int j = 0; j < 3; j++) {
				final Vec3f worldCoord = model.getVertex(face.get(j));
				screenCoords[j] = worldToScreen(worldCoord);
			}
			drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], color);
		}
	}
}
