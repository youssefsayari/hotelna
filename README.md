# Système de Gestion de Réclamations Hôtelières 

 # Structure :
 
   hotelna/
   
   ├── complaint-service/
   
   │   ├── src/
   
   │   ├── pom.xml
   
   │   └── README.md
   
   ├── api-gateway/
   
   ├── eureka-server/
   
   ├── .gitignore
   
   └── README.md
## 📌 Aperçu
  Application microservices pour la gestion des réclamations dans les hôtels avec :
  - Création de réclamations via chatbot IA
  - Suivi en temps réel
  - Génération automatisée de solutions (Mistral AI)

## 🛠 Technologies
- **Backend** : Spring Boot (Discovery Server, API Gateway)
- **Frontend** : Angular 15+
- **Base de Données** : MySQL
- **IA** : Mistral AI

## 🚀 Démarrage Rapide
```bash
# Lancer les microservices
cd eureka-server && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd complaint-service && mvn spring-boot:run

# Frontend
cd frontend-angular
npm install && ng serve
