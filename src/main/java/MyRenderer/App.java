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

		Renderer mainRenderer = new Renderer(filePath, width, height);
		mainRenderer.clear();
		mainRenderer.drawPoint(100, 100);
		mainRenderer.drawLine(0, 0, 100, 50, LineAlgorithm.NAIVE);

		try {
			mainRenderer.save();
		} catch (IOException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
