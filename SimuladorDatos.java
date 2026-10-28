import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimuladorDatos {

    private static final String[] ESTACIONES = {
        "Centro", "Norte", "Sur", "Terminal"
    };

    private static final String[] RUTAS = {
        "R1", "R2", "R3"
    };

    public static List<Registro> generarRegistros() {
        return IntStream.rangeClosed(1, 25)
                .boxed()
                .flatMap(i -> {
                    String usuario = String.format("U%02d", i);
                    String ruta = RUTAS[(i - 1) % RUTAS.length];
                    String estacion1 = ESTACIONES[(i - 1) % ESTACIONES.length];
                    String estacion2 = ESTACIONES[i % ESTACIONES.length];
                    String estacion3 = ESTACIONES[(i + 1) % ESTACIONES.length];

                    LocalDateTime inicio = LocalDateTime
                            .of(2026, 10, 28, 6, 0)
                            .plusMinutes(i * 3L);

                    return Stream.of(
                            new Registro(usuario, ruta, estacion1,
                                    "entrada", inicio),
                            new Registro(usuario, ruta, estacion1,
                                    "salida", inicio.plusMinutes(10)),
                            new Registro(usuario, ruta, estacion2,
                                    "entrada", inicio.plusMinutes(25)),
                            new Registro(usuario, ruta, estacion3,
                                    "salida", inicio.plusMinutes(45))
                    );
                })
                .sorted(Comparator.comparing(Registro::getTimestamp))
                .toList();
    }

    public static List<Viaje> generarViajes() {
        return IntStream.rangeClosed(1, 25)
                .mapToObj(i -> {
                    String usuario = String.format("U%02d", i);
                    String ruta = RUTAS[(i - 1) % RUTAS.length];

                    String subida = ESTACIONES[(i - 1) % ESTACIONES.length];
                    String bajada = ESTACIONES[i % ESTACIONES.length];

                    LocalDateTime horaSubida = LocalDateTime
                            .of(2026, 10, 28, 6, 0)
                            .plusMinutes(i * 3L);

                    LocalDateTime horaBajada = horaSubida.plusMinutes(15);

                    return new Viaje(
                            usuario, ruta, subida, bajada,
                            horaSubida, horaBajada
                    );
                })
                .toList();
    }
}