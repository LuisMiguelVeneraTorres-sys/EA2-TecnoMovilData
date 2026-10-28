import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcesadorDatos {

    // Cuenta las entradas registradas en cada estación.
    public static Map<String, Long> afluenciaPorEstacion(
            List<Registro> registros) {

        Map<String, Long> resultado = registros.stream()
                .filter(r -> r.getAccion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        Registro::getEstacion,
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Cuenta las entradas por hora.
    public static Map<Integer, Long> horasPico(List<Registro> registros) {
        Map<Integer, Long> resultado = registros.stream()
                .filter(r -> r.getAccion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        r -> r.getTimestamp().getHour(),
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Cuenta las entradas por ruta.
    public static Map<String, Long> rutasMasUtilizadas(
            List<Registro> registros) {

        Map<String, Long> resultado = registros.stream()
                .filter(r -> r.getAccion().equalsIgnoreCase("entrada"))
                .collect(Collectors.groupingBy(
                        Registro::getRuta,
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Ordena las estaciones visitadas por cada usuario.
    public static Map<String, List<String>> patronesDeViaje(
            List<Registro> registros) {

        Map<String, List<String>> resultado = registros.stream()
                .collect(Collectors.groupingBy(
                        Registro::getIdUsuario,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                lista -> {
                                    List<String> estaciones = lista.stream()
                                            .sorted((a, b) ->
                                                    a.getTimestamp().compareTo(
                                                            b.getTimestamp()))
                                            .map(Registro::getEstacion)
                                            .toList();

                                    return IntStream.range(0, estaciones.size())
                                            .filter(i -> i == 0
                                                    || !estaciones.get(i).equals(
                                                            estaciones.get(i - 1)))
                                            .mapToObj(estaciones::get)
                                            .toList();
                                }
                        )
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Calcula el promedio de tiempo entre cambios de estación.
    public static double tiempoPromedioEntreEstaciones(
            List<Registro> registros) {

        return registros.stream()
                .collect(Collectors.groupingBy(Registro::getIdUsuario))
                .values()
                .stream()
                .flatMap(lista -> {
                    List<Registro> ordenados = lista.stream()
                            .sorted((a, b) ->
                                    a.getTimestamp().compareTo(b.getTimestamp()))
                            .toList();

                    return IntStream.range(1, ordenados.size())
                            .filter(i -> !ordenados.get(i).getEstacion()
                                    .equals(ordenados.get(i - 1).getEstacion()))
                            .mapToObj(i -> Duration.between(
                                    ordenados.get(i - 1).getTimestamp(),
                                    ordenados.get(i).getTimestamp()
                            ).getSeconds());
                })
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
    }

    // Marca como crítica una ruta que supere el umbral.
    public static Map<String, Boolean> detectarRutasCriticas(
            List<Registro> registros, long umbral) {

        Map<String, Boolean> resultado = rutasMasUtilizadas(registros)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entrada -> entrada.getValue() > umbral,
                        (anterior, nuevo) -> anterior,
                        TreeMap::new
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Cuenta los viajes de cada usuario.
    public static Map<String, Long> viajesPorUsuario(List<Viaje> viajes) {
        Map<String, Long> resultado = viajes.stream()
                .collect(Collectors.groupingBy(
                        Viaje::getIdUsuario,
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Cuenta los viajes que salen de cada estación.
    public static Map<String, Long> estacionesDeSubida(List<Viaje> viajes) {
        Map<String, Long> resultado = viajes.stream()
                .collect(Collectors.groupingBy(
                        Viaje::getEstacionSubida,
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Cuenta los viajes que terminan en cada estación.
    public static Map<String, Long> estacionesDeBajada(List<Viaje> viajes) {
        Map<String, Long> resultado = viajes.stream()
                .collect(Collectors.groupingBy(
                        Viaje::getEstacionBajada,
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Cuenta los viajes por ruta.
    public static Map<String, Long> viajesPorRuta(List<Viaje> viajes) {
        Map<String, Long> resultado = viajes.stream()
                .collect(Collectors.groupingBy(
                        Viaje::getRuta,
                        TreeMap::new,
                        Collectors.counting()
                ));

        return Collections.unmodifiableMap(resultado);
    }

    // Agrupa los viajes por fecha.
    public static Map<LocalDate, List<Viaje>> viajesPorFecha(
            List<Viaje> viajes) {

        Map<LocalDate, List<Viaje>> resultado = viajes.stream()
                .collect(Collectors.groupingBy(
                        Viaje::getFecha,
                        TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                List::copyOf
                        )
                ));

        return Collections.unmodifiableMap(resultado);
    }
}
