# application-web-e-commerce
Développement d'une  application web e-commerce avec JSF et JPA 

Application Web E-Commerce avec JSF et JPA
Description
Ce projet est une application web d'e-commerce développée à l'aide de JSF (JavaServer Faces) pour l'interface utilisateur et JPA (Java Persistence API) avec EclipseLink pour la gestion de la persistance des données. L'objectif principal est de permettre la gestion des utilisateurs, des produits, des vitrines, et des commandes dans un environnement de type e-commerce.

L'application est construite avec Maven pour la gestion des dépendances, déployée sur WildFly et utilise MySQL comme base de données.

Fonctionnalités
Côté Client
Inscription : Inscription d'un utilisateur avec une adresse e-mail unique.
Connexion : Accès à l'application pour les utilisateurs inscrits avec un mot de passe sécurisé.
Gestion du Panier : Les utilisateurs peuvent ajouter des produits à leur panier. Si un produit est déjà présent, sa quantité est incrémentée.
Validation des Commandes : Les utilisateurs peuvent valider leurs commandes, associées à la date et l'heure de validation.
Côté Administrateur
Gestion des Produits : Les administrateurs peuvent ajouter, modifier ou supprimer des produits.
Gestion des Vitrines : Les administrateurs peuvent gérer les vitrines et associer des produits.
Consultation des Commandes : Consultation des commandes passées, mais sans possibilité de modification.
Prérequis
Avant de démarrer le projet, assurez-vous d'avoir les éléments suivants installés :

JDK 8 ou supérieur
Maven 3.6+
WildFly (ou autre serveur d'applications compatible Java EE)
MySQL (ou autre base de données compatible JPA)
IDE : IntelliJ IDEA ou tout autre IDE compatible Java
