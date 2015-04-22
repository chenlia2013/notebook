package com.chen.notebook.ui;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImageJPanel extends JPanel {
	/**
	 * 
	 */
//	private static final long serialVersionUID = -9090858703576894159L;

	public ImageJPanel() {

	}

	public void paintComponent(Graphics g) {
		int x = 0, y = 0;
		ImageIcon icon = new ImageIcon("image/toolBg.jpg");
		g.drawImage(icon.getImage(), x, y, getSize().width, getSize().height,
				this);
	}
}
