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
		cabine.changerIntention('-');
		cabine.porteOuverte = true;
		cabine.faireDescendrePassagers(immeuble,date);
		//verifier nb personne dans la cabine
		while (!cabine.cabineRemplie() && étage.aDesPassagers()){
			Passager p = étage.faireEntrerPremierPassager();
			cabine.changerIntention(p.sens());
			char fmp = cabine.faireMonterPassager(p);
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + Global.tempsPourOuvrirOuFermerLesPortes));
		}
    }

}
