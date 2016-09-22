package mainEM;

import earnerMovementTypes.EarnerMovement500x500Specular;
import earnerMovementTypes.EarnerMovementUniversal;
import graphicsUtility.GraphicWindow;
import imageUtility.EmployImage;

import java.io.IOException;
import java.util.List;

public class MainEM {

	public static void main(String[] args) throws IOException {
		
		int numberOfSaccades = 1;
		EmployImage ei = new EmployImage();
		EarnerMovement500x500Specular emSpecular = new EarnerMovement500x500Specular();
		EarnerMovementUniversal em = new EarnerMovementUniversal();
		
		//**********************************************************************************
		//EARNER MOVEMENT UNIVERSAL
		//**********************************************************************************
		int numbRect = 5;
		int lowerBound = 41;
		int upperBound = 0;
		int limitDiffBasi = 2;
		//int lowerBound = 80;
		double contIll = 0;
		double contStat = 0;
		int[] listS;
		List<Integer> list;
		//Carico l'immagine
		ei.loadImage();

		//Avvio il percettore sull'immagine caricata
		em.testIllusion(ei.getImg(), numberOfSaccades);  
		
		//Stampo il keySet della mappa delle saccadi
			System.out.println(em.getSaccades().keySet());
		
		//Stampo le liste di luminosità
		for(int i=1; i<=em.getSaccades().keySet().size(); i++) {
		  	System.err.println("**********************************************************************************************************************************ITERAZIONE: "+i);
			lowerBound = 41;
			upperBound = 0;
			//System.err.print("Lista numero "+i);
			listS = null;
			listS = em.getSaccades().get(i);
			for (int lum:listS){
				System.out.print(lum+",  ");
			}
			
			//Grafico
	  		GraphicWindow f = new GraphicWindow(ei.getImagePath());
	  		f.visualizza(listS);
	  		//
	  		
	  		for (int u: listS) {
	  			if (u>upperBound)
	  				upperBound=u;
	  		}

	  		list = em.getListLumBetweenMinLocal(numbRect, lowerBound, upperBound, listS);
	  		if(list.size()==0) {
	  			while (lowerBound<upperBound) {
		  			lowerBound++;	
	  				list = em.getListLumBetweenMinLocal(numbRect, lowerBound, upperBound, listS);
	  				if(list.size()>0)
	  					lowerBound=upperBound;
	  			}
	  		}
	  		
			System.out.println("Lower Bound: "+lowerBound);
	  		  			
	  		if(list.size()>0) {
				  			System.out.println("\n\nListRectSize: "+list.size());
				  			em.andamentoGraficoSaccade(list);
				  			
				  			System.err.println(em.getBaseSal()-em.getBaseDisc());
				  			//CONCLUSIONE SACCADE
				  			if(Math.abs(em.getBaseSal()-em.getBaseDisc())<limitDiffBasi){	
				  				contStat++;
				  			}
				  			else
				  				contIll++;
				  			//
	  		}
	  		else {
	  				System.out.println("Non ho trovato una porzione di grafico adeguata");
	  		}
		}
	  	
		System.out.println("\n\n\n---------------STATISTICHE:");
		//STATISTICHE IMMAGINE
		if (contIll>contStat) {
	    			System.out.println("L'immagine e' un'ILLUSIONE OTTICA con probabilità del: "+(double)(contIll/(contIll+contStat))*100+"%");
				   	System.out.println("Su un numero di test totali pari a: "+(contIll+contStat));
					System.out.println("L'immagine e' stata rilevata come in movimento "+contIll+" volte e statica "+contStat+" volte");
		  			System.out.println("\n\n\nIllusion o.O': it drives me crazy");
	    }
		else if (contIll<contStat) {
					System.out.println("L'immagine e' STATICA con probabilità del: "+(double)(contStat/(contIll+contStat))*100+"%");
		   			System.out.println("Su un numero di test totali pari a: "+(contIll+contStat));
		   			System.out.println("L'immagine e' stata rilevata come statica "+contStat+" volte e come illusione "+contIll+" volte");
		}
		
		else if ((contIll==contStat)&&(contIll!=0)&&(contStat!=0)) {
					System.out.println("Non so dire nulla");
		}

	}

	/*
	//**********************************************************************************
	// EARNER MOVEMENT 500x500 SPECULAR
	//**********************************************************************************
	//Carico l'immagine
	ei.loadImage();
		
	//Avvio il percettore sull'immagine caricata
   	emSpecular.testIllusion(ei.getImg());     	
	   	
	 //Statistiche
	  if(emSpecular.getIllusionTests()>emSpecular.getStaticTests()){
	    	System.out.println("L'immagine e' un'ILLUSIONE OTTICA con percentuale di probabilita': "+((double)emSpecular.getIllusionTests()/((double)(emSpecular.getIllusionTests()+emSpecular.getStaticTests())))*100+"%");
	   		System.out.println("Su un numero di test totali pari a: "+(emSpecular.getIllusionTests()+emSpecular.getStaticTests()));
	   		System.out.println("L'immagine e' stata rilevata come in movimento "+emSpecular.getIllusionTests()+" volte e statica "+emSpecular.getStaticTests()+" volte");
	  		System.out.println("\n\n\nIllusion o.O': it drives me crazy");
	  	
		}
	    else if(emSpecular.getIllusionTests()<emSpecular.getStaticTests()) {
	    	System.out.println("L'immagine e' STATICA con percentuale di probabilita': "+((double)emSpecular.getStaticTests()/((double)(emSpecular.getIllusionTests()+emSpecular.getStaticTests())))*100+"%");
	    	System.out.println("Su un numero di test totali pari a: "+(emSpecular.getIllusionTests()+emSpecular.getStaticTests()));
	   		System.out.println("L'immagine e' stata rilevata come in movimento "+emSpecular.getIllusionTests()+" volte e statica "+emSpecular.getStaticTests()+" volte");
	   		System.out.println("\n\n\nStatic image =D!!!");
	   	}
	    else if((emSpecular.getIllusionTests()==emSpecular.getStaticTests())&&(emSpecular.getIllusionTests()!=0)&&(emSpecular.getStaticTests()!=0)) {
	    	System.out.println("L'immagine ha la stessa probabilita' di essere statica quanto un illusione");
			System.out.println("Su un numero di test totali pari a: "+emSpecular.getNumberExperiments());	
		}
	    //Se l'immagine non è mai stata rilevata come sicuramente statica o come illusione
	   	else if(emSpecular.getRiprova()>=emSpecular.getRetryLimit()){
	   		for(int i=0; i<emSpecular.getListContJump().size(); i++) {
	   			if(emSpecular.getListContJump().get(i)>emSpecular.getMaxContJump()) {
	   				emSpecular.setMaxContJump(emSpecular.getListContJump().get(i));
	    		}
	    		if(emSpecular.getListContJump().get(i)<emSpecular.getMinContJump()) {
	    			emSpecular.setMinContJump(emSpecular.getListContJump().get(i));
	    		}
	   		}
	    	if(emSpecular.getMaxContJump()>=400) {
		   			System.out.print("Ho tenunto conto del valore piu' alto :");
		   			System.out.println(emSpecular.getMaxContJump());
		   			System.out.println(emSpecular.getMinContJump()+"-"+emSpecular.getMaxContJump());
		   			System.out.println("Ho osservato l'immagine piu' volte! L'immagine e' probabile che sia statica!!! ma non ne ho la certezza..\n");
	    	}
		    	else if(emSpecular.getMinContJump()<325) {
		   			System.out.print("Ho tenunto conto del valore piu' basso: ");
		   			System.out.println(emSpecular.getMinContJump());
		   			System.out.println(emSpecular.getMinContJump()+"-"+emSpecular.getMaxContJump());
		   			System.out.println("Ho osservato l'immagine piu' volte! L'immagine e' probabile che sia un illusione ottica!!! ma non ne ho la certezza..\n");
		   		}
	   		
	   	else {
	   			System.out.println(emSpecular.getMinContJump()+"-"+emSpecular.getMaxContJump());
	   			System.out.print("Non so dire nulla :(");   	
	    	}		    	
		   	//Calcolo basato sulla media dei jump
	    	if((sumContJump/listContJump.size())>=325) {
	   			System.out.print("Tenendo conto del valore medio: ");
	   			System.out.println(sumContJump/listContJump.size());
	   			System.out.println("ed aventdo osservato l'immagine piu' volte! L'immagine e' probabile che sia statica!!! ma non ne ho la certezza..\n");
	   		}
	    	if(((int)(sumContJump/listContJump.size()))<325) {
	    		System.out.print("Tenendo conto del valore medio: ");
	    		System.out.println(sumContJump);
	   			System.out.println("ed aventdo osservato l'immagine piu' volte! L'immagine e' probabile che sia un illusione ottica!!! ma non ne ho la certezza..\n");
	   		}
	    }

	    //Disegno il grafico di luminosita'
	    GraphicWindow f = new GraphicWindow(ei.getImagePath());
	    f.visualizza(emSpecular.getLumListVal());
	   	//
	   	}*/
}