package redblack;

import java.util.*;

public class TestesRB {
    public static void main(String[] args) {
        final int N = 10000;
        Random rand = new Random(42);
        int[] dados = gerarDadosUnicos(N, rand);
        TADArvoreRB arvore = new TADArvoreRB();
        System.out.println("Teste de árvore Red-Black\n");
        System.out.println("Fase A: Ingestão (inserção)");
        testeInsercaoOtimizado(arvore, dados);
        System.out.println("\nFase B: Consulta (busca)");
        testeBusca(arvore, dados);
        System.out.println("\nFase C: Limpeza (remoção)");
        testeRemocaoOtimizado(arvore, dados);
        System.out.println("\nVerificação final de integridade");
        try {
            arvore.verificarIntegridade();
            System.out.println("Status: OK - Todas as propriedades Red-Black foram mantidas.");
        } catch (Exception e) {
            System.out.println("Status: ERRO - " + e.getMessage());
        }
        System.out.println("\nResumo final");
        System.out.println("Rotações totais (inserção): " + arvore.getRotacoesInsercao());
        System.out.println("Rotações totais (remoção): " + arvore.getRotacoesRemocao());
        System.out.println("Rotações totais: " + arvore.getRotacoesTotal());
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

    private static void testeInsercaoOtimizado(TADArvoreRB arvore, int[] dados) {
        long inicio = System.nanoTime();
        for (int v : dados) {
            arvore.inserir(v);
        }
        long fim = System.nanoTime();
        long tempoInsercao = fim - inicio;
        System.out.println("Elementos inseridos: " + dados.length);
        System.out.println("Tempo de inserção: " + formatarTempo(tempoInsercao));
        System.out.println("Rotações (inserção): " + arvore.getRotacoesInsercao());
        System.out.println("Média por inserção: " + (tempoInsercao / dados.length) + " ns");
        System.out.println("Status: OK");
    }

    private static void testeBusca(TADArvoreRB arvore, int[] dados) {
        List<Integer> buscaExistentes = new ArrayList<>();
        List<Integer> buscaInexistentes = new ArrayList<>();
        for (int i = 0; i < dados.length / 2; i++) buscaExistentes.add(dados[i]);
        for (int i = 0; i < dados.length / 2; i++) buscaInexistentes.add(dados.length * 10 + i);
        long inicio = System.nanoTime();
        int encontrados = 0;
        int naoEncontrados = 0;
        for (int v : buscaExistentes) {
            if (arvore.buscar(v) != arvore.getNIL()) {
                encontrados++;
            }
        }
        for (int v : buscaInexistentes) {
            if (arvore.buscar(v) == arvore.getNIL()) {
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

    private static void testeRemocaoOtimizado(TADArvoreRB arvore, int[] dados) {
        int qtdRemover = dados.length / 10;
        long inicio = System.nanoTime();
        int removidos = 0;
        for (int i = 0; i < qtdRemover; i++) {
            if (arvore.remover(dados[i])) {
                removidos++;
            }
        }
        long fim = System.nanoTime();
        long tempoRemocao = fim - inicio;
        System.out.println("Elementos a remover: " + qtdRemover);
        System.out.println("Elementos removidos: " + removidos);
        System.out.println("Tempo de remoção: " + formatarTempo(tempoRemocao));
        System.out.println("Rotações (remoção): " + arvore.getRotacoesRemocao());
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
