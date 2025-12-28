package avl;

public class No {
    private Compromisso compromisso;
    private No esquerda;
    private No direita;
    private int altura;

    public No(Compromisso compromisso) {
        this.compromisso = compromisso;
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
    }

    public Compromisso getCompromisso() {
        return compromisso;
    }

    public void setCompromisso(Compromisso compromisso) {
        this.compromisso = compromisso;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public long getChave() {
        return compromisso.getTimestamp();
    }
}
