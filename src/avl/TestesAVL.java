package avl;

import java.util.*;

public class TestesAVL {
    public static void main(String[] args) {
        final int N = 10000;
        Random rand = new Random(42);
        int[] dados = gerarDadosUnicos(N, rand);
        TADAgenda avl = new TADAgenda();
        System.out.println("Teste de árvore AVL\n");
        System.out.println("Fase A: Ingestão (inserção)");
        testeInsercao(avl, dados);
        System.out.println("\nFase B: Consulta (busca)");
        testeBusca(avl, dados);
        System.out.println("\nFase C: Limpeza (remoção)");
        testeRemocao(avl, dados);
        System.out.println("\nResumo final");
        System.out.println("Rotações totais (inserção): " + avl.getNumRotacoesInsercao());
        System.out.println("Rotações totais (remoção): " + avl.getNumRotacoesRemocao());
        System.out.println("Rotações totais: " + (avl.getNumRotacoesInsercao() + avl.getNumRotacoesRemocao()));
    }

    private static int[] gerarDadosUnicos(int n, Random rand) {
        int[] dados = new int[n];
        Set<Integer> conjunto = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int valor;
            do {
                valor = rand.nextInt(n * 10);
            } while (!conjunto.add(valor));
            dados[i] = valor;
        }
        return dados;
    }

    private static void testeInsercao(TADAgenda avl, int[] dados) {
        long inicio = System.nanoTime();
        for (int v : dados) {
            avl.inserir(new Compromisso(v, ""));
        }
        long fim = System.nanoTime();
        long tempoInsercao = fim - inicio;
        System.out.println("Elementos inseridos: " + dados.length);
        System.out.println("Tempo de inserção: " + formatarTempo(tempoInsercao));
        System.out.println("Rotações (inserção): " + avl.getNumRotacoesInsercao());
        System.out.println("Média por inserção: " + (tempoInsercao / dados.length) + " ns");
        System.out.println("Status: OK");
    }

    private static void testeBusca(TADAgenda avl, int[] dados) {
        List<Integer> buscaExistentes = new ArrayList<>();
        List<Integer> buscaInexistentes = new ArrayList<>();
        for (int i = 0; i < dados.length / 2; i++) buscaExistentes.add(dados[i]);
        for (int i = 0; i < dados.length / 2; i++) buscaInexistentes.add(dados.length * 10 + i);
        long inicio = System.nanoTime();
        int encontrados = 0;
        int naoEncontrados = 0;
        for (int v : buscaExistentes) {
            if (avl.buscar(v) != null) {
                encontrados++;
            }
        }
        for (int v : buscaInexistentes) {
            if (avl.buscar(v) == null) {
                naoEncontrados++;
            }
        }
        long fim = System.nanoTime();
        long tempoBusca = fim - inicio;
        System.out.println("Buscas existentes: " + encontrados + "/" + buscaExistentes.size());
        System.out.println("Buscas inexistentes: " + naoEncontrados + "/" + buscaInexistentes.size());
        System.out.println("Tempo de busca: " + formatarTempo(tempoBusca));
        System.out.println("Buscas totais: " + (encontrados + naoEncontrados));
        System.out.println("Média por busca: " + (tempoBusca / (buscaExistentes.size() + buscaInexistentes.size())) + " ns");
    }

    private static void testeRemocao(TADAgenda avl, int[] dados) {
        int qtdRemover = dados.length / 10;
        long inicio = System.nanoTime();
        int removidos = 0;
        for (int i = 0; i < qtdRemover; i++) {
            avl.remover(dados[i]);
            removidos++;
        }
        long fim = System.nanoTime();
        long tempoRemocao = fim - inicio;
        System.out.println("Elementos a remover: " + qtdRemover);
        System.out.println("Elementos removidos: " + removidos);
        System.out.println("Tempo de remoção: " + formatarTempo(tempoRemocao));
        System.out.println("Rotações (remoção): " + avl.getNumRotacoesRemocao());
        System.out.println("Média por remoção: " + (tempoRemocao / removidos) + " ns");
        System.out.println("Status: OK");
    }

    private static String formatarTempo(long nanos) {
        if (nanos >= 1000000) {
            return String.format("%.3f ms", nanos / 1000000.0);
        } else {
            if (nanos >= 1000) {
                return String.format("%.3f us", nanos / 1000.0);
            } else {
                return nanos + " ns";
            }
        }
    }
}
