/**
 * Java Basic. Home work#12
 * 
 * @author Volodymyr
 * @date 17.10.2022
 * @todo 17.9.2022
 * 
 */


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lesson12  extends JFrame {
    private final int PANEL_SIZE = 400;
    private final int CELL_SIZE = PANEL_SIZE / 3;

    private boolean gameOver = false;

    public static void main(String[] args) {
        new Lesson12();
    }
    public Lesson12() {
        setTitle("Tic Tak Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Game.init();

        PaintPanel paintPanel = new PaintPanel();
        paintPanel.setBackground(Color.white);
        paintPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        paintPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if (Game.isCellValid(x, y) && !gameOver) {
                    Game.table[y] [x] = 'x';
                    //turn human
                    if (Game.isWin('x')) {
                        System.out.println("You won!");
                        gameOver = true;
                    }
                    if (Game.isTableFull()) {
                        System.out.println("Sorry, Draw!");
                        gameOver = true;
                    }
                    //turn PC
                    if (!gameOver) {
                        Game.turnAI();
                        if (Game.isWin('o')) {
                            System.out.println("Ai win!");
                            gameOver = true;
                        }
                        if (Game.isTableFull()) {
                            System.out.println("Sorry, Draw!");
                            gameOver = true;
                        }
                    }
                    paintPanel.repaint();
                }
            }
        });

        Button restart = new Button("Restart");
        restart.addActionListener(e -> {
            Game.init();
            gameOver = false;
            paintPanel.repaint();
        });
        Button exit = new Button("Exit");
        exit.addActionListener(e -> System.exit(0));

        JPanel btnPanel = new JPanel();
        btnPanel.add(restart);
        btnPanel.add(exit);

        add(paintPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    private class PaintPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.lightGray);
            for (int i = 1; i < 3; i++) {
                g.drawLine(0, CELL_SIZE * i, PANEL_SIZE, CELL_SIZE * i);
                g.drawLine(CELL_SIZE * i, 0, CELL_SIZE * i, PANEL_SIZE);
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(10f));

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (Game.table [y] [x] == 'x' ) {
                        g2.setColor(Color.blue);
                        g2.drawLine(x * CELL_SIZE + CELL_SIZE / 5,
                                y * CELL_SIZE + CELL_SIZE / 5,
                                (x +1) * CELL_SIZE -  CELL_SIZE/5,
                                (y + 1) * CELL_SIZE - CELL_SIZE/5);
                        g2.drawLine((x + 1) * CELL_SIZE - CELL_SIZE/5,
                                y * CELL_SIZE + CELL_SIZE/5,
                                x * CELL_SIZE + CELL_SIZE/5,
                                (y + 1) * CELL_SIZE - CELL_SIZE/5);
                    }
                    if (Game.table [y] [x] == 'o' ) {
                        g2.setColor(Color.red);
                        g2.drawOval(x * CELL_SIZE + CELL_SIZE / 5,
                                y * CELL_SIZE + CELL_SIZE/5,
                                CELL_SIZE - CELL_SIZE/5*2,
                                CELL_SIZE - CELL_SIZE/5*2);
                    }
                }
            }
        }
    }
}