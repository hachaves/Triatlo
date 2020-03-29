package view;

import controller.ThreadTriatlo;

public class Principal {

	public static void main(String[] args) {
		
		for (int i = 1; i <= 25; i++) {
			Thread t = new ThreadTriatlo(i);
			t.start();
		}
	}

}
