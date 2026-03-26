package br.edu.iff.po.genetico;

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

        System.out.println("Melhor rota: " + formatarRota(melhor.getRota()));
        System.out.println("Custo total: " + melhor.getCusto());

        Individuo melhorComOpt = ag.doisOpt(melhor);

        System.out.println("\n--- Resultado apos 2-opt ---");
        System.out.println("Melhor rota: " + formatarRota(melhorComOpt.getRota()));
        System.out.println("Custo total: " + melhorComOpt.getCusto());
    }

    // Converte os índices internos (começando em 0) para os índices do enunciado (começando em 1)
    // Exemplo: [0, 2, 3, 1] → [1, 3, 4, 2]
    private static String formatarRota(int[] rota) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < rota.length; i++) {
            sb.append(rota[i] + 1);
            if (i < rota.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}