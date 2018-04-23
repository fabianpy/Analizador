
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
    private static int nroLinea = 1;
    private static final String DIRECTORIO_ACTUAL = new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().lastIndexOf("."));

    private static void error(String mensaje) {
        System.out.println("Error léxico en la línea " + nroLinea + ". " + mensaje);
    }

    private static HashMap<String, Object> analizarPalabraReservada(char[] array, Integer i) {
        Integer j = i;
        char c = array[i];
        HashMap<String, Object> hm = new HashMap<>();
        String id = Character.toString(c).toLowerCase();
        try {

            while (array[j+1] != '"' && array[j+1] != ' ' && array[j+1] != ',') {
                if (c == '\n')
                    error("Se llegó al final de la línea sin finalizar el nombre del identificador.");
                j=j+1;
                c = array[j];
                id += Character.toLowerCase(c);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            error("Se llegó al final del archivo sin finalizar el nombre del identificador. Se esperaba: '\"' ");
            e.printStackTrace();
        }
        hm.put("palabra", id);
        hm.put("indice", j);
        return hm;
    }

    private static void generarOutput(ArrayList<String> valueList){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DIRECTORIO_ACTUAL.concat("output.txt")))) {

            for (String value : valueList) {
                bw.write(value + " ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void siguienteLexema(String fuente) {
        char[] array = fuente.toCharArray();
        Integer i = 0;
        ArrayList<String> contenidoList = new ArrayList<>();
        while (i < array.length) {
            char c = array[i];

            if (c == ' ' || c == '\t') {
                i += 1;
                contenidoList.add(String.valueOf(c));
                continue; //elimina espacios en blanco y tabulaciones
            }

            else if (c == '\n') { //si es salto de línea, aumenta la cantidad
                nroLinea += 1;
                i += 1;
                contenidoList.add(String.valueOf(c));
                continue;
            } else if (c == '[') {
                //L_CORCHETE
                contenidoList.add("L_CORCHETE");
            } else if (c == ']') {
                //R_CORCHETE
                contenidoList.add("R_CORCHETE");
            } else if (c == '{') {
                //L_LLAVE
                contenidoList.add("L_LLAVE");
            } else if (c == '}') {
                //R_LLAVE
                contenidoList.add("R_LLAVE");
            } else if (c == ',') {
                //COMA
                contenidoList.add("COMA");
            } else if (c == '"') {
                //LITERAL_CADENA
                String id = Character.toString(c);
                int j = i;
                try {
                    do {
                        j += 1;
                        c = array[j];
                        if (c == '\n')
                            error("Se llegó al final de la línea sin finalizar el nombre del identificador. Se esperaba: '\"' ");
                        id = id + c;
                    } while (c != '"' && c != '\n');
                    if (TablaSimbolos.buscar(id) == null) {
                        TablaSimbolos.insertTablaSimbolos(id, null);
                    }
                    contenidoList.add("LITERAL_CADENA");

                } catch (ArrayIndexOutOfBoundsException e) {
                    error("Se llegó al final del archivo sin finalizar el nombre del identificador. Se esperaba: '\"' ");
                    e.printStackTrace();
                }
                i = j; //actualiza el índice principal
            } else if (Character.isDigit(c)) {
                //LITERAL_NUM
                // [0-9]+(\.[0-9]+)?((e|E)(+|-)?[0-9]+)?
                int estado = 0;
                String numero = "";
                boolean acepta = false;
                try {
                    while (!acepta) {
                        if (c != ',' && c != '\n') {
                            c = array[i];
                            switch (estado) {
                                case 0:
                                    //es un entero
                                    numero += c; //arma el numero
                                    if (array[i+1] == ',' || array[i+1] == '\n' || array[i+1] == ' ')
                                        estado = 3;
                                    else if (array[i+1] == '.')
                                        estado = 1;
                                    else if (Character.toLowerCase(array[i+1]) == 'e')
                                        estado = 2;
                                    break;
                                case 1:
                                    //es un punto decimal
                                    numero += c; //arma el numero
                                    if (!Character.isDigit(array[i+1])) {
                                        estado = -1;
                                        break;
                                    }
                                    estado = 0; //después del punto, debe venir un número
                                    break;
                                case 2:
                                    //es una e|E
                                    numero += c; //arma el numero
                                    if (array[i+1] == '+' || array[i+1] == '-') {
                                        estado = 0; //tiene que venir un número
                                        break;
                                    }
                                    if (!Character.isDigit(array[i+1])) {
                                        estado = -1;
                                        break;
                                    } else
                                        estado = 0; //es un número
                                    break;
                                case 3:
                                    //número podría ser válido
                                    if (c == ' ' || c == '\n' || c == ',') {
                                        contenidoList.add("LITERAL_NUM");
                                        acepta = true;
                                        i = i-2;
                                    } else {
                                        throw new Exception("Caso no contemplado. c = " + c);
                                    }
                                    break;
                                case -1:
                                    //número no válido
                                    numero += c; //arma el número
                                    error(numero + " no es un número válido. No se esperaba '"+c+"'");
                                    acepta = true; //para que salga del ciclo
                                    break;
                                default:
                                    throw new Exception("Caso no contemplado: '"+c+"'");
                            }
                        }
                        i+=1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (c == ':') {
                //DOS_PUNTOS
                contenidoList.add("DOS_PUNTOS");
            } else if (Character.toLowerCase(c) == 't') {
                //PR_TRUE
                //puede formarse la palabra 'true'
                HashMap<String, Object> map = analizarPalabraReservada(array, i);
                if (map.get("palabra").equals("true")) {
                    contenidoList.add("PR_TRUE");
                } else {
                    error("No se reconoce '"+map.get("palabra")+"' como componente del lenguaje.");
                }
                i = (Integer) map.get("indice");
            } else if (Character.toLowerCase(c) == 'f') {
                //PR_FALSE
                //puede formarse la palabra 'false'
                HashMap<String, Object> map = analizarPalabraReservada(array, i);
                if (map.get("palabra").equals("false")) {
                    contenidoList.add("PR_FALSE");
                } else {
                    error("No se reconoce '"+map.get("palabra")+"' como componente del lenguaje.");
                }
                i = (Integer) map.get("indice");
            } else if (Character.toLowerCase(c) == 'n') {
                //PR_NULL
                //puede formarse la palabra 'null'
                HashMap<String, Object> map = analizarPalabraReservada(array, i);
                if (map.get("palabra").equals("null")) {
                    contenidoList.add("PR_NULL");
                } else {
                    error("No se reconoce '"+map.get("palabra")+"' como componente del lenguaje.");
                }
                i = (Integer) map.get("indice");
            }

            i += 1;

        }
        generarOutput(contenidoList);

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta hasta el archivo: ");
        String path = scanner.nextLine();

        try {
            //lee el archivo según el path que fue introducido
            String contenido = new String(Files.readAllBytes(Paths.get(path)));
            TablaSimbolos.inicializarTablaSimbolos();
            siguienteLexema(contenido);
            //TablaSimbolos.tabla.forEach((k, v) -> System.out.println("Key: " + k + ": Value: " + v));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
