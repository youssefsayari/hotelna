
# Microservice de Hotelna

## 📋 Description
Notre application de gestion hôtelière offre une plateforme complète permettant une administration efficace de l’établissement.  
Ce projet est un microservice développé avec Spring Boot dans le cadre d’un système distribué.  
Il est dédié à la gestion des utilisateurs (CRUD, authentification, réinitialisation du mot de passe via OTP, etc.).

Il fait partie d’une architecture microservices avec :
- Un service Eureka (Service Discovery)
- Un API Gateway (Spring Cloud Gateway)
- D'autres microservices 

---

## 🛠️ Technologies utilisées

- **Java 17**
- **Spring Boot** (Framework principal)
- **Spring Data JPA** (Pour l'accès aux données)
- **Spring Cloud** (Eureka, Gateway)
- **MySQL** (Base de données)
- **Swagger / OpenAPI** (Documentation et test des API)
- **Lombok** (Réduction de code boilerplate)
- **Git** (Gestion de version)

---

## 🚀 Fonctionnalités

- **CRUD des utilisateurs** (ajout, mise à jour, suppression, consultation)
- **Authentification via login** (Email + mot de passe)
- **Réinitialisation du mot de passe par OTP** (Envoi de code par mail)
- **Recherche d’utilisateurs par nom**
- **Intégration Swagger pour tester les endpoints** via l'interface web.
- **Communication avec d'autres microservices** via Eureka et Gateway.

---

## 🔧 Installation

### 1. Cloner le projet
```bash
git clone https://github.com/youssefsayari/hotelna.git
```

### 2. Configurer la base de données MySQL

Dans `src/main/resources/application.properties`, configurez la connexion à votre base de données MySQL :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotelna_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

### 3. Lancer le projet

Assurez-vous que Maven est installé et exécutez la commande suivante pour démarrer le microservice :

```bash
mvn spring-boot:run
```

### 4. Accéder à Swagger

Une fois l'application lancée, accédez à Swagger pour tester les endpoints via :

```
http://localhost:8090/swagger-ui/index.html
```

---

## 📡 API Endpoints

| Méthode | Endpoint                        | Description                            |
|---------|----------------------------------|----------------------------------------|
| GET     | /user/getAllUsers               | Récupérer tous les utilisateurs        |
| GET     | /user/getUserById/{id}          | Récupérer un utilisateur par ID        |
| POST    | /user/addUser                   | Ajouter un utilisateur                 |
| PUT     | /user/updateUser                | Mettre à jour un utilisateur           |
| DELETE  | /user/deleteUser/{id}           | Supprimer un utilisateur               |
| POST    | /user/login                     | Authentifier un utilisateur            |
| POST    | /user/send-otp                  | Envoyer un OTP pour reset password     |
| POST    | /user/verify-otp                | Vérifier un OTP                        |
| POST    | /user/change-password           | Changer le mot de passe                |
| GET     | /user/search?name=xxxx          | Rechercher par nom                     |

---

## 🧰 Architecture du projet

Le microservice utilise **Spring Cloud Eureka** pour la découverte de services, et **Spring Cloud Gateway** comme passerelle d'API pour acheminer les demandes vers le service approprié.

**Architecture globale :**
- **Eureka Service** (Découverte de services)
- **API Gateway** (Point d'entrée unifié pour les microservices)
- **Microservice de gestion des utilisateurs**
- **Autres microservices** (Gestion des missions, congés, etc.)
  
---

## 📅 Roadmap

- ✅ **MVP** : Gestion des utilisateurs (CRUD)
- 🚧 **Fonctionnalités futures** :
  - Authentification via JWT (JSON Web Token)
  - Gestion de rôles utilisateurs (admin, user)
  - Intégration d'un service de messagerie pour les notifications
  - Optimisation des performances via cache (ex : Redis)

---

## 📈 Documentation Swagger / OpenAPI

Le projet est documenté avec Swagger.  
Vous pouvez accéder à la documentation des API via l'interface Swagger à l'adresse suivante une fois l'application lancée :

```
http://localhost:8090/swagger-ui/index.html
```

---

## ✅ Bonnes pratiques Git

- Commits réguliers avec messages explicites et clairs
- Utilisation de branches pour chaque fonctionnalité
- Structure du projet bien définie
- Mise à jour régulière du fichier README.md

---

## 🧑‍💻 Auteur

- **Nom** : Amenallah Kthiri
- **Email** : amenallah.kthiri@esprit.tn
- **Université / Institut** : Esprit
  
---

## 🔗 Liens utiles

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [Dépôt GitHub](https://github.com/youssefsayari/hotelna)
