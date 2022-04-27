package MyRenderer;

import MyRenderer.math.*;

public class FlatShadingRenderer extends Renderer {
	Vec3f direction = new Vec3f(0, 0, 1).normalize();
	Vec3f diffuseLightColor = new Vec3f(255, 255, 255);
	
	public FlatShadingRenderer(String filename, int width, int height) {
		super(filename, width, height);
	}

	void render(Model model) {
		int count = model.facesCount();
		for (int i = 0; i < count; i++) {
			final Vec3i face = model.getFace(i);
			final Vec3f normal = model.getFaceNormal(i);
			final float facing = normal.dot(direction);
			if (facing < 0) {
				continue;
			}

			//final int color = 0xFFFFFFFF;
			//final int color = normal.multiplied(255).toVec3i().toColorARGB();
			final int color = diffuseLightColor.multiplied(facing).toVec3i().toColorARGB();

			final Vec2i[] screenCoords = new Vec2i[3];
			for (int j = 0; j < 3; j++) {
				final Vec3f worldCoord = model.getVertex(face.get(j));
				screenCoords[j] = new Vec2i(
					(int)((worldCoord.x + 1.0) * this.width / 2.0),
					(int)((worldCoord.y + 1.0) * this.height / 2.0) - this.height / 2
				);
			}
			drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], color);
		}
	}
}
