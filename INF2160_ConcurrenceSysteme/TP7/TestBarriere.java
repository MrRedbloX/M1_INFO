class Test extends Thread {
	private Barrier bar;
	private String mot;
	public Test(Barrier bar, String mot){
		this.bar = bar;
		this.mot = mot;
	}
	public void run(){
		while(true){
			bar.synchr();
			System.out.println(mot);
		}
	}
}

public class TestBarriere {
	public static void main(String args[]){
		final int nbT=3;
		Barrier unBar = new Barrier(nbT);
		for(int i=0; i< nbT; i++){
			Test th = new Test(unBar, String.valueOf(i));
			th.start();
		}
	}
}
