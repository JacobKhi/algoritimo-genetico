package br.edu.iff.po.genetico;

/*
    Integrantes:
    Gustavo Jacob Ribeiro, 
    Jorge Pozes Pereira Neto, 
    José Guilherme Barcelos Borges, 
    Marcos Gabriel Ramos França
*/
public class Individuo {

    private int[] rota;
    private int custo;

    public Individuo(int[] rota, Grafo grafo) {
        setRota(rota);
        setCusto(grafo);
    }

    private int calcularCusto(Grafo grafo) {
        int total = 0;

        // soma a distancia entre cada cidade consecutiva
        for (int i = 0; i < rota.length - 1; i++) {
            total += grafo.getDistancia(rota[i], rota[i + 1]);
        }

        // volta para a cidade de origem
        total += grafo.getDistancia(rota[rota.length - 1], rota[0]);

        return total;
    }

    private void setRota(int[] rota) {
        if (rota == null || rota.length == 0)
            throw new IllegalArgumentException("A rota nao pode ser nula nem ter tamanho 0");

        this.rota = rota;
    }

    private void setCusto(Grafo grafo) {
        if (grafo == null)
            throw new IllegalArgumentException("O grafo nao pode ser nulo");

        this.custo = calcularCusto(grafo);
    }

    public int[] getRota() {
        return this.rota;
    }

    public int getCusto() {
        return this.custo;
    }
}