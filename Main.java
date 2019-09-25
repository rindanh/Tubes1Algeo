import java.util.Scanner;

public class Main{
	private static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

	private void start() {
		System.out.println("=======================================================================");
		System.out.println("==         				  	    WELCOME								 ==");
		System.out.println("=======================================================================");

		do {

		} while (!mainMenu());
	}

	private Boolean mainMenu() {
		System.out.println("\nMENU");
		System.out.println("1. Sistem Persamaan Linier");
		System.out.println("2. Determinan");
		System.out.println("3. Matriks Balikan");
		System.out.println("4. Matriks Kofaktor");
		System.out.println("5. Adjoin");
		System.out.println("6. Interpolasi Polinom");
		System.out.println("7. Keluar");
		System.out.print(">> ");
		int option = scan.nextInt(); 

		if (option == 7) {
			return true;
		} else
		if (option == 1) {
			return subMenuSPL();
		} else 
		if (option == 2) {
			return subMenuDet();
		} else
		if (option == 3) {
			return subMenuInvers();
		} else {
			return false;
		}
	}

	private Boolean subMenuSPL() {
		System.out.println("\nPilih Metode");
		System.out.println("1. Metode Eliminasi Gauss");
		System.out.println("2. Metode Eliminasi Gauss-Jordan");
		System.out.println("3. Metode Matriks Balikan");
		System.out.println("4. Kaidah Cramer");
		System.out.print(">> ");
		int option = scan.nextInt(); 
		return false;
	}

	private Boolean subMenuDet() {
		System.out.println("\nPilih Metode");
		System.out.println("1. Metode Eliminasi Gauss");
		System.out.println("2. Metode Eliminasi Gauss-Jordan");
		System.out.println("3. Metode Matriks Balikan");
		System.out.println("4. Kaidah Cramer");
		System.out.print(">> ");
		int option = scan.nextInt(); 
		return false;
	}

	private Boolean subMenuInvers() {
		System.out.println("\nPilih Metode");
		System.out.println("1. Metode Eliminasi Gauss-Jordan");
		System.out.println("2. Menggunakan Matriks Adjoin");
		System.out.print(">> ");
		int option = scan.nextInt(); 
		return false;
	}
}