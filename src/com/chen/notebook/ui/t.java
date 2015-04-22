import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.chen.notebook.beans.ResArticleList;
import com.chen.notebook.beans.User;
import com.chen.notebook.service.impl.BaiduNoteServiceImpl;

public void actionPerformed(ActionEvent e) {
		
	}
	/**
	 * 失去焦点，保存文件。。。
	 */
	@Override
	public void focusLost(FocusEvent e) {
		
	}
	
	public void newSort(Lists[] list,int fileCount){
		String tempTitle ,tempDate,tempMaintext;
		int s;
		tempTitle = list[fileCount].getTitle();
		tempDate = list[fileCount].getDate();
		tempMaintext =  list[fileCount].getMaintext();
		s=list[fileCount].getStatus();
		for(int i=fileCount;i>0;i--){
			list[i].setTitle(list[i-1].getTitle());
			list[i].setStatus(list[i-1].getStatus());
			list[i].setDate(list[i-1].getDate());
			list[i].setMaintext(list[i-1].getMaintext());
		}
		list[0].setTitle(tempTitle);
		list[0].setDate(tempDate);
		list[0].setStatus(s);
		list[0].setMaintext(tempMaintext);
	}
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==jtfTitle){

		}
	}
	/**
	 * 文章列表 点击（切换）
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		

	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	public void initList(){
//		try {
//
//			ObjectInputStream  in = new ObjectInputStream(new FileInputStream("NoteBook.txt"));
//			int a=in.readInt();
//			if(a!=0){
//				queue.last(jpTextcard);
//				queue.last(jpListcard);
//				Lists[] l =(Lists[])in.readObject();
//				for(int i=0;i<a;i++){
//				list[i].setDate(l[i].getDate());
//				if(i==0)
//					list[i].setStatus(3);
//				else
//					list[i].setStatus(1);
//				list[i].setTitle(l[i].getTitle());
//				list[i].setMaintext(l[i].getMaintext());
//				newSort(list,fileCount);//使新建的在第一个
//				repaint();
//			}
//				jtfTitle.setText(l[0].getTitle());
//				jtaMaintext.setText(l[0].getMaintext());
//				fileCount=a;
//				jbDeletefile.setEnabled(true);
//			}
//			in.close();
//		} catch (FileNotFoundException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			// TODO 自动生成的 catch 块
//			e1.printStackTrace();
//		}
	}
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
			
			
	}

	