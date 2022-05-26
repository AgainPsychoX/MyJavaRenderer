package MyRenderer;

import java.io.*;
import java.util.ArrayList;

import MyRenderer.math.*;

public class Model {
	protected ArrayList<Vec3f> vertices;
	protected ArrayList<Vec3i> faces;
	protected ArrayList<Vec3f> facesNormals;

	public int verticesCount() { return vertices.size(); }
	public int facesCount() { return faces.size(); }

	public Vec3f getVertex(int index) { return vertices.get(index); }
	public Vec3i getFace(int index) { return faces.get(index); }
	public Vec3f getFaceNormal(int index) { return facesNormals.get(index); }

	public Model() {
		vertices = new ArrayList<>();
		faces = new ArrayList<>();
		facesNormals = null;
	}

	protected void calculateNormals() {
		int count = facesCount();
		facesNormals = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			final Vec3i face = getFace(i);
			final Vec3f P1 = getVertex(face.get(0));
			final Vec3f P2 = getVertex(face.get(1));
			final Vec3f P3 = getVertex(face.get(2));
			final Vec3f V = P2.subtracted(P1);
			final Vec3f W = P3.subtracted(P1);
			facesNormals.add(new Vec3f(
				V.y * W.z - V.z * W.y,
				V.z * W.x - V.x * W.z,
				V.x * W.y - V.y * W.x
			).normalize());
		}
	}

	public static Model fromOBJFile(String path) throws IOException {
		return fromOBJ(new FileInputStream(path));
	}
	public static Model fromOBJ(InputStream inputStream) throws IOException {
		final var model = new Model();
		final var reader = new BufferedReader(new InputStreamReader(inputStream));
		model.vertices.add(new Vec3f(0, 0, 0));
		while (reader.ready()) {
			final var line = reader.readLine();
			final var spaceIndex = line.indexOf(' ');
			if (spaceIndex < 1) {
				continue;
			}
			final var word = line.substring(0, spaceIndex);
			switch (word) {
				case "v": 
					model.vertices.add(parseVertexFromOBJ(line));
					break;
				case "f":
					model.faces.add(parseFaceFromOBJ(line));
					break;
				default: 
					// Ignoring line
					break;
			}
		}
		model.calculateNormals();
		return model;
	}
	static private Vec3f parseVertexFromOBJ(String line) {
		String[] splitted = line.split("\\s+");
		return new Vec3f(
			Float.parseFloat(splitted[1]),
			Float.parseFloat(splitted[2]),
			Float.parseFloat(splitted[3])
		);
	}
	static private Vec3i parseFaceFromOBJ(String line) {
		String[] splitted = line.split("\\s+");
		return new Vec3i(
			Integer.parseInt(splitted[1].split("/")[0]),
			Integer.parseInt(splitted[2].split("/")[0]),
			Integer.parseInt(splitted[3].split("/")[0])
		);
	}

	/**
	 * Translates all vertices in the model by given vector.
	 * @param v Vector to translate all vertices by.
	 * @return The model, for chaining.
	 */
	public Model translate(final Vec3f v) {
		int count = verticesCount();
		for (int i = 0; i < count; i++) {
			getVertex(i).add(v);
		}
		calculateNormals();
		return this; // for chaining
	}
}
