package net.aluink.life.model;

import java.util.Random;


public class Board {
	int pos [][];
	int width;
	int height;
	
	public Board(int h, int w){
		this.width = w;
		this.height = h;
		pos = new int[h][w];
		Random rnd = new Random(1);
		for(int i = 0;i < getHeight();i++){
			for(int j = 0;j < getWidth();j++){
				setPos(i,j,rnd.nextInt()%4);
			}
		}
	}

	public int getPos(int h, int w){
		return pos[h][w];
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPos(int h, int w, int v) {
		pos[h][w] = v%4;
	}
	
	public void reset(){
		pos = new int[height][width];
	}

	public int[][] getPos() {
		// TODO Auto-generated method stub
		return pos;
	}
	
}
