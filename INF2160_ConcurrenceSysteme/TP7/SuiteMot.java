public class SuiteMot{
	public static void main(String args[]){
		Barrier bar = new Barrier(args.length);
		Word first = new Word(args[0],bar);
		Word current = first;
		current.start();
		for(int i=1; i<args.length; i++){
			Word tmp = new Word(args[i],bar);
			current.setNextThread(tmp);
			current = tmp;
			current.start();
			if(i == args.length-1){
				 current.setNextThread(first);
				 synchronized(first){
					first.notify();
				}
			}
		}
	}
}

class Word extends Thread{
	private String word;
	private Word nextThread;
	private Barrier bar;
	public Word(String word, Barrier bar){
		this.word = word;
		this.nextThread = nextThread;
		this.bar = bar;
	}
	public void run(){
		synchronized(this){
			bar.synchr();
			while(true){
				try{
					wait();
				}
				catch (InterruptedException ie){
					ie.printStackTrace();
				}
				System.out.println(this.word);
				try{
					Thread.sleep(500);
				}
				catch (InterruptedException ie){
					ie.printStackTrace();
				}
				synchronized(this.nextThread){
					this.nextThread.notify();
				}
			}
		}
	}
	public void setNextThread(Word nextThread){
		this.nextThread = nextThread;
	}
}
