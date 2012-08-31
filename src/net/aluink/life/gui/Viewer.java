package net.aluink.life.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.aluink.life.controller.MasterMaker;
import net.aluink.life.model.Board;

public class Viewer extends JPanel {

	public static final int FACTOR = 3;
	
	private static final long serialVersionUID = -746836099092120549L;
	
	boolean paused = false;
	
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
			if(!paused){
				MasterMaker.iterate(b);
				repaint(0,0,getWidth(), getHeight());
			}
			try {Thread.sleep(30);} catch (InterruptedException e) {}
		}
	}
	
	static class ToggleListener implements MouseListener{

		Viewer v;
		JButton b;
		
		public ToggleListener(JButton start, Viewer v) {
			b = start;
			this.v = v;
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(v.paused){
				v.paused = false;
				b.setText("Start");
			} else {
				v.paused = true;
				b.setText("Stop");
			}
		}
	}

	static class ClearListener implements MouseListener {

		Viewer v;
		
		public ClearListener(Viewer v) {
			this.v = v;
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			v.b.reset();
			v.repaint();
		}
		
	}
	
	static class ModifyListener implements MouseListener{

		Viewer v;
		
		public ModifyListener(Viewer v) {
			this.v = v;
		}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {
			int x = e.getX()/FACTOR;
			int y = e.getY()/FACTOR;
			
			v.b.setPos(y, x, true);
			v.repaint();
			
		}
		
	}
	
	public static void main(String[] args) {
		final int BUTTONS_HEIGHT = 100;
		int w = 400, h = 250;
		
		Viewer v = new Viewer(new Board(h,w));
		JFrame frame = new JFrame("Game of Life");
		frame.getContentPane().setLayout(new BorderLayout());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(w*FACTOR,h*FACTOR+BUTTONS_HEIGHT);
		
		JPanel buttons = new JPanel();
		buttons.setSize(w,BUTTONS_HEIGHT);
		JButton start, clear;
		start = new JButton("Start");
		start.setSize(BUTTONS_HEIGHT*3, BUTTONS_HEIGHT);
		start.addMouseListener(new ToggleListener(start, v));
		
		clear = new JButton("Clear");
		clear.setSize(BUTTONS_HEIGHT*3, BUTTONS_HEIGHT);
		clear.addMouseListener(new ClearListener(v));
		
		buttons.add(start);
		buttons.add(clear);
		
		v.addMouseListener(new ModifyListener(v));
		
		frame.add(buttons, BorderLayout.PAGE_START);
		frame.add(v, BorderLayout.CENTER);		
		frame.setVisible(true);	
		v.comeAlive();
		
	}
}
