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

		// Clear as black
		final int black = 0xFF000000;
		mainRenderer.clear(black);

		final var A = new Vec2f(50, 50);
		final var B = new Vec2f(100, 150);
		final var C = new Vec2f(250, 100);

		// Draw triangle "under"
		final var offsetUnder = new Vec2f(10, -10);
		mainRenderer.drawTriangle(
			A.transformed(offsetUnder),
			B.transformed(offsetUnder),
			C.transformed(offsetUnder),
			mainRenderer.colorFromRGBA(208, 244, 234, 255)
		);

		// Draw main triangle
		mainRenderer.drawTriangle(
			A, B, C,
			new Vec3i(177, 204, 116)
		);

		// Draw lines around
		mainRenderer.color = mainRenderer.colorFromRGB(130, 147, 153);
		mainRenderer.drawLine(A.toVec2i(), B.toVec2i());
		mainRenderer.drawLine(B.toVec2i(), C.toVec2i());
		mainRenderer.drawLine(C.toVec2i(), A.toVec2i());

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
