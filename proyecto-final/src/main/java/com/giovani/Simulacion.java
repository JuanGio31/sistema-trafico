package com.giovani;

import com.giovani.tad.linear.DoublyLinkedList;
import com.giovani.tad.linear.StackGeneric;
import com.giovani.tad.nonlinear.TablaDispersion;

import java.util.Scanner;

public class Simulacion {
    private final Ciudad ciudad;
    private final TablaDispersion dispersion;
    private final Scanner scanner = new Scanner(System.in);
    private StackGeneric<String> eventos;

    public Simulacion() {
        this.ciudad = new Ciudad(10, 10);
        this.dispersion = new TablaDispersion();
    }

    public void init() {
        this.ciudad.getMatriz().crearObstaculos();
        boolean loop = true;
        String input;
        do {
            ciudad.proceso();
            input = Utilidad.getEnter(scanner);
            if (input.length() == 1 && input.charAt(0) == 'q') loop = false;
            if (input.length() == 1 && input.charAt(0) == 'm') registrarVehiculo();
            if (input.length() == 1 && input.charAt(0) == 'a') cargaCSV();
            //mostrar reportes del simulador
            if (dispersion.getSize() == ciudad.getVehiculosEnDestino().size()) break;
        } while (loop);
        ciudad.getVehiculosEnDestino().print();
    }

    public void getMenu() {
        int option;
        do {
            printMenuOp();
            option = Utilidad.getNumber(scanner, "Seleccione una opcion");
            switch (option) {
                case 1 -> {
                    cargaCSV();
                    init();
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (option != 0);
        scanner.close();
    }

    private void cargaCSV() {
        int vehiculosRegistrados = 0;
        GestorArchivo gestorArchivo = new GestorArchivo(this.dispersion, ciudad);
        do {
            String path = Utilidad.getString(scanner, "Ingresar la ruta del archivo");
            vehiculosRegistrados = gestorArchivo.readCSV(path);
        } while (vehiculosRegistrados == -1);
        System.out.println("Se agregaron: " + vehiculosRegistrados + " vehiculos.");
        dispersion.mostrarEstructura();
    }

    private int ingresarPrioridadVehiculo() {
        int prioridad = 0;
        do {
            prioridad = Utilidad.getNumber(scanner, "Ingrese la prioridad del vehiculo");
            if (prioridad < 1 || prioridad > 5) {
                System.out.println("La prioridad debe ser un numero entre 1-5.");
                continue;
            }
            break;
        } while (true);
        return prioridad;
    }

    private void registrarVehiculo() {
        String entrada = " ";
        do {
            System.out.println("Registrando vehiculo...");
            String placa = ingresarPlaca();
            String tipo = seleccionarTipoVehiculo();
            int prioridad = ingresarPrioridadVehiculo();
            int tiempo = ingresarTiempoEsperaVehiculo();
            Vehiculo nuevo = new Vehiculo(tipo, placa, prioridad, tiempo);
            Posicion origen = ingresarCoordenada();
            Posicion destino = ingresarCoordenada();
            nuevo.setOrigen(origen);
            nuevo.setDestino(destino);
            ciudad.agregar(nuevo);
            dispersion.insertar(placa, nuevo);
            System.out.println("Vehiculo registrado.");
            entrada = Utilidad.getString(scanner, "Ingrese 'salir' si ya no quiere registrar mas vehiculos.");
        } while (!entrada.equalsIgnoreCase("salir"));
    }

    private int ingresarTiempoEsperaVehiculo() {
        int tiempo = 0;
        do {
            tiempo = Utilidad.getNumber(scanner, "Ingrese el tiempo de espera del vehiculo");
            if (tiempo < 1) {
                System.out.println("El tiempo debe ser un numero mayor a 0.");
                continue;
            }
            break;
        } while (true);
        return tiempo;
    }

    private String ingresarPlaca() {
        String placa = Utilidad.getString(scanner, "Ingrese la placa del vehiculo");
        if (dispersion.buscar(placa) != null) {
            System.out.println("Placa ya registrada.");
            return ingresarPlaca();
        }
        return placa;
    }

    private Posicion ingresarCoordenada() {
        String coordenada = Utilidad.getString(
                scanner,
                "Ingrese la interseccion del vehiculo, Ejemplo: A1" +
                        "\nLa fila debe ser una letra de A-Z, la columna un numero entre 1-27");
        int fila = -1;
        if (coordenada.length() < 2) {
            System.out.println("La interseccion debe tener mas de 1 caracter, Ejemplo: B1.");
            return ingresarCoordenada();
        }
        if (coordenada.charAt(0) < 'A' || coordenada.charAt(0) > 'Z') {
            System.out.println("La primera letra debe ser una letra de la abecedario.");
            return ingresarCoordenada();
        }
        fila = Utilidad.convertirCharAEntero(coordenada.charAt(0));
        int columna = -1;
        try {
            columna = Integer.parseInt(coordenada.substring(1));
            if (columna < 1 || columna > 27) {
                System.out.println("La columna debe ser un numero entre 1-27.");
                return ingresarCoordenada();
            }
            return new Posicion(fila, columna);
        } catch (NumberFormatException e) {
            System.out.println("La columna debe ser un numero entre 1-27.");
            return ingresarCoordenada();
        }
    }

    private String seleccionarTipoVehiculo() {
        do {
            printMenuSeleccionTipo();
            int option = Utilidad.getNumber(scanner, "Seleccione una opcion");
            switch (option) {
                case 1 -> {
                    return "AMBULANCIA";
                }
                case 2 -> {
                    return "POLICIA";
                }
                case 3 -> {
                    return "TRANSPORTE";
                }
                case 4 -> {
                    return "PARTICULAR";
                }
                default -> System.out.println("Opcion no valida.");
            }
        } while (true);
    }

    private void printMenuSeleccionTipo() {
        String options = """
                \t+ - SELECCIONAR TIPO DE VEHICULO - +
                \t|     (1) AMBULANCIA               |
                \t|     (2) POLICIA.                 |
                \t|     (3) TRANSPORTE PUBLICO.      |
                \t|     (4) PARTICULAR.              |
                \t+ - - - - - - - - - - - - - - - - +
                """;
        System.out.println(options);
    }

    private void printMenuOp() {
        String options = """
                \t+ - - SISTEMA DE TRAFICO. - - +
                \t| (1) INICIAR SIMULACION.     |
                \t| (0) SALIR.                  |
                \t+ - - - - - - - - - - - - - - +
                """;
        System.out.println(options);
    }
}
