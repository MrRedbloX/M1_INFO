public class Barrier {
	private int nbThread;
	private int count;
	public Barrier(int nbThread){
		this.nbThread = nbThread;
		this.count = 0;
	}
	public synchronized void synchr(){
		if(count < this.nbThread-1){
			this.count++;
			try{
				wait();
			}
			catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
		else{
			System.out.println(nbThread);
			try{
				Thread.sleep(1000);
			}
			catch (InterruptedException ie){
				ie.printStackTrace();
			 }
			notifyAll();
			count = 0;
		}
	}
}
