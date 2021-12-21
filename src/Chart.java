package remme;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

public class Chart extends JPanel {
	private static final long serialVersionUID = 1L;

	static final Color Blue = new Color(0, 153, 204, 150);
	static final Color LightBlue = new Color(0, 204, 204, 150);
	static final Color DarkBlue = new Color(0, 0, 204, 150);
	static final Color Green = new Color(255, 102, 255, 150); // Free IO
	static final Color DarkGreen = new Color(255, 0, 0, 150); // Used IO
	static final Color Orange = new Color(102, 51, 0, 150); // Free P
	static final Color Red = new Color(51, 204, 51, 150); // Used P
	static final Color Gray = new Color(153, 153, 153);
	static final Color Black = new Color(255, 255, 255);
	static final Color SwingWhite = new Color(238, 238, 238);

	private double pixelW;
	private double pixelH;

	Monitor owner;

	public Chart(Monitor owner) {
		this.owner = owner;
	}

	private void generateLine(Graphics2D g, Color color, CircularArray<Integer> arr, ArrayList<Double> widths) {
		g.setColor(color);

		for (int i = 1; i < arr.size(); i++)
			g.draw(new Line2D.Double(widths.get(i - 1), getHeight() * 0.95 - (arr.get(i - 1) * pixelH),
					widths.get(i), getHeight() * 0.95 - (arr.get(i) * pixelH)));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));

		pixelW = 0.96 * getWidth() / owner.cpu5SecArray.limit();
		pixelH = 0.95 * getHeight() / 100;

		g.setColor(Gray);

		for (int i = 0; i < 10; i++)
			g.drawLine(0, (int) (0.95 * i * getHeight() / 10), getWidth(), (int) (0.95 * i * getHeight() / 10));

		for (int i = 0; i < (owner.cpu5SecArray.limit() / 3); i++)
			g.drawLine((int) (0.96 * i * getWidth() / (owner.cpu5SecArray.limit() / 3)), 0, (int) (0.96 * i * getWidth() / (owner.cpu5SecArray.limit() / 3)), getHeight());

		ArrayList<Double> widths = new ArrayList<>();
		for (int i = 0; i < owner.cpu5SecArray.size(); i++)
			widths.add(i * pixelW);

		g2.setStroke(new BasicStroke(3));
		if (owner.owner.memUsedPCheckbox.isSelected())
			generateLine(g2, Red, owner.memUsedPArrayPerc, widths);
		if (owner.owner.memFreePCheckbox.isSelected())
			generateLine(g2, Orange, owner.memFreePArrayPerc, widths);
		if (owner.owner.memUsedIOCheckbox.isSelected())
			generateLine(g2, DarkGreen, owner.memUsedIOArrayPerc, widths);
		if (owner.owner.memFreeIOCheckbox.isSelected())
			generateLine(g2, Green, owner.memFreeIOArrayPerc, widths);
		if (owner.owner.cpu5SecCheckbox.isSelected())
			generateLine(g2, LightBlue, owner.cpu5SecArray, widths);
		if (owner.owner.cpu1MinCheckbox.isSelected())
			generateLine(g2, Blue, owner.cpu1MinArray, widths);
		if (owner.owner.cpu5MinCheckbox.isSelected())
			generateLine(g2, DarkBlue, owner.cpu5MinArray, widths);

		g.setColor(Gray);

		g.fillRect((int) (getWidth() * 0.96), 0, (int) (getWidth() * 0.05 + 2), getHeight());
		g.fillRect(0, (int) (getHeight() * 0.95), getWidth() - 2, (int) (getHeight() * 0.06));
		
		g.setColor(Black);
		g.setFont(new Font("Calibri", Font.PLAIN, getWidth() / 70));

		for (int i = 0; i < 10; i++)
			g.drawString(String.valueOf(i * 10) + "%", (int) (getWidth() * 0.965), (int) (pixelH * (10.15 - i) * 10));
	}
}
