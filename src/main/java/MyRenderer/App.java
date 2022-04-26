package MyRenderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
			// Render deer
			final var model = Model.fromOBJFile("deer.obj");
			mainRenderer.render(model);

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
