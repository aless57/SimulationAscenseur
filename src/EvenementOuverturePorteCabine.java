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
		if (immeuble.passagerAuDessus(cabine.étage)){
			cabine.changerIntention('^');
			ajoutEventFermeture = true;
		}else if(immeuble.passagerEnDessous(cabine.étage)){
			cabine.changerIntention('v');
			ajoutEventFermeture = true;
		}
		if (!immeuble.passagerEnDessous(cabine.étage) && (!immeuble.passagerAuDessus(cabine.étage))){
			cabine.changerIntention('-');
			ajoutEventFermeture = false;
		}
		cabine.porteOuverte = true;
		int nbPersonneQuiDescendent = 0;
		if (cabine.getTableauPassager().length > 0){
			 nbPersonneQuiDescendent= cabine.faireDescendrePassagers(immeuble,date );
			 if (cabine.cabineVide()){
			 	cabine.changerIntention(cabine.getPassager(0).sens());
			 	ajoutEventFermeture = true;
			 }
		}
		int nbPersonneQuiEntrent = 0;
		if (étage.aDesPassagers()){
			nbPersonneQuiEntrent=étage.entrerPassagerCabine(cabine,echeancier);
		}
		if (nbPersonneQuiEntrent > 0){
			ajoutEventFermeture = true;
		}
		if(ajoutEventFermeture){
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes + tempsPourEntrerOuSortirDeLaCabine *(nbPersonneQuiEntrent+nbPersonneQuiDescendent)));
		}

    }

}
