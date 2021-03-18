public class EvenementPietonArrivePalier extends Evenement {
    /* PAP: Pieton Arrive Palier
       L'instant précis ou un passager qui à décidé de continuer à pieds arrive sur un palier donné.
    */

    private Etage étage;
    private Passager passager;

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
      buffer.append("PAP ");
      buffer.append(étage.numéro());
      buffer.append(" #");
      buffer.append(passager.numéroDeCréation);
    }
    
    
    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        étage.ajouterPietonEscalier(passager,echeancier);
        if (passager.sens()=='v'){
            Etage e = immeuble.étage(étage.numéro()+1);
            if(e.sportifSurEtage(passager)){
                e.supprimerSportif(passager);
            }
        }else{
            Etage e = immeuble.étage(étage.numéro()-1);
            if(e.sportifSurEtage(passager)){
                e.supprimerSportif(passager);
            }
        }
        if(passager.numéroDestination()!=étage.numéro()){
            Etage e = passager.étageDépart();
            if (passager.sens() == 'v'){
                echeancier.ajouter(new EvenementPietonArrivePalier(date + Global.tempsPourMonterOuDescendreUnEtageAPieds,immeuble.étage(e.numéro()+-1),passager));
            }else{
                echeancier.ajouter(new EvenementPietonArrivePalier(date + Global.tempsPourMonterOuDescendreUnEtageAPieds,immeuble.étage(e.numéro()+1),passager));
            }
        }

    }

    public EvenementPietonArrivePalier(long d, Etage edd, Passager pa) {
	    super(d);
	    étage = edd;
	    passager = pa;
    }

    public Passager getPassager(){
        return this.passager;
    }
    
}
