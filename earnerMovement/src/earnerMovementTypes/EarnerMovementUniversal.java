package earnerMovementTypes;
import earnerMovement.EarnerMovement;
import graphicsUtility.GraphicWindow;
import imageUtility.EmployImage;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EarnerMovementUniversal implements EarnerMovement {
	
	private int imgSect = 3;
	private int linearShift;
	private int basepos;
	private int baseneg;
	private int[] lumListVal;
	private int[] deltaListVal;
	private int maxContJump = 0;
	private int minContJump = 10000;
	private int illusionTests = 0;
	private int staticTests = 0;
	private int riprova = 0;
	private int failure = 0;
	private int contSacc = 0;
	private int outImg = 0; 
	private int delta;
	
	private EmployImage ei = new EmployImage();
	private int[] listRectFiltered;
	private ArrayList<Integer> list;
	private ArrayList<Integer> listContJump = new ArrayList<Integer>();
	private Map<Integer, int[]> saccades = new HashMap<Integer, int[]>();
	private int imgWidth, imgHeight, rangeM, segnoX, segnoY, rX, rY, xCenter, yCenter, upperBoundX, upperBoundY;
	private double randX, randY, mX, mY, xToSumCenterX, yToSumCenterY, newOriginX, newOriginY;
	private List<Integer> lista;
	private int posStartLocalMin, posEndLocalMin, cont;
	private boolean upperBoundBol;
	int deltaCorrente, salgo, scendo, creste, eps, numeroCreste, diffValStartEnd, maxGlob, baseSal, baseDisc;

	
	public void testIllusion(BufferedImage img, int numberOfSaccades) {

		imgWidth = img.getWidth()-10;
		imgHeight = img.getHeight()-10;
		System.err.println("imgWidth: "+imgWidth);
		System.err.println("imgHeight: "+imgHeight);
		
		
		if(imgWidth>=imgHeight)
			linearShift =  imgHeight/imgSect;
		else
			linearShift =  imgWidth/imgSect;
		System.err.println("linearShift: "+linearShift);

		
		xCenter = imgWidth/2;	
		yCenter = imgHeight/2;
		System.err.println("xCenter: "+xCenter);
		System.err.println("yCenter: "+yCenter);
		
		upperBoundX = (imgWidth/2)-linearShift;
		upperBoundY = (imgHeight/2)-linearShift;
		if(upperBoundX>=upperBoundY) {
		   upperBoundX=upperBoundY;
		}
		else
		   upperBoundY=upperBoundX;
		System.err.println("upperBoundX: "+upperBoundX);
		System.err.println("upperBoundY: "+upperBoundY);
				
		
		rangeM = 1-0;
	 	mX = (rangeM*Math.random())+0;
	 	mY = (rangeM*Math.random())+0;
	 	System.err.println("LA MX: "+mX);
	 	System.err.println("LA MY: "+mY);

	 	
	 	segnoX = randInt(0,1); 
		segnoY = randInt(0,1);  
		System.err.println("0 o 1 che daranno segno alla X: "+segnoX);
	 	System.err.println("0 o 1 che daranno segno alla Y: "+segnoY);
			
	 	
		rX = randInt(0,upperBoundX);
		rY = randInt(0,upperBoundY);
		System.err.println("rX tra 0 e"+upperBoundX+": "+rX);
		System.err.println("rY tra 0 e"+upperBoundY+": "+rY);
			
		
		xToSumCenterX = (rX*(segnoX))+(rX*(segnoX-1))+mX;
		yToSumCenterY = (rY*(segnoY))+(rY*(segnoY-1))+mY;	
		System.err.println("X da sommare al centro: "+xToSumCenterX);
	 	System.err.println("Y da sommare al centro: "+yToSumCenterY);
			
	 	
	 	newOriginX = xCenter+xToSumCenterX;
	 	newOriginY = yCenter+yToSumCenterY;
		System.err.println("Dal centro mi sposto e trovo le nuove coordinate: LA X: "+newOriginX);
	 	System.err.println("Dal centro mi sposto e trovo le nuove coordinate: LA Y: "+newOriginY);

	 	
		randX=newOriginX;
		randY=newOriginY;
		list = new ArrayList<Integer>();
    		for(int i=1; i<=linearShift; i++) {
			int[] rgb = ei.getPixelData(img, (int)randX, (int)randY);
		
			if(!((rgb[0]==72)&&(rgb[1]==209)&&(rgb[2]==204)&&(outImg<=0))) {
			    	ei.setLum((rgb[0]+rgb[1]+rgb[2])/3);
			    	list.add(ei.getLum());
			    	randX+=1*segnoX+1*(segnoX-1);
	    	 		randY+=1*segnoY+1*(segnoY-1);
	    	 		System.out.println("X: "+randX);
	    			System.out.println("Y: "+randY);
	        	 	System.out.println();
			}
	        	else {
            	 	System.err.println("****************************************************\n****************************************************\n****************************************************\nSONO USCITO O STO SUL BACKGORUND");
            	 	outImg++;
	        	}
		}
		
		//Trasformo "list" in un array "lumListVal"
		int j = 0;
		lumListVal = new int[list.size()];

	 	System.out.println();
		for(Integer i: list) {
			lumListVal[j]=i;
			System.err.println("Luminosita': "+lumListVal[j]);    	
			j++;
		}
	 	System.out.println();
		
	 	if(contSacc==0) {
			saccades.put(1, lumListVal);
			this.list=null;
			contSacc++;
		}
		//Creo la mappa saccades con le liste di luminosita'
	 	for (int i=2; i<=numberOfSaccades; i++){
	 		testIllusion(img, numberOfSaccades-i);
	 		saccades.put(i, lumListVal);
			this.list=null;
	 	}
	}
	
	public List<Integer> getListLumBetweenMinLocal(int numbRect, int lowerBound, int upperBound, int[] lumVal) {
		//UPPERBOUND 175
		upperBoundBol = false;
		lista = new ArrayList<Integer>();
		posStartLocalMin = 0;
		posEndLocalMin = 0;
		cont = 0;
		for(int k=0; k<getLumListVal().length-2;k++) {
			
				if((lumVal[k]<=lowerBound)&&(posStartLocalMin==0)) {		
						posStartLocalMin=k;
						k++;
						while(lumVal[k]==0) {
							posStartLocalMin=k;
							k++;
						}
						while((lumVal[k]>=lowerBound)&&(!(k==lumVal.length-2))) {
							if(lumVal[k]>upperBound-50){
								upperBoundBol=true;
							}	
							cont++;
							k++;
						}
						if((lumVal[k]<=lowerBound)&&(cont>=numbRect)&&(upperBoundBol)) {
							posEndLocalMin = k;
							System.err.println(posEndLocalMin);
							k=lumVal.length-1;	
						}
						else {
							posStartLocalMin=0;
							upperBoundBol=false;
						}
				}
				/*if((cont>=numbRect)&&(posStartLocalMin!=0)&&(posEndLocalMin!=0))
					k=lumVal.length-1;	*/
		}
		
		
		if((posStartLocalMin!=0)&&(posEndLocalMin!=0)) {
			for(int k=posStartLocalMin; k<=posEndLocalMin;k++) {	
				lista.add(lumVal[k]);
			}
		}
		
		if((posStartLocalMin!=0)&&(posEndLocalMin!=0)) {	
			for(int k=0; k<lumVal.length-1; k++) {
				System.out.println("POS: "+k+"  VAL: "+lumVal[k]+",  ");
			}
			System.out.print("\n\n");
			System.out.println("Start: "+posStartLocalMin+" valore: "+lumVal[posStartLocalMin]);
			System.out.println("End: "+posEndLocalMin+" valore: "+lumVal[posEndLocalMin]);
			return lista;
		}
	return lista;
	}
	
	public void andamentoGraficoSaccade(List<Integer> list) {
		listRectFiltered = new int[list.size()];
		maxGlob = 0;
		for(int h=0; h<list.size(); h++) {
			listRectFiltered[h]=list.get(h);
			if (listRectFiltered[h]>maxGlob) 
				maxGlob = listRectFiltered[h];
			System.out.print(listRectFiltered[h]+", ");
		}
		salgo=0;
		scendo=0;
		creste=0;
		eps=10;
		numeroCreste = 0;
		int maxLocalSalita = 0;
		baseSal = 0;
		baseDisc = 0;
		boolean inCresta = false;
		System.out.print("\n\n");
		
		for(int k=0; k<listRectFiltered.length; k++) {
			if (!(k==listRectFiltered.length-1)) {
				
				deltaCorrente = listRectFiltered[k]-listRectFiltered[k+1];
				
				//SALITA
				if((deltaCorrente<-eps)&&(!inCresta)) {
							if(listRectFiltered[k]>=maxLocalSalita) {
								maxLocalSalita = listRectFiltered[k];
								salgo+=deltaCorrente;
								System.out.println("Delta corrente salita: "+deltaCorrente+"			"+listRectFiltered[k]+",  "+listRectFiltered[k+1]);
							}
				baseSal++;
				}
				
				//CRESTA
				else if (((deltaCorrente>-eps)&&(deltaCorrente<eps))&&((listRectFiltered[k]>maxGlob-30)||(listRectFiltered[k+1]>maxGlob-30))){ 
					numeroCreste++;
					creste+=deltaCorrente;
					System.out.println("Delta corrente cresta: "+deltaCorrente+"			"+listRectFiltered[k]+",  "+listRectFiltered[k+1]);	
					inCresta = true;
				}
				
				//FINTA SALITA
				else if ((k<listRectFiltered.length-4)&&(deltaCorrente<-eps)&&(inCresta)&&((listRectFiltered[k+1]>maxGlob-30)||(listRectFiltered[k+2]>maxGlob-30))) {
					System.out.println("falsa SALITA: "+deltaCorrente+"			"+listRectFiltered[k]+",  "+listRectFiltered[k+1]);
			
				}
			
				//DISCESA
				else if((deltaCorrente>+eps)&&(inCresta)&&(((listRectFiltered[k-1]>maxGlob-30)||(listRectFiltered[k+1]>maxGlob-30)))) {
					if ((k<listRectFiltered.length-4)&&(((listRectFiltered[k+1]-listRectFiltered[k+2])<-eps)||((listRectFiltered[k+2]-listRectFiltered[k+3])<-eps))){
							System.out.println("falsa DISCESA: "+deltaCorrente+"			"+listRectFiltered[k]+",  "+listRectFiltered[k+1]);
					}
					else {	
							scendo+=deltaCorrente;
							System.out.println("Delta corrente discesa: "+deltaCorrente+"			"+listRectFiltered[k]+",  "+listRectFiltered[k+1]);
							baseDisc++;
					}				
				}
				
				//DISCESA FINALE
				else if((deltaCorrente>+eps)&&(inCresta)&&(discesaFinale(listRectFiltered, k))) {
							scendo+=deltaCorrente;
							System.out.println("Delta corrente discesa: "+deltaCorrente+"			"+listRectFiltered[k]+",  "+listRectFiltered[k+1]);			
							baseDisc++;
				}
				
				else {
							baseSal++;
				}
			}
		}
		
		System.err.println("INIZIALI"); 
		System.err.println("Delta salita: "+salgo);
		System.err.println("Delta discesa: "+scendo);
		System.err.println("Numero creste: "+numeroCreste);
		System.err.println("Ampiezza base salita: "+baseSal+" 		Ampiezza base discesa: "+baseDisc);
		
		diffValStartEnd = Math.abs(listRectFiltered[0]-listRectFiltered[listRectFiltered.length-1]);
		if(Math.abs(salgo)>scendo)
			scendo+=diffValStartEnd;
		else
			salgo=salgo-diffValStartEnd;
		
		System.out.print("\n\n");
		
		System.err.println("FINALI"); 
		System.err.println("Delta salita: "+salgo);
		System.err.println("Delta discesa: "+scendo);
		System.err.println("Numero creste: "+numeroCreste);
		System.err.println("BASE SALITA: "+baseSal+" 		BASE DISCESA: "+baseDisc);
	}
	
	public boolean discesaFinale(int[] listRectFiltered,  int k) {
		while (k!=listRectFiltered.length-1) {
			if(listRectFiltered[k]<listRectFiltered[k+1])
				return false;
		k++;
		}
	return true;
	}
	
	public int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
    return randomNum;
	}
	
	public int getSalgo() {
		return salgo;
	}

	public void setSalgo(int salgo) {
		this.salgo = salgo;
	}

	public int getScendo() {
		return scendo;
	}

	public void setScendo(int scendo) {
		this.scendo = scendo;
	}

	public int getNumeroCreste() {
		return numeroCreste;
	}

	public void setNumeroCreste(int numeroCreste) {
		this.numeroCreste = numeroCreste;
	}
	
	public int getBasepos() {
		return basepos;
	}

	public int getBaseneg() {
		return baseneg;
	}

	public int[] getLumListVal() {
		return lumListVal;
	}

	public int getMaxContJump() {
		return maxContJump;
	}

	public int getMinContJump() {
		return minContJump;
	}

	public ArrayList<Integer> getListContJump() {
		return listContJump;
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

	public int getRiprova() {
		return riprova;
	}

	public int getFailure() {
		return failure;
	}
	
	public Map<Integer, int[]> getSaccades() {
		return saccades;
	}

	public void setSaccades(Map<Integer, int[]> saccades) {
		this.saccades = saccades;
	}
	
	public int getBaseSal() {
		return baseSal;
	}

	public void setBaseSal(int baseSal) {
		this.baseSal = baseSal;
	}

	public int getBaseDisc() {
		return baseDisc;
	}

	public void setBaseDisc(int baseDisc) {
		this.baseDisc = baseDisc;
	}
}