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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

/**
 * @author Pink_Hands
 * @date 2024/08/04
 */
public class ReName {

    public static void main(String[] args) {

        setLookAndFeel();
        JFrame f = new JFrame("�ƶ��ļ���������");//������
        f.setSize(400, 120);//�������С
        f.setLocation(200, 200);//������λ��
        f.setLayout(new FlowLayout());//˳�򲼾�,������ˮƽ�ڷ�,�����趨λ�ô�С
        JLabel hunt = new JLabel("�������ļ��У�");
        JLabel message = new JLabel("�汾2: ֱ��ȷ�ϼ���");
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
        Font ff = new Font("����", Font.BOLD, 18);
        hunt.setFont(ff);
        message.setFont(ff);
        b.setFont(ff);
        suss.setFont(ff);
        fail.setFont(ff);
        fail2.setFont(ff);
        suss.setVisible(false);
        fail.setVisible(false);
        fail2.setVisible(false);
        f.add(hunt);
        f.add(tfName);
        f.add(b);
        f.add(message);
        f.add(fail2);
        f.add(suss);
        f.add(fail);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

        // ����Ƿ񵥻����ļ��������,����ȫѡ
        tfName.addMouseListener(new MouseAdapter() {
            // ֻ��mouseEntered�õ���
            public void mouseReleased(MouseEvent e) {
                tfName.selectAll();
                f.repaint();


            }
        });
        // ����Ƿ����˻س�,�س���ȷ��
        tfName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                // 10�������˻س�
                if (e.getKeyCode() == 10) {
                    b.doClick();
                    f.repaint();
                }
            }
        });

        // ������ťb
        b.addActionListener(new ActionListener() {
            // ����ť�����ʱ,�ͻᴥ��ActionEvent�¼�
            public void actionPerformed(ActionEvent e) {
                // ��ǰ�ļ�
                File current = new File("");
                String absolutePath = current.getAbsolutePath();
                current = new File(absolutePath);
                // ��ȡ�ļ����������ļ�
                File[] fs = current.listFiles();
                // �Ҳ����ļ���
                if (fs == null || fs.length <= 1) {
                    fail2.setVisible(true);
                    f.repaint();
                    return;
                }
                // ���˵�ǰ�ļ�
                List<File> fileList = Arrays.stream(fs).filter(file -> !file.getName().endsWith(".jar")).collect(Collectors.toList());
                File[] fileArray = fileList.toArray(new File[fileList.size()]);
                try {
                    search(fileArray, "", absolutePath);
                    fail.setVisible(false);
                    suss.setVisible(true);
                    f.repaint();
                    System.out.println("�ɹ����");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    suss.setVisible(false);
                    fail.setVisible(true);
                    f.repaint();
                    System.out.println("�����쳣");
                }
            }
        });
    }

    private static void search(File[] fs, String upstreamName, String path) {
        // �Ƚ����е��ļ��������ƶ�
        if (upstreamName != null && !upstreamName.isEmpty()) {
            for (File each : fs) {
                if (each.isFile()) {
                    each.renameTo(new File(path + "\\" + upstreamName + "-" + each.getName()));
                }
            }
        }

        for (File each : fs) {
            // ���ļ��н��в���
            if (each.isDirectory()) {
                String absolutePath = each.getAbsolutePath();
                String originName = each.getName();
                // ���µ�ַ
                String newPath;
                if (upstreamName == null || upstreamName.isEmpty()) {
                    newPath = originName;
                } else {
                    newPath = upstreamName + '-' + originName;
                }
                // �ݹ�������ļ���
                File f2 = new File(absolutePath);
                File[] fs2 = f2.listFiles();
                search(fs2, newPath, path);
                each.delete();
            }
        }

    }


    private static void setLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
            // handle exception
        }
    }
}
