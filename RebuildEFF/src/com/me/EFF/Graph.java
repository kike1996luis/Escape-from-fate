package com.me.EFF;

class Nodo {
	int vertice;
	int pesoArista;
	int x;
	int y;
	int f = 0;
	int g = 0;
	Nodo sgte;
	Nodo padre;

	public Nodo(int v, int peso, int x, int y) {
		this.vertice = v;
		this.pesoArista = peso;
		this.x = x;
		this.y = y;
		this.sgte = null;
	}

}

public class Graph {
	public Nodo grafo[];
	private int nroVertices;
	int x;
	int y;
	int mundo[][];

	public Graph(int nroVertices, int x, int y) {
		this.nroVertices = nroVertices;
		this.x = x;
		this.y = y;
		grafo = new Nodo[this.nroVertices];

		for (int i = 0; i < this.nroVertices; i++) {
			grafo[i] = null;
		}

	}

	public void crearMundo(int matriz[][]) {
		this.mundo = matriz;
        for(int i = 0; i < y ; i++){
           
            for(int j = 0; j < x ; j++){
              
                if(matriz [i][j]==0 || matriz [i][j]==3){
                    if( j + 1 < x){
                     if(matriz [i][j+1]==0 || matriz [i][j+1]==3 ){
                         if(i==0){
                            insertaArista(j,j+1,10,j+1,i);
                          }
                         
                         else{
                             insertaArista(i*x+j,i*x+j+1,10,j+1,i);
                         }
                     }
                     }
                    
                  if( i + 1 < y){
                     if(matriz [i+1][j]==0  || matriz [i+1][j]==3){
                         
                        if(i==0){
                            insertaArista(j,j+x,10,j,i+1);
                          }
                         
                         else{
                             insertaArista(i*x+j,i*x+j+x,10,j,i+1);
                         }  
                     }
                   }
                  
                  
                   if( j - 1 >=0 ){
                       
                         if(matriz [i][j-1]==0  || matriz [i][j-1]==3){
                             if(i==0){
                             insertaArista(j,j-1,10,j-1,i);
                             }
                             
                             else{
                             insertaArista(i*x+j,i*x+j-1,10,j-1,i);
                            }
                         }
                  }
                  
                   
                    if( i - 1 >=0){
                     if(matriz [i-1][j]==0  || matriz [i-1][j]==3){
                             insertaArista(i*x+j,i*x+j-x,10,j,i-1);
                     }
                   }
                
               }   
             
            }
       }
	}

	public void crearDeNuevo(int matriz[][]) {
		for (int i = 0; i < this.nroVertices; i++) {
			grafo[i] = null;
		}
		crearMundo(mundo);
	}

	public boolean existeArista(int v1, int v2) {
		if (grafo[v1] == null) {
			return false;
		}

		Nodo actual = grafo[v1];

		while (actual != null) {
			if (actual.vertice == v2) {
				return true;
			}
		}
		return false;
	}

	public Nodo Adj(int a) {
		return grafo[a];
	}

	public void insertaArista(int v1, int v2, int peso, int x, int y) {

		Nodo nuevo = new Nodo(v2, peso, x, y);

		if (grafo[v1] == null) {
			grafo[v1] = nuevo;
		}

		else {
			Nodo actual = grafo[v1];

			while (actual.sgte != null) {
				actual = actual.sgte;
			}
			actual.sgte = nuevo;
		}

	}

	public void liberarGrafo() {
		for (int i = 0; i < grafo.length; i++) {
			grafo[i] = null;
		}
	}

	public void mostrarGrafo() {

		for (int i = 0; i < grafo.length; i++) {
			Nodo actual = grafo[i];

			System.out.print(i + " --+ |");

			while (actual != null) {
				System.out.print(actual.vertice + "|");
				actual = actual.sgte;
			}

			System.out.println();
		}

	}

	public boolean listaAdyVacia(int v) {
		if (grafo[v] == null) {
			return true;
		}

		else {
			return false;
		}
	}

	public Nodo getPrimerAd(int v) {
		return grafo[v];
	}

	public Nodo nextAdy(Nodo anterior) {
		if (anterior.sgte == null) {
			return null;
		} else {
			return anterior.sgte;
		}
	}

}
