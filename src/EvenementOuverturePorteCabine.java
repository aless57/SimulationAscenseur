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
		if (!cabine.étage.aDesPassagers() && !cabine.passagersVeulentDescendre()){
			if (immeuble.passagerAuDessus(cabine.étage)){
				cabine.changerIntention('^');
			}else if(immeuble.passagerEnDessous(cabine.étage)){
				cabine.changerIntention('v');
			}
		}
		if (!immeuble.passagerEnDessous(cabine.étage) && (!immeuble.passagerAuDessus(cabine.étage))){
			cabine.changerIntention('-');
		}
		cabine.porteOuverte = true;
		int nbPersonneQuiDescendent = cabine.faireDescendrePassagers(immeuble,date );
		int nbPersonneQuiEntrent=0;
		boolean ajoutEventFermeture = false;
		while (!cabine.cabineRemplie() && étage.aDesPassagers()){
			Passager p = étage.faireEntrerPremierPassager();
			nbPersonneQuiEntrent++;
			ajoutEventFermeture = true;
			cabine.changerIntention(p.sens());
			char fmp = cabine.faireMonterPassager(p);
			echeancier.supprimerPAP(p);
		}
		if(ajoutEventFermeture){
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes + tempsPourEntrerOuSortirDeLaCabine *(nbPersonneQuiEntrent+nbPersonneQuiDescendent)));
		}
    }

}
