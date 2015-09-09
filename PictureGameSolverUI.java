import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PictureGameSolverUI extends JFrame{
    public static char[][] arr;
    public static void main(String[] args) throws Exception{
        PictureGameSolver x = new PictureGameSolver();
        x.main(null);
        arr = x.finalSolution; //contains 'x' and 'shaded' cells 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
                } catch (UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                
                
                display(x.r,x.c);
            }
        });
    }
    
    public PictureGameSolverUI(String name) {
        super(name);
        setResizable(false);
    }
    
    public static void display(int r, int c) {
        PictureGameSolverUI frame = new PictureGameSolverUI("Solved Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel layout = new JPanel();
        layout.setLayout(new GridLayout(r,c));
        
        JButton b = new JButton("Just fake button");
        Dimension buttonSize = b.getPreferredSize();
        int dim = (int)(buttonSize.getWidth() * 3);
        layout.setPreferredSize(new Dimension(dim, (int)(dim*1.3)));
        
        for(int i = 0; i < r; i++) {
            for(int j = 0; j < c; j++) {
                JButton button = new JButton();
                if(arr[i][j] == 248) {
                    button.setBackground(Color.black);
                } else {
                    button.setBackground(Color.white);
                }
                button.setOpaque(true);
                button.setBorderPainted(false);
                layout.add(button);
            }
        }
        frame.getContentPane().add(layout, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        
        
        }
    }