package graphicsUtility;

import java.awt.*;

import javax.swing.*;

public class GraphicFunction extends JPanel {

	private static final long serialVersionUID = 42L;
	private int [] valori;
	private int [] valNorm;
	private int max;
	private int valMax;
	private boolean drawChart;

	public GraphicFunction(int [] valori) {
		this.valori = valori;
		valNorm = new int[valori.length];
		drawChart = false;
		cercaMax();
	}

	public void setValori(int [] valori) {
		this.valori = valori;
		valNorm = new int[valori.length];
		cercaMax();
		normalizza();
	}
	
	public void disegna() {
		drawChart = true; repaint(); 
	}

	@SuppressWarnings("static-access")
	public void paint(Graphics g) {
		if (drawChart) {
			for (int i=0; i<valori.length; i++) {
				/*if((valori[i]==161)||(valNorm[i]==437)) {
					g.setColor(getBackground().RED);
				}
				else if((valori[i]==98)) {
					g.setColor(getBackground().BLACK);
				}
				else {*/
				g.setColor(getBackground().GREEN.darker());
				//}
				//g.fillRect((i*10 + 11), (valMax - valNorm[i])+10 , 3, getHeight());
				//g.fillRect(i, (valMax - valNorm[i])+10 , 1, getHeight());
				g.fillRect((i*8 + 5), (valMax - valNorm[i])+10 , 6, getHeight());
				

				
			}
			//g.drawRect(0, 0, getWidth()-1, getHeight()-1);
			
		}
	}

	private void cercaMax() {
		for (int i=0; i<valori.length; i++) 
			max = (valori[i] > max) ? valori[i] : max;
	}

	private void normalizza() {
		int altezza = getHeight()-10;
		for (int i=0; i<valori.length; i++) {
			valNorm[i] = (altezza * valori[i]) / max;
			if (valori[i] == max) valMax = valNorm[i];
			//System.out.println(valNorm[i]);
		}
	}
}