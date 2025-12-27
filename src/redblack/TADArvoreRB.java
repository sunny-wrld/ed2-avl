package redblack;

public class TADArvoreRB {

    private final No NIL = new No(0, Cor.preto);
    private No raiz = NIL;
    private long rotacoesEsquerda = 0;
    private long rotacoesDireita = 0;

    public TADArvoreRB() {
        NIL.esquerda = NIL;
        NIL.direita = NIL;
        NIL.pai = NIL;
    }

    public No getRaiz() { return raiz; }
    public No getNIL() { return NIL; }
    public long getRotacoesEsquerda() { return rotacoesEsquerda; }
    public long getRotacoesDireita() { return rotacoesDireita; }
    public long getRotacoesTotal() { return rotacoesEsquerda + rotacoesDireita; }

    private void rotacaoEsquerda(No x) {
        rotacoesEsquerda++;
        No y = x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != NIL) {
            y.esquerda.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == NIL) {
            raiz = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }
        y.esquerda = x;
        x.pai = y;
    }

    private void rotacaoDireita(No y) {
        rotacoesDireita++;
        No x = y.esquerda;
        y.esquerda = x.direita;
        if (x.direita != NIL) {
            x.direita.pai = y;
        }
        x.pai = y.pai;
        if (y.pai == NIL) {
            raiz = x;
        } else if (y == y.pai.direita) {
            y.pai.direita = x;
        } else {
            y.pai.esquerda = x;
        }
        x.direita = y;
        y.pai = x;
    }

    public No buscar(int chave) {
        No x = raiz;
        while (x != NIL) {
            if (chave == x.chave) return x;
            x = (chave < x.chave) ? x.esquerda : x.direita;
        }
        return NIL;
    }

    public void inserir(int chave) {
        No z = new No(chave, Cor.vermelho);
        z.esquerda = NIL;
        z.direita = NIL;
        No y = NIL;
        No x = raiz;
        while (x != NIL) {
            y = x;
            if (z.chave < x.chave) x = x.esquerda;
            else x = x.direita;
        }
        z.pai = y;
        if (y == NIL) {
            raiz = z;
        } else if (z.chave < y.chave) {
            y.esquerda = z;
        } else {
            y.direita = z;
        }
        rbInsertFixup(z);
    }

    private void rbInsertFixup(No z) {
        while (z.pai.cor == Cor.vermelho) {
            if (z.pai == z.pai.pai.esquerda) {
                No y = z.pai.pai.direita;
                if (y.cor == Cor.vermelho) {
                    z.pai.cor = Cor.preto;
                    y.cor = Cor.preto;
                    z.pai.pai.cor = Cor.vermelho;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) {
                        z = z.pai;
                        rotacaoEsquerda(z);
                    }
                    z.pai.cor = Cor.preto;
                    z.pai.pai.cor = Cor.vermelho;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                No y = z.pai.pai.esquerda;
                if (y.cor == Cor.vermelho) {
                    z.pai.cor = Cor.preto;
                    y.cor = Cor.preto;
                    z.pai.pai.cor = Cor.vermelho;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }
                    z.pai.cor = Cor.preto;
                    z.pai.pai.cor = Cor.vermelho;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = Cor.preto;
        raiz.pai = NIL;
    }

    public boolean remover(int chave) {
        No z = buscar(chave);
        if (z == NIL) return false;
        rbRemove(z);
        return true;
    }

    private void rbTransplant(No u, No v) {
        if (u.pai == NIL) {
            raiz = v;
        } else if (u == u.pai.esquerda) {
            u.pai.esquerda = v;
        } else {
            u.pai.direita = v;
        }
        v.pai = u.pai;
    }

    private No treeMinimum(No x) {
        while (x.esquerda != NIL) x = x.esquerda;
        return x;
    }

    private void rbRemove(No z) {
        No y = z;
        Cor yCorOriginal = y.cor;
        No x;
        if (z.esquerda == NIL) {
            x = z.direita;
            rbTransplant(z, z.direita);
        } else if (z.direita == NIL) {
            x = z.esquerda;
            rbTransplant(z, z.esquerda);
        } else {
            y = treeMinimum(z.direita);
            yCorOriginal = y.cor;
            x = y.direita;
            if (y.pai == z) {
                x.pai = y;
            } else {
                rbTransplant(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            rbTransplant(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }
        if (yCorOriginal == Cor.preto) {
            rbRemoveFixup(x);
        }
        if (raiz != NIL) {
            raiz.cor = Cor.preto;
            raiz.pai = NIL;
        }
    }

    private void rbRemoveFixup(No x) {
        while (x != raiz && x.cor == Cor.preto) {
            if (x == x.pai.esquerda) {
                No w = x.pai.direita;
                if (w.cor == Cor.vermelho) {
                    w.cor = Cor.preto;
                    x.pai.cor = Cor.vermelho;
                    rotacaoEsquerda(x.pai);
                    w = x.pai.direita;
                }
                if (w.esquerda.cor == Cor.preto && w.direita.cor == Cor.preto) {
                    w.cor = Cor.vermelho;
                    x = x.pai;
                } else {
                    if (w.direita.cor == Cor.preto) {
                        w.esquerda.cor = Cor.preto;
                        w.cor = Cor.vermelho;
                        rotacaoDireita(w);
                        w = x.pai.direita;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = Cor.preto;
                    w.direita.cor = Cor.preto;
                    rotacaoEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                No w = x.pai.esquerda;
                if (w.cor == Cor.vermelho) {
                    w.cor = Cor.preto;
                    x.pai.cor = Cor.vermelho;
                    rotacaoDireita(x.pai);
                    w = x.pai.esquerda;
                }
                if (w.direita.cor == Cor.preto && w.esquerda.cor == Cor.preto) {
                    w.cor = Cor.vermelho;
                    x = x.pai;
                } else {
                    if (w.esquerda.cor == Cor.preto) {
                        w.direita.cor = Cor.preto;
                        w.cor = Cor.vermelho;
                        rotacaoEsquerda(w);
                        w = x.pai.esquerda;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = Cor.preto;
                    w.esquerda.cor = Cor.preto;
                    rotacaoDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = Cor.preto;
    }

    public void verificarIntegridade() {
        if (raiz == NIL) return;
        if (raiz.cor != Cor.preto) {
            throw new IllegalStateException("Integridade falhou: raiz não é PRETA.");
        }
        if (NIL.cor != Cor.preto) {
            throw new IllegalStateException("Integridade falhou: NIL não é PRETA.");
        }
        int bh = verificarRec(raiz);
    }

    private int verificarRec(No no) {
        if (no == NIL) return 0;
        int left = verificarRec(no.esquerda);
        int right = verificarRec(no.direita);
        if (left != right) throw new IllegalStateException("Altura preta inconsistente");
        return left + (no.cor == Cor.preto ? 1 : 0);
    }

    public String emOrdem() {
        StringBuilder sb = new StringBuilder();
        emOrdemRec(raiz, sb);
        return sb.toString().trim();
    }

    private void emOrdemRec(No no, StringBuilder sb) {
        if (no == NIL) return;
        emOrdemRec(no.esquerda, sb);
        sb.append(no.chave).append("(").append(no.cor == Cor.vermelho ? "R" : "B").append(") ");
        emOrdemRec(no.direita, sb);
    }
}
