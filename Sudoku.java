package Tarea2;

import java.util.Arrays;
import java.util.Scanner;


public class Sudoku {

	private static int [][] matriz= {{0,0,0,0,0,0,0,0,0},{0,7,9,0,5,0,1,8,0},{8,0,0,0,0,0,0,0,7},{0,0,7,3,0,6,8,0,0},{4,5,0,7,0,8,0,9,6},{0,0,3,5,0,2,7,0,0},{7,0,0,0,0,0,0,0,5},{0,1,6,0,3,0,4,2,0},{0,0,0,0,0,0,0,0,0}};
	//public static int [][] ma= {{0,0,0,0,0,0,0,0,0},{0,7,9,0,5,0,1,8,0},{8,0,0,0,0,0,0,0,7},{0,0,7,3,0,6,8,0,0},{4,5,0,7,0,8,0,9,6},{0,0,3,5,0,2,7,0,0},{7,0,0,0,0,0,0,0,5},{0,1,6,0,3,0,4,2,0},{0,0,0,0,0,0,0,0,0}};
	
	//esta funcion lee el sudoku de un archivo txt
    public static void leerSudoku() {
        Scanner s = new Scanner(System.in);
        //matriz[fila][columna]
        //como el input siempre es igual, leemos 9 lineas
        for (int i = 0; i < 9; i++) {
        	//separamos cada linea en un arreglo de strings, con espacio como separador
            String[] linea = s.nextLine().split(" ");
            //leemos el arreglo de lineas
            for (int j = 0; j < linea.length; j++) {
                matriz[i][j] = Integer.parseInt(linea[j]); //para pasar de String a int
            }
        }
        s.close();
    }
	
    //funcion que imprime el sudoku
	private static void imprimirSudoku()
    {
		for (int i=0; i<matriz.length; i++) {
			System.out.println(Arrays.toString(matriz[i]));
		}
    }
	
	//funcion que permite ver si un numero n esta en una fila determinada
	static boolean enfila(int fila, int n) {
		for (int i = 0; i<=8; i++ ) {
			if (n==matriz[fila][i]){
				return true;
			}
		}
		return false;
	}
	
	//funcion que permite ver si un numero n esta en una columna determinada
	static boolean encolumna(int columna, int n) {
		for (int i=0; i<=8; i++) {
			if (n==matriz[i][columna]) {
				return true;
			}
		}
			return false;
		}
	
	//funcion que permite ver si un numero n esta en un cuadrante determinado
	 static boolean encuadrante(int cuadrante, int n) {
		//primero asignamos valores iniciales para fila y columna, con ellos aumentaremos en 2 cada valor para encontrar un valor en un cuadrante de 3x3
		 int f=0;
		 int c=0;
		 if (cuadrante==1) {
			f=0;
			c=0;
		}
		else if (cuadrante==2){
			f=0;
			c=3;
		}
		else if (cuadrante==3) {
			f=0;
			c=6;
		}
		else if (cuadrante==4) {
			f=3;
			c=0;
		}
		else if (cuadrante==5) {
			f=3;
			c=3;
		}
		else if (cuadrante==6) {
			f=3;
			c=6;
		}
		else if (cuadrante==7) {
			f=6;
			c=0;
		}
		else if (cuadrante==8) {
			f=6;
			c=3;
		}
		else if (cuadrante==9) {
			f=6;
			c=6;
		}
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (n==matriz[f+i][c+j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	 //esta funcion además de revisar si el valor esta en la fila y en la columna, revisa si esta en el cuadrante
	 //si el valor no esta ni en la fila, ni en la columna, ni el cuadrante, retorna true, es decir, es candidato
	static boolean candidato (int x, int y, int valor) {
		if(enfila(x, valor)==false && encolumna(y,valor)==false) {
			if (0<=x && x<3 && 0<=y && y<3 && encuadrante(1,valor)==false ) {
			 return true;
		 }
			else if (0<=x && x<3 && 3<=y && y<6 && encuadrante(2,valor)==false ) {
				 return true;
			 }
			else if (0<=x && x<3 && 6<=y && y<=8 && encuadrante(3,valor)==false ) {
				 return true;
			 }
			else if (3<=x && x<6 && 0<=y && y<3 && encuadrante(4,valor)==false ) {
				 return true;
			 }
			else if (3<=x && x<6 && 3<=y && y<6 && encuadrante(5,valor)==false ) {
				 return true;
			 }
			else if (3<=x && x<6 && 6<=y && y<=8 && encuadrante(6,valor)==false ) {
				 return true;
			 }
			else if (6<=x && x<9 && 0<=y && y<3 && encuadrante(7,valor)==false ) {
				 return true;
			 }
			else if (6<=x && x<9 && 3<=y && y<6 && encuadrante(8,valor)==false ) {
				 return true;
			 }
			else if (6<=x && x<9 && 6<=y && y<=8 && encuadrante(9,valor)==false ) {
				 return true;
			 }
			else {
				return false;
			}
	}
		return false;
	}
	
	//esta funcion crea una matriz de candidatos, en la cual para cada casilla vacía de la matriz, guarda una lista con los posibles valores que podría tener. 
	//en esta lista se representa con un 1 en el indice del numero que es candidato y un 0 en el indice del numero que no lo es
 	static int[][][] matrizcandidatos(int[][] matriz) {
		int [][][] matrizcandidatos=new int [9][9][10];
		for (int i=0; i<9;i++) {
			for(int j=0;j<9;j++) {
				
				if (matriz[i][j]==0) {
					for (int n=0; n<10;n++) {
						if (candidato(i,j,n)) {
							matrizcandidatos[i][j][n]=1;
						}
						else {
							matrizcandidatos[i][j][n]=0;
						}
					}
				}
				
				else if(matriz[i][j]!=0) {
					for (int n=0; n<10;n++) {
							matrizcandidatos[i][j][n]=0;
						}
					}
				}
					
			
			}
		
		return matrizcandidatos;
		 
	}
	
 	//esta funcion retorna el primer candidato unico encontrado, junto con su posicion y su valor, en la matriz de candidatos
	static int[] hayUnico(int[][][] matrizcandidatos) {
		int contador=0;
		int fila=-1;
		int columna=-1; 
		int indice=0;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				for (int n=0; n<10;n++) {
					if (matrizcandidatos[i][j][n]!=0) {
						contador=contador+1;
						indice =n;
					}
				}
				if (contador==1) {
					fila=i;
					columna=j;
					//en la primera componente de a ingresamos un 1 si el candidato es unico, en el resto de componentes ingresamos su posicion y el valor del candidato dado por el indice
					int [] a = {1,fila,columna,indice};
					return a;
				}
				//reseteamos los contadores para buscar el siguiente candidato único en caso de que sea necesario
				contador=0;
				indice=0;
			}
		}
		//en el caso de que ya no queden mas candidatos unicos, la primera componente de a tendrá un 0 para indicarnos, por otro lado se asignan valores no validos tanto a la posicion como al valor del candidato ficticio 
		int [] a = {0, -1, -1,-1};
		return a;
		}
	
	//esta funcion toma una matriz y para cada posicion vacia con un unico candidato, se lo agrega
	public static int rellenador(int[][] matriz, int[][][] matrizcandidatos) {
		int b = 0;
		int contador=0;
		while (b<82) {
			int[] a = hayUnico(matrizcandidatos);
			if(a[0]==1) {
				matriz[a[1]][a[2]]=a[3];
				matrizcandidatos=matrizcandidatos(matriz);
			}
			else if(a[0]!=1) {
				break;
			}
			b++;
			contador++;
			
		}
		return contador;
					
	}
	
	//encuentra una casilla vacia y guarda el indice fila,columna de esta celda.
    private static int[] casillaVacia(int fila, int columna)
    {
        //la casilla esta vacia si es igual a 0
    	int vacia = 0;
    	//recorremos la matriz en busca de casilla vacias
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                //vemos si la casilla esta vacia
                if(matriz[i][j] == 0)
                {
                    //guardamos los valores de la posicion de esta casilla
                    fila = i;
                    columna = j;
                    //guardamos el valor de 1 en caso de que haya una casilla vacia en la matriz, con su posicion 
                    vacia = 1;
                    int[] a = {vacia, fila, columna};
                    return a;
                }
            }
        }
        //una vez iterada toda la matriz, nos entrega una lista en que vacía va a tener una posicion invalida, por lo que ya no quedarán casillas vacias
        int[] a = {vacia, -1, -1};
        return a;
    }
	
    //metodo que resuelve el sudoku
	private static boolean resolverSudoku(){
        int fila=0;
        int columna=0;
        //creamos una lista con la casilla vacia (en caso de que haya) y su ubicación fila,columna en la matriz
        int[] a = casillaVacia(fila, columna);
        //revisamos si hay efectivamente casillas vacias a[0]==1, o en su defecto, están todas llenas y a[0]==0
        //si todas estan asignadas el sudoku está listo
        if(a[0] == 0)
            return true;
        //si no, guardamos la posicion de esta casilla vacia 
        fila = a[1];
        columna = a[2];
        //agregamos un numero del 1 al 9 a la casilla vacia
        for(int i=1;i<=9;i++){
            //revisamos si es candidato o no
            if(candidato(fila, columna,i)){
                matriz[fila][columna] = i;
                //backtracking
                if(resolverSudoku())
                    return true;
                //si no podemos resolver el sudoku, volvemos a dejar la celda vacía en 0 
                matriz[fila][columna]=0;
            }
        }
        return false;
    }
	
			public static void main(String args[]) {
				
				//le entregamos un input, hay que ir a run->run configurations->common->input file
				leerSudoku();
				System.out.println("Imprimimos el input");
				imprimirSudoku();
				
				System.out.println("Resolvemos primero rellenando y descartando");
				//esta parte crea una matriz de candidatos M, con el for imprimimos los candidatos de una posicion 
				int [][][] M= matrizcandidatos(matriz);
				int r= rellenador(matriz,M);
				System.out.println("Se hicieron "+r+" iteraciones de llenado de candidatos unicos, antes del backtracking");
				
				
				System.out.println("Terminamos de resolver usando backtracking");
				 if (resolverSudoku())
			            imprimirSudoku();
			        else
			            System.out.println("Sin solucion");
				
				
			}
}
				
				
				
				
				