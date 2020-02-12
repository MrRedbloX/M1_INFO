public class Barrier {
	private int nbThreads;
	private int count;
	public Barrier(int nbThreads){
		this.nbThreads = nbThreads;
		this.count = 0;
	}
	public synchronized void synchr(){
		this.count++;
		if(count < this.nbThreads){
			try{
				wait();
			}
			catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
		else{
			try{
				Thread.sleep(500);
			}
			catch (InterruptedException ie){
				ie.printStackTrace();
			 }
			 count = 0;
			notifyAll();
		}
	}
}
