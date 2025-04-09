# Microservice de Gestion des Réclamations - Hotelna

## 📋 Description
Module intelligent de gestion des réclamations pour établissements hôteliers intégrant :  
✅ Génération automatisée de solutions par IA (Mistral AI)  
✅ Workflow de suivi multi-états (Ouvert/En cours/Résolu/Fermé)  
✅ Interface administrateur/visiteur  

Ce microservice Spring Boot fait partie d'une architecture distribuée comprenant :  
- Service Discovery (Eureka Server)  
- API Gateway (Spring Cloud Gateway)  
- Autres microservices (Utilisateurs, Réservations...)

---

## 🛠️ Technologies

- **Java 17**
- **Spring Boot 3** (+ Web, Data JPA, Validation)
- **Spring Cloud** (Eureka, Gateway)
- **Mistral AI** (Génération de solutions)
- **MySQL** (Base de données)
- **Swagger/OpenAPI 3** (Documentation API)
- **Lombok** (Optimisation du code)
- **ModelMapper** (Mapping DTO/Entités)

---

## 🚀 Fonctionnalités Clés

- **Workflow complet des réclamations**  
  ○ Création par les visiteurs  
  ○ Mise à jour du statut par les admins  
  ○ Historique des modifications

- **IA Intégrée**  
  ○ Génération de solutions contextuelles  
  ○ Proposition de compensations automatisées  
  ○ Analyse sémantique des descriptions

- **Filtres avancés**  
  ○ Recherche par statut/catégorie  
  ○ Filtrage par utilisateur  
  ○ Tri chronologique

- **Sécurité**  
  ○ Validation des données en entrée  
  ○ Gestion centralisée des erreurs  
  ○ Intégration avec le service d'authentification

---

## 🔧 Installation

### 1. Clonage du dépôt
```bash
git clone https://github.com/youssefsayari/hotelna.git
cd complaint-service ```

