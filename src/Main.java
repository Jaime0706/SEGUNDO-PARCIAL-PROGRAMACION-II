import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {


    private static ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
    private static HashSet<String> placasRegistradas = new HashSet<>();
    private static HashMap<String, Double> recaudacionPorTipo = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;


        recaudacionPorTipo.put("Automóvil", 0.0);
        recaudacionPorTipo.put("Motocicleta", 0.0);

        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        registrarVehiculo();
                        break;
                    case 2:
                        mostrarVehiculos();
                        break;
                    case 3:
                        buscarPorPlaca();
                        break;
                    case 4:
                        mostrarVehiculoMayorCosto();
                        break;
                    case 5:
                        mostrarTotalGeneral();
                        break;
                    case 6:
                        mostrarTotalPorTipo();
                        break;
                    case 7:
                        System.out.println("\nSaliendo del sistema... ¡Gracias por utilizar la aplicación!");
                        break;
                    default:
                        System.out.println("\n[ERROR] Opción no válida. Ingrese un número del 1 al 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n[EXCEPCIÓN] Entrada inválida. Debe ingresar únicamente un número entero.");
                scanner.nextLine(); // Limpia el valor erróneo para evitar un bucle infinito
            } finally {
                System.out.println("--------------------------------------------------");
                System.out.println("[SISTEMA] Operación procesada correctamente.");
                System.out.println("--------------------------------------------------");
            }

        } while (opcion != 7);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==================================================");
        System.out.println("   SISTEMA DE ESTACIONAMIENTO - EXAMEN PARCIAL II ");
        System.out.println("==================================================");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Mostrar todos los vehículos registrados");
        System.out.println("3. Buscar un vehículo por placa");
        System.out.println("4. Mostrar el vehículo que generó el mayor costo");
        System.out.println("5. Mostrar el total general recaudado");
        System.out.println("6. Mostrar el total recaudado por tipo de vehículo");
        System.out.println("7. Salir");
        System.out.println("==================================================");
    }

    private static void registrarVehiculo() {
        System.out.println("\n--- REGISTRO DE VEHÍCULO ---");

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        if (placa.isEmpty()) {
            System.out.println("[ERROR] La placa no puede estar vacía.");
            return;
        }

        if (placasRegistradas.contains(placa)) {
            System.out.println("[ERROR] La placa " + placa + " ya se encuentra registrada en el sistema.");
            return;
        }

        System.out.print("Ingrese el nombre del propietario: ");
        String propietario = scanner.nextLine().trim();
        if (propietario.isEmpty()) {
            System.out.println("[ERROR] El nombre del propietario no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese la hora de ingreso (ejemplo: 08:30): ");
        String horaIngreso = scanner.nextLine().trim();
        if (horaIngreso.isEmpty()) {
            horaIngreso = "00:00";
        }

        double horas = 0;
        try {
            System.out.print("Ingrese la cantidad de horas utilizadas: ");
            horas = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer

            if (horas <= 0) {
                System.out.println("[ERROR] Las horas utilizadas deben ser estrictamente mayores que cero.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("[EXCEPCIÓN] Debe ingresar un número válido para las horas utilizadas.");
            scanner.nextLine(); // Limpiar entrada errónea
            return;
        }

        System.out.println("Tipo de vehículo:");
        System.out.println("1. Automóvil");
        System.out.println("2. Motocicleta");
        System.out.print("Seleccione una opción (1-2): ");
        int tipo = 0;

        try {
            tipo = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("[EXCEPCIÓN] Tipo de vehículo no válido.");
            scanner.nextLine();
            return;
        }

        Vehiculo nuevoVehiculo = null;

        if (tipo == 1) {
            nuevoVehiculo = new Automovil(placa, propietario, horaIngreso, horas);
        } else if (tipo == 2) {
            nuevoVehiculo = new Motocicleta(placa, propietario, horaIngreso, horas);
        } else {
            System.out.println("[ERROR] Tipo de vehículo inválido.");
            return;
        }

        listaVehiculos.add(nuevoVehiculo);
        placasRegistradas.add(placa);

        recalcularTotales();

        System.out.println("\n[ÉXITO] Vehículo registrado correctamente.");
    }

    private static void recalcularTotales() {
        double totalAuto = 0.0;
        double totalMoto = 0.0;

        for (Vehiculo v : listaVehiculos) {
            if (v instanceof Automovil) {
                totalAuto += v.calcularCosto();
            } else if (v instanceof Motocicleta) {
                totalMoto += v.calcularCosto();
            }
        }

        recaudacionPorTipo.put("Automóvil", totalAuto);
        recaudacionPorTipo.put("Motocicleta", totalMoto);
    }

    private static void mostrarVehiculos() {
        System.out.println("\n--- LISTADO DE VEHÍCULOS REGISTRADOS ---");
        if (listaVehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados en el sistema.");
            return;
        }

        for (Vehiculo v : listaVehiculos) {
            v.mostrarInformacion();
        }
    }


    private static void buscarPorPlaca() {
        System.out.println("\n--- BUSCAR VEHÍCULO POR PLACA ---");
        System.out.print("Ingrese la placa a buscar: ");
        String placa = scanner.nextLine().trim().toUpperCase();

        boolean encontrado = false;
        for (Vehiculo v : listaVehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.println("\n[RESULTADO] Vehículo encontrado:");
                v.mostrarInformacion();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("[INFO] No se encontró ningún vehículo registrado con la placa: " + placa);
        }
    }


    private static void mostrarVehiculoMayorCosto() {
        System.out.println("\n--- VEHÍCULO CON MAYOR COSTO GENERADO ---");
        if (listaVehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados en el sistema.");
            return;
        }

        Vehiculo mayor = listaVehiculos.get(0);
        for (Vehiculo v : listaVehiculos) {
            if (v.calcularCosto() > mayor.calcularCosto()) {
                mayor = v;
            }
        }

        System.out.println("El vehículo con el costo más alto registrado es:");
        mayor.mostrarInformacion();
    }


    private static void mostrarTotalGeneral() {
        System.out.println("\n--- TOTAL GENERAL RECAUDADO ---");
        double total = 0.0;
        for (Vehiculo v : listaVehiculos) {
            total += v.calcularCosto();
        }
        System.out.printf("Monto Total Recaudado: Q%.2f%n", total);
    }


    private static void mostrarTotalPorTipo() {
        System.out.println("\n--- TOTAL RECAUDADO POR TIPO DE VEHÍCULO ---");
        for (Map.Entry<String, Double> entry : recaudacionPorTipo.entrySet()) {
            System.out.printf("%-12s: Q%.2f%n", entry.getKey(), entry.getValue());
        }
    }
}
