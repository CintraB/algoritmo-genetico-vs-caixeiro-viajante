package caixeiroviajante;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;



public class CaixeiroViajante {
    
    static Stack<Integer> rotas = new Stack<>();
    
    static int procura(int[][] graph, boolean[] v,int posAtu, int n, int cont, int custo, int resp)
    {
 
  
        if(rotas.isEmpty()){
            rotas.add(0);
        }
        
        
       //contador for igual ao numero de nos e for o ultimo ponto 
        if (cont == n && graph[posAtu][0] > 0)
        {
           
            resp = Math.min(resp, custo + graph[posAtu][0]);
            
            Stack<Integer> caminhos = new Stack<>();
            
            caminhos = rotas;
                       
            //System.out.println("Custo "+resp+" :"+caminhos); //mostrar os caminhos
            
            return resp;
        }
 
           
       
        //loop para percorrer a lista do nó atual e aumentando a contagem 
        
        for (int i = 0; i < n; i++)
        {
            if (v[i] == false && graph[posAtu][i] > 0)
            {
                // marcar visitado
                v[i] = true;
                
                rotas.add(i);
                
                resp = procura(graph, v, i, n, cont + 1, custo + graph[posAtu][i], resp);
 
                // marcar nó como nao visitado
                v[i] = false;

                rotas.pop();
                //System.out.println(rotas+"\n");
            }
        }
        
        
        return resp;
    }
    
    
    public static void main(String[] args) {
        
        Scanner reader = new Scanner(System.in);
        
        int escolha = 0;
        
        
        FileManager fileManager = new FileManager();
        ArrayList<String> text = fileManager.stringReader("./data/avaliacao.txt"); //mudar o _numero para escolher outro arquivo de teste
        
        System.out.println("Escolha o tamanho da matriz: (max. 100)");
        escolha = reader.nextInt();
        
       
        long tempoinicial = System.currentTimeMillis();
        int aux = 1024*1024;
        Runtime runtime = Runtime.getRuntime();
        
        int nVertex = escolha;
        int graph[][] = null;
        
        
        
        for (int i = 0; i <= nVertex; i++) {
            String line = text.get(i);
            if (i == 0){
                //nVertex = Integer.parseInt(line.trim());
                graph = new int[nVertex][nVertex];
            }
            else {
                int oriVertex = Integer.parseInt(line.split(" ")[0]);
                String splits[] = line.substring(line.indexOf(" "), line.length()).split(";");
                for (String part : splits){
                    String edgeData[] = part.split("-");
                    int targetVertex = Integer.parseInt(edgeData[0].trim());
                    int weight = Integer.parseInt(edgeData[1]);
                    
                    /*
                        ADICIONAR A ARESTA À REPRESENTAÇÃO
                    */


                    if (oriVertex > nVertex-1 || targetVertex > nVertex-1) {
                        
                    }else{
                      graph[oriVertex][targetVertex] = weight;
                      graph[targetVertex][oriVertex] = weight; // comentar para direc.
                    }



                }
            }
        }
        
        
        // mostrar o graph
        System.out.println("Matriz gerada ");
//        for (int i = 0; i < nVertex; i++) 
//        {
//            for (int j = 0; j < nVertex; j++) 
//            {
//                System.out.print(graph[i][j]+ " ");   
//            }
//              System.out.println("\n");
//        }
//        
        
        
        //passando numero de nós
        int n = nVertex;
        
        
        //array de boolean para ver quando o nó foi visitado ou não
        boolean[] v = new boolean[n];
        
        v[0] = true; // 0 para nó visitado
        
        int resp = Integer.MAX_VALUE;
        
        //achar o peso minimo
        resp = procura(graph, v, 0, n, 1, 0, resp);
        
        
   
        
        long tempofinal = System.currentTimeMillis() - tempoinicial;
        System.out.println("Tempo gasto : "+tempofinal+" ms");
        System.out.println("Memoria gasta : "+(runtime.totalMemory() - runtime.freeMemory())/aux +" MB"  );
        System.out.println("\nO custo total para o menor caminho foi de: "+ resp+"\n");
     
   
    }
    
}
