package br.edu.iff.po.genetico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
    Integrantes:
    Gustavo Jacob Ribeiro,
    Jorge Pozes Pereira Neto,
    José Guilherme Barcelos Borges,
    Marcos Gabriel Ramos França
*/
public class Verificador {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java Verificador <grafo.txt> <solucao.txt>");
            return;
        }

        Grafo grafo = new Grafo(args[0]);

        if (grafo.getNumeroCidades() == 0) {
            System.out.println("Erro: nao foi possivel ler o arquivo de grafo: " + args[0]);
            return;
        }

        int custoDeclado;
        int[] vertices;

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(args[1]));
            custoDeclado = Integer.parseInt(leitor.readLine().trim());
            String[] partes = leitor.readLine().trim().split("\\s+");
            vertices = new int[partes.length];
            for (int i = 0; i < partes.length; i++) {
                vertices[i] = Integer.parseInt(partes[i]);
            }
            leitor.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler solucao.txt: " + e.getMessage());
            return;
        }

        System.out.println("=== Verificacao da Solucao ===");

        // Validação 1: começa e termina em 1
        boolean ciclovalido = vertices.length > 1
                && vertices[0] == 1
                && vertices[vertices.length - 1] == 1;
        System.out.println("Ciclo comeca e termina no vertice 1: " + (ciclovalido ? "OK" : "ERRO"));

        // Validação 2: sem vértices repetidos (ignora o 1 final)
        boolean semRepetidos = true;
        String erroRepetido = "";
        int n = grafo.getNumeroCidades();
        Set<Integer> vistos = new HashSet<>();
        for (int i = 0; i < vertices.length - 1; i++) {
            if (!vistos.add(vertices[i])) {
                semRepetidos = false;
                erroRepetido = " (vertice " + vertices[i] + " repetido)";
                break;
            }
        }
        if (semRepetidos && vistos.size() != n) {
            semRepetidos = false;
            erroRepetido = " (quantidade de vertices incorreta: esperado " + n + ", encontrado " + vistos.size() + ")";
        }
        System.out.println("Vertices repetidos: " + (semRepetidos ? "OK" : "ERRO" + erroRepetido));

        // Validação 3: custo correto
        int custoCalculado = 0;
        for (int i = 0; i < vertices.length - 1; i++) {
            int de = vertices[i] - 1;
            int para = vertices[i + 1] - 1;
            custoCalculado += grafo.getDistancia(de, para);
        }
        System.out.println("Custo declarado:  " + custoDeclado);
        System.out.println("Custo calculado:  " + custoCalculado);
        boolean custoCorreto = custoDeclado == custoCalculado;
        System.out.println("Custo correto: " + (custoCorreto ? "OK" : "ERRO"));

        System.out.println();
        boolean valida = ciclovalido && semRepetidos && custoCorreto;
        System.out.println("Solucao: " + (valida ? "VALIDA" : "INVALIDA"));
    }

}
