
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

    private static void generarOutput(ArrayList<String> valueList){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DIRECTORIO_ACTUAL.concat("output.txt")))) {

            for (String value : valueList) {
                System.out.println("value: " + value); //borrar
                bw.write(value + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void siguienteLexema(String fuente) {
        char[] array = fuente.toCharArray();
        int i = 0;
        ArrayList<String> contenidoList = new ArrayList<String>();
        while (i < array.length) {
            char c = array[i];

            System.out.println("valor de c = '" + c + "' ; valor de i = "+i);

            if (c == ' ' || c == '\t') {
                i += 1;
                contenidoList.add(String.valueOf(c));
                continue; //elimina espacios en blanco y tabulaciones
            }

            else if (c == '\n') { //si es salto de línea, aumenta la cantidad
                nroLinea += 1;
                System.out.println("ES UN SALTO DE LINEA. i = "+ i); //borrar
                i += 1;
                contenidoList.add(String.valueOf(c));
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
                String id = ""+c;
                int j = i;
                try {
                    do {
                        j += 1;
                        c = array[j];
                        if (c == '\n')
                            error("Se llegó al final de la línea sin finalizar el nombre del identificador. Se esperaba: '\"' ");
                        id = id + c;
                    } while (c != '"' && c != '\n');
                    System.out.println("  AGREGA ID A LA TABLA. id = " + id); //borrar
                    //TODO: agregar 'id' a la tabla de símbolos
                } catch (ArrayIndexOutOfBoundsException e) {
                    error("Se llegó al final del archivo sin finalizar el nombre del identificador. Se esperaba: '\"' ");
                    e.printStackTrace();
                }
                //generarOutput("LITERAL_CADENA"); //TODO: cambiar por key del hash
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

            i += 1;
            contenidoList.add("LITERAL_CADENA"); //TODO: cambiar por los valores del key

        }
        generarOutput(contenidoList);

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
