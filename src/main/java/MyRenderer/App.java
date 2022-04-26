package MyRenderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import MyRenderer.Renderer.LineAlgorithm;
import MyRenderer.math.Vec2f;
import MyRenderer.math.Vec3i;

public class App {
	public static void main(String[] args) {
		final String filePath = args.length >= 1 ? args[0] : (System.getProperty("user.home") + "/render.png");
		final int width = args.length >= 2 ? Integer.parseInt(args[1]) : 200;
		final int height = args.length >= 3 ? Integer.parseInt(args[2]) : width;
		final LineAlgorithm lineAlgorithm = args.length >= 4 ? LineAlgorithm.parse(args[3]) : LineAlgorithm.DEFAULT;

		Renderer mainRenderer = new Renderer(filePath, width, height, lineAlgorithm);
		mainRenderer.clear();
		mainRenderer.drawTriangle(
			new Vec2f(50 + 10, 50 - 10),
			new Vec2f(100 + 10, 150 - 10),
			new Vec2f(250 + 10, 100 - 10),
			mainRenderer.colorFromRGBA(208, 244, 234, 255)
		);
		mainRenderer.drawTriangle(
			new Vec2f(50, 50),
			new Vec2f(100, 150),
			new Vec2f(250, 100),
			new Vec3i(177, 204, 116)
		);

		try {
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
}
