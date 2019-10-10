/**
 * Cette classe représente une ligne de métro
 * @author COGOLUEGNES Charles
 */
public class Ligne{
	private int numLigne;

	/**
	 * Le constructeur de la classe Ligne
	 * @param numLigne_ Le numéro de la ligne
	 */
	public Ligne(int numLigne_){
		this.numLigne = numLigne_;
	}

	/**
	 * Getter de la classe Ligne
	 * @return le numéro de la ligne
	 */
	public int getLigne(){
		return this.numLigne;
	}

	/**
	 * Setter de la classe Ligne
	 * @param newLigne_ Le nouveau numéro de ligne
	 */
	public void setLigne(int newLigne_){
		this.numLigne = newLigne_;
	}

	@Override
	public int hashCode(){
		return this.numLigne;
	}

	@Override
	public boolean equals(Object o_){
		boolean ret = false;
		if(this.hashCode() == o_.hashCode()) ret = true;
		return ret;
	}
}
