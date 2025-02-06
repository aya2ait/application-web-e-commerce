package ma.fstt.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "client")
@Data

public class Client {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ID_CLIENT")
        private Long idClient;

        @Column(name = "EMAIL", nullable = false, unique = true)
        private String email;

        @Column(name = "MOT_DE_PASSE", nullable = false)
        private String motDePasse;

        @Column(name = "NOM", nullable = false)
        private String nom;
        public Long getIdClient() {
            return idClient;
        }
        public void setIdClient(Long idClient) {
            this.idClient = idClient;
        }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return motDePasse;
    }

    public void setMot_de_passe(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}


