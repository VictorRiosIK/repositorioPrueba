import java.io.*;
import java.net.*;
public class MatricesEnviaBien {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java MatrizMultiplicacion <numero>");
            return;
        }

        int opcion = Integer.parseInt(args[0]);

        switch (opcion) {
            case 1:
                crearMatrices();
                break;
            case 2:
               socketTcp();
                break;
            case 3:
                socketTcp();
                break;
            default:
                System.out.println("Opción no válida. Debe ser 1, 2 o 3.");
        }
    }
    public static void socketTcp(){
        try{
            ServerSocket serverSocket = new ServerSocket(50001); 
            System.out.println("Servidor esperando conexiones en el puerto 50001...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

            int filasA = inputStream.readInt();
            int columnasA = inputStream.readInt();
            

            long[][] matrizA = new long[filasA][columnasA];
            long[][] matrizB = new long[filasA][columnasA];
            System.out.println("Filas: " + filasA + "Columnas:" + columnasA);
            // Leer matriz A
            for (int i = 0; i < filasA; i++) {
                for (int j = 0; j < columnasA; j++) {
                    matrizA[i][j] = inputStream.readLong();
                }
            }

            

            // Realizar la multiplicación de matrices aquí
             // Imprimir la matriz recibida
            System.out.println("Matriz A recibida desde el cliente:");
            imprimirMatrizLong(matrizA);
            long[][] matrizBTranspuesta = new long[3][6];
            
            int columnas = matrizBTranspuesta[0].length;
            // Leer matriz BTranspuesta
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 6; j++) {
                    matrizBTranspuesta[i][j] = inputStream.readLong();
                }
            }

        // Imprimir la matriz BTranspuesta recibida
        System.out.println("Matriz BTranspuesta recibida desde el cliente:");
        imprimirMatrizLong(matrizBTranspuesta);
            // Cerrar conexiones y recursos
            inputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void crearMatrices() {
        // Aquí puedes implementar la lógica para crear las matrices
        System.out.println("Creando matrices...");
        int filas = 6;
        int columnas = 5;
        int[][] A = new int[filas][columnas];
        int[][] B = new int[filas][columnas];

        // Llenar la matriz A
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                A[i][j] = 5 * i - 2 * j;
            }
        }

        // Llenar la matriz B
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                B[i][j] = 6 * i + 3 * j;
            }
        }

        // Imprimir las matrices A y B
        System.out.println("Matriz A:");
        imprimirMatriz(A);

        System.out.println("Matriz B:");
        imprimirMatriz(B);

        // Calcular y imprimir la matriz B transpuesta
        int[][] BTranspuesta = transponerMatriz(B);
        System.out.println("Matriz B Transpuesta:");
        imprimirMatriz(BTranspuesta);

        
         int[][] C = new int[6][6];

        // Realizar los cálculos C1 a C36
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                C[i][j] = multiplicarMatrices(A, BTranspuesta, i, j);
            }
        }

        // Imprimir la matriz C
        System.out.println("Matriz C:");
        imprimirMatriz(C);
        System.out.println("Enviando matrices a las instancias");
        enviaMatrices(A,BTranspuesta);
    }
    public static void enviaMatrices(int [][] A, int [][] BTranspuesta){

        // Enviar A1, A2 y A3 al servidor
        try { Socket clientSocket = new Socket("20.84.41.105", 50001);// Conéctate al servidor en el puerto 12345
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            // Enviar las dimensiones de A1, A2 y A3
            int filasA1A2A3 = 3;
            int columnasA1A2A3 = 5;
            outputStream.writeInt(filasA1A2A3);
            outputStream.writeInt(columnasA1A2A3);

            // Enviar A1, A2 y A3
            for (int i = 0; i < filasA1A2A3; i++) {
                for (int j = 0; j < columnasA1A2A3; j++) {
                    outputStream.writeLong(A[i][j]);
                }
            }
            outputStream.flush();
            
            System.out.println("Filas matrizTranspuesta: " + BTranspuesta.length + "Columnas: " + BTranspuesta[0].length);
            // Enviar la matriz BTranspuesta
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 6; j++) {
                    outputStream.writeLong(BTranspuesta[i][j]);
                }
            }
            // Cerrar la conexión
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void multiplicarMatricesOtraForma() {
        // Aquí puedes implementar la lógica para multiplicar las matrices de otra forma
        System.out.println("Multiplicando matrices de otra forma...");
    }
    public static void imprimirMatriz(int[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(); // Nueva línea después de cada fila
        }
    }
    public static void imprimirMatrizLong(long[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println(); // Nueva línea después de cada fila
        }
    }
    public static int[][] transponerMatriz(int[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        int[][] matrizTranspuesta = new int[columnas][filas];
        System.out.println("METODO TRANSPONER MATRIZ: COLUMNAS:"+ columnas + " FILAS: " + filas);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizTranspuesta[j][i] = matriz[i][j];
            }
        }

        return matrizTranspuesta;
    }
     public static int multiplicarMatrices(int[][] A, int[][] B, int filaA, int columnaB) {
        int resultado = 0;
        for (int i = 0; i < A[0].length; i++) {
            resultado += A[filaA][i] * B[i][columnaB];
        }
        return resultado;
    }
    
}