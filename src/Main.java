// 6. Изобразить четырехугольник, вращающийся в плоскости апплета вокруг своего центра тяжести.

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JPanel implements Runnable {
    private double angle = 0;

    public Main() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        double rotationSpeed = 0.05;
        while (true) {
            angle += rotationSpeed;
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int size = 100; // Размер четырехугольника

        int[] xPoints = {(int) (centerX - size / 2), (int) (centerX + size / 2), (int) (centerX + size / 2), (int) (centerX - size / 2)};
        int[] yPoints = {(int) (centerY - size / 2), (int) (centerY - size / 2), (int) (centerY + size / 2), (int) (centerY + size / 2)};

        // Поворот координат
        double sinTheta = Math.sin(angle);
        double cosTheta = Math.cos(angle);

        int[] xPointsRotated = new int[4];
        int[] yPointsRotated = new int[4];
        for (int i = 0; i < 4; i++) {
            xPointsRotated[i] = centerX + (int) ((xPoints[i] - centerX) * cosTheta - (yPoints[i] - centerY) * sinTheta);
            yPointsRotated[i] = centerY + (int) ((xPoints[i] - centerX) * sinTheta + (yPoints[i] - centerY) * cosTheta);
        }

        g.setColor(Color.BLUE);
        g.fillPolygon(xPointsRotated, yPointsRotated, 4);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Вращающийся квадрат");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Main());
            frame.setSize(400, 400);
            frame.setVisible(true);
        });
    }
}
