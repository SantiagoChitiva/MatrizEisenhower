import java.util.ArrayList;
import java.util.Scanner;

class Tarea {
    String descripcion;
    boolean esUrgente;
    boolean esImportante;

    public Tarea(String descripcion, boolean esUrgente, boolean esImportante) {
        this.descripcion = descripcion;
        this.esUrgente = esUrgente;
        this.esImportante = esImportante;
    }

    @Override
    public String toString() {
        return descripcion + " (Urgente: " + esUrgente + ", Importante: " + esImportante + ")";
    }
}

public class MatrizEisenhower {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Tarea> cuadrante1 = new ArrayList<>();
    private static ArrayList<Tarea> cuadrante2 = new ArrayList<>();
    private static ArrayList<Tarea> cuadrante3 = new ArrayList<>();
    private static ArrayList<Tarea> cuadrante4 = new ArrayList<>();

    private static String obtenerEntrada(String texto) {
        System.out.print(texto);
        return scanner.nextLine();
    }

    private static boolean obtenerRespuestaSiNo(String texto) {
        while (true) {
            System.out.print(texto + " (si/no): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("si")) {
                return true;
            } else if (respuesta.equals("no")) {
                return false;
            } else {
                System.out.println("Respuesta no válida. Por favor, ingrese 'si' o 'no'.");
            }
        }
    }

    private static void agregarTarea() {
        String descripcion = obtenerEntrada("Ingrese la descripción de la tarea: ");
        boolean esUrgente = obtenerRespuestaSiNo("¿Es urgente?");
        boolean esImportante = obtenerRespuestaSiNo("¿Es importante?");
        
        Tarea nuevaTarea = new Tarea(descripcion, esUrgente, esImportante);
        
        if (esUrgente && esImportante) {
            cuadrante1.add(nuevaTarea);
        } else if (!esUrgente && esImportante) {
            cuadrante2.add(nuevaTarea);
        } else if (esUrgente && !esImportante) {
            cuadrante3.add(nuevaTarea);
        } else {
            cuadrante4.add(nuevaTarea);
        }

        System.out.println("Tarea agregada exitosamente.");
    }

    private static void eliminarTarea() {
        mostrarCuadrantes();
        int cuadrante = obtenerEntradaEntero("Seleccione el número del cuadrante de la tarea que desea eliminar (1-4): ");
        scanner.nextLine(); 
        ArrayList<Tarea> listaTareas = obtenerCuadrante(cuadrante);

        if (listaTareas.isEmpty()) {
            System.out.println("No hay tareas en este cuadrante.");
            return;
        }

        int indice = obtenerEntradaEntero("Ingrese el número de la tarea que desea eliminar: ") - 1;
        scanner.nextLine(); 
        if (indice >= 0 && indice < listaTareas.size()) {
            listaTareas.remove(indice);
            System.out.println("Tarea eliminada exitosamente.");
        } else {
            System.out.println("Número de tarea no válido.");
        }
    }

    private static void editarTarea() {
        mostrarCuadrantes();
        int cuadrante = obtenerEntradaEntero("Seleccione el número del cuadrante de la tarea que desea editar (1-4): ");
        scanner.nextLine(); 
        ArrayList<Tarea> listaTareas = obtenerCuadrante(cuadrante);

        if (listaTareas.isEmpty()) {
            System.out.println("No hay tareas en este cuadrante.");
            return;
        }

        int indice = obtenerEntradaEntero("Ingrese el número de la tarea que desea editar: ") - 1;
        scanner.nextLine();
        if (indice >= 0 && indice < listaTareas.size()) {
            Tarea tarea = listaTareas.get(indice);

            System.out.println("Editando tarea: " + tarea.descripcion);
            String nuevaDescripcion = obtenerEntrada("Ingrese la nueva descripción (o presione Enter para dejarla igual): ");
            if (!nuevaDescripcion.trim().isEmpty()) {
                tarea.descripcion = nuevaDescripcion;
            }

            boolean nuevaUrgencia = obtenerRespuestaSiNo("¿Es urgente?");
            tarea.esUrgente = nuevaUrgencia;

            boolean nuevaImportancia = obtenerRespuestaSiNo("¿Es importante?");
            tarea.esImportante = nuevaImportancia;

            listaTareas.remove(indice);
            reubicarTarea(tarea);

            System.out.println("Tarea editada exitosamente.");
        } else {
            System.out.println("Número de tarea no válido.");
        }
    }

    private static void reubicarTarea(Tarea tarea) {
        if (tarea.esUrgente && tarea.esImportante) {
            cuadrante1.add(tarea);
        } else if (!tarea.esUrgente && tarea.esImportante) {
            cuadrante2.add(tarea);
        } else if (tarea.esUrgente && !tarea.esImportante) {
            cuadrante3.add(tarea);
        } else {
            cuadrante4.add(tarea);
        }
    }

    private static void mostrarCuadrantes() {
        System.out.println("\n=== Matriz de Eisenhower ===");

        System.out.printf("+-------------------------------+-------------------------------+\n");
        System.out.printf("|         Urgente e Importante   |    Importante pero no Urgente |\n");
        System.out.printf("+-------------------------------+-------------------------------+\n");
        imprimirCuadrantes(cuadrante1, cuadrante2);
        System.out.printf("+-------------------------------+-------------------------------+\n");
        System.out.printf("|  Urgente pero no Importante    |   No Urgente ni Importante    |\n");
        System.out.printf("+-------------------------------+-------------------------------+\n");
        imprimirCuadrantes(cuadrante3, cuadrante4);
        System.out.printf("+-------------------------------+-------------------------------+\n");
    }

    private static void imprimirCuadrantes(ArrayList<Tarea> cuadranteA, ArrayList<Tarea> cuadranteB) {
        int maxTareas = Math.max(cuadranteA.size(), cuadranteB.size());
        for (int i = 0; i < maxTareas; i++) {
            String tareaA = (i < cuadranteA.size()) ? "- " + cuadranteA.get(i).descripcion : "";
            String tareaB = (i < cuadranteB.size()) ? "- " + cuadranteB.get(i).descripcion : "";

            System.out.printf("| %-29s | %-29s |\n", tareaA, tareaB);
        }
    }

    private static int obtenerEntradaEntero(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, ingrese un número entero válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static ArrayList<Tarea> obtenerCuadrante(int numeroCuadrante) {
        switch (numeroCuadrante) {
            case 1:
                return cuadrante1;
            case 2:
                return cuadrante2;
            case 3:
                return cuadrante3;
            case 4:
                return cuadrante4;
            default:
                System.out.println("Cuadrante no válido. Seleccionando el Cuadrante 1 por defecto.");
                return cuadrante1;
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Agregar nueva tarea");
            System.out.println("2. Editar tarea");
            System.out.println("3. Eliminar tarea");
            System.out.println("4. Ver matriz de Eisenhower");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    agregarTarea();
                    break;
                case "2":
                    editarTarea();
                    break;
                case "3":
                    eliminarTarea();
                    break;
                case "4":
                    mostrarCuadrantes();
                    break;
                case "5":
                    System.out.println("Saliendo del programa.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción correcta.");
            }
        }
    }
}
