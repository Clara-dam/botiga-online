/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.botiga.online;

import dao.ProducteDAO;
import util.Connexio;

/**
 *
 * @author clara
 */
public class BotigaOnline {

    public static void main(String[] args) {
        Connexio.testConnexio();
        ProducteDAO.llistarProductes();
    }
}
