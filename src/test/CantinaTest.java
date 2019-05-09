package test;

import main.FileReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.Test;

public class CantinaTest {
	@Test
	public void testFind() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the full path of the file:");
		String filePath = in.next();
		FileReader fr = new FileReader(filePath);
		assertEquals(26, fr.find("Input"));
	}
}
