import java.util.Scanner;
import java.io.*;

public class Matriks {
	private static Scanner scan = new Scanner(System.in);
	private static int ADD_ROW = 1;
	private static int ADD_COL = 2;
	private static double [][] matriks;
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
			r = scan.nextLine();
			temp[i] = r.trim().split("\\s+");
			for (int j=1; j<column; j++) {
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

	public Matriks kali(Matriks m) {
		Matriks res = new Matriks(this.row, m.col, false);


		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < m.col; j++) {
				double total = 0;
				for (int k = 0; k < m.row; k++) {
					total = total + (this.matriks[i][k] * m.matriks[k][j]);
				}
				res.matriks[i][j] = total;
			}
		}

		return res;
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

	private void GaussForDet(boolean isSPL, boolean isGaussJordan) {
		for(int n = 1; n <= this.row; ++n) {
			double reducer = this.matriks[n][n];

			if(reducer == 0) {
				int notZeroRow = 0;
				
				for(int i = n + 1; i <= this.row; ++i) {
					if(this.matriks[i][n] > 0) {
						notZeroRow = i;
						break;
					}
				}

				for(int i = n; i <= this.col + 1; ++i) {
					double swap = this.matriks[notZeroRow][i];
					this.matriks[notZeroRow][i] = this.matriks[n][i];
					if(!isSPL) {
						this.matriks[notZeroRow][i] *= -1;
					}
					this.matriks[n][i] = swap;
				}

				reducer = this.matriks[n][n];
			} 

			for (int i = n + 1; i <= this.row; ++i) {
				if (this.matriks[i][n] != 0) {
					double diff = this.matriks[i][n] / reducer;

					for (int j = n; j <= this.col + 1; ++j) {
						this.matriks[i][j] -= (this.matriks[n][j] * diff);
					}
				}
			}
		}
	}

	private Boolean rowReducerForInverse(double[][] matrix, double[][] identity, int i, int j) {
		int start=1;
		int r = this.row;
		int column = this.col + 1;

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

		if (isMatrixNotInvertible(matrix)) {
			return false;
		}
		return true;

	}

	private void Gauss(double[][] matrix, boolean is_gaussjordan) {
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

	public void GaussSolution(boolean isDet, boolean isGaussJordan) throws IOException {
		if(!isDet && isGaussJordan) {
			Gauss(this.matriks, isGaussJordan);
		} else {
			GaussForDet(!isDet, isGaussJordan);
		}

		if (isGaussJordan) {
			System.out.println("Hasil operasi Gauss-Jordan dalam bentuk matriks");
		} else {
			System.out.println("Hasil operasi Gauss dalam bentuk matriks");
		}

		printMatriks();
		
		if(!isDet) {
			System.out.println();
			SPLSolution(isGaussJordan);
		}
	}

	public double getDetFromGauss(boolean isGaussJordan) {
		double res = 1;

		for(int i = 1; i <= this.row; ++i) 
			res *= (this.matriks[i][i]);

		if(isGaussJordan) {
			return res * -1;
		} else {
			return res;
		}
	}

	private void SPLSolution(boolean isGaussJordan) throws IOException {
		if (!isGaussJordan) {
			double [] result = new double[this.row + 1];

			for(int i = this.row; i >= 1; --i) {
				if(i < this.row) {
					double temp = 0;

					for(int j = this.col; j > i; --j) {
						temp += this.matriks[i][j] * result[j];
					}

					this.matriks[i][this.col + 1] -= temp;
				}

				result[i] = this.matriks[i][this.col + 1] / this.matriks[i][i];
			}

			System.out.println("Solusi SPL:");

			for(int i = 1; i < result.length; ++i)
				System.out.println("x" + i + " = " + result[i]);

		} else {
			String solution = "";
			if (isNoSolution()) {
				solution = "Solusi SPL tidak ada";
				System.out.println("Solusi SPL tidak ada");
			} else 
			if (isSolutionUnique()) {
				solution = "Solusi SPL Unik\n" + calculateUniqueSolution();
				System.out.println(solution);

			} else 
			if (isSolutionInfinite()) {
				solution = "Solusi SPL Infinite";
				System.out.println("Solusi SPL Infinite");
				// calculateInfiniteSolution();
			}
			// saveToFile(solution);
		}
	}

	private String calculateUniqueSolution() {
		String solution = "";
		for (int i=1; i<= this.row; i++) {
			solution += "x";
			solution += Integer.toString(i);
			solution += " = ";
			solution += Double.toString(this.matriks[i][this.col + 1]);
			solution += "\n";
		}
		return solution;
	}

	private void calculateInfiniteSolution() {
		for (int i=1; i<=this.row; i++) {
			for (int j=1; j<=this.col; j++) {
				if (this.matriks[i][j] == 1) {
					System.out.print("x");
					System.out.print(j);
					System.out.print("= ");
					System.out.print(matriks[i][this.col + 1]);

					for (int p=j+1; p<=this.col; p++) {
						if (this.matriks[i][p] != 0) {
							double coeff = this.matriks[i][p] * -1;
							if (coeff > 0) {
								System.out.print('+');
								System.out.print(coeff);
							} else 
							if (this.matriks[i][p] < 0) {
								System.out.print(coeff);
							}
							System.out.print("t");
							System.out.print(p);
							// p++;
						}
					}
				} else 
				if (this.matriks[i][j]!=0 && this.matriks[i][j]!=1) {
					System.out.println();
					System.out.print("x");
					System.out.print(j);
					System.out.print("= ");
					System.out.print("t");
					System.out.println(j);
				}
			}
		}
	}

	private void calculateInfiniteSolution1() {
		char[] list_of_char = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','y','z'};
		int idxchar = 0;
		String solution;
		String[] list_of_solution = new String[this.col + 1];
		Boolean[] list_sol = new Boolean[this.col + 1];
		
		for (int j = 1; j<=this.col; j++) {
			list_sol[j] = false;
		}

		for (int i = 1; i<=this.row; i++) {
			for (int j=1; j<=this.col; j++) {
				if (this.matriks[i][j] == 1) {
					list_sol[j] = true;
					j = this.col;
				}
			}
		}

		for (int j = 1; j<=this.col; j++) {
			if (!list_sol[j]) {
				list_of_solution[j] = Character.toString(list_of_char[idxchar]);
				idxchar++;
			}
		}

		// for (int kol=1; kol<=this.col; kol++) {
		// 	if (list_sol[kol]) {
		// 		solution = 
		// 	}
		// }


		for (int j = 1; j<=this.col; j++) {
			System.out.println(list_of_solution[j]);
		}


	}

	private Boolean isRowAllZero(double[] rowMatrix) {
		for (int i=1; i<=this.col+1; i++) {
			if (rowMatrix[i]!=0) {
				return false;
			}
		}
		return true;
	}

	private Boolean GaussJordanForInverse(double[][] matrix, double[][] identity) {
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
         		boolean is_invertible = rowReducerForInverse(matrix, identity, i,j);
         		// if (!is_invertible) {
         		// 	return false;
         		// }
         		i++;
         	}
         	j++;
		}

		return true;
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

		double[][] inverse = identity;

		boolean is_invertible = GaussJordanForInverse(this.matriks, inverse);
		
		if (is_invertible) {
			System.out.println("Matrix is invertible");
			System.out.println("Hasil inverse");
			for (int i=1; i<= this.row; i++) {
				for (int j=1; j<= this.col; j++) {
					System.out.print(inverse[i][j] + " ");
				}
				System.out.println();
			}
 		} else {
 			System.out.println("Matrix is not invertible");
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

	private Boolean isSolutionUnique() {
		if (this.matriks[this.row][this.col] == 1 && this.matriks[this.row][this.col + 1] != 0) {
			return true;
		}
		return false;
	}

	private Boolean isSolutionInfinite() {
		if (this.matriks[this.row][this.col] == 0 && this.matriks[this.row][this.col + 1] == 0) {
			return true;
		}
		return false;
	}

	private Boolean isNoSolution() {
		if (this.matriks[this.row][this.col] == 0 && this.matriks[this.row][this.col + 1] != 0) {
			return true;
		}
		return false;
	}

	private void saveToFile(String solution) throws IOException {
		System.out.println("Masukkan nama output file (xxx.txt):");
		System.out.print(">> ");
		
		String outfile = scan.nextLine();

		if(!outfile.isEmpty()) {
			PrintWriter out = new PrintWriter(new FileWriter(outfile));
			out.print(solution);
			out.close();
		}
	}

	public double[][] kofaktor () throws IOException{
		double [][] res = new double[this.row+1][this.row+1];
		Matriks temp = new Matriks(this.row - 1, this.row - 2, false);

		System.out.println(this.row + ", " + this.row);

		for(int i = 1; i <= this.row; ++i) {
			for (int j = 1; j <= this.row; ++j) {
				int row = 1, col = 1;

				for(int k = 1; k <= this.row; ++k) {
					for (int l= 1; l <= this.row; ++l) {
						if(k != i && l != j) {
							temp.matriks[row][col] = this.matriks[k][l];
							++col;
						}

					}
					++row;
					col = 1;
				}

				temp.printMatriks();

				temp.GaussSolution(true, false);
				double det = temp.getDetFromGauss(false);
				if((i % 2 == 1 && j % 2 ==0) || (i % 2 == 0 && j % 2 == 1)) {
					det *= -1;
				}

				System.out.println("det = " + det);
				res[i][j] = det;
			}
		}

		return res;
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