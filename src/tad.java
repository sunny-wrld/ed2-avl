public class tad {
    class No {
        int chave;
        No esq, dir;
        int altura;

        No(int c) {
            chave = c;
            altura = 1;
        }
    }

    class AVL {

        No raiz;

        int altura(No p) {
            if (p == null) return 0;
            return p.altura;
        }

        int fb(No p) {
            return altura(p.esq) - altura(p.dir);
        }

        No rotDir(No y) {
            No x = y.esq;
            No t2 = x.dir;
            x.dir = y;
            y.esq = t2;
            y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
            x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
            return x;
        }

        No rotEsq(No x) {
            No y = x.dir;
            No t2 = y.esq;
            y.esq = x;
            x.dir = t2;
            x.altura = 1 + Math.max(altura(x.esq), altura(x.dir));
            y.altura = 1 + Math.max(altura(y.esq), altura(y.dir));
            return y;
        }

        No inserirRec(No p, int chave) {
            if (p == null) return new No(chave);

            if (chave < p.chave)
                p.esq = inserirRec(p.esq, chave);
            else if (chave > p.chave)
                p.dir = inserirRec(p.dir, chave);
            else
                return p;

            p.altura = 1 + Math.max(altura(p.esq), altura(p.dir));
            int f = fb(p);

            if (f > 1 && chave < p.esq.chave) return rotDir(p);
            if (f < -1 && chave > p.dir.chave) return rotEsq(p);
            if (f > 1 && chave > p.esq.chave) {
                p.esq = rotEsq(p.esq);
                return rotDir(p);
            }
            if (f < -1 && chave < p.dir.chave) {
                p.dir = rotDir(p.dir);
                return rotEsq(p);
            }

            return p;
        }

        void inserir(int chave) {
            raiz = inserirRec(raiz, chave);
        }

        No buscar(No p, int chave) {
            if (p == null) return null;
            if (chave == p.chave) return p;
            if (chave < p.chave) return buscar(p.esq, chave);
            return buscar(p.dir, chave);
        }

        boolean contem(int chave) {
            return buscar(raiz, chave) != null;
        }

        No menor(No p) {
            while (p.esq != null) p = p.esq;
            return p;
        }

        No removerRec(No p, int chave) {
            if (p == null) return null;

            if (chave < p.chave)
                p.esq = removerRec(p.esq, chave);
            else if (chave > p.chave)
                p.dir = removerRec(p.dir, chave);
            else {
                if (p.esq == null || p.dir == null) {
                    No t = (p.esq != null) ? p.esq : p.dir;
                    p = t;
                } else {
                    No suc = menor(p.dir);
                    p.chave = suc.chave;
                    p.dir = removerRec(p.dir, suc.chave);
                }
            }

            if (p == null) return null;

            p.altura = 1 + Math.max(altura(p.esq), altura(p.dir));
            int f = fb(p);

            if (f > 1 && fb(p.esq) >= 0) return rotDir(p);
            if (f > 1 && fb(p.esq) < 0) {
                p.esq = rotEsq(p.esq);
                return rotDir(p);
            }
            if (f < -1 && fb(p.dir) <= 0) return rotEsq(p);
            if (f < -1 && fb(p.dir) > 0) {
                p.dir = rotDir(p.dir);
                return rotEsq(p);
            }

            return p;
        }

        void remover(int chave) {
            raiz = removerRec(raiz, chave);
        }

        void emOrdem(No p) {
            if (p == null) return;
            emOrdem(p.esq);
            System.out.print(p.chave + " ");
            emOrdem(p.dir);
        }

        void imprimir() {
            emOrdem(raiz);
            System.out.println();
        }
    }

}
