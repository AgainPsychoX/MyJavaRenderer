package MyRenderer;

import MyRenderer.math.*;

public class FlatShadingRenderer extends Renderer {
	Vec3f direction = new Vec3f(0, -25, -75).normalize();
	Vec3f diffuseLightColor = new Vec3f(255, 255, 255);
	
	public FlatShadingRenderer(String filename, int width, int height) {
		super(filename, width, height);
	}

	void render(Model model) {
		for (Vec3i face : model.getFaceList()) {
			final Vec2i[] screenCoords = new Vec2i[3];
			for (int j = 0; j < 3; j++) {
				final Vec3f worldCoord = model.getVertex(face.get(j));
				screenCoords[j] = new Vec2i(
					(int)((worldCoord.x + 1.0) * this.width / 2.0),
					(int)((worldCoord.y + 1.0) * this.height / 2.0) - this.height / 2
				);
			}
			final Vec3f provokingVertex = model.getVertex(face.get(0));
			final Vec3f color = diffuseLightColor.multiplied(Math.max(0.f, -provokingVertex.normalized().dot(direction)));
			drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], color.toVec3i());
		}
	}
}
