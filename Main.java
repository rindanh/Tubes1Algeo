import java.util.Scanner;
import java.io.*;

public class Main{
	private static Scanner scan = new Scanner(System.in);
	private static Matriks matrix;
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.start();
	}

	private void start() throws IOException {
		System.out.println("=======================================================================");
		System.out.println("==         				  	    WELCOME								 ==");
		System.out.println("=======================================================================");

		do {

		} while (!mainMenu());
	}

	private Boolean mainMenu() throws IOException {
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

	private Boolean subMenuSPL() throws IOException {
		System.out.println("\nPilih Metode");
		System.out.println("1. Metode Eliminasi Gauss");
		System.out.println("2. Metode Eliminasi Gauss-Jordan");
		System.out.println("3. Metode Matriks Balikan");
		System.out.println("4. Kaidah Cramer");
		System.out.print(">> ");
		int option = scan.nextInt(); 
		selectInputType();

		if (option == 1) {
			matrix.Gauss(false);
		} else if (option == 2) {
			matrix.Gauss(true);
		}
		matrix.printMatriks();
		return false;
	}

	private void selectInputType() throws IOException {
		System.out.println("\nPilih media input matriks");
		System.out.println("1. Keyboard");
		System.out.println("2. File eksternal");
		System.out.print(">> ");
		int option = scan.nextInt(); 
		if (option == 1) {
			inputMatrixFromKeyboard();
		} else 
		if (option==2) {
			inputMatrixFromFile();
		}
	}

	private void inputMatrixFromFile() throws IOException {
		matrix = new Matriks();
		matrix.readMatriksFromFile();
		matrix.printMatriks();
	}

	private void inputMatrixFromKeyboard() {
		System.out.print("Masukkan nilai m (jumlah baris) >> ");
		int row = scan.nextInt(); 
		System.out.print("Masukkan nilai n (jumlah kolom) >> ");
		int col = scan.nextInt(); 

		matrix = new Matriks(row, col);
		matrix.readMatriksFromKeyboard();
	}

	private Boolean subMenuDet() {
		// System.out.println("\nPilih Metode");
		// System.out.println("1. Metode Eliminasi Gauss");
		// System.out.println("2. Metode Eliminasi Gauss-Jordan");
		// System.out.println("3. Metode Matriks Balikan");
		// System.out.println("4. Kaidah Cramer");
		// System.out.print(">> ");
		// int option = scan.nextInt(); 
		System.out.println("Maaf belum bisa");
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