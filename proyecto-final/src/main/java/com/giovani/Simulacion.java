package com.giovani;

import com.giovani.tad.nonlinear.TablaDispersion;

import java.util.Scanner;

public class Simulacion {
    private Ciudad ciudad;
    private TablaDispersion dispersion;
    private final Scanner scanner = new Scanner(System.in);
    private Reporte reporte;

    public void init() {
        int contador = 0;
        this.ciudad.generarObstaculos();
        boolean loop = true;
        String input;
        ciudad.imprimirEstructura();
        do {
            imprimirMenuDeSimulacion();
            input = Utilidad.getEnter(scanner, "Presiona enter para mover los vehiculos");
            if (input.isBlank()) {
                ciudad.generarAcciones();
                ciudad.imprimirEstructura();
                contador++;
            } else if (input.length() == 1) {
                switch (Character.toLowerCase(input.charAt(0))) {
                    case 'b' -> buscarPorPlaca();
                    case 'e' -> verEstadoInterseccion();
                    case 'm' -> registrarVehiculo();
                    case 'd' -> mostrarEstadoDeColas();
                    case 'q' -> {
                        contador--;
                        loop = false;
//                    case 'a' -> cargaCSV();
                    }
                    default -> {
                        Utilidad.printCadenaEnRojo("Opcion no valida.");
                        if (contador != 0) {
                            contador--;
                        }
                    }
                }
                contador++;
            }
            if (contador == 3) {
                reporte.mostrarEventos(ciudad.getEventos());
                contador = 0;
            }
            if (dispersion.getSize() == ciudad.getVehiculosEnDestino().size()) break;
        } while (loop);
        reporte.mostrarReporteCiudad();
    }

    public void getMenu() {
        int option;
        do {
            imprimirMenuPrincipal();
            option = Utilidad.getNumber(scanner, "Seleccione una opcion");
            switch (option) {
                case 1 -> {
                    System.out.println("Iniciando simulacion...");
                    ciudad = new Ciudad(8, 8);
                    this.dispersion = new TablaDispersion();
                    this.reporte = new Reporte(ciudad, dispersion);
                    cargaCSV();
                    Utilidad.getEnter(scanner, "Presione enter para iniciar la simulacion");
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
            String path = Utilidad.getString(scanner, "Ingresar la ruta del archivo");
            vehiculosRegistrados = gestorArchivo.readCSV(path);
        } while (vehiculosRegistrados == -1);
        System.out.println("Se agregaron: " + vehiculosRegistrados + " vehiculos.");
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
            System.out.println("Ingresar Posicion de origen: ");
            Posicion origen = ingresarPosicion();
            System.out.println("Ingresar Posicion de destino: ");
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
            var aux = this.ciudad.getInterseccion(columna - 1, fila);
            if (aux == null) {
                Utilidad.printCadenaEnRojo("La ubicacion esta bloqueada");
                posValida = false;
            }
        } while (!posValida);
        return new Posicion(fila, columna - 1);
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

    private void verEstadoInterseccion() {
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
        } while (!posValida);
        var interseccion = ciudad.getInterseccion(columna - 1, fila);
        if (interseccion != null) {
            interseccion.mostrarEstado();
        } else {
            System.out.println("Error: problemas con la interseccion.");
        }
    }

    private void imprimirMenuDeSimulacion() {
        String options = """
                \t + - - - - - - OPCIONES - - - - - - -  +
                \t | (b) Buscar Vehiculo por placa.      |
                \t | (d) Ver estado de Colas.            |
                \t | (e) Ver estado de la interseccion.  |
                \t | (m) Ingresar Vehiculo.              |
                \t | (q) salir.                          |
                """;
        System.out.println(options);
    }

    private void mostrarEstadoDeColas() {
        System.out.println("\nEstado de colas:");
        for (int i = 0; i < ciudad.getFilas(); i++) {
            for (int j = 0; j < ciudad.getColumnas(); j++) {
                var interseccion = ciudad.getInterseccion(j, i);
                if (interseccion != null) {
                    if (interseccion.getCola() != null) {
                        System.out.print(interseccion.getCola().getTam());
                    } else {
                        Utilidad.printCharColor(interseccion.getSimbolo());
                    }
                    System.out.print(" ");
                }
            }
            System.out.println(" ");
        }
    }
}