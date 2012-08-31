package net.aluink.life.controller;

import net.aluink.life.model.Board;

public class MasterMaker {
	static public void iterate(Board b){
		int oldPos[][] = b.getPos();
		b.reset();
		for(int i = 0;i < b.getHeight();i++){
			for(int j = 0;j < b.getWidth();j++){
				int count = 0;
				if(i > 0){
					if(j > 0 && oldPos[i-1][j-1] > 1){
						count++;
					}
					if(oldPos[i-1][j] > 1){
						count++;
					}
					if(j < (b.getWidth()-1) && oldPos[i-1][j+1] > 1){
						count++;
					}
				}
				if(j > 0 && oldPos[i][j-1] > 1){
					count++;
				}
				if(j < (b.getWidth()-1) && oldPos[i][j+1] > 1){
					count++;
				}
				if(i < (b.getHeight()-1)){
					if(j > 0 && oldPos[i+1][j-1] > 1){
						count++;
					}
					if(oldPos[i+1][j] > 1){
						count++;
					}
					if(j < (b.getWidth()-1) && oldPos[i+1][j+1] > 1){
						count++;
					}
				}
				
				
				if(oldPos[i][j] > 1){
					if(count == 2 || count == 3){
						b.setPos(i, j, 3);
					} else {
						b.setPos(i,j,1);
					}
				} else {
					if(count == 3){
						b.setPos(i, j, 2);
					} else {
						b.setPos(i,j,0);
					}
				}
			}
		}
	}
}
