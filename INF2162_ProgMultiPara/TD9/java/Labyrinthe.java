import java.util.HashSet; 
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Labyrinthe{
	private Integer[][] init;
	public Labyrinthe(Integer[][] init_){
		this.init = init_;
	}
	
	public Set<Integer[][]> cheminSortieAPartirDe(Pair pos_, Integer[][] lab_){
		Set<Integer[][]> ret = new HashSet<Integer[][]>();
		switch(lab_[pos_.getKey()][pos_.getValue()]) {
			case 9:
				ret.add(lab_);
				break;
			case 0:
				Integer x = pos_.getKey();
				Integer y = pos_.getValue();
				Set<Pair> voisins = Stream.of(new Pair(x-1,y),new Pair(x+1,y),new Pair(x,y-1),new Pair(x,y+1))
					.filter(e -> e.getKey()>=0 && e.getKey()<lab_.length && e.getValue()>=0 && e.getValue()<lab_[e.getKey()].length)
					.collect(Collectors.toSet());
				Integer[][] labTmp = java.util.Arrays.stream(lab_).map(e -> e.clone()).toArray($ -> lab_.clone());
				labTmp[x][y]=2;
				ret = voisins.stream().map(e -> cheminSortieAPartirDe(e,labTmp)).flatMap(s -> s.stream()).collect(Collectors.toSet());
				break;
		}
		return ret;
	}
	
	public String toString() {
		String ret = "";
		for (int i = 0; i < this.init.length; i++) {
			for (int j = 0; j < this.init[i].length; j++) {
				switch (this.init[i][j]) {
				case 0:
					ret += "\u001B[40m  ";break;
				case 1:
					ret += "\u001B[43m  ";break;
				case 2:
					ret += "\u001B[46m  ";break;
				case 9:
					ret += "\u001B[41m  ";break;
				}
			}
			ret+="\u001B[0m\n";
		}
		return ret;
	}
	public static void main(String[] args){
		Integer laby[][] = {
				{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
				{ 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
				{ 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
				{ 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

		Labyrinthe laby1 = new Labyrinthe(laby);
		System.out.println(laby1);
		laby1.cheminSortieAPartirDe(new Pair(0,1),laby).forEach(sol -> System.out.println(new Labyrinthe(sol)));
	}
}
