package main.java;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class Register extends JFrame implements ActionListener,MouseListener{

    private JTextField id;
    private JTextField name;
    private JPasswordField pwd;
    private JButton but_reg,but_login;
    private JLayeredPane contentpane;

    public static void main(String args[]){
        try{
            Register frame=new Register();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Register(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentpane=new JLayeredPane();
        contentpane.setOpaque(true);
        contentpane.setBackground(Color.decode("#3b5998"));
        contentpane.setBorder(new EmptyBorder(15,15,15,15));
        add(contentpane);
        contentpane.setLayout(null);

        JLabel lbl_login= new JLabel("REGISTRATION");
        lbl_login.setForeground(Color.decode("#3b5998"));
        lbl_login.setFont(new Font("Times New Roman",Font.PLAIN,50));
        lbl_login.setBounds(795,190,380,95);
        contentpane.add(lbl_login,JLayeredPane.PALETTE_LAYER);

        JButton bg= new JButton("");
        bg.setEnabled(false);
        bg.setBackground(Color.decode("#ffffff"));
        bg.setFont(new Font("Times New Roman",Font.PLAIN,50));
        bg.setBounds(620,160,700,500);
        contentpane.add(bg,JLayeredPane.DEFAULT_LAYER);

        id=new JTextField();
        name=new JTextField();
        pwd=new JPasswordField();
        
        id.setForeground(Color.decode("#3b5998"));
        name.setForeground(Color.decode("#3b5998"));
        pwd.setForeground(Color.decode("#3b5998"));

        id.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        id.setBounds(920, 310, 300, 50);
        contentpane.add(id,JLayeredPane.PALETTE_LAYER);
        name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        name.setBounds(920, 370, 300, 50);
        contentpane.add(name,JLayeredPane.PALETTE_LAYER);

        pwd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        pwd.setBounds(920, 430, 300, 50);
        contentpane.add(pwd,JLayeredPane.PALETTE_LAYER);

        name.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));
        id.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));
        pwd.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));


        JLabel lbl_id=new JLabel("USER ID");
        lbl_id.setBackground(Color.BLACK);
        lbl_id.setForeground(Color.decode("#3b5998"));
        lbl_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_id.setBounds(750, 320, 190, 40);
        contentpane.add(lbl_id,JLayeredPane.PALETTE_LAYER);

        JLabel lbl_name=new JLabel("NAME");
        lbl_name.setBackground(Color.BLACK);
        lbl_name.setForeground(Color.decode("#3b5998"));
        lbl_name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_name.setBounds(750, 380, 190, 40);
        contentpane.add(lbl_name,JLayeredPane.PALETTE_LAYER);

        JLabel lbl_pwd=new JLabel("PASSWORD");
        lbl_pwd.setBackground(Color.WHITE);
        lbl_pwd.setForeground(Color.decode("#3b5998"));
        lbl_pwd.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_pwd.setBounds(750, 440, 190, 40);
        contentpane.add(lbl_pwd,JLayeredPane.PALETTE_LAYER);

        but_reg=new JButton("REGISTER");
        but_reg.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_reg.setBounds(750,550,200,50);
        but_reg.setBackground(Color.decode("#3b5998"));
        but_reg.setForeground(new Color(255,255,220));
        but_reg.addActionListener(this);     
        contentpane.add(but_reg,JLayeredPane.PALETTE_LAYER);     

        but_login=new JButton("LOGIN");
        but_login.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_login.setBounds(1020,550,200,50);
        but_login.setBackground(Color.decode("#3b5998"));
        but_login.setForeground(new Color(255,255,220));
        but_login.addActionListener(this);     
        contentpane.add(but_login,JLayeredPane.PALETTE_LAYER);     

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==but_reg){
            int input_id=-1;
            input_id=Integer.parseInt(id.getText());
            String input_name=name.getText();
            String input_pwd=new String(pwd.getPassword()); //resolved with new
            if(input_id==-1 || input_name.isEmpty() || input_pwd.isEmpty()){
                JOptionPane.showMessageDialog(but_login,"Enter all values to proceeed.");                                
            }
            else{
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/file","root","@sdr2005#sanaviot7");
                    PreparedStatement st=conn.prepareStatement("select user_id,name,password from user_data where user_id=?;");
                    st.setInt(1,input_id);

                    ResultSet rs=st.executeQuery();
                    if(rs.next()){                   
                        JOptionPane.showMessageDialog(but_login,"Given user_id already exists!");                    
                    }else{

                        PreparedStatement sq=conn.prepareStatement("INSERT into user_data values (?,?,?)");
                        sq.setInt(1, input_id);
                        sq.setString(2, input_name);
                        sq.setString(3, input_pwd);

                        int r=sq.executeUpdate();

                        if(r>0){                    
                            conn.close();
                            
                            Login uh=new Login();
                            uh.setTitle("File Encrytion System");
                            uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            uh.setVisible(true);
                            dispose();
                        }
                    }
                }
                catch (Exception sqlException){
                    sqlException.printStackTrace();
                } 
            }       

        }

        else if(e.getSource()==but_login){
            getContentPane().removeAll();
                Login uh=new Login();
                uh.setTitle("File Encrytion System");
                uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
                uh.setVisible(true);
               // dispose();
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==name){
            name.setText("");
        }
        if(e.getSource()==id){
            id.setText("");
        }
        if(e.getSource()==pwd){
            
            pwd.setText("");
            pwd.setEchoChar('*');
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

}
