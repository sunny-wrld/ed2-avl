package avl;

import java.util.ArrayList;
import java.util.List;

public class ArvoreAVL {
	private No raiz;
	private int numRotacoes = 0;
	private int numRotacoesInsercao = 0;
	private int numRotacoesRemocao = 0;
	private boolean emRemocao = false;

	public ArvoreAVL() {
		this.raiz = null;
	}

	private int altura(No no) {
		return no == null ? 0 : no.getAltura();
	}

	private int fatorBalanceamento(No no) {
		if (no == null) {
			return 0;
		} else {
			return altura(no.getEsquerda()) - altura(no.getDireita());
		}
	}

	private void atualizarAltura(No no) {
		no.setAltura(1 + Math.max(altura(no.getEsquerda()), altura(no.getDireita())));
	}

	private No rotacaoDireita(No p) {
		if (emRemocao)
			numRotacoesRemocao++;
		else
			numRotacoesInsercao++;
		numRotacoes++;
		No q = p.getEsquerda();
		No temp = q.getDireita();

		q.setDireita(p);
		p.setEsquerda(temp);

		atualizarAltura(p);
		atualizarAltura(q);

		return q;
	}

	private No rotacaoEsquerda(No p) {
		if (emRemocao)
			numRotacoesRemocao++;
		else
			numRotacoesInsercao++;
		numRotacoes++;
		No q = p.getDireita();
		No temp = q.getEsquerda();

		q.setEsquerda(p);
		p.setDireita(temp);

		atualizarAltura(p);
		atualizarAltura(q);

		return q;
	}

	private No balancear(No no) {
		atualizarAltura(no);
		int fb = fatorBalanceamento(no);

		if (fb > 1 && fatorBalanceamento(no.getEsquerda()) >= 0) {
			return rotacaoDireita(no);
		}

		if (fb > 1 && fatorBalanceamento(no.getEsquerda()) < 0) {
			no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
			return rotacaoDireita(no);
		}

		if (fb < -1 && fatorBalanceamento(no.getDireita()) <= 0) {
			return rotacaoEsquerda(no);
		}

		if (fb < -1 && fatorBalanceamento(no.getDireita()) > 0) {
			no.setDireita(rotacaoDireita(no.getDireita()));
			return rotacaoEsquerda(no);
		}

		return no;
	}

	public void inserir(Compromisso compromisso) {
		emRemocao = false;
		raiz = inserirRec(raiz, compromisso);
	}

	private No inserirRec(No no, Compromisso compromisso) {
		if (no == null) {
			return new No(compromisso);
		}

		long chave = compromisso.getTimestamp();
		if (chave < no.getChave()) {
			no.setEsquerda(inserirRec(no.getEsquerda(), compromisso));
		} else if (chave > no.getChave()) {
			no.setDireita(inserirRec(no.getDireita(), compromisso));
		} else {
			no.getCompromisso().setDescricao(compromisso.getDescricao());
			return no;
		}

		return balancear(no);
	}

	public void remover(long timestamp) {
		emRemocao = true;
		raiz = removerRec(raiz, timestamp);
		emRemocao = false;
	}

	private No removerRec(No no, long timestamp) {
		if (no == null) {
			return null;
		}

		if (timestamp < no.getChave()) {
			no.setEsquerda(removerRec(no.getEsquerda(), timestamp));
		} else if (timestamp > no.getChave()) {
			no.setDireita(removerRec(no.getDireita(), timestamp));

		} else {
			if (no.getEsquerda() == null || no.getDireita() == null) {
				if (no.getEsquerda() != null) {
					no = no.getEsquerda();
				} else {
					no = no.getDireita();
				}
			} else {
				No sucessor = encontrarMinimo(no.getDireita());
				no.setCompromisso(sucessor.getCompromisso());
				no.setDireita(removerRec(no.getDireita(), sucessor.getChave()));
			}
		}

		if (no == null) {
			return null;
		} else {
			return balancear(no);
		}
	}

	private No encontrarMinimo(No no) {
		while (no.getEsquerda() != null) {
			no = no.getEsquerda();
		}
		return no;
	}

	public Compromisso buscar(long timestamp) {
		No no = buscarNo(raiz, timestamp);
		if (no != null) {
			return no.getCompromisso();
		} else {
			return null;
		}
	}

	private No buscarNo(No no, long timestamp) {
		if (no == null || timestamp == no.getChave()) {
			return no;
		}
		if (timestamp < no.getChave()) {
			return buscarNo(no.getEsquerda(), timestamp);
		} else {
			return buscarNo(no.getDireita(), timestamp);
		}
	}

	public void listarTodos() {
		if (raiz == null) {
			System.out.println("Agenda vazia.");
			return;
		}
		System.out.println("\nAgenda completa");
		listarEmOrdem(raiz);
		System.out.println();
	}

	private void listarEmOrdem(No no) {
		if (no != null) {
			listarEmOrdem(no.getEsquerda());
			System.out.println(no.getCompromisso());
			listarEmOrdem(no.getDireita());
		}
	}

	public void listarIntervalo(long inicio, long fim) {
		List<Compromisso> resultado = new ArrayList<>();
		listarIntervaloRec(raiz, inicio, fim, resultado);

		if (resultado.isEmpty()) {
			System.out.println("Nenhum compromisso encontrado.");
		} else {
			System.out.println("\nCompromissos no intervalo");
			for (Compromisso c : resultado) {
				System.out.println(c);
			}
			System.out.println();
		}
	}

	private void listarIntervaloRec(No no, long inicio, long fim, List<Compromisso> resultado) {
		if (no == null) {
			return;
		}

		if (no.getChave() > inicio) {
			listarIntervaloRec(no.getEsquerda(), inicio, fim, resultado);
		}

		if (no.getChave() >= inicio && no.getChave() <= fim) {
			resultado.add(no.getCompromisso());
		}

		if (no.getChave() < fim) {
			listarIntervaloRec(no.getDireita(), inicio, fim, resultado);
		}
	}

	public boolean estaVazia() {
		return raiz == null;
	}

	public int tamanho() {
		return contarNos(raiz);
	}

	private int contarNos(No no) {
		if (no == null) {
			return 0;
		}
		return 1 + contarNos(no.getEsquerda()) + contarNos(no.getDireita());
	}

	public int getNumRotacoes() {
		return numRotacoes;
	}

	public int getNumRotacoesInsercao() {
		return numRotacoesInsercao;
	}

	public int getNumRotacoesRemocao() {
		return numRotacoesRemocao;
	}
}
