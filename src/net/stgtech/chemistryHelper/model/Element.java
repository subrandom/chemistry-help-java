/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.stgtech.chemistryHelper.model;

import javafx.scene.control.Label;
import net.stgtech.chemistryHelper.model.Elements.ELEMENTS;

/**
 *
 * @author Stephen.Bradley
 */
public class Element {
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
    
    public Element (String atomicNumber, String elementSymbol, String elementName, String atomicMass, 
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

}
