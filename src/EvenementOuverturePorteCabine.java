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
		//cabine.changerIntention('-');
		cabine.porteOuverte = true;
		cabine.faireDescendrePassagers(immeuble,date );
		//faire descendre1par1avecconditionEtIncrémenteur
		int nbPersonneQuiEntre=0;
		boolean ajoutEventFermeture = false;
		while (!cabine.cabineRemplie() && étage.aDesPassagers()){
			Passager p = étage.faireEntrerPremierPassager();
			nbPersonneQuiEntre++;
			ajoutEventFermeture = true;
			cabine.changerIntention(p.sens());
			char fmp = cabine.faireMonterPassager(p);
			echeancier.supprimerPAP(p);
		}
		if(ajoutEventFermeture){
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes + tempsPourEntrerOuSortirDeLaCabine *(nbPersonneQuiEntre)));
		}
    }

}
