package graphicsUtility;

import javax.swing.*;

public class GraphicWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private GraphicFunction g;

	@SuppressWarnings("deprecation")
	public GraphicWindow(String imgName) {
		getContentPane().setLayout(null);
		setTitle("Luminance function of image: "+imgName);
		setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		show();
	}

	public void visualizza(int[] val) {
		g = new GraphicFunction(val);
		//g.setBounds(50, 50, (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100), 
							//(int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200));
		g.setBounds(50, 50, (int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()-100), 
							(int)(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()-200));
		getContentPane().add(g);
		g.setValori(val);
		g.disegna();
	}
}