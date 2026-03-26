# Algoritmo Genético — Problema do Caixeiro Viajante

Trabalho T2 — Pesquisa Operacional — Bacharelado em Sistemas de Informação — IFF

## Requisitos

- Java JDK 11 ou superior

## Compilação

```bash
javac -d bin src/br/edu/iff/po/genetico/*.java
```

## Execução

```bash
java -cp bin br.edu.iff.po.genetico.Main <arquivo.txt>
```

**Exemplo:**

```bash
java -cp bin br.edu.iff.po.genetico.Main entrada.txt
```

## Formato do arquivo de entrada

A primeira linha deve conter o número de vértices e o número de arestas separados por espaço. As demais linhas armazenam a matriz de distâncias.

**Exemplo:**

```
4 6
0 7 1 3
7 0 5 8
1 5 0 6
3 8 6 0
```