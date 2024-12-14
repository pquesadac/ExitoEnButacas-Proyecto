import java.util.Objects;

/**
 * Clase Usuario
 * Representa a un usuario dentro de un sistema, con un identificador único y un nombre.
 */
public class Usuario {
    private int id; // Identificador único del usuario
    private String nombre; // Nombre del usuario.

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Son iguales si es la misma instancia.
        if (o == null || getClass() != o.getClass()) return false; // No son iguales si son de clases diferentes o si es nulo.
        Usuario usuario = (Usuario) o;
        return id == usuario.id; // La comparación se basa únicamente en el ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // El hashCode también se basa únicamente en el ID
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}


