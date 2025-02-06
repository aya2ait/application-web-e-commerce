package ma.fstt.Entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Panier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PANIER") // Spécifiez le nom correct de la colonne dans la base de données

    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)  // Clé étrangère vers l'entité Client
    private Client client;  // Client associé au panier

    @ElementCollection
    @CollectionTable(name = "panier_produits", joinColumns = @JoinColumn(name = "id_panier"))
    @MapKeyJoinColumn(name = "id_produit")
    @Column(name = "quantite")
    private Map<Produit, Integer> produits = new HashMap<>(); // Initialisation de la Map pour éviter les NullPointerException
    @Temporal(TemporalType.TIMESTAMP)  // Spécifie que c'est une date avec l'heure
    @Column(name = "date_creation", nullable = false)
    private Date dateCreation;  // Date de création du panier
    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Map<Produit, Integer> getProduits() {
        return produits;
    }

    public void setProduits(Map<Produit, Integer> produits) {
        this.produits = produits;
    }

    public void ajouterProduit(Produit produit) {
        // Assurez-vous que la map est initialisée
        if (produits == null) {
            produits = new HashMap<>();
        }

        if (produits.containsKey(produit)) {
            produits.put(produit, produits.get(produit) + 1); // Incrémente la quantité
        } else {
            produits.put(produit, 1); // Ajoute le produit avec une quantité de 1
        }
    }

    public void supprimerProduit(Produit produit) {
        if (produits != null) {
            produits.remove(produit); // Supprime le produit
        }
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
