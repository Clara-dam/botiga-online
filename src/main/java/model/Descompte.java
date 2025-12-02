/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author clara
 */
public class Descompte {
    private int id;
    private int producte_id;
    private String tipus;
    private double valor;

    public Descompte(int id, int producte_id, String tipus, double valor) {
        this.id = id;
        this.producte_id = producte_id;
        this.tipus = tipus;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProducte_id() {
        return producte_id;
    }

    public void setProducte_id(int producte_id) {
        this.producte_id = producte_id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
}
