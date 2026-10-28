import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Viaje {
    private final String idUsuario;
    private final String ruta;
    private final String estacionSubida;
    private final String estacionBajada;
    private final LocalDateTime horaSubida;
    private final LocalDateTime horaBajada;

    public Viaje(String idUsuario, String ruta,
                 String estacionSubida, String estacionBajada,
                 LocalDateTime horaSubida, LocalDateTime horaBajada) {

        this.idUsuario = Objects.requireNonNull(idUsuario);
        this.ruta = Objects.requireNonNull(ruta);
        this.estacionSubida = Objects.requireNonNull(estacionSubida);
        this.estacionBajada = Objects.requireNonNull(estacionBajada);
        this.horaSubida = Objects.requireNonNull(horaSubida);
        this.horaBajada = Objects.requireNonNull(horaBajada);

        if (horaBajada.isBefore(horaSubida)) {
            throw new IllegalArgumentException(
                    "La hora de bajada no puede ser anterior a la subida.");
        }
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getRuta() {
        return ruta;
    }

    public String getEstacionSubida() {
        return estacionSubida;
    }

    public String getEstacionBajada() {
        return estacionBajada;
    }

    public LocalDate getFecha() {
        return horaSubida.toLocalDate();
    }

    public LocalDateTime getHoraSubida() {
        return horaSubida;
    }

    public LocalDateTime getHoraBajada() {
        return horaBajada;
    }

    public long getDuracionMinutos() {
        return Duration.between(horaSubida, horaBajada).toMinutes();
    }

    @Override
    public String toString() {
        return "Usuario: " + idUsuario
                + " | Fecha: " + getFecha()
                + " | Subida: " + estacionSubida + " (" + horaSubida.toLocalTime() + ")"
                + " | Bajada: " + estacionBajada + " (" + horaBajada.toLocalTime() + ")"
                + " | Ruta: " + ruta
                + " | Duracion: " + getDuracionMinutos() + " minutos";
    }
}