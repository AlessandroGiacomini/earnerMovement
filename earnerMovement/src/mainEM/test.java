package mainEM;
import earnerMovementTypes.EarnerMovementUniversal;

public class test {
	public static void main(String[] args) {

		EarnerMovementUniversal emu = new EarnerMovementUniversal();
		//Variabili
		/*int imgWidth = img.getWidth();
		int imgHeight = img.getHeight();*/
		int imgWidth = 500;
		int imgHeight = 500;
		int linearShift = 100;
		int xCenter = imgWidth/2;
		int yCenter = imgHeight/2;
		int spostPixel = 1;
		double k, randX, randY, range;
		int upperBoundX = (imgWidth/2)-linearShift;
		int upperBoundY = (imgHeight/2)-linearShift;
		int x = 250;
		int y = 250;
		
		
		
		
		int rangeM = 1-0;
	 	double mX = (rangeM*Math.random())+0;
	 	double mY = (rangeM*Math.random())+0;
	 	System.err.println("LA MX: "+mX);
	 	System.err.println("LA MY: "+mY);

	 	
	 	int segnoX = emu.randInt(0,1); 
		int segnoY = emu.randInt(0,1);  
		System.err.println("0 o 1 che daranno segno alla X: "+segnoX);
	 	System.err.println("0 o 1 che daranno segno alla Y: "+segnoY);
		
	 	
		int rX= emu.randInt(0,upperBoundX);
		int rY= emu.randInt(0,upperBoundY);
		System.err.println("rX: "+rX);
	 	System.err.println("rY: "+rY);
		
				
		double xToSumCenterX = (rX*(segnoX))+(rX*(segnoX-1))+mX;
		double yToSumCenterY = (rY*(segnoY))+(rY*(segnoY-1))+mY;	
		System.err.println("X da sommare al centro: "+xToSumCenterX);
	 	System.err.println("Y da sommare al centro: "+yToSumCenterY);
		
	 	
	 	double newOriginX = x+xToSumCenterX;
	 	double newOriginY = y+yToSumCenterY;
		System.err.println("nuovo centro: LA X: "+newOriginX);
	 	System.err.println("nuovo centro: LA Y: "+newOriginY);
	 	
		randX=newOriginX;
 		randY=newOriginY;
 		for(int i=1; i<=linearShift; i++) {
	 		randX+=1*segnoX+1*(segnoX-1);
	 		randY+=1*segnoY+1*(segnoY-1);
	 		System.out.println("              LA X: "+randX);
		 	System.out.println("              LA Y: "+randY);
		 	System.out.println();
	 	}
	}

}
