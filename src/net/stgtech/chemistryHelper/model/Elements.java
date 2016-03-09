
package net.stgtech.chemistryHelper.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import net.stgtech.chemistryHelper.ChemistryHelper;

public class Elements {
    
    private static final ArrayList<Element> ALL_ELEMENTS = new ArrayList<>();
    private static final ArrayList<String> ALL_ELEMENT_SYMBOLS = new ArrayList<>();
    private static final ArrayList<String> ALL_ELEMENT_NAMES = new ArrayList<>();
    
    public static void loadElementDataFromFile() {
        try {    
            InputStream is;
            String line;
            BufferedReader br;
            
            is = ChemistryHelper.class.getResourceAsStream("data/elements.txt");
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));                                                                                                                                                                                            
            while ((line = br.readLine()) != null) {   
                String[] cols = line.split("\t");                
                ALL_ELEMENTS.add(new Element(cols[0], cols[1], cols[2], cols[3], cols[4], cols[5], 
                        cols[6], cols[7], cols[8], cols[9], cols[10], cols[11]));           
            }   
        } catch (IOException ex) {
            String strMessage = "Unable to locate the element data file. "
                + "please contact support.";
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cannot locate data file");
            alert.setHeaderText(null);
            alert.setContentText(strMessage);
            alert.showAndWait();
            
            Platform.exit();
        }
    }
    
    public static void initializeElementLists() {
        ALL_ELEMENTS.stream().map((e) -> {
            ALL_ELEMENT_NAMES.add(e.getElementName().toLowerCase());
            return e;
        }).forEach((e) -> {
            ALL_ELEMENT_SYMBOLS.add(e.getElementSymbol());
        });
    }

    public static boolean isValidElement(String searchString) {  
        return ALL_ELEMENT_NAMES.contains(searchString.toLowerCase()) || ALL_ELEMENT_SYMBOLS.contains(searchString);
    }
    
    public static Element getElementBySymbolorName(String symbol) {
        if(symbol.length() > 2) {
            return ALL_ELEMENTS.get(ALL_ELEMENT_NAMES.indexOf(symbol.toLowerCase()));
        }
        
        return ALL_ELEMENTS.get(ALL_ELEMENT_SYMBOLS.indexOf(symbol));
    }
    
    public static String getAtomicMassString(String symbol) {
        return Double.toString(ALL_ELEMENTS.get(ALL_ELEMENT_SYMBOLS.indexOf(symbol)).atomicMass());
    }
    
    public enum ELEMENTS {
        ATOMIC_NUMBER,
        ELEMENT_SYMBOL,
        ELEMENT_NAME,
        ATOMIC_MASS,
        STANDARD_STATE,
        BONDING_TYPE,
        MELTING_POINT_K,
        BOILING_POINT_K,
        DENSITY,
        METAL_NONMETAL,
        YEAR_DISCOVERED,
        FORMAL_OXIDATION_NUM;
    };
}
