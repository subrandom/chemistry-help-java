
package net.stgtech.chemistryHelper.model;

import javafx.scene.control.Label;

public class Elements {
    private final Integer atomicNumber; 
    private final String elementSymbol;
    private final String elementName;
    private final String atomicMass;
    private final String standardState;
    private final String bondingType;
    private final Double meltingPointK;
    private final Double boilingPointK;
    private final Double density;
    private final String metalOrNot;
    private final String yearDiscovered;
    private final String formalOxidationNumbers;

    public int getFinalOxidationValue() {
        //we want the last value, which is everything from the final ',' to the end of string
        String strValue = this.formalOxidationNumbers.substring((formalOxidationNumbers.lastIndexOf(",")+1), formalOxidationNumbers.length());
        
        return(Integer.parseInt(strValue));
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

    public Elements (String atomicNumber, String elementSymbol, String elementName, String atomicMass, 
            String standardState, String bondingType, String meltingPointK, String boilingPointK,
            String density, String metalOrNot, String yearDiscovered, String formalOxidationNumbers) {
        this.atomicNumber = Integer.parseInt(atomicNumber);
        this.elementSymbol = elementSymbol;
        this.elementName = elementName;
        this.atomicMass = atomicMass;
        this.standardState = standardState;
        this.bondingType = bondingType;
        this.meltingPointK = Double.parseDouble(meltingPointK);
        this.boilingPointK = Double.parseDouble(boilingPointK);
        this.density = Double.parseDouble(density);
        this.metalOrNot = metalOrNot;
        this.yearDiscovered = yearDiscovered;
        this.formalOxidationNumbers = formalOxidationNumbers;
    }
    
    public String getElementSymbol() {
        return this.elementSymbol;  
    }
    
    public String getElementName() {
        return this.elementName;
    }
    
    public boolean searchFor(String searchTerm) {
        boolean match = false;
        searchTerm = searchTerm.toLowerCase();
        
        if(this.elementName.toLowerCase().equals(searchTerm) || this.elementSymbol.toLowerCase().equals(searchTerm) || this.atomicNumber.toString().equals(searchTerm)) {
            match = true;
        }
        return match;
    }
    
    public Label getLabelForProperty (ELEMENTS property) {
        Label returnLabel = new Label();
        switch(property) {
            case ATOMIC_NUMBER:
                returnLabel.setText(Integer.toString(this.atomicNumber));
                break;
            case ELEMENT_SYMBOL:
                returnLabel.setText(this.elementSymbol);
                break;
            case ELEMENT_NAME:
                returnLabel.setText(this.elementName);
                break;
            case ATOMIC_MASS:
                returnLabel.setText(this.atomicMass);
                break;
            case STANDARD_STATE:
                returnLabel.setText(this.standardState);
                break;
            case BONDING_TYPE:
                returnLabel.setText(this.bondingType);
                break;
            case MELTING_POINT_K:
                returnLabel.setText(Double.toString(this.meltingPointK));
                break;
            case BOILING_POINT_K:
                returnLabel.setText(Double.toString(this.boilingPointK));
                break;
            case DENSITY:
                returnLabel.setText(Double.toString(this.density));
                break;
            case METAL_NONMETAL:
                returnLabel.setText(this.metalOrNot);
                break;
            case YEAR_DISCOVERED:
                returnLabel.setText(this.yearDiscovered);
                break;
            case FORMAL_OXIDATION_NUM:
                returnLabel.setText(this.formalOxidationNumbers);
                break;
            default:
                returnLabel = null;
                break;
        }
        
        return returnLabel;
    }
    
    public int[] getOxidationLevels() {
        int[] oxidationLevels;
        
        String [] strOxidationLevels = this.formalOxidationNumbers.split(",");
        
        int numberOfStates = strOxidationLevels.length;
        
        oxidationLevels = new int[numberOfStates];
        
        for(int i = 0; i < numberOfStates; i++) {
            oxidationLevels[i] = Integer.parseInt(strOxidationLevels[i]);
        }
        return oxidationLevels;
    }
    
    public String getOxidationLevelsAsString() {
        return this.formalOxidationNumbers;
    }

}
