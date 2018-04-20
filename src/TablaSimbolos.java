import java.util.HashMap;

/**
 * Created by Haide on 03/04/2018.
 */
public class TablaSimbolos {
    public static final Integer L_CORCHETE = 73;
    public static final Integer R_CORCHETE = 74;
    public static final Integer L_LLAVE = 75;
    public static final Integer R_LLAVE = 76;
    public static final Integer COMA = 77;
    public static final Integer DOS_PUNTOS = 78;
    public static final Integer LITERAL_CADENA = 79;
    public static final Integer PR_TRUE = 80;
    public static final Integer PR_FALSE = 81;
    public static final Integer PR_NULL = 82;
    public static final Integer EOF = 83;
    public static HashMap<String, Integer> tabla = new HashMap<String, Integer>();

    private static void insertTablaSimbolos(String componente, Integer caracter){
        tabla.put(componente, caracter);
    }

    public static void inicializarTablaSimbolos(){
        insertTablaSimbolos("{", L_LLAVE);
        insertTablaSimbolos("}", R_LLAVE);
        insertTablaSimbolos("[", L_CORCHETE);
        insertTablaSimbolos("]", R_CORCHETE);
        insertTablaSimbolos(",", COMA);
        insertTablaSimbolos(":", DOS_PUNTOS);
        insertTablaSimbolos("True", PR_TRUE);
        insertTablaSimbolos("False", PR_FALSE);
    }

    public static Integer buscar(String componente){
        Integer codigo =tabla.get(componente);
        return codigo;
    }
}

