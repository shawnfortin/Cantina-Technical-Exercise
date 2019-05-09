package main;

import java.util.Scanner;

public class CantinaMain {
	public static void main(String args[]) {
		FileReader fr = new FileReader(args[0]);
		Scanner in = new Scanner(System.in);
		try {
			while(true) {
				String selector = in.next();
				System.out.println(fr.find(selector));
			}
		} finally {
			in.close();
		}
	}
}
