/** 
 * Cette classe symbolise une pair de String
 * @author COGOLUEGNES Charles
 */
public class Pair{
	private String key;
	private String value;
	
	/**
	 * Le constructeur de la classe Pair
	 * @param key_ La clé
	 * @param value_ La valeur
	 */
	public Pair(String key_, String value_){
		this.key = key_;
		this.value = value_;
	}
	
	/** 
	 * Getter sur la clé
	 * @return la clé
	 */
	public String getKey(){
		return this.key;
	}
	
	/**
	 * Getter sur la valeur
	 * @return la valeur
	 */
	public String getValue(){
		return this.value;
	}
}
