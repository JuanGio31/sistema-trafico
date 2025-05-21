package com.giovani;

import com.giovani.tad.nonlinear.TablaDispersion;

import java.util.Scanner;

public class Simulacion {
    private final Ciudad ciudad;
    private final TablaDispersion dispersion;
    private final Scanner scanner = new Scanner(System.in);
    //private final StackGeneric<String> eventos;
//    private Reporte reporte;

    public Simulacion() {
//        this.eventos = new StackGeneric<>();
        ciudad = new Ciudad(10, 10);
        this.dispersion = new TablaDispersion();
        //this.reporte = new Reporte(eventos, ciudad);
    }

    public void init() {
        this.ciudad.generarObstaculos();
        boolean loop = true;
        String input;
        ciudad.imprimirEstructura();
        do {
            imprimirMenuDeSimulacion();
            input = Utilidad.getEnter(scanner);
            if (input.isBlank()) {
                ciudad.generarMovimiento();
                ciudad.imprimirEstructura();
            }
            if (input.length() == 1 && input.charAt(0) == 'q') loop = false;
            //if (input.length() == 1 && input.charAt(0) == 'm') registrarVehiculo();
            if (input.length() == 1 && Character.toLowerCase(input.charAt(0)) == 'a') cargaCSV();
            if (input.length() == 1 && Character.toLowerCase(input.charAt(0)) == 'b') buscarPorPlaca();
            if (dispersion.getSize() == ciudad.getVehiculosEnDestino().size()) break;
        } while (loop);
        ciudad.getVehiculosEnDestino().print();
        //reporte.ver();
    }

    public void getMenu() {
        int option;
        do {
            imprimirMenuPrincipal();
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
        int vehiculosRegistrados;
        GestorArchivo gestorArchivo = new GestorArchivo(this.dispersion, this.ciudad);
        do {
            //String path = Utilidad.getString(scanner, "Ingresar la ruta del archivo");
            //vehiculosRegistrados = gestorArchivo.readCSV(path);
            vehiculosRegistrados = gestorArchivo.readCSV("/home/giovanic/Documents/Tareas/1S2025/EDD/Proyectos/sistema-trafico/proyecto-final/prueba.csv");
        } while (vehiculosRegistrados == -1);
        System.out.println("Se agregaron: " + vehiculosRegistrados + " vehiculos.");
        //eventos.push("-Se agregaron: " + vehiculosRegistrados + " vehiculos.");
//        dispersion.mostrarEstructura();
    }

    private int ingresarPrioridadVehiculo() {
        int prioridad;
        do {
            prioridad = Utilidad.getNumber(scanner, "Ingrese la prioridad del vehiculo");
            if (prioridad < 1 || prioridad > 5) {
                Utilidad.printCadenaEnRojo("La prioridad debe ser un numero entre 1-5.");
                continue;
            }
            break;
        } while (true);
        return prioridad;
    }

    private void registrarVehiculo() {
        String entrada;
        do {
            System.out.println("Registrando vehiculo...");
            String placa = ingresarPlaca();
            String tipo = seleccionarTipoVehiculo();
            int prioridad = ingresarPrioridadVehiculo();
            int tiempo = ingresarTiempoEsperaVehiculo();
            Vehiculo nuevo = new Vehiculo(tipo, placa, prioridad, tiempo);
            Posicion origen = ingresarPosicion();
            Posicion destino = ingresarPosicion();
            nuevo.setOrigen(origen);
            nuevo.setDestino(destino);
            ciudad.agregarVehiculoEnCiudad(nuevo);
            dispersion.insertar(placa, nuevo);
            Utilidad.printCyan("Vehiculo registrado.\n");
            entrada = Utilidad.getString(scanner, "Ingrese 'salir' si ya no quiere registrar mas vehiculos.");
        } while (!entrada.equalsIgnoreCase("salir"));
    }

    private int ingresarTiempoEsperaVehiculo() {
        int tiempo;
        do {
            tiempo = Utilidad.getNumber(scanner, "Ingrese el tiempo de espera del vehiculo");
            if (tiempo < 1) {
                Utilidad.printCadenaEnRojo("El tiempo (entero) de espera debe ser un numero positivo.");
                continue;
            }
            break;
        } while (true);
        return tiempo;
    }

    private String ingresarPlaca() {
        String placa;
        do {
            placa = Utilidad.getString(scanner, "Ingrese la placa del vehiculo").trim();
            if (placa.length() < 8) {
                Utilidad.printCadenaEnRojo("La placa debe tener al menos 8 caracteres.");
                continue;
            }
            if (dispersion.buscar(placa) != null) {
                Utilidad.printCadenaEnRojo("La placa ya esta registrada.");
            } else {
                break;
            }
        } while (true);
        return placa;
    }

    private Posicion ingresarPosicion() {
        boolean posValida = false;
        String posicion;
        int fila = -1, columna = -1;
        do {
            posicion = Utilidad.getString(
                    scanner,
                    "Ingrese la interseccion del vehiculo, Ejemplo: A1" +
                            "\nLa fila debe ser una letra de A-Z, la columna un numero entre 1-27");
            if (posicion.length() < 2) {
                Utilidad.printCadenaEnRojo("La interseccion debe tener mas de 1 caracter, Ejemplo: B1.");
                continue;
            }
            if (posicion.charAt(0) < 'A' || posicion.charAt(0) > 'Z') {
                Utilidad.printCadenaEnRojo("la fila se identifica con una letra del abecedario.");
                continue;
            }
            fila = Utilidad.convertirCharAEntero(posicion.charAt(0));
            try {
                columna = Integer.parseInt(posicion.substring(1));
                if (columna < 1 || columna > 27) {
                    Utilidad.printCadenaEnRojo("La columna debe ser un numero entre 1-27.");
                    continue;
                }
                posValida = true;
            } catch (NumberFormatException e) {
                Utilidad.printCadenaEnRojo("La columna debe ser un numero entre 1-27.");
            }
            var aux = this.ciudad.getInterseccion(columna, fila);
            if (aux == null) {
                Utilidad.printCadenaEnRojo("La ubicacion esta bloqueada");
                posValida = false;
            }
        } while (!posValida);
        return new Posicion(fila, columna);
    }

    private String seleccionarTipoVehiculo() {
        do {
            imprimirSeleccionTipo();
            int option = Utilidad.getNumber(scanner, "Seleccione una opcion");
            if (option == 1) return "AMBULANCIA";
            if (option == 2) return "POLICIA";
            if (option == 3) return "TRANSPORTE";
            if (option == 4) return "PARTICULAR";
            Utilidad.printCadenaEnRojo("Opcion no valida.");
        } while (true);
    }

    private void imprimirSeleccionTipo() {
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

    private void imprimirMenuPrincipal() {
        String options = """
                \t+ - - SISTEMA DE TRAFICO. - - +
                \t| (1) INICIAR SIMULACION.     |
                \t| (0) SALIR.                  |
                \t+ - - - - - - - - - - - - - - +
                """;
        System.out.println(options);
    }

    private void buscarPorPlaca() {
        String placa = Utilidad.getString(scanner, "Ingresar placa a buscar");
        var vehiculo = dispersion.buscar(placa.trim());
        if (vehiculo != null) {
            System.out.print(vehiculo.getInfo());
            if (vehiculo.finRecorrido()) {
                System.out.println(", ya ha llegado a su destino");
            }
        } else {
            System.out.println("No se encontraron coincidencias.");
        }
        System.out.println(" ");
    }

    private void imprimirMenuDeSimulacion() {
        String options = """
                \t + - - - - - - OPCIONES - - - - - - +
                \t | (a) Ingresar Vehiculo.           |
                \t | (a) Ingresar Vehiculo.           |
                \t | (b) Buscar Vehiculo por placa.   |
                \t | (m) I.V. por CSV.                |
                \t | (q) salir.                       |
                """;
        System.out.println(options);
    }
}