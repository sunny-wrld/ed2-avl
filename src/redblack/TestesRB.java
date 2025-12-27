package redblack;

public class TestesRB {
	public static void main(String[] args) {
		TADArvoreRB T = new TADArvoreRB();
		int[] inserir = { 41, 38, 31, 12, 19, 8 };
		long inicio, fim;

		inicio = System.nanoTime();
		for (int v : inserir) {
			T.inserir(v);
		}
		fim = System.nanoTime();
		long tempoInsercao = fim - inicio;
		System.out.println("Tempo de inserção RB: " + tempoInsercao + " ns");
		System.out.println("Rotações RB: " + T.getRotacoesTotal());

		inicio = System.nanoTime();
		for (int v : inserir) {
			T.buscar(v);
		}
		fim = System.nanoTime();
		long tempoBusca = fim - inicio;
		System.out.println("Tempo de busca RB: " + tempoBusca + " ns");

		inicio = System.nanoTime();
		for (int v : inserir) {
			T.remover(v);
		}
		fim = System.nanoTime();
		long tempoRemocao = fim - inicio;
		System.out.println("Tempo de remoção RB: " + tempoRemocao + " ns");
	}
}
