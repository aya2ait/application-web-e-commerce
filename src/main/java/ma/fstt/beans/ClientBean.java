package ma.fstt.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import ma.fstt.Entities.Client;

@Named
@RequestScoped
@Data
public class ClientBean {

    // Attributs pour les formulaires JSF
    private String email;
    private String motDePasse;
    private String nom;


    // Gestion de l'EntityManager
    private EntityManagerFactory emf;
    private EntityManager em;

    public ClientBean() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    public void inscrire() {
        try {
            if (email == null || motDePasse == null || nom == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tous les champs sont obligatoires", null));
                return;
            }

            // Crée un nouvel objet Client avec les informations du formulaire
            Client client = new Client();
            client.setEmail(email);
            client.setMot_de_passe(motDePasse);
            client.setNom(nom);

            // Commence une transaction
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();

            // Réinitialise le formulaire
            resetForm();

            // Affiche un message de succès
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscription réussie", null));

            // Redirige vers la page de connexion après l'inscription
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(
                    FacesContext.getCurrentInstance(), null, "login.xhtml");
        }catch (Exception e) {
                // Si une exception se produit, rollback la transaction
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();  // Affiche l'exception dans la console
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'inscription", e.getMessage()));
            }

    }

    public String connecter() {
        try {
            Client client = em.createQuery("SELECT c FROM Client c WHERE c.email = :email AND c.motDePasse = :motDePasse", Client.class)
                    .setParameter("email", email)
                    .setParameter("motDePasse", motDePasse)
                    .getSingleResult();

            if (client != null) {
                // Ajouter le client à la session
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("client", client);

                // Vérifier si le client est un administrateur et rediriger en conséquence
                if ("admin".equals(client.getNom())) {
                    return "adminredirec.xhtml"; // Rediriger vers listProduit.xhtml si le client est admin
                } else {
                    return "listProduits.xhtml"; // Sinon, rediriger vers listProduits.xhtml
                }
            } else {
                // Supprimer le client de la session en cas d'échec de connexion
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("client");
                return "login.xhtml"; // Rediriger vers la page de connexion en cas d'échec
            }
        } catch (Exception e) {
            // Gérer l'exception si l'email ou le mot de passe est incorrect
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Identifiants incorrects"));
            return "login.xhtml"; // Retourner à la page de connexion en cas d'erreur
        }
    }

    // Réinitialisation des champs
    private void resetForm() {
        email = null;
        motDePasse = null;
        nom = null;

    }

    // Fermer l'EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
