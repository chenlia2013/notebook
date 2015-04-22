package com.chen.notebook.ui;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.chen.notebook.beans.ResArticleList;
import com.chen.notebook.beans.User;
import com.chen.notebook.service.impl.BaiduNoteServiceImpl;

public class NoteBook extends JFrame implements ActionListener, FocusListener,
		MouseListener, AdjustmentListener {

	private static final long serialVersionUID = -996420024497318807L;
	private JButton jbMinmize, jbClose;
	private JButton jbNewfile, jbNewfile2, jbDeletefile, jbRecycle;
	private JButton jbSearch;
	private Lists[] list = new Lists[24];
	public static int fileCount = 0, fileNum = 24;
	private JTextField jtfSearch, jtfTitle;
	private JTextArea jtaMaintext;
	final Color listCheckbg = new Color(209, 224, 237);
	final Color listPassbg = new Color(218, 231, 242);
	final Color listbg = new Color(248, 246, 245);
	private JLabel jlImage;
	private JPanel jpText, jpTitle, jpMaintext, jpTextcard, jpListcard, jpList;
	private static JPanel jpHead;
	private CardLayout queue = new CardLayout();
	static Point origin = new Point();
	private JScrollBar jscbList;

	private boolean isLogin = false;

	public static void main(String[] args) throws IOException {
		final NoteBook frame = new NoteBook();
		frame.setUndecorated(true);// 去掉标题栏
		frame.setVisible(true);// 这俩的顺序很关键
		frame.pack();//
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		Dimension frameSize = frame.getSize();
		int x = (screenWidth - frameSize.width) / 2;
		int y = (screenHeight - frameSize.height) / 2;
		frame.setLocation(x, y - 50);

		jpHead.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		jpHead.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = frame.getLocation();
				frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
						- origin.y);
			}
		});

	}

	public NoteBook() {
		jbMinmize = new JButton();
		jbMinmize.setBorderPainted(false);// 去掉按钮焦点
		jbMinmize.setIcon(new ImageIcon("image/min.jpg"));
		jbClose = new JButton();
		jbClose.setBorderPainted(false);
		jbClose.setIcon(new ImageIcon("image/close.jpg"));
		jbMinmize.setPreferredSize(new Dimension(20, 20));
		jbClose.setPreferredSize(new Dimension(20, 20));

		jpHead = new JPanel();
		jpHead.setPreferredSize(new Dimension(900, 25));
		Color jpHeadColor = new Color(23, 158, 252);
		jpHead.setBackground(jpHeadColor);

		jpHead.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jpHead.setBorder(new MatteBorder(0, 1, 0, 1, Color.LIGHT_GRAY));
		jpHead.add(jbMinmize);
		jpHead.add(jbClose);

		JPanel jpTool = new ImageJPanel();
		jpTool.setPreferredSize(new Dimension(900, 55));
		jpTool.setBorder(new MatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
		jbNewfile = new JButton();
		jbNewfile.setMnemonic('N');
		jbNewfile.setPreferredSize(new Dimension(100, 30));
		jbNewfile.setIcon(new ImageIcon("image/newfile.jpg"));
		jbNewfile.setBorderPainted(false);
		jbDeletefile = new JButton();
		jbDeletefile.setMnemonic('D');
		jbDeletefile.setPreferredSize(new Dimension(67, 30));
		jbDeletefile.setIcon(new ImageIcon("image/delete.jpg"));
		jbDeletefile.setBorderPainted(false);
		jbDeletefile.setEnabled(false);
		jbDeletefile.setDisabledIcon(new ImageIcon("image/deleteEnable.jpg"));

		jpTool.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 12));
		jpTool.add(jbNewfile);
		jpTool.add(jbDeletefile);

		JPanel jpContent = new JPanel();
		jpContent.setPreferredSize(new Dimension(900, 530));
		jpContent.setLayout(new BorderLayout());
		JPanel jpLeft = new JPanel();
		jpText = new JPanel();
		jpTextcard = new JPanel();
		jpTextcard.setLayout(queue);
		JLabel jlImage1 = new JLabel(new ImageIcon("image/textBg.jpg"));
		jpTextcard.add(jlImage1, "1");
		jpTextcard.add(jpText, "2");
		jlImage = new JLabel(new ImageIcon("image/textBg.jpg"));

		jpLeft.setPreferredSize(new Dimension(200, 530));
		jpLeft.setBorder(new MatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
		jpContent.add(jpLeft, BorderLayout.WEST);
		jpContent.add(jpTextcard, BorderLayout.CENTER);
		jpTextcard.setBorder(new MatteBorder(0, 0, 1, 1, Color.LIGHT_GRAY));

		jtfTitle = new JTextField();
		jtaMaintext = new JTextArea(26, 62);
		JScrollPane jscpMaintext = new JScrollPane(jtaMaintext);
		jscpMaintext.setBorder(null);
		jtaMaintext.setLineWrap(true);
		jpTitle = new JPanel();
		jpTitle.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
		jpTitle.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		jpTitle.setBackground(Color.WHITE);
		jpTitle.add(jtfTitle);
		jpMaintext = new JPanel();
		jpMaintext.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		jpMaintext.setPreferredSize(new Dimension(700, 500));
		jpMaintext.add(jscpMaintext);
		jpMaintext.setBackground(Color.white);
		jpText.setLayout(new BorderLayout());
		jtfTitle.setPreferredSize(new Dimension(650, 29));
		jtfTitle.setBorder(null);
		jpText.add(jpTitle, BorderLayout.NORTH);
		jpText.add(jpMaintext, BorderLayout.CENTER);//

		jpLeft.setLayout(new BorderLayout());
		JPanel jpSearch = new JPanel();
		jpSearch.setPreferredSize(new Dimension(200, 30));
		jpList = new JPanel();
		JPanel jpList_scroll = new JPanel();
		jpList.setBounds(0, 0, 184, 100 * fileNum);
		jpList_scroll.setLayout(null);
		jpList_scroll.add(jpList, BorderLayout.CENTER);
		jscbList = new JScrollBar();// 滚动条
		jscbList.setOrientation(Adjustable.VERTICAL);
		jscbList.setVisibleAmount(100);
		if (fileCount <= 5)
			jscbList.setEnabled(false);
		else
			jscbList.setEnabled(true);
		jpList_scroll.add(jscbList);
		jscbList.setBounds(184, 0, 15, 500);
		jpListcard = new JPanel();
		jpListcard.setLayout(queue);
		JPanel jpNew = new JPanel();
		jpNew.setLayout(null);
		jpNew.add(jbNewfile2 = new JButton());
		jbNewfile2.setBounds(50, 200, 100, 35);// 面板布局必须设为null
		jbNewfile2.setIcon(new ImageIcon("image/newfile2.jpg"));
		jbNewfile2.setBorderPainted(false);
		jpListcard.add(jpNew, "1");
		jpListcard.add(jpList_scroll, "2");
		jpLeft.add(jpSearch, BorderLayout.NORTH);
		jpLeft.add(jpListcard, BorderLayout.CENTER);

		jtfSearch = new JTextField();
		jtfSearch.setBorder(null);
		jtfSearch.setBorder(new MatteBorder(0, 1, 1, 0, Color.LIGHT_GRAY));
		jbSearch = new JButton();
		jbSearch.setBorderPainted(false);
		jbSearch.setPreferredSize(new Dimension(30, 30));
		jbSearch.setIcon(new ImageIcon("image/search.jpg"));
		jpSearch.setLayout(new BorderLayout());
		jpSearch.add(jtfSearch, BorderLayout.CENTER);
		jpSearch.add(jbSearch, BorderLayout.EAST);

		jpList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		for (int i = 0; i < fileNum; i++) {
			list[i] = new Lists();
			list[i].setPreferredSize(new Dimension(183, 100));
			jpList.add(list[i]);
		}
		// initList();//初始化，读取已有笔记

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jpHead, BorderLayout.NORTH);
		getContentPane().add(jpTool, BorderLayout.CENTER);
		getContentPane().add(jpContent, BorderLayout.SOUTH);

		jbMinmize.addActionListener(this);
		jbMinmize.addMouseListener(this);
		jbClose.addActionListener(this);
		jbClose.addMouseListener(this);
		jbNewfile.addActionListener(this);
		jbDeletefile.addActionListener(this);
		jbNewfile.addMouseListener(this);
		jbNewfile2.addMouseListener(this);
		jbDeletefile.addMouseListener(this);
		jbNewfile2.addActionListener(this);
		jtfTitle.addFocusListener(this);
		jtaMaintext.addFocusListener(this);
		for (int i = 0; i < fileNum; i++)
			list[i].addMouseListener(this);
		jbSearch.addActionListener(this);
		jscbList.addAdjustmentListener(this);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		double value = jscbList.getValue();
		if (fileCount > 5)
			jpList.setBounds(0, -(int) value * 20, 184, 100 * fileCount);
	}

	/**
	 * 鼠标点击，如果在列表上，则切换显示内容
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		for (int j = 0; j < fileCount; j++) {
			System.out.println(">>>>>>>>>>>>>>列表切换，选中了：" + e.getSource());
			if (e.getSource() == list[j]) {

				for (int i = 0; i < fileCount; i++) {
					if (list[i].getStatus() == 3) {
						String temp = jtfTitle.getText().trim();
						if (temp.isEmpty())
							list[i].setTitle("无标题笔记");
						else
							list[i].setTitle(temp);
						list[i].setMaintext(jtaMaintext.getText());
						break;
					}
				}
				if (list[j].getStatus() != 0 && list[j].getStatus() != 3) {
					for (int i = 0; i < fileCount; i++) {
						list[i].setStatus(1);
					}
					list[j].setStatus(3);
					jtfTitle.setText(list[j].getTitle());
					jtaMaintext.setText(list[j].getMaintext());
				}
				repaint();
			}

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == jbMinmize) {
			jbMinmize.setIcon(new ImageIcon("image/minPress.jpg"));
		} else if (e.getSource() == jbClose) {
			jbClose.setIcon(new ImageIcon("image/closePress.jpg"));
		} else if (e.getSource() == jbNewfile) {
			jbNewfile.setIcon(new ImageIcon("image/newfilePress.jpg"));
		} else if (e.getSource() == jbNewfile2) {
			jbNewfile2.setIcon(new ImageIcon("image/newfile2Press.jpg"));
		} else if (e.getSource() == jbDeletefile) {
			jbDeletefile.setIcon(new ImageIcon("image/deletePress.jpg"));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == jbMinmize) {
			jbMinmize.setIcon(new ImageIcon("image/minEnter.jpg"));
		} else if (e.getSource() == jbClose) {
			jbClose.setIcon(new ImageIcon("image/closeEnter.jpg"));
		} else if (e.getSource() == jbNewfile) {
			jbNewfile.setIcon(new ImageIcon("image/newfileEnter.jpg"));
		} else if (e.getSource() == jbNewfile2) {
			jbNewfile2.setIcon(new ImageIcon("image/newfile2Enter.jpg"));
		} else if (e.getSource() == jbDeletefile) {
			jbDeletefile.setIcon(new ImageIcon("image/deleteEnter.jpg"));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == jbMinmize) {
			jbMinmize.setIcon(new ImageIcon("image/minEnter.jpg"));
		} else if (e.getSource() == jbClose) {
			jbClose.setIcon(new ImageIcon("image/closeEnter.jpg"));
		} else if (e.getSource() == jbNewfile) {
			jbNewfile.setIcon(new ImageIcon("image/newfileEnter.jpg"));
		} else if (e.getSource() == jbNewfile2) {
			jbNewfile2.setIcon(new ImageIcon("image/newfile2Enter.jpg"));
		} else if (e.getSource() == jbDeletefile) {
			jbDeletefile.setIcon(new ImageIcon("image/deleteEnter.jpg"));
		}
		for (int i = 0; i < fileCount; i++) {
			if (e.getSource() == list[i]) {
				if (list[i].getStatus() != 0 && list[i].getStatus() != 3) {
					list[i].setStatus(2);
					repaint();
				}
				break;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == jbMinmize) {
			jbMinmize.setIcon(new ImageIcon("image/min.jpg"));
		} else if (e.getSource() == jbClose) {
			jbClose.setIcon(new ImageIcon("image/close.jpg"));
		} else if (e.getSource() == jbNewfile) {
			jbNewfile.setIcon(new ImageIcon("image/newfile.jpg"));
		} else if (e.getSource() == jbNewfile2) {
			jbNewfile2.setIcon(new ImageIcon("image/newfile2.jpg"));
		} else if (e.getSource() == jbDeletefile) {
			jbDeletefile.setIcon(new ImageIcon("image/delete.jpg"));
		}
		for (int i = 0; i < fileCount; i++) {
			if (e.getSource() == list[i]) {
				if (list[i].getStatus() != 0 && list[i].getStatus() != 3) {
					list[i].setStatus(1);
					repaint();
				}
				break;
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 失去焦点时，保存标题或保存文本内容
	 */
	@Override
	public void focusLost(FocusEvent e) {
		/** 标题栏失去焦点 */
		if (e.getSource() == jtfTitle) {
			int i;
			String temp = new String();
			for (i = 0; i < fileCount; i++) {
				if (list[i].getStatus() == 3) {
					temp = jtfTitle.getText().trim();
					if (temp.isEmpty())
						list[i].setTitle("无标题笔记");
					else
						list[i].setTitle(temp);
					repaint();
					break;
				}
			}
		}
		/** 文本内容栏失去焦点 */
		else if (e.getSource() == jtaMaintext) {
			int i;
			String temp = new String();
			for (i = 0; i < fileCount; i++) {
				if (list[i].getStatus() == 3) {
					temp = jtaMaintext.getText().trim();
					if (temp.isEmpty()) {
						list[i].setMaintext(" ");
					} else {
						list[i].setMaintext(temp);
						BaiduNoteServiceImpl.updateArticle(temp,
								list[i].get_key());
					}

					repaint();
					break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbMinmize)
			setExtendedState(JFrame.ICONIFIED);
		/** 序列话当前数据 */
		else if (e.getSource() == jbClose) {
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new FileOutputStream(
						"NoteBook.txt"));
				out.writeInt(fileCount);
				out.writeObject(list);
				out.close();
			} catch (FileNotFoundException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			System.exit(0);
		}
		/** 新建 按钮 */
		else if (e.getSource() == jbNewfile || e.getSource() == jbNewfile2) {
			// appendFile("无标题","");
			login();
			initArticleList();
		}
		/** 删除文件 */
		else if (e.getSource() == jbDeletefile) {
			// appendFile("无标题","");
			 removeFile();
		}
	}

	/**
	 * 从 左侧文件列表 添加一个
	 */
	private void removeFile() {
		int i;
		for (i = 0; i < fileCount; i++) {
			if (list[i].getStatus() == 3) {
				break;
			}
		}
		if (i != fileCount) {
			list[fileCount - 1].setStatus(0);
			for (int j = i; j < fileCount - 1; j++) {
				list[j].setTitle(list[j + 1].getTitle());
				list[j].setDate(list[j + 1].getDate());
				list[j].setMaintext(list[j + 1].getMaintext());
			}
			fileCount--;
			if (fileCount <= 5) {
				jscbList.setEnabled(false);
			}
			if (fileCount >= 5)
				jscbList.setVisibleAmount((int) (jscbList.getVisibleAmount() + 5));// 滚动条
			if (fileCount == 0)
				jbDeletefile.setEnabled(false);
			if (i == fileCount) {
				if (fileCount == 0) {
					jtfTitle.setText("");
					jtaMaintext.setText("");
				} else {
					list[i - 1].setStatus(3);
					jtfTitle.setText(list[i - 1].getTitle());
					jtaMaintext.setText(list[i - 1].getMaintext());
				}
			} else {
				jtfTitle.setText(list[i].getTitle());
				jtaMaintext.setText(list[i].getMaintext());
			}
			repaint();
		}
		if (fileCount == 0) {
			queue.first(jpTextcard);
			queue.first(jpListcard);
		}
	}

	/**
	 * 向 左侧文件列表 添加一个
	 * 
	 * @param title
	 * @param text
	 * @param key
	 */
	private void appendFile(String title, String text, String key) {
		if (fileCount < fileNum) {
			for (int i = 0; i < fileCount; i++) {
				list[i].setStatus(1);
			}
			list[fileCount].newDate();
			list[fileCount].setStatus(3);
			list[fileCount].setTitle(title);
			list[fileCount].setMaintext(text);
			list[fileCount].set_key(key);
			// list[fileCount].setTitle(String.valueOf(fileCount));//测试用，显示记事本序号
			// newSort(list,fileCount);//使新建的在第一个

			jtfTitle.setText(list[fileCount].getTitle());
			jtaMaintext.setText(list[fileCount].getMaintext());

			fileCount++;

			if (fileCount > 5) {
				jscbList.setEnabled(true);
				jscbList.setVisibleAmount((int) (jscbList.getVisibleAmount() - 5));// 滚动条
			}

			if (fileCount != 0)
				jbDeletefile.setEnabled(true);
			repaint();
		}
		if (fileCount == 1) {
			queue.last(jpTextcard);
			queue.last(jpListcard);
		}
	}

	/**
	 * 登陆过后初始化 左侧文件
	 */
	private void initArticleList() {
		ResArticleList articlelist = BaiduNoteServiceImpl.getArticle();
		System.out.println("record : " + articlelist.getRecords());
		for (int i = 0; i < articlelist.getRecords().size(); i++) {
			appendFile(articlelist.getRecords().get(i).getTitle(), articlelist
					.getRecords().get(i).getContent(), articlelist.getRecords()
					.get(i).getKey());
		}
	}

	/**
	 * 登陆
	 */
	private void login() {
		if (isLogin)
			return;
		User user = new User();
		user.setUsername("506766132@qq.com");
		user.setPassword("c53281874");
		BaiduNoteServiceImpl.login(user);
		isLogin = true;
	}
}
