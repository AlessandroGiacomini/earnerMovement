package imageUtility;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class EmployImage {
	
	private int lum;
	private BufferedImage img;
	private String imagePath;
	
	
	public int[] getPixelData(BufferedImage img, int x, int y) {
	    	if((lum==161)||(!(((x)>0)&&((x)<img.getWidth())&&((y)>0)&&((y)<img.getHeight())))){	    	
	    		x = (img.getHeight()/2);
		    	y = (img.getWidth()/2);	
	
	    	}
		int argb = img.getRGB(x, y);
	    	int rgb[] = new int[] {
	    	    (argb >> 16) & 0xff, //red
	    	    (argb >>  8) & 0xff, //green
	    	    (argb      ) & 0xff  //blue
	    	};
	    	//System.out.println("rgb: " + rgb[0] + " " + rgb[1] + " " + rgb[2]);
	return rgb;
	}
	
	public void loadImage() {
		try {
			String imagePath = null;	
			JFileChooser browse = new JFileChooser();
			browse.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);			
			browse.showOpenDialog(null);
			imagePath = browse.getSelectedFile().getCanonicalPath();		
	    	img = ImageIO.read(new File(imagePath));
	    	this.imagePath=imagePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getLum() {
		return lum;
	}

	public void setLum(int lum) {
		this.lum = lum;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}