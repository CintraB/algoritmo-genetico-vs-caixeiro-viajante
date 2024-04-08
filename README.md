# Algoritmo Genético e Caixeiro Viajante

Este repositório contém implementações em Java de um algoritmo genético e do problema do caixeiro viajante.

## Algoritmo Genético

O arquivo `CaixeiroViajante.java` contém a implementação de um algoritmo genético para resolver o problema do caixeiro viajante.

### Funcionalidades

- Geração de população inicial aleatória.
- Cruzamento (recombinação) de indivíduos para gerar filhos.
- Mutação dos indivíduos para diversificação genética.
- Seleção dos melhores indivíduos para a próxima geração.
- Cálculo do fitness (custo da rota) para cada indivíduo.
- Utilização de configurações como número de gerações, tamanho da população, taxa de mutação e taxa de sobrevivência.

## Problema do Caixeiro Viajante

O arquivo `CaixeiroViajante.java` também contém uma implementação para resolver diretamente o problema do caixeiro viajante usando busca em profundidade.

### Funcionalidades

- Implementação da busca em profundidade para encontrar a rota de menor custo.
- Leitura de arquivos de teste para construir o grafo de cidades e distâncias.
- Cálculo do menor caminho entre as cidades.

## Arquivos de Teste

Os arquivos de teste utilizados para os problemas do caixeiro viajante estão localizados na pasta `./data`. Cada arquivo contém informações sobre as cidades e as distâncias entre elas.

## Relatório

Para mais informações, análises e resultados detalhados, consulte o arquivo `Readme.pdf` neste repositório. Ele contém gráficos, tabelas e casos de teste comparando as soluções dos algoritmos genético e de busca em profundidade para o problema do caixeiro viajante.
