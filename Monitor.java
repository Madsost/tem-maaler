import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Monitor{

	private static double conValue, min, max, urgent;
	private static String laege = "Læge tilkaldt";
	private static String plejer = "Målingen ligger uden for det tilladte";
	
	/** Sætter minimumsgrænsen til det indtastede */
	public static void setMin(){
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Indtast den lavest tilladte temperatur i grader: ");
		try{
		double check = keyboard.nextDouble();
		if (check < 37.1 && check > 36.0) {
			min = check;
			System.out.println("Minimalt tilladte afvigelse: " + min + " °C");
		}
		else
		{
			min = 36.5;
			System.out.println("Ugyldigt tal. Anvender standard 36.5 °C");
		}
		}
		catch(Exception e){
			System.out.println("Fejl. Det indtastede var ikke et kommatal. Anvender standard 36.5 °C");
			min = 36.5;
		}
	}
	
	/** Sætter maximumsgrænsen til det indtastede */
	public static void setMax(){
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Indtast den højest tilladte temperatur i grader: ");
		try{
			double check = keyboard.nextDouble();
			if (check > min && check >= 36.5){
				max = check;
				System.out.println("Maximalt tilladte afvigelse: " + max + " °C");
			}
			else
			{
				max = 37.1;
				System.out.println("Ugyldigt tal. Anvender standard 37.1 °C");
			}	
		}
		catch(Exception e){
			System.out.println("Fejl. Det indtastede var ikke et kommatal. Anvender standard 37.1 °C");
			max = 37.1;
		}
	}
	
	/** Sætter ekstremt minimum */
	public static void setUrgent(){
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Indtast tilladt afvigelse +/-: ");
		try{
		double check = keyboard.nextDouble();
		if (check < 10 && check > 0.0) {
			urgent = check;
			System.out.println("Kritisk laveste værdi: " + (min-urgent) + " °C");
			System.out.println("Kritisk højeste værdi: " + (max+urgent) + " °C");
		}
		else
		{
			urgent = 1.4;
			System.out.println("Ugyldigt tal. Anvender standard afvigelse 1.4");
			System.out.println("Kritisk laveste værdi: " + (min-urgent) + " °C");
			System.out.println("Kritisk højeste værdi: " + (max+urgent) + " °C");
		}
		}
		catch(Exception e){
			System.out.println("Fejl. Det indtastede var ikke et kommatal. Anvender standard 1.4");
			urgent = 1.4;
			System.out.println("Kritisk laveste værdi: " + (min-urgent) + " °C");
			System.out.println("Kritisk højeste værdi: " + (max+urgent) + " °C");
		}
	}
	
	/** Returnerer min */
	public static double getMin(){
		return min;
	}
	
	/** Returnerer max */
	public static double getMax(){
		return max;
	}
	
	/** Returnerer minurgent */
	public static double getMinUrgent(){
		return (min-urgent);
	}
	
	/** Returnerer maxurgent */
	public static double getMaxUrgent(){
		return (max+urgent);
	}
	
	/** Konverterer en måling fra sensoren */
	public static void convertValue(double i){
		conValue = i;
		// Til test: 
		// conValue = (conValue * 4 / 50) + 24;
	}
	
	/** Returnerer den sidst konverterede måling */
	public static double getConValue(){
		return conValue;
	}
	
	/** run-metode */
	public static void run(){
		Sensor s = new Sensor();
		double prevTemp = 0;
		while (true){
			try{
				Thread.sleep(15000);
				}
			catch(InterruptedException e){
					System.out.println("Der skete en fejl");
					e.printStackTrace();
				}
			Monitor.convertValue(s.getValue());
			if ((conValue <= min && conValue > (min-urgent)) || (conValue >= max && conValue < (max+urgent))){
				if ((prevTemp <= min && prevTemp > (min-urgent)) || (prevTemp >= max && prevTemp < (max+urgent))){
					continue;
				}
				else {
					System.out.println(plejer);
				}
			}
			if (conValue > (max+urgent) || conValue < (min-urgent)){
				if (prevTemp > (max+urgent) || prevTemp < (min-urgent)){
					continue;
				}
				else {
					System.out.println(laege);
				}
			}
			try {
				FileWriter fil = new FileWriter("Maalinger.txt",true);
				PrintWriter ud = new PrintWriter(fil);
				ud.println(conValue);
				ud.close();
				} 
			catch (IOException e1) {
				e1.printStackTrace();
				}
			
			// Sætter variablen prevTemp til den nuværende måling, hvorefter løkken gentages og 
			// prevTemp kommer således til at repræsentere den forrige måling. 
			prevTemp = conValue;			
		}
	}
	/* Til test af sensor 
	public static void main(String[] args) {
		Sensor sensor = new Sensor();
		run(sensor);			
		}*/
	}

