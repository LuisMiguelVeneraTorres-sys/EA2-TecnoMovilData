import java.time.LocalDateTime;
import java.util.Objects;

public class Registro {

    private final String idUsuario;
    private final String ruta;
    private final String estacion;
    private final String accion;
    private final LocalDateTime timestamp;

    public Registro(String idUsuario, String ruta,
                    String estacion, String accion,
                    LocalDateTime timestamp) {

        this.idUsuario = Objects.requireNonNull(idUsuario);
        this.ruta = Objects.requireNonNull(ruta);
        this.estacion = Objects.requireNonNull(estacion);
        this.accion = Objects.requireNonNull(accion);
        this.timestamp = Objects.requireNonNull(timestamp);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getRuta() {
        return ruta;
    }

    public String getEstacion() {
        return estacion;
    }

    public String getAccion() {
        return accion;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}