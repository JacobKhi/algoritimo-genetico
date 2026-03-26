package br.edu.iff.po.genetico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/*
    Integrantes:
    Gustavo Jacob Ribeiro, 
    Jorge Pozes Pereira Neto, 
    José Guilherme Barcelos Borges, 
    Marcos Gabriel Ramos França
*/
public class Grafo {

    private int numeroCidades;
    private int numeroArestas;
    private int[][] distancias;

    public Grafo(String caminhoArquivo) {

        montarDistancias(caminhoArquivo);

    }

    private void montarDistancias(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.isEmpty())
            throw new IllegalArgumentException("O caminho do arquivo nao pode ser nulo ou em branco");

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));

            StringTokenizer linha = new StringTokenizer(leitor.readLine());
            numeroCidades = Integer.parseInt(linha.nextToken());
            numeroArestas = Integer.parseInt(linha.nextToken());

            distancias = new int[numeroCidades][numeroCidades];

            for (int i = 0; i < numeroCidades; i++) {
                linha = new StringTokenizer(leitor.readLine());

                for (int j = 0; j < numeroCidades; j++) {
                    distancias[i][j] = Integer.parseInt(linha.nextToken());
                }
            }

            leitor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getNumeroCidades() {
        return this.numeroCidades;
    }

    public int getNumeroArestas() {
        return this.numeroArestas;
    }

    public int getDistancia(int i, int j) {
        return this.distancias[i][j];
    }

}
