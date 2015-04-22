package com.chen.notebook.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

public class Lists extends JPanel implements Serializable {
	private static final long serialVersionUID = -8967755614053783424L;
	private String title = "无标题笔记", mainText = " ";
	private String date;
	private int status = 0;// 0=未创建 1=未选中 2=鼠标经过 3=选中
	private String _key = null;
	
	public String get_key() {
		return _key;
	}

	public void set_key(String _key) {
		this._key = _key;
	}

	public Lists() {
	}

	public void setMaintext(String mainText) {
		this.mainText = mainText;
	}

	public String getMaintext() {
		return mainText;
	}

	public void newDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss");
		date = sdf.format(d);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (status == 3) {
			Color listCheckbg = new Color(209, 224, 237);
			setBackground(listCheckbg);
		} else if (status == 1) {
			Color listbg = new Color(248, 246, 245);
			setBackground(listbg);
		} else if (status == 2) {
			Color listbg = new Color(218, 231, 242);
			setBackground(listbg);
		} else if (status == 0) {
			setBackground(Color.white);
		}
		if (status != 0) {
			g.setFont((new Font("幼圆", Font.BOLD, 15)));
			if (title.length() > 10)
				g.drawString(title.substring(0, 10) + "...", 20, 30);
			else
				g.drawString(title, 20, 30);
			g.setColor(Color.GRAY);
			g.setFont((new Font("幼圆", Font.PLAIN, 12)));
			g.drawString(date, 20, 50);
			g.setColor(Color.black);
			g.setFont((new Font("宋体", Font.PLAIN, 13)));
			if (mainText.length() <= 12)
				g.drawString(mainText, 20, 70);
			else if (mainText.length() > 12 && mainText.length() <= 24) {
				g.drawString(mainText.substring(0, 12), 20, 70);
				g.drawString(mainText.substring(12, mainText.length()), 20, 85);
			} else if (mainText.length() > 24) {
				g.drawString(mainText.substring(0, 12), 20, 70);
				g.drawString(mainText.substring(12, 23) + "...", 20, 85);
			}
			g.setColor(Color.lightGray);
			g.drawLine(0, 0, 199, 0);
			g.drawLine(0, 99, 199, 99);
		}
	}
}
