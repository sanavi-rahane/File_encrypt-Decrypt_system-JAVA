
package main.java;

import java.io.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.*;

public class Encrypt extends JFrame implements ActionListener {

    private int user_id;
    private String file_p;
    private JTextField file_path;
    private JTextField file_name;
    private JTextField file_key;
    private JTextField file_desc;
    private JButton but_enc, but_home;
    private JLayeredPane contentpane;
    JButton open = new JButton("OPEN");

    Encrypt(int id) {
        this.user_id = id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 700, 550);

        contentpane = new JLayeredPane();
        contentpane.setOpaque(true);
        contentpane.setBackground(Color.decode("#3b5998"));
        contentpane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentpane);
        contentpane.setLayout(null);

        JLabel lbl_login = new JLabel("FILE ENCRYPTION SYSTEM");
        lbl_login.setForeground(Color.decode("#3b5998"));
        lbl_login.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lbl_login.setBounds(760, 190, 550, 95);
        contentpane.add(lbl_login, JLayeredPane.PALETTE_LAYER);

        JButton bg = new JButton("");
        bg.setEnabled(false);
        bg.setBackground(Color.decode("#ffffff"));
        bg.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        bg.setBounds(620, 160, 780, 550);
        contentpane.add(bg, JLayeredPane.DEFAULT_LAYER);

        open.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        open.setBounds(1260, 300, 90, 50);
        open.setBackground(Color.decode("#3b5998"));
        open.setForeground(new Color(255, 255, 220));
        open.addActionListener(this);
        contentpane.add(open, JLayeredPane.PALETTE_LAYER);

        file_path = new JTextField();
        file_path.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        file_path.setBounds(930, 300, 330, 50);
        file_path.setEditable(false);
        contentpane.add(file_path, JLayeredPane.PALETTE_LAYER);

        file_name = new JTextField();
        file_key = new JTextField();
        file_desc = new JTextField();

        file_name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        file_key.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        file_desc.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        file_name.setBounds(930, 350, 330, 50);
        file_key.setBounds(930, 410, 330, 50);
        file_desc.setBounds(930, 470, 330, 50);

        contentpane.add(file_name, JLayeredPane.PALETTE_LAYER);
        contentpane.add(file_key, JLayeredPane.PALETTE_LAYER);
        contentpane.add(file_desc, JLayeredPane.PALETTE_LAYER);

        file_key.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#3b5998")));
        file_name.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#3b5998")));
        file_desc.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#3b5998")));

        JLabel lbl_path = new JLabel("FILE PATH");
        lbl_path.setForeground(Color.decode("#3b5998"));
        lbl_path.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_path.setBounds(700, 300, 190, 40);
        contentpane.add(lbl_path, JLayeredPane.PALETTE_LAYER);

        JLabel lbl_name = new JLabel("FILE NAME");
        lbl_name.setForeground(Color.decode("#3b5998"));
        lbl_name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_name.setBounds(700, 360, 190, 40);
        contentpane.add(lbl_name, JLayeredPane.PALETTE_LAYER);

        JLabel lbl_key = new JLabel("FILE KEY");
        lbl_key.setForeground(Color.decode("#3b5998"));
        lbl_key.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_key.setBounds(700, 420, 190, 40);
        contentpane.add(lbl_key, JLayeredPane.PALETTE_LAYER);

        JLabel lbl_desc = new JLabel("FILE DESCRIPTION");
        lbl_desc.setForeground(Color.decode("#3b5998"));
        lbl_desc.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_desc.setBounds(700, 480, 300, 40);
        contentpane.add(lbl_desc, JLayeredPane.PALETTE_LAYER);

        but_enc = new JButton("ENCRYPT");
        but_enc.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_enc.setBounds(770, 600, 200, 50);
        but_enc.setBackground(Color.decode("#3b5998"));
        but_enc.setForeground(new Color(255, 255, 220));
        but_enc.addActionListener(this);
        contentpane.add(but_enc, JLayeredPane.PALETTE_LAYER);

        but_home = new JButton("HOME");
        but_home.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_home.setBounds(1060, 600, 200, 50);
        but_home.setBackground(Color.decode("#3b5998"));
        but_home.setForeground(new Color(255, 255, 220));
        but_home.addActionListener(this);
        contentpane.add(but_home, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            j.setAcceptAllFileFilterUsed(true);
            j.setDialogTitle("Select any type of file to encrypt");

            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                file_p = j.getSelectedFile().getAbsolutePath();
                file_path.setText(file_p);

                // Auto-fill file name from selected file
                String selectedName = new File(file_p).getName();
                file_name.setText(selectedName);
            } else {
                file_path.setText("Operation Cancelled");
            }
        } else if (e.getSource() == but_enc) {
            String ip_filekey = file_key.getText();
            String ip_filename = file_name.getText();
            String ip_filedesc = file_desc.getText();

            if (file_p == null || file_p.isEmpty() || ip_filekey.isEmpty() || ip_filename.isEmpty() || ip_filedesc.isEmpty()) {
                JOptionPane.showMessageDialog(but_enc, "Empty fields not allowed.!");
            } else {
                File input_file = new File(file_p);
                String encFileName = ip_filename + ".encrypted";
              //  File enc_file = new File("C:/Users/Sanavi/Desktop/OOP-Harsh/File-encrypt-decrypt-JAVA/Enc-Dec-Files/" + encFileName);
              File enc_file = new File(System.getProperty("user.home") + "/Downloads/" + encFileName);

                try {
                    Algo.Encrypt(ip_filekey, input_file, enc_file);
                    System.out.println("SUCCESS ENCRYPTING !!!!!!");

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/file", "root", "@sdr2005#sanaviot7");

                        Statement stlimit = conn.createStatement();
                        stlimit.execute("SET GLOBAL max_allowed_packet=104857600");

                        PreparedStatement sql = conn.prepareStatement(
                            "INSERT INTO uploads(user_id, data, file_key, file_name, description) VALUES (?, ?, ?, ?, ?)");

                        InputStream inputStream = new FileInputStream(enc_file);

                        sql.setInt(1, user_id);
                        sql.setBlob(2, inputStream);
                        sql.setString(3, ip_filekey);
                        sql.setString(4, ip_filename);
                        sql.setString(5, ip_filedesc);

                        int row = sql.executeUpdate();
                        if (row > 0) {
                            JOptionPane.showMessageDialog(null, "File successfully encrypted and saved in downloads");
                        }

                        conn.close();
                        inputStream.close();

                        UserHome uh = new UserHome(user_id);
                        uh.setTitle("File Encrytion System");
                        uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        uh.setVisible(true);
                        dispose();

                    } catch (Exception exsql) {
                        exsql.printStackTrace();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (e.getSource() == but_home) {
            UserHome uh = new UserHome(user_id);
            uh.setTitle("File Encrytion System");
            uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
            uh.setVisible(true);
            dispose();
}
}
}