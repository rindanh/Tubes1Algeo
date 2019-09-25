import java.util.Scanner;
import java.io.*;

public class Matriks {
	private static Scanner scan = new Scanner(System.in);
	private static int ADD_ROW = 1;
	private static int ADD_COL = 2;
	private static double [][] matriks;
	private int row;	// m
	private int col;	// n

	// ctor
	public Matriks() {
		this.row = 100;
		this.col = 100;
		matriks = new double[this.row + ADD_ROW][this.col + ADD_COL];
	}

	public Matriks(int row, int col){
		this.row = row;
		this.col = col;

		matriks = new double[this.row+ADD_ROW][this.col+ADD_COL];
	}

	public void readMatriksFromKeyboard() {
		String r = scan.nextLine();
		String [][] temp = new String[this.row + ADD_ROW][this.col + ADD_COL];
		for (int i=1; i< this.row + ADD_ROW; i++) {
			r = scan.nextLine();
			temp[i] = r.trim().split("\\s+");
			for (int j=1; j<this.col + ADD_COL; j++) {
				this.matriks[i][j] = Double.parseDouble(temp[i][j-1]);
			}
		}	
	}

	public void readMatriksFromFile() throws IOException {
		System.out.println("Masukkan nama input file (xxx.txt):");
		System.out.print(">> ");
		String infile = scan.nextLine();

		BufferedReader in = new BufferedReader (new FileReader(infile));

		String line = in.readLine();

		int m=0;
		int n=0;

		while (line != null) {
			m++;
			
			String[] temp = line.trim().split("\\s+");

			for (n=1; n <= temp.length; n++) {
				this.matriks[m][n] = Double.parseDouble(temp[n-1]);
			}

			line = in.readLine();
		}
		this.row = m;
		this.col = n - ADD_COL;

		in.close();
	}

	public void printMatriks() {
		for (int i=1; i< this.row + ADD_ROW; i++) {
			for (int j=1; j< this.col + ADD_COL; j++) {
				System.out.print(this.matriks[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException {
		// Input dari keyboard
		// System.out.print("Masukkan nilai m (jumlah baris) >> ");
		// int row = scan.nextInt(); 
		// System.out.print("Masukkan nilai n (jumlah kolom) >> ");
		// int col = scan.nextInt(); 

		// Matriks matrix = new Matriks(row, col);
		// matrix.readMatriksFromKeyboard();

		// input dari file
		Matriks matrix = new Matriks();
		matrix.readMatriksFromFile();
		matrix.printMatriks();
	}
}