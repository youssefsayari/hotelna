# Système de Gestion Hôtelière - Microservices Backend

## 📋 Description
Notre application est une solution complète de gestion hôtelière basée sur une architecture microservices. Elle permet d'administrer toutes les facettes d'un hôtel moderne : utilisateurs, blocs, chambres, restaurants, réclamations intelligentes, activités et spa.

## 🌟 Microservices

### 1. **Gestion Utilisateurs**
- **Technologies** : Spring Boot, Angular, MySQL
- **Fonctionnalités** :
  - CRUD complet des utilisateurs
  - Authentification sécurisée
  - Réinitialisation de mot de passe via OTP
  - Gestion des rôles et permissions

### 2. **Gestion des Blocs**
- **Technologies** : Spring Boot, Angular, H2
- **Fonctionnalités** :
  - Création/modification/suppression des blocs
  - Affectation des chambres aux blocs
  - Visualisation hiérarchique

### 3. **Gestion Restaurant**
- **Technologies** : Spring Boot, Angular
- **Fonctionnalités** :
  - CRUD des restaurants (admin)
  - Réservation de tables (clients)
  - Système d'avis et évaluations
  - Gestion des menus

### 4. **Gestion Chambres**
- **Technologies** : Spring Boot, Angular, H2
- **Fonctionnalités** :
  - CRUD des chambres (admin)
  - Réservation en ligne
  - Paiement intégré via Stripe
  - Gestion des disponibilités

### 5. **Gestion Réclamations**
- **Technologies** : Spring Boot, Angular, MySQL, IA
- **Fonctionnalités** :
  - Soumission de réclamations
  - Système intelligent de suggestions de solutions
  - Suivi des tickets
  - Analyse des tendances

### 6. **Gestion Activités**
- **Technologies** : Spring Boot, Angular, MySQL
- **Fonctionnalités** :
  - CRUD des activités (admin)
  - Calendrier des activités
  - Inscription des clients
  - Statistiques de participation
  - Visualisation des activités populaires

### 7. **Gestion Spa**
- **Technologies** : Flask (Python)
- **Fonctionnalités** :
  - Gestion des services spa
  - Réservation des soins
  - Gestion du personnel

---

## 🛠️ Architecture Technique

### Composants Principaux
- **Service Discovery** : Eureka Server
- **API Gateway** : Spring Cloud Gateway
- **Base de données** :
  - MySQL (Users, Réclamations, Activités)
  - H2 (Blocs, Chambres - développement)
- **Frontend** : Angular
- **IA** : Intégration pour les suggestions de réclamations
- **Paiement** : Stripe API

### Communication
- Synchronous : REST APIs
- Asynchronous : (Optionnel) Kafka/RabbitMQ pour certains événements

---

## 🔧 Installation & Configuration

### Prérequis
- Java 17+
- Maven 3.6+
- Python 3.8+ (pour le service Spa)
- MySQL 8+
- Node.js 16+ (pour le frontend Angular)

### 1. Cloner le dépôt
```bash
git clone https://github.com/votre-repo/hotel-management.git
cd hotel-management
