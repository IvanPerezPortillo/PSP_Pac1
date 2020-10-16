package com.ieseljust.psp.apac1;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author ivpepo
 */
public class ObteMaximMultiproces {

    public static void main(String[] args) throws IOException {
        ObteMaximMultiproces omm = new ObteMaximMultiproces();

        // Comprovem que hi ha almenys un argument.         
        if (args.length < 0) {
            System.out.println("Nombre d'arguments incorrecte.");
            System.exit(0);
        }

        // Tamaño array inicial
        long tam = Long.parseLong(args[0]);

        //Creamos el array con numeros aleatorios
        long array[] = new long[omm.funAuxConver(tam)];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextLong();
        }

        //Obtenemos el número de prcesadores del sistema
        long numProcessors = Runtime.getRuntime().availableProcessors();

        //Optenemos el tamaño de los vectores auxiliares
        long tamVectoresAux = tam / numProcessors;

        // Muestro por pantalla el número de procesadores y en caunto de va a dividir
        System.out.println("Dividint la tasca en " + numProcessors + " procesos");

        // Si la division no es exacta hay que tener en cuenta el resto por ejemplo para el ultimo proceso.
        long resto = tam - (numProcessors * tamVectoresAux);

        // Vector de copia
        long arrayCopia[];

        // Vector para buffer le paso el número de procesadores y lo convierto a long con funAuxConver
        BufferedReader aBuffer[] = new BufferedReader[omm.funAuxConver(numProcessors)];


        // Recorro hasta el numProcessars
        for (int i = 0; i < (numProcessors); i++) {
            // convierto a long -1 para no salirme
            if (i == (omm.funAuxConver(numProcessors) - 1)) {
                //Creo vector copia con tamaño vector aux + resto
                arrayCopia = new long[omm.funAuxConver(tamVectoresAux + resto)];
                System.arraycopy(array, omm.funAuxConver(i * tamVectoresAux), arrayCopia, 0, (omm.funAuxConver(tamVectoresAux + resto) - 1));
                aBuffer[i] = omm.run(arrayCopia, i, (i * tamVectoresAux), ((i * tamVectoresAux) + (tamVectoresAux + resto) - 1));
            } else {
                arrayCopia = new long[omm.funAuxConver(tamVectoresAux)];
                System.arraycopy(array, omm.funAuxConver(i * tamVectoresAux), arrayCopia, 0, (omm.funAuxConver(tamVectoresAux) - 1));
                aBuffer[i] = omm.run(arrayCopia, i, (i * tamVectoresAux), ((i * tamVectoresAux) + (tamVectoresAux) - 1));
            }
        }
        /*
         * Utilizando la funcion de readFromBuffer leemos la salida estandar del
         * otros programa hay que pasarle como parametro un buffer y accedemos a
         * el mediante abuffer.get(i).
         */

        // Vector para máximos de cada resultado
        long arrayResultado[] = new long[omm.funAuxConver(numProcessors)];
        long resultadoBuffer= 0;
        long resultadoFinal= 0;
        int idProces = 0;
        for (int i = 0; i < aBuffer.length; i++) {
            idProces++;
            resultadoBuffer = omm.readFromBuffer(aBuffer[i]);
            //System.out.println("Resultado "+resultadoBuffer);
            System.out.println("Maxim parcial del procés " + idProces + " = " + resultadoBuffer);
            if (resultadoBuffer>resultadoFinal) {
                resultadoFinal = resultadoBuffer;    
            }
        }
        System.out.println("Maxim valor del vector: "+ resultadoFinal);
        
    }

    /**
     * Función que convertie un long a un int.
     *
     * @param num numero de tipo long
     * @return result de tipo int.
     */
    public int funAuxConver(long num) {
        int result = Integer.parseInt(String.valueOf(num));
        return result;
    }

    /**
     * Función que se le pasa un array long, un id como int y posición inicial y
     * posición final como long y devuelve un Buffere
     *
     * @param array
     * @param id
     * @param posIn
     * @param posFi
     * @return br
     */
    public BufferedReader run(long array[], int id, long posIn, long posFi) {
        // Vector parametros que empieza en la pos 1 ya que 0 y 1 es la ruta de calculaMaxim.java
        String parametros[] = new String[array.length + 2];
        parametros[0] = "java";
        parametros[1] = "com.ieseljust.psp.apac1.calculaMaxim";

        for (int i = 0; i < array.length; i++) {
            parametros[i + 2] = String.valueOf(array[i]);
        }
        ProcessBuilder pb;

        try {
            // Creamos obj de la clase ProcessBuilder lo lanzamos, capturamos y devolvemos.
            pb = new ProcessBuilder(parametros);

            // Lanzamos el proceso
            System.out.println("Llançant el procés " + id + " per fer càculs de " + posIn + " a " + posFi);
            Process p = pb.start();

            // Captura la salida estandart
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return br;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Función que lee el BufferedReader que le pasa el run como parámetro y
     * devuelve un long.
     *
     * @param br
     * @return long
     */
    public long readFromBuffer(BufferedReader br) {
        try {
            int bufferContador = 0;
            String line;
            while ((line = br.readLine()) != null) {
                bufferContador++;
                System.out.println("Esperant al buffer " + bufferContador);
                System.out.println("Esperant que es plene el buffer");
                // Cuando tenemos la linea que será la única salida
                // la devolvemos al programa principal convertida en long
                return Long.parseLong(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

}
