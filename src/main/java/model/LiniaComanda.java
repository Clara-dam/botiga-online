/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author clara
 */

public class LiniaComanda {

    private int id;
    private int comanda_id;
    private int producte_id;
    private String producteNom;   // Nom del producte
    private double descompte;     // Percentatge de descompte
    private int quantitat;
    private double preuUnitari;

    public LiniaComanda(int id, int comanda_id, int producte_id, String producteNom, double descompte, int quantitat, double preuUnitari) {
        this.id = id;
        this.comanda_id = comanda_id;
        this.producte_id = producte_id;
        this.producteNom = producteNom;
        this.descompte = descompte;
        this.quantitat = quantitat;
        this.preuUnitari = preuUnitari;
    }

    public LiniaComanda(int producte_id, int quantitat, double preuUnitari) {
        this.producte_id = producte_id;
        this.quantitat = quantitat;
        this.preuUnitari = preuUnitari;
    }

    public double calcularTotal() {
        return quantitat * preuUnitari * (1 - descompte / 100.0);
    }

    @Override
    public String toString() {
        return String.format("Línia %d | %s | Quantitat: %d | Preu unitari: %.2f | Desc: %.1f%% | Total línia: %.2f",
                id, producteNom, quantitat, preuUnitari, descompte, calcularTotal());
    }

    // Getters i setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getComanda_id() { return comanda_id; }
    public void setComanda_id(int comanda_id) { this.comanda_id = comanda_id; }

    public int getProducte_id() { return producte_id; }
    public void setProducte_id(int producte_id) { this.producte_id = producte_id; }

    public String getProducteNom() { return producteNom; }
    public void setProducteNom(String producteNom) { this.producteNom = producteNom; }

    public double getDescompte() { return descompte; }
    public void setDescompte(double descompte) { this.descompte = descompte; }

    public int getQuantitat() { return quantitat; }
    public void setQuantitat(int quantitat) { this.quantitat = quantitat; }

    public double getPreuUnitari() { return preuUnitari; }
    public void setPreuUnitari(double preuUnitari) { this.preuUnitari = preuUnitari; }
}
