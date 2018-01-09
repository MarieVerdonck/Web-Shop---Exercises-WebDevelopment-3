/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 *
 * @author Marie
 */
public class ProductErrorListPair {
    
    private Product product;
    private ArrayList<String> errors;

    public ProductErrorListPair(Product product, ArrayList<String> errors) {
        this.product = product;
        this.errors = errors;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
    
}
