public class Graenseflade implements Runnable {
	//indeholder vores main metode
	
	private double m�ling = 0;
	
	public Graenseflade(){
		setMinMax();
	}
	
	/** Overskriver run()-metoden i Runnable og bliver henvist til som update() */
	public void run() {
		while(true){
			try{
				Thread.sleep(30000);
			}
			catch(InterruptedException e){
				System.out.println("Der skete en fejl!");
				e.printStackTrace();
			}
			this.m�ling = Monitor.getConValue();	
			System.out.println("M�lt v�rdi i celcius: " +m�ling+ " �C");
			}
		}
	
	/** Starter en tr�d og kalder run()-metoden */
	public void start() {
		Thread t = new Thread (this, "Interface");
		t.start();
	}
	
	/** Metode, der kalder de relevante setter-metoder i monitor-modulet */
	public void setMinMax(){
		Monitor.setMax();
		Monitor.setMin();
		Monitor.setUrgent();
	}
	
	public static void main(String[] args) {
		// laver et gr�nseflade-objekt. Ved instantieringen bliver setMinMax() kaldt
		Graenseflade i = new Graenseflade();
		
		// starter gr�nsefladen som en parallel tr�d
		i.start();
		
		// starter monitorens run-metode, som k�rer som en uendelig l�kke 
		Monitor.run();
		}
	}

