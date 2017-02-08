public class Graenseflade implements Runnable {
	//indeholder vores main metode
	
	private double måling = 0;
	
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
			this.måling = Monitor.getConValue();	
			System.out.println("Målt værdi i celcius: " +måling+ " °C");
			}
		}
	
	/** Starter en tråd og kalder run()-metoden */
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
		// laver et grænseflade-objekt. Ved instantieringen bliver setMinMax() kaldt
		Graenseflade i = new Graenseflade();
		
		// starter grænsefladen som en parallel tråd
		i.start();
		
		// starter monitorens run-metode, som kører som en uendelig løkke 
		Monitor.run();
		}
	}

