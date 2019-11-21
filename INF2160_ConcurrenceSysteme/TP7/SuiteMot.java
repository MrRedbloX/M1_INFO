public class SuiteMot{
	public static void main(String args[]){
		Word first = null;
		Word current = null;
		Barrier bar = new Barrier(args.length);
		for(int i=0; i<args.length; i++){
			if(i == 0){
				first = new Word(args[i],bar);
				current = first;
			}
			else{
				Word tmp = new Word(args[i],bar);
				current.setNextThread(tmp);
				current = tmp;
				if(i == args.length-1){
					 current.setNextThread(first);
					 synchronized(first){
						first.notify();
					}
				}
			}
			current.start();
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
					Thread.sleep(1000);
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
