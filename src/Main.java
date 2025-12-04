public class Main {
    public static void main(String[] args) {
        tad t = new tad();
        tad.AVL arvore = t.new AVL();

        arvore.inserir(100);
        arvore.inserir(80);
        arvore.inserir(60);
        arvore.inserir(40);
        arvore.inserir(20);
        arvore.inserir(70);
        arvore.inserir(30);
        arvore.inserir(50);
        arvore.inserir(35);
        arvore.inserir(45);
        arvore.inserir(55);
        arvore.inserir(75);
        arvore.inserir(65);
        arvore.inserir(73);
        arvore.inserir(77);
        arvore.inserir(99);

        System.out.println("Em ordem:");
        arvore.imprimir();

        System.out.println("Contem 100? " + arvore.contem(100));
        System.out.println("Contem 101? " + arvore.contem(101));

        arvore.remover(60);
        System.out.println("Aps remover 60:");
        arvore.imprimir();
    }
}
