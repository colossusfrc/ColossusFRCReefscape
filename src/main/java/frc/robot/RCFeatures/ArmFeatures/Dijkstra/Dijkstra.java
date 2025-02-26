package frc.robot.RCFeatures.ArmFeatures.Dijkstra;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import frc.robot.Constants;

public class Dijkstra {
    private static  double[][] vertices;
    private static Dijkstra dijkstra;
    private Dijkstra(final int n){
        vertices = new double[n][n];
    }
    public static Dijkstra getInstance() throws InvalidAlgorithmParameterException{
        if(dijkstra==null){
            dijkstra = new Dijkstra(Constants.ArmUtility.ArmPositions.armPositions.size());
            criarAresta(0, 2, 71.0);
            criarAresta(0, 3, 110.0);
            criarAresta(0, 4, 200.0);
            criarAresta(0, 5, 195.0);
            criarAresta(0, 6, 208.0);
            //
            criarAresta(2, 3, 39.0);
            criarAresta(2, 4, 153.0);
            criarAresta(2, 5, 208.0);
            criarAresta(2, 6, 137.0);
            //
            criarAresta(3, 4, 130.0);
            criarAresta(3, 5, 185.0);
            criarAresta(3, 6, 98.0);
            //
            criarAresta(4, 5, 55.0);
            criarAresta(4, 6, 98.0);
            //
            criarAresta(5, 6, 153.0);
        }
        return dijkstra;
    }
    private static void criarAresta(final int noOrigem, final int noFim, final double peso) throws InvalidAlgorithmParameterException{
        if(peso>=1){
            vertices[noFim][noOrigem] = peso;
            vertices[noOrigem][noFim] = peso;
        }
        else{
            throw new InvalidAlgorithmParameterException("O peso precisa ser maior ou igual que 1");
        }
    }

    public int getMinDIstance(final double listaPesos[], final Set<Integer> naoVisitados){
        double peso = Double.MAX_VALUE;
        int noProximo = 0;
        for(Integer i : naoVisitados){
            if(listaPesos[i]<peso){
                peso = listaPesos[i];
                noProximo = i;
            }
        }
        return
         noProximo;
    }

    public List<Integer> getVIzinhos(final int no){
        List<Integer> vizinhos = new ArrayList<Integer>();
        for(int i = 0; i<vertices.length; i++){
            if(vertices[no][i]>0){
                vizinhos.add(i);
            }
        }

        return
         vizinhos;
    }

    public double getPeso(final int noOrigem, final int noDestino){
        return vertices[noDestino][noOrigem];
    }

    public List<Integer> dijkstra(final int noORigem, final int noDestino){
        double[] custo = new double[vertices.length];
        int[] antecessor = new int[vertices.length];
        Set<Integer> naoVisitados = new HashSet<Integer>();
        
        custo[noORigem] = 0;

        for(int v = 0; v < vertices.length; v++){
            if(v!=noORigem){
                custo[v] = Double.MAX_VALUE;
            }
            antecessor[v] = -1;
            naoVisitados.add(v);
        }

        while(!naoVisitados.isEmpty()){
            int noMaisProximo = getMinDIstance(custo, naoVisitados);
            naoVisitados.remove(noMaisProximo);
            getVIzinhos(noMaisProximo).forEach(
                s->{
                    double custoTotal = custo[noMaisProximo] + getPeso(noMaisProximo, s);
                    if(custoTotal<custo[s]){
                        custo[s] = custoTotal;
                        antecessor[s] = noMaisProximo;
                    }
                }
            );
            if(noMaisProximo == noDestino){
                return caminho(antecessor, noMaisProximo);
            }
        }
        return null;
    }

    private final List<Integer> caminho(int[] antecessores, int noMaisProximo){
        List<Integer> caminho = new ArrayList<>();
        caminho.add(noMaisProximo);
        while(antecessores[noMaisProximo]!=-1){
            caminho.add(antecessores[noMaisProximo]);
            noMaisProximo = antecessores[noMaisProximo];
        }
        Collections.reverse(caminho);
        return caminho;
    }
    public static void main(String[] args) throws InvalidAlgorithmParameterException {
            System.out.println(Dijkstra.getInstance().dijkstra(0, 2));
            System.out.println(Dijkstra.getInstance().dijkstra(0, 3));
            System.out.println(Dijkstra.getInstance().dijkstra(0, 4));
            System.out.println(Dijkstra.getInstance().dijkstra(0, 5));
            System.out.println(Dijkstra.getInstance().dijkstra(0, 6));
            //
            System.out.println(Dijkstra.getInstance().dijkstra(2, 3));
            System.out.println(Dijkstra.getInstance().dijkstra(2, 4));
            System.out.println(Dijkstra.getInstance().dijkstra(2, 5));
            System.out.println(Dijkstra.getInstance().dijkstra(2, 6));
            //
            System.out.println(Dijkstra.getInstance().dijkstra(3, 4));
            System.out.println(Dijkstra.getInstance().dijkstra(3, 5));
            System.out.println(Dijkstra.getInstance().dijkstra(3, 6));
            //
            System.out.println(Dijkstra.getInstance().dijkstra(4, 5));
            System.out.println(Dijkstra.getInstance().dijkstra(4, 6));
            //
            System.out.println(Dijkstra.getInstance().dijkstra(5, 6));
    }
}

