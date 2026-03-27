package br.edu.iff.po.genetico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
    Integrantes:
    Gustavo Jacob Ribeiro, 
    Jorge Pozes Pereira Neto, 
    José Guilherme Barcelos Borges, 
    Marcos Gabriel Ramos França
*/
public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Uso: java GeneticTSP <arquivo.txt>");
            return;
        }

        Grafo grafo = new Grafo(args[0]);

        Populacao populacao = new Populacao(150, grafo);

        AlgoritimoGenetico ag = new AlgoritimoGenetico(grafo);

        Individuo melhor = ag.evoluir(populacao, 1000, 0.9, 0.02, 2);
        Individuo melhorComOpt = ag.doisOpt(melhor);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("solucao.txt"));
            writer.write(melhorComOpt.getCusto() + "\n");
            writer.write(formatarRota(melhorComOpt.getRota()) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Converte os índices internos (começando em 0) para os índices do enunciado (começando em 1)
    // Exemplo: [0, 2, 3, 1] → [1, 3, 4, 2]
    private static String formatarRota(int[] rota) {
        // Encontra a posição da cidade 0 (vértice 1) para rotacionar o ciclo
        int inicio = 0;
        for (int i = 0; i < rota.length; i++) {
            if (rota[i] == 0) {
                inicio = i;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rota.length; i++) {
            sb.append(rota[(inicio + i) % rota.length] + 1);
            sb.append(" ");
        }
        sb.append("1");
        return sb.toString();
    }

}