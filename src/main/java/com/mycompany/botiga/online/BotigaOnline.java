/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.botiga.online;

import dao.ClientDAO;
import dao.ComandaDAO;
import dao.DescompteDAO;
import dao.ProducteDAO;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Comanda;
import model.LiniaComanda;
import util.Connexio;

/**
 *
 * @author clara
 */
public class BotigaOnline {

    public static void main(String[] args) {
        // Mètode per mostrat missatge de Benvinguda quan es connecta correctament a la BD, o missatge d'error.
        //Connexio.testConnexio();

        Scanner sc = new Scanner(System.in);
        int opcio;
        do {
            System.out.println("\n===== BOTIGA ONLINE =====");
            System.out.println("1. Gestionar Productes");
            System.out.println("2. Gestionar Clients");
            System.out.println("3. Gestionar Descomptes");
            System.out.println("4. Crear Comanda");
            System.out.println("5. Llistar Comandes");
            System.out.println("0. Sortir");
            System.out.print("Opció: ");
            opcio = sc.nextInt();
            switch (opcio) {
                case 1: // Gestionar Productes
                    gestionarProductes(sc);
                    break;
                case 2: // Gestionar Clients
                    gestionarClients(sc);
                    break;
                case 3: // Gestionar Descomptes
                    gestionarDescomptes(sc);
                case 4: // Crear Comanda
                    crearComandaMenu();
                    break;
                case 5: // Llistar Comandes
                    llistarComandesPerClient(sc);
                    break;
                case 0: // Sortir
                    System.out.println("Adéu!");
                    break;
                default:
                    System.out.println("Opció no vàlida.");
            }
        } while (opcio != 0);
        sc.close();
    }

    // Submenú de gestionar productes
    private static void gestionarProductes(Scanner sc) {
        int opcioProducte;
        do {
            System.out.println("\n===== GESTIONAR PRODUCTES =====");
            System.out.println("1. Afegir Producte");
            System.out.println("2. Eliminar Producte");
            System.out.println("3. Actualitzar Preu Producte");
            System.out.println("4. Llistar Productes");
            System.out.println("5. Enrere");
            System.out.print("Opció: ");
            opcioProducte = sc.nextInt();

            switch (opcioProducte) {
                case 1: // Afegir producte
                    System.out.print("Nom del producte: ");
                    String nom = sc.next();
                    System.out.print("Preu del producte: ");
                    double preu = sc.nextDouble();
                    System.out.print("Estoc del producte: ");
                    int estoc = sc.nextInt();
                    ProducteDAO.afegirProducte(nom, preu, estoc);
                    break;
                case 2: // Eliminar producte
                    System.out.print("ID del producte a eliminar: ");
                    int idEliminar = sc.nextInt();
                    ProducteDAO.eliminarProducte(idEliminar);
                    break;
                case 3: // Actualitzar preu producte
                    System.out.print("ID del producte a actualitzar: ");
                    int idPreu = sc.nextInt();
                    System.out.print("Nou preu: ");
                    double nouPreu = sc.nextDouble();
                    ProducteDAO.actualitzarPreu(idPreu, nouPreu);
                    break;
                case 4: // Llistar productes
                    ProducteDAO.llistarProductes();
                    break;
                case 5: // Tornar enrere
                    System.out.println("Tornant enrere...");
                    break;
                default:
                    System.out.println("Opció no vàlida.");
            }
        } while (opcioProducte != 5);
    }

    // Submenú de gestionar clients
    private static void gestionarClients(Scanner sc) {
        int opcioClient;
        do {
            System.out.println("\n===== GESTIONAR CLIENTS =====");
            System.out.println("1. Afegir Client");
            System.out.println("2. Eliminar Client");
            System.out.println("3. Actualitzar Dades Client");
            System.out.println("4. Llistar Clients");
            System.out.println("5. Enrere");
            System.out.print("Opció: ");
            opcioClient = sc.nextInt();

            switch (opcioClient) {
                case 1: // Afegir client
                    System.out.print("Nom del client: ");
                    String nomClient = sc.next();
                    System.out.print("Correu del client: ");
                    String correu = sc.next();
                    ClientDAO.afegirClient(nomClient, correu);
                    break;
                case 2: // Eliminar client
                    System.out.print("ID del client a eliminar: ");
                    int idEliminarClient = sc.nextInt();
                    ClientDAO.eliminarClient(idEliminarClient);
                    break;
                case 3: // Actualitzar correu client
                    System.out.print("ID del client a actualitzar: ");
                    int idClient = sc.nextInt();
                    System.out.print("Nou nom: ");
                    String nouNom = sc.next();
                    System.out.print("Nou correu: ");
                    String nouCorreu = sc.next();
                    ClientDAO.actualitzarDades(idClient, nouNom, nouCorreu);
                    break;
                case 4: // Llistar clients
                    ClientDAO.llistarClients();
                    break;
                case 5: // Tornar enrere
                    System.out.println("Tornant enrere...");
                    break;
                default:
                    System.out.println("Opció no vàlida.");
            }
        } while (opcioClient != 5);
    }

// Submenú para gestionar descomptes
    private static void gestionarDescomptes(Scanner sc) {
        int opcioDescompte;
        do {
            System.out.println("===== GESTIONAR DESCOMPTES =====");
            System.out.println("1. Afegir Descompte");
            System.out.println("2. Eliminar Descompte");
            System.out.println("3. Actualitzar Descompte");
            System.out.println("4. Llistar Descomptes");
            System.out.println("5. Enrere");
            System.out.print("Opció: ");
            opcioDescompte = sc.nextInt();

            switch (opcioDescompte) {
                case 1: // Afegir descompte
                    System.out.print("ID del producte: ");
                    int producte_id = sc.nextInt();
                    System.out.print("Tipus de descompte (percentatge/quantitat fixa): ");
                    String tipus = sc.next();
                    System.out.print("Valor del descompte: ");
                    double valor = sc.nextDouble();
                    // Llamamos al método del DAO para agregar el descuento
                    DescompteDAO.afegirDescompte(producte_id, tipus, valor);
                    break;
                case 2: // Eliminar descompte
                    System.out.print("ID del descompte a eliminar: ");
                    int idEliminar = sc.nextInt();
                    // Llamamos al método del DAO para eliminar el descuento
                    DescompteDAO.eliminarDescompte(idEliminar);
                    break;
                case 3: // Actualitzar descompte
                    System.out.print("ID del descompte a actualitzar: ");
                    int idDescompte = sc.nextInt();
                    System.out.print("Nou tipus de descompte (percentatge/quantitat fixa): ");
                    String nouTipus = sc.next();
                    System.out.print("Nou valor del descompte: ");
                    double nouValor = sc.nextDouble();
                    // Llamamos al método del DAO para actualizar el descuento
                    DescompteDAO.actualitzarDescompte(idDescompte, nouTipus, nouValor);
                    break;
                case 4: // Llistar descomptes
                    // Llamamos al método del DAO para listar todos los descuentos
                    DescompteDAO.llistarDescomptes();
                    break;
                case 5: // Tornar enrere
                    System.out.println("Tornant enrere...");
                    break;
                default:
                    System.out.println("Opció no vàlida.");
            }
        } while (opcioDescompte != 5);
    }

    public static void crearComandaMenu() {
        Scanner sc = new Scanner(System.in);
        ComandaDAO cdao = new ComandaDAO();

        System.out.print("ID del client: ");
        int clientId = sc.nextInt();

        List<LiniaComanda> linies = new ArrayList<>();

        char mes;
        do {
            System.out.print("ID producte: ");
            int prodId = sc.nextInt();
            System.out.print("Quantitat: ");
            int quant = sc.nextInt();

            // Aquí consultaries preu del producte
            double preu = ProducteDAO.getPreu(prodId);

            linies.add(new LiniaComanda(prodId, quant, preu));

            System.out.print("Afegir més productes? (s/n): ");
            mes = sc.next().charAt(0);

        } while (mes == 's' || mes == 'S');

        Comanda comanda = new Comanda(clientId, new Timestamp(System.currentTimeMillis()));

        boolean ok = cdao.crearComanda(comanda, linies);

        if (ok) {
            System.out.println("Comanda creada correctament!");
        } else {
            System.out.println("ERROR al crear comanda.");
        }
    }

    private static void llistarComandesPerClient(Scanner sc) {
        System.out.print("ID del client: ");
        int clientId = sc.nextInt();
        sc.nextLine();

        ComandaDAO comandaDAO = new ComandaDAO();
        List<Comanda> comandes = comandaDAO.getComandesByClient(clientId);

        if (comandes.isEmpty()) {
            System.out.println("Aquest client no té comandes.");
        } else {
            System.out.println("\n=== COMANDES DEL CLIENT " + clientId + " ===");
            for (Comanda c : comandes) {
                System.out.printf("\nComanda %d | Data: %s | Total BD: %.2f€\n",
                        c.getId(), c.getData(), c.getTotal());

                for (LiniaComanda linia : c.getLinies()) {
                    System.out.println(linia);
                }

                System.out.printf("Total recomputat (amb descomptes): %.2f€\n",
                        c.calcularTotalFinal());
            }
        }
    }
}
