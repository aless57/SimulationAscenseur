public class EvenementOuverturePorteCabine extends Evenement {
    /* OPC: Ouverture Porte Cabine
       L'instant précis ou la porte termine de s'ouvrir.
    */
    
    public EvenementOuverturePorteCabine(long d) {
	super(d);
    }
    
    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	buffer.append("OPC");
    }
    
    public void traiter(Immeuble immeuble, Echeancier echeancier) {
		Cabine cabine = immeuble.cabine;
		Etage étage = cabine.étage;
		//REGARDER LE PAP POUR CHANGER L INTENTION PAR RAPPORT A L'ORDRE
		boolean ajoutEventFermeture = false;
		if (immeuble.passagerAuDessus(cabine.étage) && !étage.aDesPassagers()){
			cabine.changerIntention('^');
			ajoutEventFermeture = true;
		}else if(immeuble.passagerEnDessous(cabine.étage) && !étage.aDesPassagers()){
			cabine.changerIntention('v');
			ajoutEventFermeture = true;
		}
		cabine.porteOuverte = true;
		int nbPersonneQuiDescendent = 0;
		if (cabine.getTableauPassager().length > 0){
			 nbPersonneQuiDescendent= cabine.faireDescendrePassagers(immeuble,date );
			 if (cabine.cabineVide()){
				 if (((cabine.intention()=='^' && !immeuble.passagerAuDessus(étage)) || (cabine.intention()=='v' && !immeuble.passagerEnDessous(étage)))){
					 cabine.changerIntention(cabine.getPassager(0).sens());
				 }
			 }
			ajoutEventFermeture = true;
		}
		if (cabine.intention()=='v' && !cabine.cabineVide() && étage.aDesPassagersQuiMontent() && étage.getNbPassager()==1){
			cabine.changerIntention('^');
		}else if(cabine.intention() == '^' && !cabine.cabineVide() && étage.aDesPassagersQuiDescendent() && étage.getNbPassager()==1){
			cabine.changerIntention('v');
		}
		int nbPersonneQuiEntrent = 0;
		if (étage.aDesPassagers()){
			if(étage.aDesPassagersQuiDescendent() && cabine.intention()=='^' && immeuble.passagerAuDessus(étage)){

			} else if (étage.aDesPassagersQuiMontent() && cabine.intention() == 'v' && immeuble.passagerEnDessous(étage)) {

			}else{
				nbPersonneQuiEntrent=étage.entrerPassagerCabine(cabine,echeancier);
			}
		}

		if (nbPersonneQuiEntrent > 0){
			ajoutEventFermeture = true;
		}
		if (!immeuble.passagerEnDessous(cabine.étage) && (!immeuble.passagerAuDessus(cabine.étage)) && (nbPersonneQuiEntrent==0) && (!cabine.cabineVide())){
			cabine.changerIntention('-');
			ajoutEventFermeture = false;
		}

		if(ajoutEventFermeture){
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes + tempsPourEntrerOuSortirDeLaCabine *(nbPersonneQuiEntrent+nbPersonneQuiDescendent)));
		}


    }

}
