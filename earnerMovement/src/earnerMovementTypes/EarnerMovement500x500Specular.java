package earnerMovementTypes;
import earnerMovement.EarnerMovement;
import imageUtility.EmployImage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class EarnerMovement500x500Specular implements EarnerMovement {
	
	private int numberExperiments = 10;
	//Variabili dipendenti dall'immagine
	private int spostMax = 25; //px
	private int spostMin = 18; //px
	private int xNoCirc = 3/8;
	private int yNoCirc = 1/4;
	private int numFix = 1000;
	private int jump = 150;
	//***Tenere in considerazione (contJump<150) e (contJump>500)***
	//***if((maxContJump+minContJump)/2>=325) e if((maxContJump+minContJump)/2<=325)

	private int x, y;
	private int randX, randY;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private int[] lumListVal;
	private int delta;
	private int contJump = 0;
	private int sumContJump = 0;
	private int maxContJump = 0;
	private int minContJump = 10000;
	private ArrayList<Integer> listContJump = new ArrayList<Integer>();
	private int illusionTests = 0;
	private int staticTests = 0;
	private int riprova = 0;
	private int failure = 0;
	private int retryLimit = 8000;
	private EmployImage ei = new EmployImage();

	//************************>>>>>>>>>>>>>>>>>CONTROLLA int n nell'intestazione e interface
	public void testIllusion(BufferedImage img, int n) {
		
	    x = (img.getWidth()/2);
		y = (img.getHeight()/2);
		
	    int range = spostMax-(spostMin)+1;
	    int rangeBool = 1-0+1;
	
    		list = new ArrayList<Integer>();

		for (int i=1; i<=numFix; i++) {
		    	int[] rgb = ei.getPixelData(img, x, y);
		    	ei.setLum((rgb[0]+rgb[1]+rgb[2])/3);
		    	list.add(ei.getLum());
		    	int bool = (int)(rangeBool*Math.random())+0;   			    	
		    	randX=((int)((range*Math.random())+spostMin)*bool)+((int)((range*Math.random())+spostMin)*(bool-1));
		    	randY=((int)((range*Math.random())+spostMin)*bool)+((int)((range*Math.random())+spostMin)*(bool-1));
		   	    	
		    	
		    	//Poiche' l'illusione e' indipendete dallo sfondo, studiamo per facilita' pattern circolari
		    	//con sfondo rosso in modo tale da non considerare nello studio i punti esterni all'immagine
		    	//Turchese rgb=(72, 209, 204), lum=161
		    	
		    	if((ei.getLum()==161)||(!(((x+randX)>0)&&((x+randX)<img.getWidth())&&((y+randY)>0)&&((y+randY)<img.getHeight())))){	    	
		    		x = (img.getHeight()/2);
			    	y = (img.getWidth()/2);	

		    	}
		    	x = x+randX;
		    	y = y+randY;  	
		}
		
		//Trasformo "list" in un array "lumListVal"
		int j = 0;
		lumListVal = new int[list.size()];

		for(Integer i: list) {
			lumListVal[j]=i;
			j++;
		}
		
		
		for(int k=0; k<lumListVal.length-1;k++) {
			//Calcolo addensamento
			delta = Math.abs(lumListVal[k]-lumListVal[k+1]);
			if (delta>jump) {
				contJump++;
			}
			//			
		}	

		if ((contJump<150)&&(staticTests+illusionTests<numberExperiments)) {
			System.err.println(contJump);
			illusionTests++;
			contJump=0;
			testIllusion(img, n);
		}
		
		else if ((contJump>500)&&(staticTests+illusionTests<numberExperiments)) { 	
			System.err.println(contJump);
			staticTests++;	
			contJump=0;
			testIllusion(img, n);
		}

		else if (staticTests+illusionTests<numberExperiments) {
			listContJump.add(contJump);
			sumContJump+=contJump;
			System.err.println(contJump);
			contJump=0;
			riprova++;
			if (riprova<=retryLimit) {
					testIllusion(img, n);
			}
		}
	}
	
	public int getNumberExperiments() {
		return numberExperiments;
	}


	public void setNumberExperiments(int numberExperiments) {
		this.numberExperiments = numberExperiments;
	}


	public int[] getLumListVal() {
		return lumListVal;
	}


	public void setLumListVal(int[] lumListVal) {
		this.lumListVal = lumListVal;
	}


	public int getMaxContJump() {
		return maxContJump;
	}


	public void setMaxContJump(int maxContJump) {
		this.maxContJump = maxContJump;
	}


	public int getMinContJump() {
		return minContJump;
	}


	public void setMinContJump(int minContJump) {
		this.minContJump = minContJump;
	}


	public ArrayList<Integer> getListContJump() {
		return listContJump;
	}


	public void setListContJump(ArrayList<Integer> listContJump) {
		this.listContJump = listContJump;
	}


	public int getIllusionTests() {
		return illusionTests;
	}


	public void setIllusionTests(int illusionTests) {
		this.illusionTests = illusionTests;
	}


	public int getStaticTests() {
		return staticTests;
	}


	public void setStaticTests(int staticTests) {
		this.staticTests = staticTests;
	}


	public int getRiprova() {
		return riprova;
	}


	public void setRiprova(int riprova) {
		this.riprova = riprova;
	}


	public int getFailure() {
		return failure;
	}


	public void setFailure(int failure) {
		this.failure = failure;
	}


	public int getRetryLimit() {
		return retryLimit;
	}


	public void setRetryLimit(int retryLimit) {
		this.retryLimit = retryLimit;
	}	
}