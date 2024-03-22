package caixeiroviajante;

import java.util.*;

public class CaixeiroViajante {

    static ArrayList<ArrayList<Integer>> populacaolimpa = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> populacao = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> filhos = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> pai = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> mae = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> elite = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> osmelhores = new ArrayList<ArrayList<Integer>>();
    static ArrayList<ArrayList<Integer>> osalpha = new ArrayList<ArrayList<Integer>>();
    
    //===== set config =====
    static int geracoes = 600; // número de gerações
    static int tampop = 1000;//tamanho da população
    static float taxaMutacao = 0.6f;//taxa de mutação
    static float taxaSobrev = 0.1f; //taxa de sobrevivência
    
    static int swap = 0, invert = 0;
    
    static ArrayList<Integer> criarpop(int tamanho) {

        ArrayList<Integer> individuo = new ArrayList<>();

        for (int i = 0; i < tamanho; i++) {
            individuo.add(i);
        }
        Collections.shuffle(individuo);

        return individuo;
    }

    static void separapopulacao(int tamanhopop) {

        int aux = (tamanhopop / 2);
        int aux2 = aux;

        for (int i = 0; i < aux; i++) {
            pai.add(populacao.get(i));
        }

        for (int i = 0; i < aux; i++) {
            mae.add(populacao.get(aux2));
            aux2++;
        }

    }

    static void cruzamento(int tamanhopop, int escolha) {

        int genoma;
        int j = 0;

        for (int i = 0; i < tamanhopop / 2; i++) {

            ArrayList<Integer> novoindividuo = new ArrayList<>();

            for (int k = 0; k < escolha / 2; k++) {
                genoma = pai.get(j).get(k);
                novoindividuo.add(genoma);
            }
            for (int k = (escolha / 2); k < escolha; k++) {
                genoma = mae.get(j).get(k);
                novoindividuo.add(genoma);
            }
            filhos.add(novoindividuo);
            j++;

        }

    }

    static void slot(int escolha, ArrayList<ArrayList<Integer>> vetor) {

        for (int j = 0; j < vetor.size(); j++) {

            if (vetor.get(j).size() == escolha) {
                vetor.get(j).add(0);
            }

        }

    }

    static void mutacaoSwap(float taxaMutacao, int escolha, int tamanhopop) {

        swap++;
        
        populacao.addAll(filhos); // juntar pais e filhos no mesmo array

        int aux = (int) (tamanhopop * taxaMutacao);
        ArrayList<Integer> indmut = new ArrayList<>();

        for (int i = 0; i < aux; i++) {
            Random aleatorio = new Random();
            int valor = aleatorio.nextInt(escolha);
            indmut.add(valor);
        }

        for (int i = 0; i < indmut.size(); i++) {
            Random aleatorio1 = new Random();
            int valor1 = aleatorio1.nextInt(escolha); // quantidade de numeros que vao ser mutados
            int valor2 = aleatorio1.nextInt(escolha); // posicao do numero que vai ser mudado
            Collections.swap(populacao.get(indmut.get(i)), valor1, valor2);
        }

    }
    
    static void mutacaoInvert(float taxaMutacao, int escolha, int tamanhopop){
        
        invert++;
        
        populacao.addAll(filhos); // juntar pais e filhos no mesmo array

        int aux = (int) (tamanhopop * taxaMutacao);
        ArrayList<Integer> indmut = new ArrayList<>();
        
        for (int i = 0; i < aux; i++) {
            Random aleatorio = new Random();
            int valor = aleatorio.nextInt(escolha);
            indmut.add(valor);
        }
        
        for (int i = 0; i < indmut.size(); i++) {
            
            Collections.reverse(populacao.get(indmut.get(i)));
            Collections.swap(populacao.get(indmut.get(i)), 0,escolha);
            int x=0;
            for (int j = 0; j < escolha-1; j++) {
                x++;
                Collections.swap(populacao.get(indmut.get(i)), j,x);
            }
        }
        
    }

    static void validarpop(int escolha) {

        for (int aux = 0; aux < populacao.size(); aux++) {
            int flag = 0;
            for (int n = 0; n < escolha; n++) {
                for (int j = n + 1; j < escolha; j++) {

                    if (populacao.get(aux).get(n).equals(populacao.get(aux).get(j))) {
                        flag++;
                    }
                }
            }
            if (flag == 0) {
                populacaolimpa.add(populacao.get(aux));
            }
        }

    }

    static void fitness(int graph[][], int escolha) {

        //int valorfinal = 0;
        for (int i = 0; i < populacaolimpa.size(); i++) {
            int valorfinal = 0;
            int aux = 0;

            for (int j = 0; j < escolha - 1; j++) {
                //System.out.println(graph[populacao.get(i).get(j)][populacao.get(i).get(j + 1)]);
                aux = aux + graph[populacaolimpa.get(i).get(j)][populacaolimpa.get(i).get(j + 1)];
            }

            //System.out.println(graph[populacaolimpa.get(i).get(escolha - 1)][populacaolimpa.get(0).get(0)]);
            valorfinal = aux + graph[populacaolimpa.get(i).get(escolha - 1)][populacaolimpa.get(i).get(0)];

            //System.out.println(populacaolimpa.get(i).get(escolha - 1)+" "+populacaolimpa.get(i).get(0)+ " posicao "+i);// pos final do vetor
            populacaolimpa.get(i).set(escolha, valorfinal); //substituir uma pos e nao adicionar 
            //populacaolimpa.get(i).add(valorfinal);
            //valorfinal = 0;
        }

    }

    static void Bubble(int escolha){
        
        for (int i = 0; i < populacaolimpa.size(); i++) {
            for (int j = 0; j < populacaolimpa.size(); j++) {
                if (populacaolimpa.get(i).get(escolha) <= populacaolimpa.get(j).get(escolha)) {
                    ArrayList<Integer> aux = populacaolimpa.get(i);
                    populacaolimpa.set(i, populacaolimpa.get(j));
                    populacaolimpa.set(j,aux);
                }
            }
        }
        
        
        elite.addAll(populacaolimpa);
        
    }

    static ArrayList<Integer> createCopy(ArrayList<Integer> orginal) {

        ArrayList<Integer> copy = new ArrayList<Integer>();
        for (Integer s : orginal) {
            copy.add(s);
        }
        return copy;
    }
       
    static void OrdenaMelhores(int escolha){
        
        for (int i = 0; i < osmelhores.size(); i++) {
            for (int j = 0; j < osmelhores.size(); j++) {
                if (osmelhores.get(i).get(escolha) <= osmelhores.get(j).get(escolha)) {
                    ArrayList<Integer> aux = osmelhores.get(i);
                    osmelhores.set(i, osmelhores.get(j));
                    osmelhores.set(j,aux);
                }
            }
        }
        
        
        osalpha.addAll(osmelhores);
        
    }
    

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        int escolha = 0;

        FileManager fileManager = new FileManager();
        ArrayList<String> text = fileManager.stringReader("./data/avaliacao.txt");

        System.out.println("Escolha o tamanho da matriz: (max. 100)");
        escolha = reader.nextInt();

        int nVertex = escolha;
        int graph[][] = null;

        for (int i = 0; i <= nVertex; i++) {
            String line = text.get(i);
            if (i == 0) {
                //nVertex = Integer.parseInt(line.trim());
                graph = new int[nVertex][nVertex];
            } else {
                int oriVertex = Integer.parseInt(line.split(" ")[0]);
                String splits[] = line.substring(line.indexOf(" "), line.length()).split(";");
                for (String part : splits) {
                    String edgeData[] = part.split("-");
                    int targetVertex = Integer.parseInt(edgeData[0].trim());
                    int weight = Integer.parseInt(edgeData[1]);

                    /*
                        ADICIONAR A ARESTA À REPRESENTAÇÃO
                     */
                    if (oriVertex > nVertex - 1 || targetVertex > nVertex - 1) {

                    } else {
                        graph[oriVertex][targetVertex] = weight;
                        graph[targetVertex][oriVertex] = weight; // comentar para direc.
                    }

                }
            }
        }

        // mostrar o graph
        System.out.println("Matriz gerada :");
//        for (int i = 0; i < nVertex; i++) {
//            for (int j = 0; j < nVertex; j++) {
//                System.out.print(graph[i][j] + " ");
//            }
//            System.out.println("\n");
//        }

        

        long tempoinicial = System.currentTimeMillis();
        int aux = 1024 * 1024;
        Runtime runtime = Runtime.getRuntime();

        //EVOLUCAO
        for (int i = 0; i < geracoes; i++) {

            //adicionando individuo no Array da população com tampop de tamanho
            for (int j = populacao.size(); j < tampop; j++) {

                populacao.add(criarpop(escolha));
            }

            slot(escolha, populacao);

            //separar população criando 2 array aux ( PAI e MAE )
            separapopulacao(tampop);

            cruzamento(tampop, escolha); // gerar filhos

            slot(escolha, filhos);

            
            
            //aplicando mutacao em individuos aleatorios do array populacao
            mutacaoSwap(taxaMutacao, escolha, tampop); //mutação por swap
            //mutacaoInvert(taxaMutacao, escolha, tampop); //mutação por inversão
            
            

            //seleção --> verificar numeros repetidos, eliminar, calcular o fitness e selecionar os % melhores
            //trabalhar com o Array populacaolimpa
            validarpop(escolha);
            fitness(graph, escolha);
            populacao.clear(); // limpando a população inicial para prox. gerações.

            
            
            
            //trabalhar com o Array elite
            Bubble(escolha);
            

            int sobreviventes = (int) (tampop * taxaSobrev);
           

            // passando os melhores para prox. gen
            for (int j = 0; j < sobreviventes; j++) {
                populacao.add(elite.get(j));
                osmelhores.add(createCopy(elite.get(j))); // salvando em outro lugar da memoria para nao perder referencia
            }

            populacaolimpa.clear();
            pai.clear();
            mae.clear();
            elite.clear();
            //System.out.println(i);
        }
        
        //OrdenaOsMelhores(escolha);
        OrdenaMelhores(escolha);
        
        long tempofinal = System.currentTimeMillis() - tempoinicial;
        System.out.println("==============================");
        System.out.println("Configuracao :");
        System.out.println("==============================");
        
        if (swap > 0 && invert == 0) {
            System.out.println("Mutacao utilizada : Swap");
        }else if(invert > 0 && swap == 0){
            System.out.println("Mutacao utilizada : Inversao");
        }else{
            System.out.println("Mutacao utilizada : Swap e Inversao");
        }
        
        
        System.out.println("Geracoes : " + geracoes);
        System.out.println("Populacao inicial : " + tampop);
        System.out.println("Taxa de Mutacao : " + taxaMutacao);
        System.out.println("Taxa de sobrevivencia : " + taxaSobrev);
        System.out.println("Memoria gasta : " + (runtime.totalMemory() - runtime.freeMemory()) / aux + " MB");
        System.out.println("Tempo gasto : " + tempofinal + " ms");

        System.out.print("melhor individuo :");
        System.out.println(osalpha.get(0) + " posicao " + 0);

        System.out.println("Fitness = " + osalpha.get(0).get(escolha));

        

    }

}
