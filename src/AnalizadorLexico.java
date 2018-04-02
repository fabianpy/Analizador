import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by fabian on 01/04/2018.
 */
public class AnalizadorLexico {
    private static int nroLinea = 0;
    private final int NUMERO = 0;

    public void error(String mensaje) {
        System.out.println("Error léxico en la línea " + nroLinea + ". " + mensaje);
    }

    public static void siguienteLexema(String linea) {
        List<String> tokens = Arrays.asList(linea.split(" "));

        /*Iterator it = tokens.iterator();
        while (it.hasNext()) {
            String token = (String) it.next();
            System.out.println("'"+token+"'");
        }*/

        tokens.forEach(token -> {
            System.out.println("'"+token+"'");
        });

    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta hasta el archivo: ");
        // String path = scanner.nextLine();
        String path = "C:\\fp\\compiladores\\Analizador\\archivos\\prueba.txt"; //ubicación del archivo de prueba. Cambiar después por Scanner

        try {
            //lee el archivo según el path que fue introducido
            Scanner sc = new Scanner(new File(path));

            while (sc.hasNext()) {
                nroLinea += 1;
                String linea = sc.nextLine();
                System.out.println(nroLinea + " " + linea);
                siguienteLexema(linea);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
