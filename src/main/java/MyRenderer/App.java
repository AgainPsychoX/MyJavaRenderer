package MyRenderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import MyRenderer.math.Vec3f;

public class App {
	public static void main(String[] args) {
		final String filePath = args.length >= 1 ? args[0] : (System.getProperty("user.home") + "/render.png");
		final int width = args.length >= 2 ? Integer.parseInt(args[1]) : 200;
		final int height = args.length >= 3 ? Integer.parseInt(args[2]) : width;

		final var mainRenderer = new FlatShadingRenderer(filePath, width, height);

		// Clear as black
		final int black = 0xFF000000;
		mainRenderer.clear(black);

		try {
			testZBuffer(mainRenderer);

			// Render deer
			// final var model = Model.fromOBJFile("deer.obj");
			// mainRenderer.render(model);

			mainRenderer.save();
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void testLinesAllDirections(final Renderer renderer, final int cx, final int cy, final int size, final int spacing) {
		testLinesAllDirections(renderer, cx, cy, size, spacing, 16);
	}

	public static void testLinesAllDirections(final Renderer renderer, final int cx, final int cy, final int size, final int spacing, final int slices) {
		final double angleStep = Math.PI * 2 / slices;
		double angle = 0;
		for (int i = 0; i < slices; i++, angle += angleStep) {
			final int x0 = (int) (cx + Math.round(Math.cos(angle) * spacing));
			final int y0 = (int) (cy + Math.round(Math.sin(angle) * spacing));
			final int x1 = (int) (cx + Math.round(Math.cos(angle) * size));
			final int y1 = (int) (cy + Math.round(Math.sin(angle) * size));
			renderer.drawLine(x0, y0, x1, y1);
			System.out.println("%d\t%.4f\tA:\t%d\t%d\tB:\t%d\t%d".formatted(i, angle, x0, y0, x1, y1));
		}
	}

	public static void testZBuffer(final Renderer renderer) {
		final int red    = 0xFFFF0000;
		final int green  = 0xFF00FF00;
		final int blue   = 0xFF0000FF;
		final int yellow = red | green;
		
		final int a = Math.min(renderer.width, renderer.height) / 8;

		// Basic test
		renderer.drawTriangle(
			new Vec3f(a * 0, a * 0, 0.5f),
			new Vec3f(a * 1, a * 0, 0.5f),
			new Vec3f(a * 0, a * 1, 0.5f),
			green
		);
		renderer.drawTriangle(
			new Vec3f(a * 0.5f, a * 0f, 0.7f),
			new Vec3f(a * 1.5f, a * 0f, 0.7f),
			new Vec3f(a * 0.5f, a * 1f, 0.7f),
			red
		);
		renderer.drawTriangle(
			new Vec3f(a * 0.25f, a * 0.5f, 0.3f),
			new Vec3f(a * 1.25f, a * 0.5f, 0.3f),
			new Vec3f(a * 0.25f, a * 1.5f, 0.3f),
			blue
		);

		// Overlapping triangles
		renderer.drawTriangle(
			new Vec3f(a * 2, a * 1, 0.2f),
			new Vec3f(a * 4, a * 1, 0.2f),
			new Vec3f(a * 2, a * 7, 0.8f),
			red
		);
		renderer.drawTriangle(
			new Vec3f(a * 4, a * 7, 0.2f),
			new Vec3f(a * 6, a * 7, 0.2f),
			new Vec3f(a * 6, a * 1, 0.8f),
			green
		);
		renderer.drawTriangle(
			new Vec3f(a * 7, a * 2, 0.2f),
			new Vec3f(a * 7, a * 4, 0.2f),
			new Vec3f(a * 1, a * 2, 0.8f),
			blue
		);
		renderer.drawTriangle(
			new Vec3f(a * 1, a * 4, 0.2f),
			new Vec3f(a * 1, a * 6, 0.2f),
			new Vec3f(a * 7, a * 6, 0.8f),
			yellow
		);
	}
}
