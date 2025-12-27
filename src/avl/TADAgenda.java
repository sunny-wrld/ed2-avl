package avl;

import java.util.Scanner;

public class TADAgenda {
    private final ArvoreAVL agenda;
    private final Scanner scanner;

    public TADAgenda() {
        this.agenda = new ArvoreAVL();
        this.scanner = new Scanner(System.in);
    }

    private void exibirMenu() {
        System.out.println("\nSistema de Agenda");
        System.out.println("1. Adicionar compromisso");
        System.out.println("2. Remover compromisso");
        System.out.println("3. Buscar compromisso");
        System.out.println("4. Listar todos os compromissos");
        System.out.println("5. Consultar por intervalo de datas");
        System.out.println("6. Exibir estatisticas");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opcao: ");
    }

    private long converterParaTimestamp(String ano, String mes, String dia, String hora, String minuto) {
        return Long.parseLong(String.format("%s%s%s%s%s", ano, mes, dia, hora, minuto));
    }

    private void adicionarCompromisso() {
        System.out.println("\nAdicionar compromisso");

        try {
            System.out.print("Ano (AAAA): ");
            String ano = scanner.next();
            System.out.print("Mes (MM): ");
            String mes = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Dia (DD): ");
            String dia = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Hora (HH): ");
            String hora = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Minuto (MM): ");
            String minuto = String.format("%02d", Integer.parseInt(scanner.next()));

            scanner.nextLine();

            System.out.print("Descricao do compromisso: ");
            String descricao = scanner.nextLine();

            long timestamp = converterParaTimestamp(ano, mes, dia, hora, minuto);
            Compromisso compromisso = new Compromisso(timestamp, descricao);

            agenda.inserir(compromisso);
            System.out.println("\nCompromisso adicionado");
        } catch (Exception e) {
            System.out.println("\nErro ao adicionar compromisso");
            scanner.nextLine();
        }
    }

    private void removerCompromisso() {
        System.out.println("\nRemover compromisso");

        try {
            System.out.print("Ano (AAAA): ");
            String ano = scanner.next();
            System.out.print("Mes (MM): ");
            String mes = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Dia (DD): ");
            String dia = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Hora (HH): ");
            String hora = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Minuto (MM): ");
            String minuto = String.format("%02d", Integer.parseInt(scanner.next()));

            long timestamp = converterParaTimestamp(ano, mes, dia, hora, minuto);

            Compromisso existe = agenda.buscar(timestamp);
            if (existe != null) {
                agenda.remover(timestamp);
                System.out.println("\nCompromisso removido");
            } else {
                System.out.println("\nCompromisso nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\nErro ao remover compromisso.");
            scanner.nextLine();
        }
    }

    private void buscarCompromisso() {
        System.out.println("\nBuscar compromisso");

        try {
            System.out.print("Ano (AAAA): ");
            String ano = scanner.next();
            System.out.print("Mes (MM): ");
            String mes = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Dia (DD): ");
            String dia = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Hora (HH): ");
            String hora = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Minuto (MM): ");
            String minuto = String.format("%02d", Integer.parseInt(scanner.next()));

            long timestamp = converterParaTimestamp(ano, mes, dia, hora, minuto);
            Compromisso compromisso = agenda.buscar(timestamp);

            if (compromisso != null) {
                System.out.println("\nCompromisso encontrado:");
                System.out.println(compromisso);
            } else {
                System.out.println("\nNenhum compromisso encontrado");
            }
        } catch (Exception e) {
            System.out.println("\nErro ao buscar compromisso.");
            scanner.nextLine();
        }
    }

    private void listarTodos() {
        agenda.listarTodos();
    }

    private void consultarIntervalo() {
        System.out.println("\nConsultar por intervalo");

        try {
            System.out.println("Data/Hora inicial:");
            System.out.print("Ano (AAAA): ");
            String anoIni = scanner.next();
            System.out.print("Mes (MM): ");
            String mesIni = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Dia (DD): ");
            String diaIni = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Hora (HH): ");
            String horaIni = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Minuto (MM): ");
            String minIni = String.format("%02d", Integer.parseInt(scanner.next()));

            System.out.println("\nData/Hora final:");
            System.out.print("Ano (AAAA): ");
            String anoFim = scanner.next();
            System.out.print("Mes (MM): ");
            String mesFim = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Dia (DD): ");
            String diaFim = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Hora (HH): ");
            String horaFim = String.format("%02d", Integer.parseInt(scanner.next()));
            System.out.print("Minuto (MM): ");
            String minFim = String.format("%02d", Integer.parseInt(scanner.next()));

            long timestampInicio = converterParaTimestamp(anoIni, mesIni, diaIni, horaIni, minIni);
            long timestampFim = converterParaTimestamp(anoFim, mesFim, diaFim, horaFim, minFim);

            agenda.listarIntervalo(timestampInicio, timestampFim);
        } catch (Exception e) {
            System.out.println("\nErro.");
            scanner.nextLine();
        }
    }

    private void exibirEstatisticas() {
        System.out.println("\nEstatisticas da agenda");
        System.out.println("Total de compromissos: " + agenda.tamanho());
        if (agenda.estaVazia()) {
            System.out.println("Agenda vazia: Sim");
        } else {
            System.out.println("Agenda vazia: Nao");
        }
        System.out.println();
    }

    private void carregarDadosExemplo() {
        agenda.inserir(new Compromisso(202512161000L, "Algebra Linear"));
        agenda.inserir(new Compromisso(202512161400L, "Algebra Linear"));
        agenda.inserir(new Compromisso(202512171030L, "Banco de dados"));
        agenda.inserir(new Compromisso(202512171500L, "Banco de dados"));
        agenda.inserir(new Compromisso(202512180900L, "Web 1"));
        agenda.inserir(new Compromisso(202512181600L, "Web 1"));
        agenda.inserir(new Compromisso(202512191100L, "Ed2"));
        agenda.inserir(new Compromisso(202512191430L, "ED2"));
        agenda.inserir(new Compromisso(202512201000L, "SO"));

        System.out.println("Dados de pre cadastrados");
    }

    public void executar() {
        System.out.print("\nDeseja carregar dados pre cadastrados? (S/N): ");
        String resposta = scanner.next();
        if (resposta.equalsIgnoreCase("S")) {
            carregarDadosExemplo();
        }

        int opcao;
        do {
            exibirMenu();
            try {
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        adicionarCompromisso();
                        break;
                    case 2:
                        removerCompromisso();
                        break;
                    case 3:
                        buscarCompromisso();
                        break;
                    case 4:
                        listarTodos();
                        break;
                    case 5:
                        consultarIntervalo();
                        break;
                    case 6:
                        exibirEstatisticas();
                        break;
                    case 0:
                        System.out.println("\nEncerrando sistema");
                        break;
                    default:
                        System.out.println("\nOpcao invalida!.");
                }
            } catch (Exception e) {
                System.out.println("\nEntrada invalida!");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);

        scanner.close();
    }

    public void inserir(Compromisso compromisso) {
        agenda.inserir(compromisso);
    }

    public Compromisso buscar(long timestamp) {
        return agenda.buscar(timestamp);
    }

    public void remover(long timestamp) {
        agenda.remover(timestamp);
    }

    public int getNumRotacoes() {
        return agenda.getNumRotacoes();
    }

    public static void main(String[] args) {
        TADAgenda sistema = new TADAgenda();
        sistema.executar();
    }
}
