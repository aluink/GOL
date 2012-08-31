package net.aluink.life.gui;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.aluink.life.controller.MasterMaker;
import net.aluink.life.model.Board;

public class Viewer extends JPanel {

	public static final int FACTOR = 1;
	
	private static final long serialVersionUID = -746836099092120549L;
	
	Board b;
	
	public Viewer(Board b) {
		this.b = b;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		for(int i = 0;i < b.getHeight();i++){
			for(int j = 0;j < b.getWidth();j++){
				if(b.getPos(i, j)){
					g.fillRect(j*FACTOR, i*FACTOR, FACTOR, FACTOR);
				}
			}
		}		
	}
	
	void comeAlive() {
		while(true){
			MasterMaker.iterate(b);
			repaint(0,0,getWidth(), getHeight());
			try {Thread.sleep(30);} catch (InterruptedException e) {}
		}
	}
	
	public static void main(String[] args) {
		int w = 800, h =300;
		Viewer v = new Viewer(new Board(h,w));
		JFrame frame = new JFrame("Game of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(w*FACTOR,h*FACTOR);
		frame.add(v);
		frame.setVisible(true);	
		v.comeAlive();
		
	}
}
