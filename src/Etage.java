import java.util.ArrayList;

public class Etage extends Global {
    /* Dans cette classe, vous pouvez ajouter/enlever/modifier/corriger les méthodes, mais vous ne
       pouvez pas ajouter des attributs (variables d'instance).
    */

	private int numéro;
    /* Le numéro de l'Etage du point de vue de l'usager (et non pas l'index correspondant
       dans le tableau.
    */

	private Immeuble immeuble;
	/* Back-pointer vers l'immeuble correspondant.
	 */

	private LoiDePoisson poissonFrequenceArrivee;
	/* Pour cet étage.
	 */

	private ArrayList<Passager> passagers = new ArrayList<Passager>();
    /* Les passagers qui attendent devant la porte et qui espèrent pouvoir monter
       dans la cabine.
       Comme toute les collections, il ne faut pas l'exporter.
    */

	private ArrayList<Passager> pietons = new ArrayList<Passager>();
    /* Les passager qui sont à pieds et qui sont actuellement arrivés ici.
       Comme toute les collections, il ne faut pas l'exporter.
    */

	public Etage(int n, int fa, Immeuble im) {
		numéro = n;
		immeuble = im;
		int germe = n << 2;
		if (germe <= 0) {
			germe = -germe + 1;
		}
		poissonFrequenceArrivee = new LoiDePoisson(germe, fa);
	}

	public void afficheDans(StringBuilder buffer) {
		if (numéro() >= 0) {
			buffer.append(' ');
		}
		buffer.append(numéro());
		if (this == immeuble.cabine.étage) {
			buffer.append(" C ");
			if (immeuble.cabine.porteOuverte) {
				buffer.append("[  ]: ");
			} else {
				buffer.append(" [] : ");
			}
		} else {
			buffer.append("   ");
			buffer.append(" [] : ");
		}
		int i = 0;
		while (((buffer.length() < 50) && (i < passagers.size()))) {
			passagers.get(i).afficheDans(buffer);
			i++;
			buffer.append(' ');
		}
		if (i < passagers.size()) {
			buffer.append("...(");
			buffer.append(passagers.size());
			buffer.append(')');
		}
		while (buffer.length() < 80) {
			buffer.append(' ');
		}
		buffer.append("| ");
		i = 0;
		while (((buffer.length() < 130) && (i < pietons.size()))) {
			pietons.get(i).afficheDans(buffer);
			i++;
			buffer.append(' ');
		}
		if (i < pietons.size()) {
			buffer.append("...(");
			buffer.append(pietons.size());
			buffer.append(')');
		}
	}

	public int numéro() {
		return this.numéro;
	}

	public void ajouter(Passager passager) {
		assert passager != null;
		passagers.add(passager);
	}

	public long arrivéeSuivante() {
		return poissonFrequenceArrivee.suivant();
	}

	public boolean aDesPassagersQuiMontent(){
		for(Passager p : passagers){
			if ( p.sens() == '^' ) {
				return true;
			}
		}
		return false;
	}

	public boolean aDesPassagersQuiDescendent(){
		for(Passager p : passagers){
			if ( p.sens() == 'v' ) {
				return true;
			}
		}
		return false;
	}

	public int entrerPassagerCabine(Cabine cabine, Echeancier echeancier){
		boolean infernal = false;
		int test = 20;
		Passager pPrio = cabine.getPassager(0);
		if(this.passagers.size() > 1) {
			infernal = true;
		}
		int res = 0;
		int i = 0;
		char m = 'O';
		while (i < this.passagers.size()) {
			Passager p = passagers.get(i);
			m = cabine.faireMonterPassager(p);
			if (m == 'O') {
				echeancier.supprimerPAP(p);
				passagers.remove(i);
				res++;
			} else if (m == 'P') {
				if (((cabine.intention()=='^' && !immeuble.passagerAuDessus(this)) || (cabine.intention()=='v' && !immeuble.passagerEnDessous(this)))){
					if(!modeParfait) {
						pPrio = cabine.getPassager(0);
						while (i < cabine.getTableauPassager().length) {
							p = cabine.getPassager(i);
							if(p != null && (cabine.intention() == p.sens())){
								if (Math.abs(cabine.étage.numéro - Math.abs(p.étageDestination().numéro)) <= test) {
									test = Math.abs(cabine.étage.numéro - Math.abs(p.étageDestination().numéro));
									pPrio = p;
								}
							}
							i++;
						}
						cabine.changerIntention(pPrio.sens());
					}
				}
				return res;
			} else {
				assert (m == 'I');
				i++;
			}
		}
		i=0;
		if (infernal && ((cabine.intention()=='^' && !immeuble.passagerAuDessus(this)) || (cabine.intention()=='v' && !immeuble.passagerEnDessous(this)))){
			if(!modeParfait){
				while (i < cabine.getTableauPassager().length){
					Passager p = cabine.getPassager(i);
					if (p != null && (cabine.intention() == p.sens())){
						if(Math.abs(cabine.étage.numéro - Math.abs(p.étageDestination().numéro))<=test){
							test = Math.abs(cabine.étage.numéro - Math.abs(p.étageDestination().numéro));
							pPrio = p;
						}
					}
					i++;
				}
				cabine.changerIntention(pPrio.sens());
			}
		}
		return res;
	}

	public void ajouterPietonEscalier(Passager p,Echeancier echeancier){
		this.passagers.remove(p);
		if(p.numéroDestination() == this.numéro){
			this.pietons.remove(p);
		}else{
			this.pietons.add(p);
			echeancier.supprimerPAP(p);
		}
	}

	public boolean aDesPassagers(){
		return (!passagers.isEmpty());
	}

	public boolean sportifSurEtage(Passager p){
		boolean test = false;
		int i=0;
		while (i<pietons.size()){
			if(pietons.get(i)==p){
				test=true;
			}
			i++;
		}
		return test;
	}

	public void supprimerSportif(Passager p){
		pietons.remove(p);
	}
	public int getNbPassager(){return passagers.size();}

}
