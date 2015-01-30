package com.nazar;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Collections;
import java.util.Stack;
@SuppressWarnings("unchecked")
public class HourseMoving {
	static volatile boolean stopAll;

	public static void main(String[] args) throws InterruptedException,
			IOException {

		for (int i = 1; i <= 4; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					findTheWay();
				}

			}, i + "").start();
		}

	}
	
	static void findTheWay() {
		Cell[][] arr = new Cell[10][10];
		int x = 0;
		int y = 0;
		arr[0][0] = new Cell(1);
		while (true) {
			if (stopAll)
				return;

			Cell c = arr[x][y];

			if (c.getValue() == 98) {
				break;
			}

			Stack<byte[]> moves = c.getAvailableMoves();
			if (!moves.isEmpty()) {
				byte[] move = moves.pop();
				int newTryX = move[0] + x;
				int newTryY = move[1] + y;
				if ((newTryY >= 0) && (newTryY < 10) && (newTryX >= 0)
						&& (newTryX < 10)) {
					if (arr[newTryX][newTryY] == null) {

						Cell newC = new Cell(c.getValue() + 1);
						newC.prevous.x = x;
						newC.prevous.y = y;
						x = newTryX;
						y = newTryY;
						arr[newTryX][newTryY] = newC;
						continue;

					}
				}

			} else {
				arr[x][y] = null;
				Coord prev = c.getPrevous();
				x = prev.x;
				y = prev.y;
				continue;
			}
		}

		stopAll = true;
		System.out.println("Thread " + Thread.currentThread().getName()
				+ " won");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				String s;
				if (arr[i][j] == null) {
					s = "   ";
				} else {
					s = "" + arr[i][j].getValue();
					switch (s.length()) {
					case 1:
						s = s + " ";
					case 2:
						s = s + " ";
					}
				}
				System.out.print(s);
			}
			System.out.println();

		}
	}
	
	static final byte[] m1 = new byte[2];
	static final byte[] m2 = new byte[2];
	static final byte[] m3 = new byte[2];
	static final byte[] m4 = new byte[2];
	static final byte[] m5 = new byte[2];
	static final byte[] m6 = new byte[2];
	static final byte[] m7 = new byte[2];
	static final byte[] m8 = new byte[2];
	static {
		m1[0] = 2;
		m1[1] = 1;

		m2[0] = 2;
		m2[1] = -1;

		m3[0] = -2;
		m3[1] = 1;

		m4[0] = -2;
		m4[1] = -1;

		m5[0] = 1;
		m5[1] = 2;

		m6[0] = -1;
		m6[1] = 2;

		m7[0] = -1;
		m7[1] = -2;

		m8[0] = 1;
		m8[1] = -2;
	}
	
	static Stack<byte[]> possibleMoves = new Stack<>();
	static {
		possibleMoves.add(m1);
		possibleMoves.add(m2);
		possibleMoves.add(m3);
		possibleMoves.add(m4);
		possibleMoves.add(m5);
		possibleMoves.add(m6);
		possibleMoves.add(m7);
		possibleMoves.add(m8);
	}
	
	static Stack<byte[]> shuffleMoves1;
	static Stack<byte[]> shuffleMoves2;
	static Stack<byte[]> shuffleMoves3;
	static Stack<byte[]> shuffleMoves4;
	static {
		shuffleMoves1 = (Stack<byte[]>) possibleMoves.clone();
		Collections.shuffle(possibleMoves);
		shuffleMoves2 = (Stack<byte[]>) possibleMoves.clone();
		Collections.shuffle(possibleMoves);
		shuffleMoves3 = (Stack<byte[]>) possibleMoves.clone();
		Collections.shuffle(possibleMoves);
		shuffleMoves4 = possibleMoves;
	}
	
	static class Cell {
		private int value;
		Coord prevous = new Coord();
		Stack<byte[]> availableMoves;

		public Coord getPrevous() {
			return prevous;
		}

		@SuppressWarnings("unchecked")
		public Cell(int j) {
			this.value = j;
			char shuffle = Thread.currentThread().getName().toCharArray()[0];

			if (shuffle == '1') {
				availableMoves = (Stack<byte[]>) shuffleMoves1.clone();
			} else if (shuffle == '2') {
				availableMoves = (Stack<byte[]>) shuffleMoves2.clone();
			} else if (shuffle == '3') {
				availableMoves = (Stack<byte[]>) shuffleMoves3.clone();
			} else if (shuffle == '4') {
				availableMoves = (Stack<byte[]>) shuffleMoves4.clone();
			}

		}

		int getValue() {
			return this.value;
		}

		Stack<byte[]> getAvailableMoves() {
			return this.availableMoves;
		}
	}

	static class Move {
		public Move(int i, int j) {
			this.x = i;
			this.y = j;
		}

		int x, y;

	}

	static class Coord {
		int x;
		int y;
	}

}
