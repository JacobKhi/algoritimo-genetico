package br.edu.iff.po.genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
    Integrantes:
    Gustavo Jacob Ribeiro, 
    Jorge Pozes Pereira Neto, 
    José Guilherme Barcelos Borges, 
    Marcos Gabriel Ramos França
*/
public class Populacao {

    private Individuo[] individuos;
    private Random random = new Random();

    public Populacao(int tamanho, Grafo grafo) {
        criarIndividuos(tamanho, grafo);
    }

    public Populacao(Individuo[] individuos, Grafo grafo) {
        this.individuos = individuos;
    }

    private void criarIndividuos(int tamanho, Grafo grafo) {
        if (tamanho < 0)
            throw new IllegalArgumentException("O tamanho nao pode ser negativo");

        if (grafo == null)
            throw new IllegalArgumentException("O grafo nao pode ser null");

        individuos = new Individuo[tamanho];

        for (int i = 0; i < tamanho; i++) {
            int[] rota = gerRotaAleatoria(grafo.getNumeroCidades());
            individuos[i] = new Individuo(rota, grafo);
        }
    }

    private int[] gerRotaAleatoria(int numeroDeCidades) {
        List<Integer> cidades = new ArrayList<>();

        for (int i = 0; i < numeroDeCidades; i++) {
            cidades.add(i);
        }

        // Embaralha o array
        Collections.shuffle(cidades, random);

        int[] rota = new int[numeroDeCidades];
        for (int i = 0; i < numeroDeCidades; i++) {
            rota[i] = cidades.get(i);
        }

        return rota;
    }

    public Individuo getIndividuo(int indice) {
        return individuos[indice];
    }

    public int getTamanho() {
        return individuos.length;
    }

}
