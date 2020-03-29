package controller;

import java.util.concurrent.Semaphore;

public class ThreadTriatlo extends Thread {

	private static int posicao = 0;

	// truque da matriz pra ordenar elaborado junto do luiz
	private static int[][] colocacao = new int[25][2];

	private Semaphore chegar = new Semaphore(1);
	private Semaphore armas = new Semaphore(5);

	private int pontosTiros = 0;
	private int nJogador;

	public ThreadTriatlo(int n) {
		this.nJogador = n;
	}

	@Override
	public void run() {
		triatlo();
	}

	private void triatlo() {
		correr();
		try {
			armas.acquire();
			atirar();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			armas.release();
		}
		pedalar();

		try {
			chegar.acquire();
			pontuacao();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			chegar.release();
		}
	}

	private void correr() {

		System.out.println("Jogador " + nJogador + " iniciou a corrida");

		int distanciaPercorrida = 0;

		while (distanciaPercorrida < 3000) {
			distanciaPercorrida += (int) ((Math.random() * 6) + 20);
			try {
				sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void atirar() {

		int tiros = 0;

		System.out.println("Jogador " + nJogador + " alcançou uma arma");

		while (tiros < 3) {
			try {
				pontosTiros += (int) (Math.random() * 11);
				sleep((int) ((Math.random() * 2501) + 500));
			} catch (Exception e) {
				e.printStackTrace();
			}
			tiros++;
		}
	}

	private void pedalar() {

		System.out.println("Jogador " + nJogador + " alcançou uma bicicleta");
		int distanciaPercorrida = 0;
		while (5000 > distanciaPercorrida) {
			distanciaPercorrida += (int) ((Math.random() * 11) + 30);
			try {
				sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void pontuacao() {

		int pontosTotais = 250;
		for (int i = 0; i < 25; i++) {
			if (i == posicao) {
				colocacao[i][0] = nJogador;
				colocacao[i][1] = pontosTotais + pontosTiros;
			}
			pontosTotais -= 10;
		}
		posicao++;
		if (posicao == 25) {
			organizar();
		}

	}

	private void organizar() {

		for (int i = 0; i < colocacao.length; i++) {
			for (int j = i + 1; j < colocacao.length; j++) {
				if (colocacao[i][1] < colocacao[j][1]) {
					int[][] aux = colocacao;
					colocacao[i][0] = colocacao[j][0];
					colocacao[i][1] = colocacao[j][1];
					colocacao[j][0] = aux[i][0];
					colocacao[j][1] = aux[i][1];
				}
			}
		}

		for (int i = 0; i < colocacao.length; i++) {

			System.out.println("Jogador " + colocacao[i][0] + "\n PONTOS: " + colocacao[i][1]);

		}

	}

}
