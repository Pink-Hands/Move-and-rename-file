package rename;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ReName {

	public static void main(String[] args) {
		
		setLookAndFeel();
		JFrame f = new JFrame("移动文件并重命名");//主窗体
		f.setSize(400, 120);//主窗体大小
		f.setLocation(200, 200);//主窗体位置
		f.setLayout(new FlowLayout());//顺序布局,容器会水平摆放,无需设定位置大小
		JLabel hunt = new JLabel("请输入文件夹：");
		JTextField tfName = new JTextField();//输入框
		tfName.setPreferredSize(new Dimension(150, 25));//输入框大小
		tfName.grabFocus();//获取焦点,即一开始光标在此框内闪烁
		JButton b = new JButton("确定");//按钮组件
		JLabel suss = new JLabel("处理成功！");
		JLabel fail = new JLabel("处理失败！");
		JLabel fail2 = new JLabel("找不到文件夹！");
		suss.setForeground(Color.red);
		fail.setForeground(Color.red);
		fail2.setForeground(Color.red);
		Font ff =new Font("楷体",Font.BOLD,18);
		hunt.setFont(ff);
		b.setFont(ff);
		suss.setFont(ff);
		fail.setFont(ff);
		fail2.setFont(ff);
		suss.setVisible(false);//设置不可见
		fail.setVisible(false);
		fail2.setVisible(false);
		f.add(hunt);
		f.add(tfName);
		f.add(b);//把按钮加入到主窗体中
		f.add(fail2);
		f.add(suss);
		f.add(fail);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体的时候,退出程序
		f.setVisible(true);//让窗体变得可见
		
		tfName.addMouseListener(new MouseAdapter() {//监控是否单击了文件名输入框,单击全选
		    public void mouseReleased(MouseEvent e) {//只有mouseEntered用到了
		    	tfName.selectAll();
		    	f.repaint();
		    	
		    	
		    }
		});
		
		tfName.addKeyListener(new KeyAdapter() {//检测是否按下了回车,回车即确定
    	    public void keyReleased(KeyEvent e) {
    	    	if (e.getKeyCode() == 10) {//10代表按下了回车
    	    		b.doClick();
    	    		f.repaint();
    	        }
    	    }
    	});
		
		b.addActionListener(new ActionListener() {//监听按钮b
		    // 当按钮被点击时,就会触发ActionEvent事件
		    public void actionPerformed(ActionEvent e) {
		        
		    	String a = tfName.getText();//用字符串a获取输入的内容
		    	File f1 = new File(a);//打开文件夹
		    	String f1Abso = f1.getAbsolutePath();//获取绝对路径
				File f11 = new File(f1Abso);//用绝对路径打开
				File[]fs= f11.listFiles();//读取文件夹下所有文件
				if(fs==null) {//找不到文件夹
					fail2.setVisible(true);
					f.repaint();
				}
				File savePar = f11.getParentFile();//获取文件父目录
				String saveePar = savePar.getAbsolutePath();//获取父目录绝对地址
				File fsave = new File(saveePar + "\\" + a + "2");//创建存储位置
				fsave.mkdirs();//创建目录
				String path = fsave.getAbsolutePath();


		    	try {
					search(a,fs,a,path);
					fail.setVisible(false);
					suss.setVisible(true);//设置不可见
					f.repaint();
				} catch (Exception e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
					suss.setVisible(false);//设置不可见
					fail.setVisible(true);
					f.repaint();
				}
				
		    	
		    }
		});
		
	}

	private static void search(String a,File[] fs,String all,String path) {
		for(File each : fs) {
			
			if(each.isFile()) {//先将所有的文件命名并移动
    			each.renameTo(new File(path + "\\" + all + "-" + each.getName()));
    		}
		}

		for(File each : fs) {
			if(each.isDirectory()) {//对文件夹进行操作
    			String bAbso = each.getAbsolutePath();
    			String b = each.getName();
    			String befor = all;//保存原地址
    			all = all + '-' + b;//更新地址
    			File f2 = new File(bAbso);
    			File[]fs2 = f2.listFiles();
    			search(befor,fs2,all,path);//对子目录进行操作
    			all = befor;//对子目录操作完后还原地址
    		}
		}

		}
		
	

	private static void setLookAndFeel() {//图形皮肤
	    try {
	    	javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
	    } catch (Exception e) {
	        e.printStackTrace();
	        // handle exception
	    }
	}
}
