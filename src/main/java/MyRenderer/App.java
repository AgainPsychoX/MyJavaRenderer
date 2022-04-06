package MyRenderer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import MyRenderer.Renderer.LineAlgorithm;

public class App {
	public static void main(String[] args) {
		final String filePath = args.length >= 1 ? args[0] : (System.getProperty("user.home") + "/render.png");
		final int width = args.length >= 2 ? Integer.parseInt(args[1]) : 200;
		final int height = args.length >= 3 ? Integer.parseInt(args[2]) : width;
		final LineAlgorithm lineAlgorithm = args.length >= 4 ? LineAlgorithm.parse(args[3]) : LineAlgorithm.DEFAULT;

		Renderer mainRenderer = new Renderer(filePath, width, height, lineAlgorithm);
		mainRenderer.clear();
		mainRenderer.drawPoint(100, 100);
		testLinesAllDirections(mainRenderer, 100, 100, 50, 5);

		try {
			mainRenderer.save();
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void testLinesAllDirections(Renderer renderer, int cx, int cy, int size, int spacing) {
		final double angleStep = 1.0 / 8 * Math.PI;
		double angle = 0;
		for (int i = 0; i < 16; i++, angle += angleStep) {
			final int x0 = (int) (cx + Math.round(Math.cos(angle) * spacing));
			final int y0 = (int) (cy + Math.round(Math.sin(angle) * spacing));
			final int x1 = (int) (cx + Math.round(Math.cos(angle) * size));
			final int y1 = (int) (cy + Math.round(Math.sin(angle) * size));
			renderer.drawLine(x0, y0, x1, y1);
			System.out.println("%d\t%.4f\tA:\t%d\t%d\tB:\t%d\t%d".formatted(i, angle, x0, y0, x1, y1));
		}
		// Poprawne linie można zaobserwować tylko dla linii mających tylko jeden piksel w y dla danego x.
		// Oktanty: 8, 7
	}
}
