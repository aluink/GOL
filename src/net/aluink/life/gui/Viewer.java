package net.aluink.life.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.aluink.life.controller.MasterMaker;
import net.aluink.life.model.Board;

public class Viewer extends JPanel {

	private static final int SLEEP_TIME = 30;

	public static final int FACTOR = 10;
	
	private static final long serialVersionUID = -746836099092120549L;
	
	boolean paused = true;
	
	Board b;
	
	public Viewer(Board b) {
		this.b = b;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		Color [] colors = {Color.RED, Color.BLUE, Color.GREEN};
		g.clearRect(0, 0, getWidth(), getHeight());
		for(int i = 0;i < b.getHeight();i++){
			for(int j = 0;j < b.getWidth();j++){
				if(b.getPos(i, j) > 0){
					g.setColor(colors[b.getPos(i,j)%4-1]);
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
			try {Thread.sleep(SLEEP_TIME);} catch (InterruptedException e) {}
		}
	}
	
	static class ToggleListener implements MouseListener{

		Viewer v;
		JButton b, ex, im;
		
		public ToggleListener(JButton start, Viewer v, JButton ex, JButton im) {
			b = start;
			this.v = v;
			this.ex = ex;
			this.im = im;
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
				im.setEnabled(false);
				ex.setEnabled(false);
				b.setText("Stop");
			} else {
				v.paused = true;
				im.setEnabled(true);
				ex.setEnabled(true);
				b.setText("Start");
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
			
			if(v.b.getPos(y,x) > 0){
				v.b.setPos(y, x, 0);
			} else {
				v.b.setPos(y, x, 2);
			}
			v.repaint();
			
		}
		
	}
	
	static class ImportListener implements MouseListener {

		Viewer v;
		
		public ImportListener(Viewer v) {
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
			File f = new File("import.dat");
			if(f.exists()){
				Board b = new Board(f);
				v.b = b;
				v.repaint();
			}
		}
		
	}
	
	static class ExportListener implements MouseListener {

		Board b;
		
		public ExportListener(Viewer v) {
			b = v.b;
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
			DataOutputStream fw = null;
			try {
				fw = new DataOutputStream(new FileOutputStream(new File("export.dat")));
				fw.writeInt(b.getHeight());
				fw.writeInt(b.getWidth());
				for(int i = 0;i < b.getHeight();i++){
					for(int j = 0;j < b.getWidth();j++){
						fw.writeInt(b.getPos(i,j));
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally{
				try {
					if(fw != null){
						fw.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		final int BUTTONS_HEIGHT = 100;
		int w = 30, h = 30;
		
		Viewer v = new Viewer(new Board(h,w));
		JFrame frame = new JFrame("Game of Life");
		frame.getContentPane().setLayout(new BorderLayout());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(w*FACTOR,h*FACTOR+BUTTONS_HEIGHT);
		
		JPanel buttons = new JPanel();
		buttons.setSize(w,BUTTONS_HEIGHT);
		JButton start, clear, export, impo;
		
		
		clear = new JButton("Clear");
		clear.setSize(BUTTONS_HEIGHT*3, BUTTONS_HEIGHT);
		clear.addMouseListener(new ClearListener(v));
		
		export = new JButton("Export");
		export.setSize(BUTTONS_HEIGHT*3, BUTTONS_HEIGHT);
		export.addMouseListener(new ExportListener(v));
		
		impo = new JButton("Import");
		impo.setSize(BUTTONS_HEIGHT*3, BUTTONS_HEIGHT);
		impo.addMouseListener(new ImportListener(v));
		
		start = new JButton("Start");
		start.setSize(BUTTONS_HEIGHT*3, BUTTONS_HEIGHT);
		start.addMouseListener(new ToggleListener(start, v, export, impo));
		
		buttons.add(start);
		buttons.add(clear);
		buttons.add(export);
		buttons.add(impo);
		
		v.addMouseListener(new ModifyListener(v));
		
		frame.add(buttons, BorderLayout.PAGE_START);
		frame.add(v, BorderLayout.CENTER);		
		frame.setVisible(true);	
		v.comeAlive();
		
	}
}
