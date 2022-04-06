package MyRenderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

class AppTest {
	@Test void outputsFileToSpecifiedLocation(@TempDir Path tempDir) {
		Path file = tempDir.resolve("output.png");
		App.main(new String[] { file.toString() });
		assertTrue(Files.exists(file));
	}
}
