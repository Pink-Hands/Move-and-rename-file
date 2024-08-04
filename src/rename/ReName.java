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
        JFrame f = new JFrame("移动文件并重命名");//主窗体
        f.setSize(400, 120);//主窗体大小
        f.setLocation(200, 200);//主窗体位置
        f.setLayout(new FlowLayout());//顺序布局,容器会水平摆放,无需设定位置大小
        JLabel hunt = new JLabel("请输入文件夹：");
        JLabel message = new JLabel("版本2: 直接确认即可");
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
        Font ff = new Font("楷体", Font.BOLD, 18);
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

        // 监控是否单击了文件名输入框,单击全选
        tfName.addMouseListener(new MouseAdapter() {
            // 只有mouseEntered用到了
            public void mouseReleased(MouseEvent e) {
                tfName.selectAll();
                f.repaint();


            }
        });
        // 检测是否按下了回车,回车即确定
        tfName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                // 10代表按下了回车
                if (e.getKeyCode() == 10) {
                    b.doClick();
                    f.repaint();
                }
            }
        });

        // 监听按钮b
        b.addActionListener(new ActionListener() {
            // 当按钮被点击时,就会触发ActionEvent事件
            public void actionPerformed(ActionEvent e) {
                // 当前文件
                File current = new File("");
                String absolutePath = current.getAbsolutePath();
                current = new File(absolutePath);
                // 读取文件夹下所有文件
                File[] fs = current.listFiles();
                // 找不到文件夹
                if (fs == null || fs.length <= 1) {
                    fail2.setVisible(true);
                    f.repaint();
                    return;
                }
                // 过滤当前文件
                List<File> fileList = Arrays.stream(fs).filter(file -> !file.getName().endsWith(".jar")).collect(Collectors.toList());
                File[] fileArray = fileList.toArray(new File[fileList.size()]);
                try {
                    search(fileArray, "", absolutePath);
                    fail.setVisible(false);
                    suss.setVisible(true);
                    f.repaint();
                    System.out.println("成功完成");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    suss.setVisible(false);
                    fail.setVisible(true);
                    f.repaint();
                    System.out.println("出现异常");
                }
            }
        });
    }

    private static void search(File[] fs, String upstreamName, String path) {
        // 先将所有的文件命名并移动
        if (upstreamName != null && !upstreamName.isEmpty()) {
            for (File each : fs) {
                if (each.isFile()) {
                    each.renameTo(new File(path + "\\" + upstreamName + "-" + each.getName()));
                }
            }
        }

        for (File each : fs) {
            // 对文件夹进行操作
            if (each.isDirectory()) {
                String absolutePath = each.getAbsolutePath();
                String originName = each.getName();
                // 更新地址
                String newPath;
                if (upstreamName == null || upstreamName.isEmpty()) {
                    newPath = originName;
                } else {
                    newPath = upstreamName + '-' + originName;
                }
                // 递归遍历子文件夹
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
