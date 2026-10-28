import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class TecnoMovilData {

    public static void main(String[] args) {
        List<Registro> registros = SimuladorDatos.generarRegistros();
        List<Viaje> viajes = SimuladorDatos.generarViajes();

        System.out.println("====================================");
        System.out.println("          TECNOMOVIL DATA");
        System.out.println("   Procesamiento funcional en Java");
        System.out.println("====================================");

        System.out.println("Registros simulados: " + registros.size());

        System.out.println("\n1. AFLUENCIA POR ESTACION");
        ProcesadorDatos.afluenciaPorEstacion(registros)
                .forEach((estacion, cantidad) ->
                        System.out.println(estacion + ": " + cantidad));

        System.out.println("\n2. ENTRADAS POR HORA");
        Map<Integer, Long> horas = ProcesadorDatos.horasPico(registros);

        horas.forEach((hora, cantidad) ->
                System.out.println(hora + ":00 - " + cantidad + " entradas"));

        horas.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(pico -> System.out.println(
                        "Hora pico: " + pico.getKey()
                        + ":00 con " + pico.getValue() + " entradas"));

        System.out.println("\n3. USO DE RUTAS");
        ProcesadorDatos.rutasMasUtilizadas(registros)
                .forEach((ruta, cantidad) ->
                        System.out.println(ruta + ": " + cantidad + " entradas"));

        System.out.println("\n4. PATRONES DE VIAJE");
        ProcesadorDatos.patronesDeViaje(registros)
                .forEach((usuario, estaciones) ->
                        System.out.println(usuario + ": " + estaciones));

        System.out.println("\n5. TIEMPO PROMEDIO ENTRE ESTACIONES");
        System.out.printf("Promedio: %.2f segundos%n",
                ProcesadorDatos.tiempoPromedioEntreEstaciones(registros));

        System.out.println("\n6. DETECCION DE RUTAS CRITICAS");
        ProcesadorDatos.detectarRutasCriticas(registros, 17)
                .forEach((ruta, critica) ->
                        System.out.println(ruta + ": "
                                + (critica ? "CRITICA" : "Normal")));

        System.out.println("\n7. REGISTRO COMPLETO DE VIAJES");
        System.out.println("Total de viajes: " + viajes.size());

        viajes.forEach(System.out::println);

        System.out.println("\n8. VIAJES POR USUARIO");
        ProcesadorDatos.viajesPorUsuario(viajes)
                .forEach((usuario, cantidad) ->
                        System.out.println(usuario + ": " + cantidad + " viaje(s)"));

        System.out.println("\n9. ESTACIONES DE SUBIDA");
        ProcesadorDatos.estacionesDeSubida(viajes)
                .forEach((estacion, cantidad) ->
                        System.out.println(estacion + ": " + cantidad));

        System.out.println("\n10. ESTACIONES DE BAJADA");
        ProcesadorDatos.estacionesDeBajada(viajes)
                .forEach((estacion, cantidad) ->
                        System.out.println(estacion + ": " + cantidad));

        System.out.println("\n11. VIAJES POR RUTA");
        ProcesadorDatos.viajesPorRuta(viajes)
                .forEach((ruta, cantidad) ->
                        System.out.println(ruta + ": " + cantidad));

        System.out.println("\n12. VIAJES POR FECHA");
        ProcesadorDatos.viajesPorFecha(viajes)
                .forEach((fecha, lista) ->
                        System.out.println(fecha + ": " + lista.size() + " viajes"));

        System.out.println("\n====================================");
        System.out.println("       FIN DEL PROCESAMIENTO");
        System.out.println("====================================");
    }
}