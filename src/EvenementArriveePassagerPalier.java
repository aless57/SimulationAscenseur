public class EvenementArriveePassagerPalier extends Evenement {
    /* APP: Arrivée Passager Palier
       L'instant précis ou un nouveau passager arrive sur un palier donné, dans le but
       de monter dans la cabine.
    */
    
    private Etage étage;

    public EvenementArriveePassagerPalier(long d, Etage edd) {
	super(d);
	étage = edd;
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	buffer.append("APP ");
	buffer.append(étage.numéro());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	assert étage != null;
	assert immeuble.étage(étage.numéro()) == étage;
	Passager p = new Passager(date, étage, immeuble);
	Cabine c = immeuble.cabine;
 
	if (c.porteOuverte && c.étage == étage) {
	    if (c.intention() == '-') {
			c.changerIntention(p.sens());
			echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
			char fmp = c.faireMonterPassager(p);
			if (fmp == 'O') {
				assert true;
			} else {
				assert false : "else impossible";
			}
	    }else {
	    	if(c.cabineRemplie()){
				étage.ajouter(p);
				echeancier.ajouter(new EvenementPietonArrivePalier(date + délaiDePatienceAvantSportif, p.étageDépart(), p));
			}else{
	    		char n = c.faireMonterPassager(p);
	    		if(n=='O'){
					echeancier.decalerFPC();
					echeancier.supprimerPAP(p);
				}else{
					étage.ajouter(p);
					echeancier.ajouter(new EvenementPietonArrivePalier(date + délaiDePatienceAvantSportif, p.étageDépart(), p));
				}
			}

		}
	} else if (c.étage == étage) {
		étage.ajouter(p);
		echeancier.ajouter(new EvenementPietonArrivePalier(date + délaiDePatienceAvantSportif, p.étageDépart(), p));
		}else if(c.porteOuverte) {
				étage.ajouter(p);
				if (!c.cabineVide()){
					if (p.numéroDepart() < c.étage.numéro() && !immeuble.passagerAuDessus(étage)) {
						c.changerIntention('v');
					} else if(p.numéroDepart() > c.étage.numéro() && !immeuble.passagerEnDessous(étage)){
						c.changerIntention('^');
					}
				}
				if (!c.cabineVide()){
					if(!echeancier.rechercheFPC()) {
						echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
					}
				}
				echeancier.ajouter(new EvenementPietonArrivePalier(date + délaiDePatienceAvantSportif, p.étageDépart(), p));
			}else{
				étage.ajouter(p);
				echeancier.ajouter(new EvenementPietonArrivePalier(date + délaiDePatienceAvantSportif, p.étageDépart(), p));
			}

	
	date += étage.arrivéeSuivante();
	echeancier.ajouter(this);
    }

}
