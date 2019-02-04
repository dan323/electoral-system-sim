package jmcd.tfg.persistencia;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Votos {
    @ElementCollection
    @JoinTable(name = "BATCH_VOTOS",
            joinColumns = @JoinColumn(name = "ID"))
    @MapKey(name = "PARTIDO")
    @Column(name = "VOTOS")
    private Map<String, Integer> votos;
    private String nombre;
    private String usuario;
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Integer> getVotos() {
        return votos;
    }

    public void setVotos(Map<String, Integer> votos) {
        this.votos = votos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
