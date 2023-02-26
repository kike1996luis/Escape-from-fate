package com.me.EFF;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
import java.util.Stack;

public class busqueda {

     int destinoaux;
    Nodo origen;
	Nodo destino;
	Graph A;
	Nodo camino;
	int destinos;
	int salidas;
	Stack<Nodo> recorrido;

	busqueda(Graph A) {
		this.A = A;
	}

	public void algoritmoBuscaCamino(int salida, int llegada,player p) {

		ArrayList<Nodo> listaAbierta;
		ArrayList<Nodo> listaCerrada;
		listaAbierta = new ArrayList<Nodo>();
		listaCerrada = new ArrayList<Nodo>();
                
                this.destino = A.grafo[llegada - 1];
                
                 if (destino == null) {
                     
                     llegada=(llegada-A.x)-A.x;
                     this.destino = A.grafo[llegada - 1];
                     
                     if(destino==null){
                      llegada=llegada+2;
                      this.destino = A.grafo[llegada - 1];
                     }
                     
                     if(destino==null){
                      llegada=llegada-2;
                      this.destino = A.grafo[llegada - 1];
                     }
                     
                     if(destino==null){
                      llegada=(llegada+A.x)+A.x;
                      this.destino = A.grafo[llegada - 1];
                     }
                     
                     if(destino==null){
                      llegada=destinoaux;
                      this.destino = A.grafo[llegada - 1];
                     }
                     
          	}
               
		A.crearDeNuevo(A.mundo);

		this.origen = A.grafo[salida - 1];

		this.destino = A.grafo[llegada - 1];
                
                if(destino != null){
                    destinoaux= llegada;
                }
                
		if (origen == null) {
                    	this.origen = A.grafo[salidas];
			salida = salidas + 1;
		}
		
                
                 
               

		Nodo Actual;
		listaAbierta.add(origen);

		while (!listaAbierta.isEmpty()) {

			Actual = VerticeConMenorF(listaAbierta);
			listaAbierta.remove(Actual);
			listaCerrada.add(Actual);

			if (Actual.vertice == destino.vertice) {
				salidas = salida - 1;
				destinos = llegada - 1;
				camino = Actual;
				recorrer();

				break;
			}

			if (Actual.vertice != destino.vertice) {

				Nodo adj = A.grafo[Actual.vertice];

				while (adj != null) {
					if (listaAbierta.indexOf(adj) == -1 && listaCerrada.indexOf(adj) == -1) {
						setEcuacion(Actual, adj);
						adj.padre = Actual;

						listaAbierta.add(adj);
					} else if (listaAbierta.indexOf(adj) != -1) {
						if (adj.f < Actual.f) {
							setEcuacion(Actual, adj);
							adj.padre = Actual;
						}
					}
					adj = adj.sgte;
				}
			}
		}

	}

	public Nodo VerticeConMenorF(ArrayList<Nodo> lista) {

		Nodo resultado = lista.get(0);

		if (lista.size() > 1) {
			for (int i = 1; i < lista.size(); i++) {

				if (lista.get(i).f < resultado.f) {
					resultado = lista.get(i);
				}
			}

		}
		return resultado;
	}

	public void setEcuacion(Nodo padre, Nodo hijo) {
		int h = Math.abs(hijo.x - destino.x) + Math.abs(hijo.y - destino.y);
		int g = hijo.pesoArista;
		hijo.f = g + h;
	}

	public void recorrer() {
		recorrido = new Stack<Nodo>();
		while (camino != null) {
			recorrido.add(camino);
			camino = camino.padre;
		}
	}

}
