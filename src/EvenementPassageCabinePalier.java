public class EvenementPassageCabinePalier extends Evenement {
    /* PCP: Passage Cabine Palier
       L'instant précis où la cabine passe juste en face d'un étage précis.
       Vous pouvez modifier cette classe comme vous voulez (ajouter/modifier des méthodes etc.).
    */
    
    private Etage étage;
    
    public EvenementPassageCabinePalier(long d, Etage e) {
	super(d);
	étage = e;
    }
    
    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
	buffer.append("PCP ");
	buffer.append(étage.numéro());
    }
    
    public void traiter(Immeuble immeuble, Echeancier echeancier) {
	Cabine cabine = immeuble.cabine;
	cabine.étage = étage;

	if (immeuble.cabine.passagersVeulentDescendre()){
		echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
	}else{
		if (cabine.étage.aDesPassagers() && !cabine.cabineVide()){
			if(cabine.étage.numéro() != 7 && immeuble.passagerAuDessus(étage) && cabine.intention()=='^'){
				Etage e;
				if (cabine.intention() == '^'){
					e = immeuble.étage(this.étage.numéro()+1);
				}
				else{
					e = immeuble.étage(this.étage.numéro()-1);
				}
				echeancier.ajouter(new EvenementPassageCabinePalier(date + tempsPourBougerLaCabineDUnEtage, e));
			}else if(cabine.étage.numéro() != -1 && immeuble.passagerEnDessous(étage) && cabine.intention()=='v'){
				if(étage.aDesPassagersQuiDescendent() && cabine.intention()=='v'){
					echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
				}else if(étage.aDesPassagersQuiMontent() && cabine.intention()=='^'){
					echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
				}else {
					Etage e;
					if (cabine.intention() == '^'){
						e = immeuble.étage(this.étage.numéro()+1);
					}
					else{
						e = immeuble.étage(this.étage.numéro()-1);
					}
					echeancier.ajouter(new EvenementPassageCabinePalier(date + tempsPourBougerLaCabineDUnEtage, e));
				}

			}else {
				echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
			}
		}else if (étage.aDesPassagersQuiMontent() && cabine.intention() == '^') {
			echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
			} else if(étage.aDesPassagersQuiDescendent() && cabine.intention() == 'v') {
					echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
				}else{
					if(étage.numéro()==7 ||étage.numéro()==-1){
						echeancier.ajouter(new EvenementOuverturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
					}else {
						Etage e;
						if (cabine.intention() == '^'){
							e = immeuble.étage(this.étage.numéro()+1);
						}
						else{
							e = immeuble.étage(this.étage.numéro()-1);
						}
						echeancier.ajouter(new EvenementPassageCabinePalier(date + tempsPourBougerLaCabineDUnEtage, e));
					}
				}
		}
	}
	//notYetImplemented();

}
