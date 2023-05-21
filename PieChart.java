package voting;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Random;

public class PieChart extends JFrame {
	Random rand = new Random();
    JPanel panel;
    
    int size = 400;

    public PieChart(int[] electionResults, int most_voted) {
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawString("Winning party: " + Integer.toString(most_voted), size / 2 - 50, 30);
                
                int total = 0;
                for (int value : electionResults) {
                    total += value;
                }

                Color[] colors = new Color[electionResults.length];
                int startAngle = 0;
                for (int i = 1; i < electionResults.length; i++) {
                    int extentAngle = (int) Math.round(360 * electionResults[i] / total);
                    
                    float r = rand.nextFloat();
                	float g = rand.nextFloat();
                	float b = rand.nextFloat();
                	colors[i] = new Color(r, g, b);

                    g2d.setColor(colors[i]);
                    g2d.fill(new Arc2D.Double(50, 50, 200, 200, startAngle, extentAngle, Arc2D.PIE));

                    startAngle += extentAngle;
                }

                int x = size - 100;
                int y = size - 350;
                for (int i = 1; i < electionResults.length; i++) {
                    g2d.setColor(colors[i]);
                    g2d.fillRect(x, y, 20, 20);
                    
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("Party " + i, x + 30, y + 15);
                    y += 30;
                }
            }
        };
        add(panel);
        
        setTitle("Election Result");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(size, size);
        setVisible(true);
    }
}
