package ma.fstt.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "vitrine")
@Data
public class Vitrine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vitrine;
    @Column(nullable = false)
    private String nom_vitrine;

    @OneToMany
    @JoinColumn(name = "id_vitrine")
    private List<Produit> produits;
}
