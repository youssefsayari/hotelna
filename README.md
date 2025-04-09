
# 📢 Microservice de Gestion de Réclamations

__Ce projet est un microservice géré principalement avec *Spring Boot* pour la gestion et le suivi des réclamations.__

Il permet à un utilisateur :
- ✅ Ajouter, modifier et supprimer des réclamations.
- 🔍 Consulter, modifier et supprimer des réclamations by utilisateur ou par statut.
- 🤖 Générer des suggestions de solutions par une intelligence artificielle basées sur la description de la réclamation.
- ✅ Récupérer et accepter une solution générée automatiquement.

---

## 🚀 Technologies utilisées

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Eureka (Service Discovery)
- Spring Cloud Gateway
- MySQL
- Swagger / OpenAPI 3
- Git
- Lombok
- Bean Validation (Jakarta)
- Mistral AI API (via `MistralAIService.java`)

---

## 🏗️ Structure du projet

```plaintext
Controller/
└── ComplaintRestController.java  # Point d'entrée REST
Entity/
└── Complaint, ComplaintStatus, ComplaintSolutionIA, ComplaintCategories
Service/
└── ComplaintService, ComplaintSolutionIAService
Util/
└── MistralAIService.java
```

---

## 🔧 Installation & Lancement

1. **Cloner le projet**
```bash
git clone https://github.com/ton-utilisateur/ton-projet.git
```

2. **Configurer votre base de données MySQL**
Créez une base de données nommée `complaints_db`, puis configurez `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/complaints_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Lancer le projet**
```bash
./mvnw spring-boot:run
```

---

## 📬 Principaux Endpoints REST

| Méthode | URL                                 | Description                              |
|---------|-------------------------------------|------------------------------------------|
| POST    | `/complaint/addComplaint`           | Ajouter une réclamation                  |
| GET     | `/complaint/getComplaintById/{id}`  | Récupérer une réclamation par ID         |
| GET     | `/complaint/getAllComplaints`       | Récupérer toutes les réclamations        |
| GET     | `/complaint/getComplaintsByUser/{userId}` | Réclamations d’un utilisateur     |
| GET     | `/complaint/getComplaintsByStatus/{status}` | Réclamations par statut           |
| PUT     | `/complaint/updateComplaint/{id}`   | Mettre à jour une réclamation            |
| DELETE  | `/complaint/deleteComplaint/{id}`   | Supprimer une réclamation                |

### 🔍 Endpoints IA

| Méthode | URL                                                       | Description                                 |
|---------|-----------------------------------------------------------|---------------------------------------------|
| POST    | `/complaint/generate-ai-solution?category=A&description=B` | Génère une solution IA                     |
| GET     | `/complaint/getSolutionByComplaint/{complaintId}`         | Obtenir la solution IA d'une réclamation    |
| POST    | `/complaint/acceptSolutionAndAffectToComplaint/{id}`      | Appliquer la solution à la réclamation      |

---

## 🧠 Fonctionnalité IA

Utilise `MistralAIService` pour générer des suggestions basées sur :
- La **catégorie** de la réclamation
- Une **description** textuelle

Exemple de réponse IA :
```json
{
  "status": "success",
  "solution": "Veuillez vérifier la configuration de votre compte avant de contacter le support."
}
```

---

## 🧪 Validation

Chaque champ d’entrée est validé via `@Valid` et `BindingResult`, pour :
- Empêcher les champs vides
- Assurer la cohérence métier
- Fournir des retours précis à l’utilisateur

---

## 🗑️ Suppression

La méthode `deleteComplaint` vérifie d'abord l'existence de la réclamation avant la suppression pour éviter les erreurs 500 inutiles.

---

## 📘 Swagger / OpenAPI

Une documentation Swagger est disponible à l’adresse suivante après démarrage :
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧑‍💻 Auteur

Développé par [Votre Nom] – Projet académique (ou professionnel) dans le cadre de l'apprentissage de Spring Boot et des microservices.

---

## 📄 License

Ce projet est sous licence MIT. Libre à vous de l’utiliser et le modifier.
