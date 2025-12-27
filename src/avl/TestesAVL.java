package avl;

public class TestesAVL {
    public static void main(String[] args) {
        TADAgenda avl = new TADAgenda();
        long[] inserir = {41, 38, 31, 12, 19, 8};
        long inicio, fim;

        inicio = System.nanoTime();
        for (long v : inserir) {
            avl.inserir(new Compromisso(v, "teste"));
        }
        fim = System.nanoTime();
        long tempoInsercao = fim - inicio;
        System.out.println("Tempo de inserção AVL: " + tempoInsercao + " ns");
        System.out.println("Rotações AVL: " + avl.getNumRotacoes());

        inicio = System.nanoTime();
        for (long v : inserir) {
            avl.buscar(v);
        }
        fim = System.nanoTime();
        long tempoBusca = fim - inicio;
        System.out.println("Tempo de busca AVL: " + tempoBusca + " ns");

        inicio = System.nanoTime();
        for (long v : inserir) {
            avl.remover(v);
        }
        fim = System.nanoTime();
        long tempoRemocao = fim - inicio;
        System.out.println("Tempo de remoção AVL: " + tempoRemocao + " ns");
    }
}
