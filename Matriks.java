import java.util.Scanner;
import java.io.*;

public class Matriks {
	private static Scanner scan = new Scanner(System.in);
	private static int ADD_ROW = 1;
	private static int ADD_COL = 2;
	public static double [][] matriks;
	private boolean is_augmented;
	private int row;	// m
	private int col;	// n

	// ctor
	public Matriks() {
		this.row = 100;
		this.col = 100;
		this.is_augmented = true;
		matriks = new double[this.row + ADD_ROW][this.col + ADD_COL];
	}

	public Matriks(int row, int col, boolean is_augmented){
		this.row = row;
		this.col = col;
		this.is_augmented = is_augmented;

		if (is_augmented) {
			matriks = new double[this.row+ADD_ROW][this.col+ADD_COL];	
		} else {
			matriks = new double[this.row+ADD_ROW][this.col+ADD_ROW];	
		}
	}

	public void readMatriksFromKeyboard() {
		String r;
		int row, column;
		row = this.row + ADD_ROW;
		if (this.is_augmented) {
			column = this.col + ADD_COL; 
		} else {
			column = this.col + ADD_ROW;
		}
		String [][] temp = new String[row][column];

		for (int i=1; i< row; i++) {
			System.out.println(i);
			r = scan.nextLine();
			temp[i] = r.trim().split("\\s+");
			for (int j=1; j<column; j++) {
				this.matriks[i][j] = Double.parseDouble(temp[i][j-1]);
			}
		}	
		System.out.println("selesai");
		printMatriks();
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

	private void swap2Rows(double[][] matrix, int i, int k, int j) {
		double temp;
		int column = matrix[0].length;
		for (int q = j; q < column; q++) {
			temp = matrix[i][q];
			matrix[i][q] = matrix[k][q];
			matrix[k][q] = temp;
     	}
	}

	private void rowDivider(double[][] matrix, int i, int j, double divider) {
		int column = matrix[0].length;
		for (int q = j; q < column; q++) {
			matrix[i][q] /= divider;
		}
	}

	private void rowReducer(double[][] matrix, int i, int j, boolean is_gaussjordan) {
		System.out.println("called");
		int start;
		if (is_gaussjordan) {
			start = 1;
		} else {
			start = i;
		}
		int r = matrix.length;
		int column = matrix[0].length;

		for (int p=start; p < r; p++) {
			double temp = matrix[p][j];
			if (p != i && temp != 0) {
				for (int q=j; q < column; q++) {
					matrix[p][q] -= temp * matrix[i][q];
				}
			}
		}
	}

	private void rowReducerForInverse(double[][] matrix, double[][] identity, int i, int j) {
		int start=1;
		int r = matrix.length;
		int column = matrix[0].length;

		System.out.println("\nbefore: matrix");
		for (int a=1; a< r; a++) {
			for (int b=1; b< column; b++) {
				System.out.print(matrix[a][b] + " ");
			}
			System.out.println();
		}

		System.out.println("\nbefore: identity");
		for (int a=1; a< r; a++) {
			for (int b=1; b< column; b++) {
				System.out.print(identity[a][b] + " ");
			}
			System.out.println();
		}

		for (int p=start; p < r; p++) {
			double temp = matrix[p][j];
			if (p != i && temp != 0) {
				for (int q = j; q < column; q++) {
					matrix[p][q] -= temp * matrix[i][q];
				}
				for (int q = 1; q < column; q++) {
					identity[p][q] -= temp * identity[i][q];
				}
			}
		}

		System.out.println("\nafter: matrix");
		for (int a=1; a< r; a++) {
			for (int b=1; b< column; b++) {
				System.out.print(matrix[a][b] + " ");
			}
			System.out.println();
		}

		System.out.println("\nafter: identity");
		for (int a=1; a< r; a++) {
			for (int b=1; b< column; b++) {
				System.out.print(identity[a][b] + " ");
			}
			System.out.println();
		}


	}

	public void Gauss(double[][] matrix, boolean is_gaussjordan) {
		int i=1;
		int j=1;
		int k;
		while (i<=this.row && j<=this.col) {

			// look for a non-zero entry in col j or below row i
			k = i;
         	while (k <= this.row && matrix[k][j]==0) {
         		k++;
         	}

         	// if non-zero entry is found
         	if (k <= this.row) {
         		if (k != i) {
         			swap2Rows(matrix, i, k, j);
         		}

         		if (this.matriks[i][j] != 1) {
         			rowDivider(matrix, i,j, matrix[i][j]);
         		}

         		// pengurang baris gauss
         		rowReducer(matrix, i,j,is_gaussjordan);
         		i++;
         	}
         	j++;
		}
	}

	private void GaussJordanForInverse(double[][] matrix, double[][] identity) {
		int i=1;
		int j=1;
		int k;
		while (i<=this.row && j<=this.col) {

			// look for a non-zero entry in col j or below row i
			k = i;
         	while (k <= this.row && matrix[k][j]==0) {
         		k++;
         	}

         	// if non-zero entry is found
         	if (k <= this.row) {
         		if (k != i) {
         			swap2Rows(matrix, i, k, j);
         			swap2Rows(identity, i, k, j);
         		}

         		if (this.matriks[i][j] != 1) {
         			double divider = matrix[i][j];
         			rowDivider(matrix, i,j, divider);
         			rowDivider(identity, i, 1, divider);
         		}

         		// pengurang baris gauss
         		rowReducerForInverse(matrix, identity, i,j);
         		i++;
         	}
         	j++;
		}
	}

	public void findInverseUsingOBE() {
		double[][] identity = new double[this.row + ADD_ROW][this.col + ADD_ROW];
		for (int i=1; i<=this.row; i++) {
			for (int j=1; j<=this.col; j++) {
				if (i==j) {
					identity[i][j] = 1;
				}
				
			}
		}
		for (int i=1; i<=this.row; i++) {
			for (int j=1; j<=this.col; j++) {
				System.out.print(identity[i][j] + " ");
			}
			System.out.println();
		}
		double[][] inverse = identity;

		GaussJordanForInverse(this.matriks, inverse);

		printMatriks();
		
		if (isMatrixEqual(identity, inverse)) {
			System.out.println("Matrix is invertible");
			System.out.println("Hasil inverse");
			for (int i=1; i<= this.row; i++) {
				for (int j=1; j<= this.col; j++) {
					System.out.print(inverse[i][j] + " ");
				}
				System.out.println();
			}
 		} else {
 			if (isMatrixNotInvertible(inverse)) {
 				System.out.println("Matrix is not invertible");
 			}
 		}

	}

	public void printMatriks() {
		int row, column;
		row = this.row + ADD_ROW;
		if (this.is_augmented) {
			column = this.col + ADD_COL; 
		} else {
			column = this.col + ADD_ROW;
		}
		System.out.println();
		for (int i=1; i< row; i++) {
			for (int j=1; j< column; j++) {
				System.out.print(this.matriks[i][j] + " ");
			}
			System.out.println();
		}
	}

	private Boolean isMatrixEqual(double[][] m1, double[][] m2) {
		for (int i=1; i<m1.length; i++) {
			for (int j=1; j<m1[0].length; j++) {
				if (m1[i][j] != m2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	private Boolean isMatrixNotInvertible(double[][] m) {
		for (int i=1; i<m[1].length; i++) {
			if (m[m.length-1][i] != 0) {
				return false;
			}
		}
		return true;
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
		// Matriks matrix = new Matriks();
		// matrix.readMatriksFromFile();
		// matrix.printMatriks();
	}
}