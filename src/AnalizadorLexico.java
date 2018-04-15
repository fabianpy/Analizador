
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by fabian on 01/04/2018.
 */
public class AnalizadorLexico {
    private static int nroLinea = 0;
    private static final String DIRECTORIO_ACTUAL = new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().lastIndexOf("."));

    private static void error(String mensaje) {
        System.out.println("Error léxico en la línea " + nroLinea + ". " + mensaje);
    }

    private static void generarOutput(String value){
        try {
            FileWriter fw = new FileWriter(DIRECTORIO_ACTUAL.concat("output.json"), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(value);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void siguienteLexema(String fuente) {
        char[] array = fuente.toCharArray();
        int i = 0;
        while (i < array.length) {
            char c = array[i];
            if (c == ' ' || c == '\t')
                continue; //elimina espacios en blanco y tabulaciones
            else if (c == '\n') { //si es salto de línea, aumenta la cantidad
                nroLinea += 1;
                System.out.println("ES UN SALTO DE LINEA"); //borrar
                continue;
            } else if (c == '[') {
                //L_CORCHETE

            } else if (c == ']') {
                //R_CORCHETE
            } else if (c == '{') {
                //L_LLAVE
            } else if (c == '}') {
                //R_LLAVE
            } else if (c == ',') {
                //COMA
            } else if (c == '"') {
                //LITERAL_CADENA
                String id = "";
                int j = i;
                try {
                    do {
                        j += j;
                        c = array[j];
                        if (c == '\n')
                            error("Se llegó al final de la línea sin finalizar el nombre del identificador. Se esperaba: '\"' ");
                        id = id + c;
                    } while (c != '"' && c != '\n');
                    System.out.println("AGREGA ID A LA TABLA"); //borrar
                    //TODO: agregar 'id' a la tabla de símbolos
                } catch (ArrayIndexOutOfBoundsException e) {
                    error("Se llegó al final del archivo sin finalizar el nombre del identificador. Se esperaba: '\"' ");
                    e.printStackTrace();
                }
                generarOutput("LITERAL_CADENA"); //TODO: cambiar por key del hash
                i = j; //actualiza el índice principal
            } else if (c == Integer.valueOf(c)) {
                //LITERAL_CADENA
            } else if (c == ':') {
                //DOS_PUNTOS
            } else if (c == 'T') {
                //PR_TRUE
            } else if (c == 'F') {
                //PR_FALSE
            } else if (c == 'N') {
                //PR_NULL
            } else if (c == 'E') {
                //EOF
            }



            System.out.println(c);
            i += 1;
        }

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta hasta el archivo: ");
        // String path = scanner.nextLine();
        String path = "C:\\fp\\compiladores\\Analizador\\archivos\\prueba.txt"; //ubicación del archivo de prueba. Cambiar después por Scanner

        try {
            //lee el archivo según el path que fue introducido
            String contenido = new String(Files.readAllBytes(Paths.get(path)));
            //System.out.println(contenido); //borrar
            siguienteLexema(contenido);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
