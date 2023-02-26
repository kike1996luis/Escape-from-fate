package com.me.aTester;

import java.util.*;

public class Bag<Item> implements Iterable<Item> {
	private Node cabeza;
	private Node last;
	private short longitud;

	Bag() {
		cabeza = null;
		longitud = 0;
		last = null;
	}

	public int getSize() {
		return longitud;
	}

	private class Node {
		Item valor;
		Node sig;
	}

	public boolean isEmpty() {
		return cabeza == null;
	}

	public void clean() {
		while (!this.isEmpty()) {
			cabeza = cabeza.sig;
			longitud--;
		}
	}

	public void add(Item item) {
		Node n = new Node();
		n.valor = item;
		if (cabeza == null) {
			cabeza = n;
			last = n;

		} else {
			last.sig = n;
			last = n;
		}
		longitud++;
	}

	public short tamano() {
		return longitud;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node actual = cabeza;

		@Override
		public boolean hasNext() {
			return actual != null;
		}

		@Override
		public void remove() {

			if (cabeza != null) {
				cabeza = cabeza.sig;

			} else {
				System.out.println("Already empty\n");
			}
		}

		@SuppressWarnings("unused")
		public void remove_n(int n) {
			if (hasNext()) {
				if (n == 0) {
					cabeza = null;
				} else if (n < longitud) {
					Node current = cabeza;
					int contador = 0;

					while (contador < (n - 1)) {
						current = current.sig;
						longitud++;
					}
					Node delete = current.sig;
					current.sig = delete.sig;
					delete.sig = null;
				}
				longitud--;
			} else {
				System.out.println("List Empty");
			}
		}

		@Override
		public Item next() {
			Item valor = actual.valor;
			actual = actual.sig;
			return valor;
		}
	}
}
