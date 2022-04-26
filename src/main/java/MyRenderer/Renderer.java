package MyRenderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import MyRenderer.math.Vec2f;
import MyRenderer.math.Vec3f;
import MyRenderer.math.Vec3i;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Renderer {
	public enum LineAlgorithm {
		NAIVE, 
		DDA, 
		BRESENHAM, 
		BRESENHAM_INT;

		static LineAlgorithm parse(String string) {
			string = string.toLowerCase();
			if (string.contains("naive")) return NAIVE;
			if (string.contains("dda")) return DDA;
			if (string.contains("bresen")) {
				if (string.contains("int")) 
					return BRESENHAM_INT;
				else
					return BRESENHAM;
			}
			Logger.getLogger(LineAlgorithm.class.getName()).log(Level.WARNING, "Invalid line algorithm name, falling back to default " + DEFAULT.toString());
			return DEFAULT;
		}

		final static LineAlgorithm DEFAULT = LineAlgorithm.BRESENHAM_INT;
	}

	private BufferedImage render;
	public final int height;
	public final int width;
	public LineAlgorithm lineAlgorithm;

	private String filename;

	public Renderer(String filename, int width, int height) {
		this(filename, width, height, LineAlgorithm.DEFAULT);
	}

	public Renderer(String filename, int width, int height, LineAlgorithm lineAlgorithm) {
		render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.filename = filename;
		this.width = width;
		this.height = height;
		this.lineAlgorithm = lineAlgorithm;
	}

	public void drawPoint(int x, int y) {
		int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
		render.setRGB(x, y, white);
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		drawLine(x0, y0, x1, y1, lineAlgorithm);
	}
	
	public void drawLine(int x0, int y0, int x1, int y1, LineAlgorithm lineAlgorithm) {
		switch (lineAlgorithm) {
			case NAIVE:         drawLineNaive(x0, y0, x1, y1);        break;
			case DDA:           drawLineDDA(x0, y0, x1, y1);          break;
			case BRESENHAM:     drawLineBresenham(x0, y0, x1, y1);    break;
			case BRESENHAM_INT: drawLineBresenhamInt(x0, y0, x1, y1); break;
		}
	}

	public void drawHorizontalLine(int x, final int y, int length) {
		while (length --> 0) {
			drawPoint(x++, y);
		}
	}

	public void drawVerticalLine(final int x, int y, int length) {
		while (length --> 0) {
			drawPoint(x, y++);
		}
	}

	public void drawLineNaive(int x0, int y0, int x1, int y1) {
		if (x1 < x0) {
			drawLineNaive(x1, y1, x0, y0);
			return;
		}

		final int dx = x1 - x0;
		final int dy = y1 - y0;
		if (dy == 0) {
			drawHorizontalLine(x0, y0, dx);
			return;
		}
		if (dx == 0) {
			if (dy < 0) {
				drawVerticalLine(x0, y1, -dy);
				return;
			}
			else {
				drawVerticalLine(x0, y0, dy);
				return;
			}
		}

		final float a = (float) dy / dx;
		final float b = y0 - a * x0;
		for (int x = x0; x <= x1; x++) {
			final int y = Math.round(a * x + b);
			this.drawPoint(x, y);
		}
	}

	public void drawLineDDA(final int x0, final int y0, final int x1, final int y1) {
		if (x1 < x0) {
			drawLineDDA(x1, y1, x0, y0);
			return;
		}

		final int dx = x1 - x0;
		final int dy = y1 - y0;
		if (dy == 0) {
			drawHorizontalLine(x0, y0, dx);
			return;
		}
		if (dx == 0) {
			if (dy < 0) {
				drawVerticalLine(x0, y1, -dy);
				return;
			}
			else {
				drawVerticalLine(x0, y0, dy);
				return;
			}
		}

		final float a = (float) dy / dx;
		final float b = y0 - a * x0;
		if (Math.abs(dy) <= dx) {
			for (int x = x0; x <= x1; x++) {
				final int y = Math.round(a * x + b);
				this.drawPoint(x, y);
			}
		}
		else {
			if (dy < 0) {
				for (int y = y1; y <= y0; y++) {
					final int x = Math.round((y - b) / a);
					this.drawPoint(x, y);
				}
			}
			else {
				for (int y = y0; y <= y1; y++) {
					final int x = Math.round((y - b) / a);
					this.drawPoint(x, y);
				}
			}
		}
	}

	public void drawLineBresenham(final int x0, final int y0, final int x1, final int y1) {
		if (x1 < x0) {
			drawLineBresenham(x1, y1, x0, y0);
			return;
		}

		final int dx = x1 - x0;
		final int dy = y1 - y0;
		if (dy == 0) {
			drawHorizontalLine(x0, y0, dx);
			return;
		}
		if (dx == 0) {
			if (dy < 0) {
				drawVerticalLine(x0, y1, -dy);
				return;
			}
			else {
				drawVerticalLine(x0, y0, dy);
				return;
			}
		}

		final float derr = Math.abs((float)dy / dx);
		float err = 0;
		int x = x0;
		int y = y0;
		while (true) {
			drawPoint(x, y);
			if (x == x1 && y == y1) {
				break;
			}

			if (err < 0.5) {
				x += 1;
				err += derr;
			}
			if (err > 0.5) {
				y += (y1 > y0 ? 1 : -1);
				err -= 1.;
			}
		}
	}

	public void drawLineBresenhamInt(final int x0, final int y0, final int x1, final int y1) {
		if (x1 < x0) {
			drawLineBresenhamInt(x1, y1, x0, y0);
			return;
		}

		final int dx = x1 - x0;
		int dy = y1 - y0;
		if (dy == 0) {
			drawHorizontalLine(x0, y0, dx);
			return;
		}
		if (dx == 0) {
			if (dy < 0) {
				drawVerticalLine(x0, y1, -dy);
				return;
			}
			else {
				drawVerticalLine(x0, y0, dy);
				return;
			}
		}
		dy = Math.abs(dy);

		int err = dx - dy;
		int x = x0;
		int y = y0;
		while (true) {
			drawPoint(x, y);
			if (x == x1 && y == y1) {
				break;
			}

			final int e2 = err * 2;
			if (-e2 <= dy) {
				err -= dy;
				x += 1;
			}
			if (e2 <= dx) {
				err += dx;
				y += (y1 > y0 ? 1 : -1);
			}
		}
	}

	public void drawTriangle(Vec2f A, Vec2f B, Vec2f C) {
		final int white = 0 | (0 << 8) | (0 << 16) | (0 << 24);
		drawTriangle(A, B, C, white);
	}
	public void drawTriangle(Vec2f A, Vec2f B, Vec2f C, Vec3i color) {
		drawTriangle(A, B, C, color.toColorARGB());
	}
	public void drawTriangle(Vec2f A, Vec2f B, Vec2f C, int color) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final var barycentric = Vec3f.barycentric(A, B, C, new Vec2f(x, y));
				if (
					0 <= barycentric.x && barycentric.x <= 1 &&
					0 <= barycentric.y && barycentric.y <= 1 &&
					0 <= barycentric.z && barycentric.z <= 1
				) {
					render.setRGB(x, y, color);
				}
			}
		}
	}

	public void save() throws IOException {
		File outputFile = new File(filename);
		render = Renderer.verticalFlip(render);
		ImageIO.write(render, "png", outputFile);
	}

	public void clear() {
		final int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
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

	public int colorFromRGBA(int red, int green, int blue, int alpha) {
		return ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
	}
}
