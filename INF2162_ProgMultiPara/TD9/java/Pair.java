/** 
 * Cette classe symbolise une pair de String
 * @author COGOLUEGNES Charles
 */
public class Pair{
	private Integer key;
	private Integer value;
	
	/**
	 * Le constructeur de la classe Pair
	 * @param key_ La clé
	 * @param value_ La valeur
	 */
	public Pair(Integer key_, Integer value_){
		this.key = key_;
		this.value = value_;
	}
	
	/** 
	 * Getter sur la clé
	 * @return la clé
	 */
	public Integer getKey(){
		return this.key;
	}
	
	/**
	 * Getter sur la valeur
	 * @return la valeur
	 */
	public Integer getValue(){
		return this.value;
	}
}
