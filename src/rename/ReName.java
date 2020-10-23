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
		JFrame f = new JFrame("�ƶ��ļ���������");//������
		f.setSize(400, 120);//�������С
		f.setLocation(200, 200);//������λ��
		f.setLayout(new FlowLayout());//˳�򲼾�,������ˮƽ�ڷ�,�����趨λ�ô�С
		JLabel hunt = new JLabel("�������ļ��У�");
		JTextField tfName = new JTextField();//�����
		tfName.setPreferredSize(new Dimension(150, 25));//������С
		tfName.grabFocus();//��ȡ����,��һ��ʼ����ڴ˿�����˸
		JButton b = new JButton("ȷ��");//��ť���
		JLabel suss = new JLabel("����ɹ���");
		JLabel fail = new JLabel("����ʧ�ܣ�");
		JLabel fail2 = new JLabel("�Ҳ����ļ��У�");
		suss.setForeground(Color.red);
		fail.setForeground(Color.red);
		fail2.setForeground(Color.red);
		Font ff =new Font("����",Font.BOLD,18);
		hunt.setFont(ff);
		b.setFont(ff);
		suss.setFont(ff);
		fail.setFont(ff);
		fail2.setFont(ff);
		suss.setVisible(false);//���ò��ɼ�
		fail.setVisible(false);
		fail2.setVisible(false);
		f.add(hunt);
		f.add(tfName);
		f.add(b);//�Ѱ�ť���뵽��������
		f.add(fail2);
		f.add(suss);
		f.add(fail);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ����ʱ��,�˳�����
		f.setVisible(true);//�ô����ÿɼ�
		
		tfName.addMouseListener(new MouseAdapter() {//����Ƿ񵥻����ļ��������,����ȫѡ
		    public void mouseReleased(MouseEvent e) {//ֻ��mouseEntered�õ���
		    	tfName.selectAll();
		    	f.repaint();
		    	
		    	
		    }
		});
		
		tfName.addKeyListener(new KeyAdapter() {//����Ƿ����˻س�,�س���ȷ��
    	    public void keyReleased(KeyEvent e) {
    	    	if (e.getKeyCode() == 10) {//10�������˻س�
    	    		b.doClick();
    	    		f.repaint();
    	        }
    	    }
    	});
		
		b.addActionListener(new ActionListener() {//������ťb
		    // ����ť�����ʱ,�ͻᴥ��ActionEvent�¼�
		    public void actionPerformed(ActionEvent e) {
		        
		    	String a = tfName.getText();//���ַ���a��ȡ���������
		    	File f1 = new File(a);//���ļ���
		    	String f1Abso = f1.getAbsolutePath();//��ȡ����·��
				File f11 = new File(f1Abso);//�þ���·����
				File[]fs= f11.listFiles();//��ȡ�ļ����������ļ�
				if(fs==null) {//�Ҳ����ļ���
					fail2.setVisible(true);
					f.repaint();
				}
				File savePar = f11.getParentFile();//��ȡ�ļ���Ŀ¼
				String saveePar = savePar.getAbsolutePath();//��ȡ��Ŀ¼���Ե�ַ
				File fsave = new File(saveePar + "\\" + a + "2");//�����洢λ��
				fsave.mkdirs();//����Ŀ¼
				String path = fsave.getAbsolutePath();


		    	try {
					search(a,fs,a,path);
					fail.setVisible(false);
					suss.setVisible(true);//���ò��ɼ�
					f.repaint();
				} catch (Exception e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
					suss.setVisible(false);//���ò��ɼ�
					fail.setVisible(true);
					f.repaint();
				}
				
		    	
		    }
		});
		
	}

	private static void search(String a,File[] fs,String all,String path) {
		for(File each : fs) {
			
			if(each.isFile()) {//�Ƚ����е��ļ��������ƶ�
    			each.renameTo(new File(path + "\\" + all + "-" + each.getName()));
    		}
		}

		for(File each : fs) {
			if(each.isDirectory()) {//���ļ��н��в���
    			String bAbso = each.getAbsolutePath();
    			String b = each.getName();
    			String befor = all;//����ԭ��ַ
    			all = all + '-' + b;//���µ�ַ
    			File f2 = new File(bAbso);
    			File[]fs2 = f2.listFiles();
    			search(befor,fs2,all,path);//����Ŀ¼���в���
    			all = befor;//����Ŀ¼�������ԭ��ַ
    		}
		}

		}
		
	

	private static void setLookAndFeel() {//ͼ��Ƥ��
	    try {
	    	javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
	    } catch (Exception e) {
	        e.printStackTrace();
	        // handle exception
	    }
	}
}
