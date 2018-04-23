import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Haide on 03/04/2018.
 */
public class TablaSimbolos {
    /*public static final Integer L_CORCHETE = 73;
    public static final Integer R_CORCHETE = 74;
    public static final Integer L_LLAVE = 75;
    public static final Integer R_LLAVE = 76;
    public static final Integer COMA = 77;
    public static final Integer DOS_PUNTOS = 78;
    public static final Integer LITERAL_CADENA = 79;
    public static final Integer PR_TRUE = 80;
    public static final Integer PR_FALSE = 81;
    public static final Integer PR_NULL = 82;
    public static final Integer EOF = 83;*/

    public static final String L_CORCHETE = "[";
    public static final String R_CORCHETE = "]";
    public static final String L_LLAVE = "{";
    public static final String R_LLAVE = "}";
    public static final String COMA = ",";
    public static final String DOS_PUNTOS = ":";
    public static final String LITERAL_CADENA = "LITERAL_CADENA";
    public static final String LITERAL_NUM = "LITERAL_NUM";
    public static final String PR_TRUE = "true";
    public static final String PR_FALSE = "false";
    public static final String PR_NULL = "null";

    public static HashMap<String, Integer> tabla = new HashMap<>();


    public static void insertTablaSimbolos(String componente, Integer codigo){
        if (codigo == null) {
            codigo = tabla.size() + 1;
        }
        tabla.put(componente, codigo);
    }

    public static void inicializarTablaSimbolos(){
        Collection<String> componenteList = new ArrayList<>();
        componenteList.add(L_CORCHETE); //0
        componenteList.add(R_CORCHETE); //2
        componenteList.add(L_LLAVE);    //3
        componenteList.add(R_LLAVE);    //4
        componenteList.add(COMA);       //5
        componenteList.add(DOS_PUNTOS); //6
        componenteList.add(PR_TRUE);    //7
        componenteList.add(PR_FALSE);   //8
        componenteList.add(PR_NULL);    //9
        Integer codigo = 0;

        for (String comp : componenteList) {
            insertTablaSimbolos(comp, codigo);
            codigo++;
        }

    }

    public static Integer buscar(String componente){
        return tabla.get(componente);
    }
}

