package ma.fstt.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "produit")
@Data
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produit;

    @Column(nullable = false)
    private String nom_produit;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Integer quantite_stock;
    // Clé étrangère vers la vitrine (relation @ManyToOne)
    @ManyToOne
    @JoinColumn(name = "id_vitrine")  // Colonne qui sera utilisée comme clé étrangère dans la table produit
    private Vitrine vitrine;

}

