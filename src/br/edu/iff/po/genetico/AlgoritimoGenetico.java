package br.edu.iff.po.genetico;

import java.util.Arrays;
import java.util.Random;

/*
    Integrantes:
    Gustavo Jacob Ribeiro, 
    Jorge Pozes Pereira Neto, 
    José Guilherme Barcelos Borges, 
    Marcos Gabriel Ramos França
*/
public class AlgoritimoGenetico {

    private Grafo grafo;
    private Random random = new Random();
    private static final int TORNEIO_K = 3;

    public AlgoritimoGenetico(Grafo grafo) {
        setGrafo(grafo);
    }

    private Individuo selecaoPorTorneio(Populacao populacao) {
        Individuo melhorIndividuo = null;

        for (int i = 0; i < TORNEIO_K; i++) {
            int indiceAleatorio = random.nextInt(populacao.getTamanho());
            Individuo candidato = populacao.getIndividuo(indiceAleatorio);

            if (melhorIndividuo == null || candidato.getCusto() < melhorIndividuo.getCusto()) {
                melhorIndividuo = candidato;
            }
        }

        return melhorIndividuo;
    }

    private Individuo orderCrossover(Individuo pai1, Individuo pai2) {
        int tamanho = pai1.getRota().length;
        int[] filho = new int[tamanho];
        Arrays.fill(filho, -1);

        int ponto1 = random.nextInt(tamanho);
        int ponto2 = random.nextInt(tamanho);

        if (ponto1 > ponto2) {
            int temp = ponto1;
            ponto1 = ponto2;
            ponto2 = temp;
        }

        // Copia o trcho do pai1 pro filho
        for (int i = ponto1; i <= ponto2; i++) {
            filho[i] = pai1.getRota()[i];
        }

        // Preenche o restante com a ordem do pai2
        boolean[] presente = new boolean[tamanho];
        for (int i = ponto1; i <= ponto2; i++) {
            presente[filho[i]] = true;
        }

        int indiceFilho = (ponto2 + 1) % tamanho;
        int indicePai2 = (ponto2 + 1) % tamanho;

        while (filho[indiceFilho] == -1) {
            int cidade = pai2.getRota()[indicePai2];

            if (!presente[cidade]) {
                filho[indiceFilho] = cidade;
                presente[cidade] = true;
                indiceFilho = (indiceFilho + 1) % tamanho;
            }

            indicePai2 = (indicePai2 + 1) % tamanho;
        }

        return new Individuo(filho, grafo);
    }

    private Individuo inverseMutation(Individuo individuo) {
        int[] rota = individuo.getRota().clone();
        int tamanho = rota.length;

        int ponto1 = random.nextInt(tamanho);
        int ponto2 = random.nextInt(tamanho);

        if (ponto1 > ponto2) {
            int temp = ponto1;
            ponto1 = ponto2;
            ponto2 = temp;
        }

        // Inverte o trecho entre ponto1 e ponto2
        while (ponto1 < ponto2) {
            int temp = rota[ponto1];
            rota[ponto1] = rota[ponto2];
            rota[ponto2] = temp;
            ponto1++;
            ponto2--;
        }

        return new Individuo(rota, grafo);
    }

    private Individuo[] selecionarElite(Populacao populacao, int quantidadeDeElite) {
        Individuo[] todos = new Individuo[populacao.getTamanho()];
        for (int i = 0; i < populacao.getTamanho(); i++) {
            todos[i] = populacao.getIndividuo(i);
        }
        Arrays.sort(todos, (a, b) -> Integer.compare(a.getCusto(), b.getCusto()));
        return Arrays.copyOfRange(todos, 0, quantidadeDeElite);
    }

    public Individuo evoluir(
            Populacao populacao,
            int numeroGeracoes,
            double taxaCruzamento,
            double taxaMutacao,
            int quantidadeElite
    ) {
        Populacao populacaoAtual = populacao;
        int geracoesSemMelhora = 0;
        int melhorCustoGlobal = selecionarElite(populacaoAtual, 1)[0].getCusto();
        double taxaMutacaoAtual = taxaMutacao;

        for (int geracao = 0; geracao < numeroGeracoes; geracao++) {
            Individuo[] novaPopulacao = new Individuo[populacaoAtual.getTamanho()];

            // preserva a elite
            Individuo[] elite = selecionarElite(populacaoAtual, quantidadeElite);
            for (int i = 0; i < quantidadeElite; i++) {
                novaPopulacao[i] = elite[i];
            }

            // preenche o restante com filhos
            for (int i = quantidadeElite; i < novaPopulacao.length; i++) {
                Individuo pai1 = selecaoPorTorneio(populacaoAtual);
                Individuo pai2;
                do {
                    pai2 = selecaoPorTorneio(populacaoAtual);
                } while (pai2 == pai1);

                Individuo filho;

                // aplica cruzamento
                if (random.nextDouble() < taxaCruzamento) {
                    filho = orderCrossover(pai1, pai2);
                } else {
                    filho = pai1;
                }

                // aplica mutação
                if (random.nextDouble() < taxaMutacaoAtual) {
                    filho = inverseMutation(filho);
                }

                // refina o filho com busca local
                filho = doisOpt(filho);

                novaPopulacao[i] = filho;
            }

            populacaoAtual = new Populacao(novaPopulacao, grafo);

            // atualiza mutação adaptativa
            int melhorCustoGeracao = selecionarElite(populacaoAtual, 1)[0].getCusto();
            if (melhorCustoGeracao < melhorCustoGlobal) {
                melhorCustoGlobal = melhorCustoGeracao;
                geracoesSemMelhora = 0;
                taxaMutacaoAtual = taxaMutacao;
            } else {
                geracoesSemMelhora++;
                if (geracoesSemMelhora >= 50) {
                    taxaMutacaoAtual = Math.min(taxaMutacaoAtual * 2, 0.5);
                }
            }
        }

        return selecionarElite(populacaoAtual, 1)[0];
    }

    public Individuo doisOpt(Individuo individuo) {
        int[] melhorRota = individuo.getRota().clone();
        int melhorCusto = individuo.getCusto();
        boolean melhorou = true;

        while (melhorou) {
            melhorou = false;

            for (int i = 0; i < melhorRota.length - 1; i++) {
                for (int j = i + 1; j < melhorRota.length; j++) {

                    // inverte o trecho entre i e j
                    int[] novaRota = melhorRota.clone();
                    int inicio = i;
                    int fim = j;
                    while (inicio < fim) {
                        int temp = novaRota[inicio];
                        novaRota[inicio] = novaRota[fim];
                        novaRota[fim] = temp;
                        inicio++;
                        fim--;
                    }

                    Individuo novoIndividuo = new Individuo(novaRota, grafo);

                    if (novoIndividuo.getCusto() < melhorCusto) {
                        melhorRota = novaRota;
                        melhorCusto = novoIndividuo.getCusto();
                        melhorou = true;
                    }
                }
            }
        }

        return new Individuo(melhorRota, grafo);
    }

    private void setGrafo(Grafo grafo) {
        if (grafo == null)
            throw new IllegalArgumentException("O grafo nao pode ser nulo");
        this.grafo = grafo;
    }

}