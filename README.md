# Projet Final Android

Voici une application mobile responsive permettant de consulter, filtrer, scanner et gérer des produits en toute simplicité.  


## Fonctionnalités principales

### Accueil : Liste des produits
- Affichage des articles sous forme de liste défilante (RecyclerView)
- Recherche dynamique via un champ de saisie
- Filtrage par **catégorie** (électronique, vêtements, bijoux...) avec noms traduits
- Icône filtre et interface conviviale

### Détail produit
- Image, titre, description, prix
- Affichage de la **note moyenne** et du **nombre d’avis**
- Bouton **"Ajouter au panier"**
- Icône **favori (cœur)** permettant d’ajouter/retirer un article de la liste des favoris

### Favoris
- Affichage de tous les produits favoris dans une page dédiée
- Retrait possible depuis la page ou la fiche produit
- Icône cœur dynamique (vide ou rouge)

### Panier
- Liste des articles ajoutés
- Affichage du **prix total**
- Suppression individuelle avec icône **poubelle**
- Bouton pour **vider entièrement le panier**

### Scanner QR Code
- Accès à un produit directement via son **identifiant** contenu dans un QR Code
- Intégration avec la bibliothèque ZXing
- Feedback utilisateur si scan invalide ou annulé



## Expérience utilisateur

- Interface fluide, minimaliste et responsive
- Feedback utilisateur : Toasts et icônes dynamiques
- Navigation centralisée via le **menu de l’action bar**
- Icône de l'application personnalisée
- Titre spécifique par page pour une meilleure orientation



## Données et API

Les données produits proviennent de l’API publique :

> [FakeStoreAPI](https://fakestoreapi.com/)

Chaque produit inclut :
- ID
- Titre, prix, description
- Catégorie
- URL d’image
- Note (rate + count)



## Fonctionnalités bonus intégrées

| Fonction                                  |
|-------------------------------------------|
| Liste de favoris                          |
| Filtrage par catégorie                    |
| Note moyenne et nombre d’avis visibles    |
| Suppression individuelle dans le panier   |




## Technologies

- **Langage** : Kotlin
- **Architecture** : MVVM léger avec appels API via coroutines
- **Outils** : Retrofit, Glide, ZXing, Material Components



## Auteur

Projet réalisé par **ABDI Amelle** – Étudiante à l’**EPF - École d'ingénieurs** – Promo **2026**

Dans le cadre du module **Matériels Mobiles**.
