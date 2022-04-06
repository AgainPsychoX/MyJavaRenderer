package MyRenderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer {
	public enum LineAlgorithm {
		NAIVE, 
		DDA, 
		BRESENHAM, 
		BRESENHAM_INT;
	}

	private BufferedImage render;
	public final int height;
	public final int width;

	private String filename;

	public Renderer(String filename, int width, int height) {
		render = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		this.filename = filename;
		this.width = width;
		this.height = height;
	}

	public void drawPoint(int x, int y) {
		int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
		render.setRGB(x, y, white);
	}

	public void drawLine(int x0, int y0, int x1, int y1, LineAlgorithm lineAlgorithm) {
		switch (lineAlgorithm) {
			case NAIVE:         drawLineNaive(x0, y0, x1, y1);        break;
			case DDA:           drawLineDDA(x0, y0, x1, y1);          break;
			case BRESENHAM:     drawLineBresenham(x0, y0, x1, y1);    break;
			case BRESENHAM_INT: drawLineBresenhamInt(x0, y0, x1, y1); break;
		}
	}

	public void drawLineNaive(int x0, int y0, int x1, int y1) {
		final float a = ((float)y0 - y1) / ((float)x0 - x1);
		final float b = y0 - a * x0;
		for (int x = x0; x <= x1; x++) {
			final int y = Math.round(a * x + b);
			this.drawPoint(x, y);
		}
	}

	public void drawLineDDA(int x0, int y0, int x1, int y1) {
		// TODO: zaimplementuj
	}

	public void drawLineBresenham(int x0, int y0, int x1, int y1) {
		// TODO: zaimplementuj
	}

	public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
		// TODO: zaimplementuj
	}

	public void save() throws IOException {
		File outputFile = new File(filename);
		render = Renderer.verticalFlip(render);
		ImageIO.write(render, "png", outputFile);
	}

	public void clear() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
				render.setRGB(x, y, black);
			}
		}
	}

	public static BufferedImage verticalFlip(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
		Graphics2D g = flippedImage.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return flippedImage;
	}
}
