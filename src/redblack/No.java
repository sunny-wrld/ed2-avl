package redblack;

public final class No {
    public int chave;
    public Cor cor;
    public No esquerda;
    public No direita;
    public No pai;

    public No(int chave, Cor cor) {
        this.chave = chave;
        this.cor = cor;
    }

    @Override
    public String toString() {
        return chave + "(" + (cor == Cor.vermelho ? "R" : "B") + ")";
    }
}
