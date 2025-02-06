package ma.fstt.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import ma.fstt.Entities.Produit;
import ma.fstt.Entities.Vitrine;

import java.util.ArrayList;
import java.util.List;
@Named
@RequestScoped
@Data
public class ProduitBean {

    // Attributs pour les formulaires JSF
    private String nomProduit;
    private Double prix;
    private String description;
    private Integer quantiteStock;

    // Ajout d'une propriété pour la vitrine
    private Vitrine vitrine;

    // Liste des vitrines pour le selectOneMenu dans JSF
    private List<Vitrine> vitrines;

    // Gestion de l'EntityManager
    private EntityManagerFactory emf;
    private EntityManager em;

    // Constructeur : Initialisation de l'EntityManager et chargement des vitrines
    public ProduitBean() {
        emf = Persistence.createEntityManagerFactory("default"); // Nom du persistence-unit
        em = emf.createEntityManager();
        loadVitrines(); // Charger la liste des vitrines
    }

    // Charger la liste des vitrines disponibles
    public void loadVitrines() {
        vitrines = em.createQuery("SELECT v FROM Vitrine v", Vitrine.class).getResultList();
    }
    public Vitrine findVitrineById(Long idVitrine) {
        return em.find(Vitrine.class, idVitrine);
    }

    // Méthode pour sauvegarder un nouveau produit
    public void save() {
        try {
            // Créer une nouvelle instance de Produit
            Produit produit = new Produit();
            produit.setNom_produit(nomProduit);
            produit.setPrix(prix);
            produit.setDescription(description);
            produit.setQuantite_stock(quantiteStock);
            produit.setVitrine(vitrine); // Associer la vitrine sélectionnée au produit

            // Transaction pour persister le produit
            em.getTransaction().begin();
            em.persist(produit);
            em.getTransaction().commit();

            // Reset des champs pour éviter des doublons dans le formulaire
            resetForm();

            // Navigation vers la page de liste
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
                    FacesContext.getCurrentInstance(), null, "listProduit.xhtml");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // Méthode pour lister tous les produits
    public List<Produit> lister() {
        return em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
    }

    // Méthode pour supprimer un produit
    public void supprimer(Long id) {
        try {
            Produit produit = em.find(Produit.class, id);
            if (produit != null) {
                em.getTransaction().begin();
                em.remove(produit);
                em.getTransaction().commit();
            } else {
                System.out.println("Produit non trouvé !");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    // Réinitialisation des champs après une opération
    private void resetForm() {
        nomProduit = null;
        prix = null;
        description = null;
        quantiteStock = null;
        vitrine = null; // Réinitialiser la vitrine sélectionnée
    }

    // Fermer l'EntityManager quand le Bean est détruit (bonne pratique)
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    // Getters et setters
    public Vitrine getVitrine() {
        return vitrine;
    }

    public void setVitrine(Vitrine vitrine) {
        this.vitrine = vitrine;
    }

    public List<Vitrine> getVitrines() {
        return vitrines;
    }

    public void setVitrines(List<Vitrine> vitrines) {
        this.vitrines = vitrines;
    }
}
