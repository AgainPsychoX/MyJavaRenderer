package MyRenderer;

import java.util.concurrent.ThreadLocalRandom;

import MyRenderer.math.*;

public class RandomColorRenderer extends Renderer {
	public RandomColorRenderer(String filename, int width, int height) {
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
			drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], randomColor());
		}
	}

	public int randomColor() {
		return ThreadLocalRandom.current().nextInt(0, 0x00ffffff) | 0xff000000;
	}
}
