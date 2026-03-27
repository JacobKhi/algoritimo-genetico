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

## Saída

O resultado é salvo em `solucao.txt` no diretório de execução, com o seguinte formato:

```
<custo>
<vértice1> <vértice2> ... <vérticeN> <vértice1>
```

O ciclo parte e retorna ao vértice 1. Exemplo:

```
17
1 3 2 4 1
```

## Verificador de soluções

O `Verificador` recebe o arquivo do grafo e uma solução e checa se:

- O ciclo começa e termina no vértice 1
- Não há vértices repetidos
- O custo declarado está correto

**Execução:**

```bash
java -cp bin br.edu.iff.po.genetico.Verificador <grafo.txt> <solucao.txt>
```

**Exemplo:**

```bash
java -cp bin br.edu.iff.po.genetico.Verificador entrada.txt solucao.txt
```

**Saída esperada:**

```
=== Verificacao da Solucao ===
Ciclo comeca e termina no vertice 1: OK
Vertices repetidos: OK
Custo declarado:  17
Custo calculado:  17
Custo correto: OK

Solucao: VALIDA
```