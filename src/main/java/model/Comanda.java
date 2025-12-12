/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author clara
 */
public class Comanda {

    private int id;
    private int client_id;
    private Timestamp data;
    private double total;
    private List<LiniaComanda> linies;

    public Comanda(int id, int client_id, Timestamp data, double total) {
        this.id = id;
        this.client_id = client_id;
        this.data = data;
        this.total = total;
        this.linies = new ArrayList<>();
    }

    public Comanda(int client_id, Timestamp data) {
        this.client_id = client_id;
        this.data = data;
        this.total = 0;
        this.linies = new ArrayList<>();
    }

    public List<LiniaComanda> getLinies() { return linies; }

    public double calcularTotalFinal() {
        return linies.stream().mapToDouble(LiniaComanda::calcularTotal).sum();
    }

    // Getters i setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClient_id() { return client_id; }
    public void setClient_id(int client_id) { this.client_id = client_id; }

    public Timestamp getData() { return data; }
    public void setData(Timestamp data) { this.data = data; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}