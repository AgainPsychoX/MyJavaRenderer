package MyRenderer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import MyRenderer.math.*;

public class Model {
	protected ArrayList<Vec3f> vertexList;
	protected ArrayList<Vec3i> faceList;

	public List<Vec3i> getFaceList() { return faceList; }
	public Vec3f getVertex(int index) { return vertexList.get(index); }

	public Model() {
		vertexList = new ArrayList<>();
		faceList = new ArrayList<>();
	}

	public static Model fromOBJFile(String path) throws IOException {
		return fromOBJ(new FileInputStream(path));
	}
	public static Model fromOBJ(InputStream inputStream) throws IOException {
		final var model = new Model();
		final var reader = new BufferedReader(new InputStreamReader(inputStream));
		model.vertexList.add(new Vec3f(0, 0, 0));
		while (reader.ready()) {
			final var line = reader.readLine();
			final var spaceIndex = line.indexOf(' ');
			if (spaceIndex < 1) {
				continue;
			}
			final var word = line.substring(0, spaceIndex);
			switch (word) {
				case "v": 
					model.vertexList.add(parseVertexFromOBJ(line));
					break;
				case "f":
					model.faceList.add(parseFaceFromOBJ(line));
					break;
				default: 
					// Ignoring line
					break;
			}
		}
		return model;
	}
	static private Vec3f parseVertexFromOBJ(String line) {
		String[] splitted = line.split("\\s");
		return new Vec3f(
			Float.parseFloat(splitted[1]),
			Float.parseFloat(splitted[2]),
			Float.parseFloat(splitted[3])
		);
	}
	static private Vec3i parseFaceFromOBJ(String line) {
		String[] splitted = line.split("\\s");
		return new Vec3i(
			Integer.parseInt(splitted[1].split("/")[0]),
			Integer.parseInt(splitted[2].split("/")[0]),
			Integer.parseInt(splitted[3].split("/")[0])
		);
	}
}
