package tn.esprit.innoxpert.Util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.innoxpert.Entity.ComplaintCategories;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MistralAIService {

    @Value("${mistral.api.key}")
    private String apiKey;


    private static final String MODEL = "mistral-small-latest";
    private static final String API_URL = "https://api.mistral.ai/v1/chat/completions";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    private final RestTemplate restTemplate;

    public MistralAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> generateInitialSolution(ComplaintCategories category, String description) {
        String prompt = buildPrompt(category, description);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        var requestBody = Map.of(
                "model", MODEL,
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            String aiResponse = extractAISolution(response.getBody());
            return formatResponse(category, description, aiResponse);
        } catch (Exception e) {
            return Map.of(
                    "status", "error",
                    "category", category.name(),
                    "description", description,
                    "solution", getFallbackSolution(category),
                    "error", e.getMessage()
            );
        }
    }
    private Map<String, Object> formatResponse(ComplaintCategories category, String description, String aiResponse) {
        try {
            // Nettoyage de la réponse
            String cleanJson = aiResponse.replace("```json", "")
                    .replace("```", "")
                    .trim();

            // Parse le JSON
            Map<String, Object> parsedResponse = objectMapper.readValue(
                    cleanJson,
                    new TypeReference<Map<String, Object>>() {}
            );

            // Structure de sortie standardisée
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("category", category.name());
            response.put("description", description);
            response.putAll(parsedResponse);
            response.put("timestamp", Instant.now().toString());

            return response;
        } catch (Exception e) {
            return Map.of(
                    "status", "format_error",
                    "category", category.name(),
                    "description", description,
                    "solution", getFallbackSolution(category),
                    "original_response", aiResponse,
                    "error", "Failed to parse AI response"
            );
        }
    }

    private String buildPrompt(ComplaintCategories category, String description) {
        return String.format("""
        🎩 **Rôle :** Concierge IA d'un hôtel 5 étoiles  
        🛎️ **Réclamation signalée :**  
        - 📌 **Catégorie** : %s  
        - 📝 **Description** : "%s"  
        
        🎯 **Tâches :**  
        1️⃣ Vérifier si la description est **compréhensible et pertinente** par rapport à la catégorie.  
           - Si elle est **hors sujet ou incohérente**, demander au client de modifier sa réclamation ou créer une nouvelle .  
           - Sinon, proposer une solution adaptée.  
        
        2️⃣ Si la réclamation est valide, générer une réponse **structurée** contenant :  
           - ✅ **Une solution immédiate**  
           - 💡 **Une mesure d'apaisement client**  
           - ⏳ **Une échéance de résolution**  

        🛠 **Format de réponse attendu :**  
        ```
        {
            "status": "valid" | "invalid",
            "message": "...",
            "solution": "...",
            "apaisement": "...",
            "delai": "..."
        }
        ```
    """, getCategoryLabel(category), description);
    }


    private String extractAISolution(Map<String, Object> response) {
        if (response == null || !response.containsKey("choices")) {
            throw new RuntimeException("Invalid AI response");
        }

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, String> message = (Map<String, String>) firstChoice.get("message");

        return message.get("content");
    }

    public String getFallbackSolution(ComplaintCategories category) {
        return switch (category) {
            case SERVICE -> "Notre équipe service client vous contactera dans les 10 minutes.";
            case NETTOYAGE -> "Un membre du personnel de ménage sera dépêché immédiatement.";
            case EQUIPEMENT -> "Un technicien interviendra dans les 30 minutes pour vérifier l'équipement.";
            case RESERVATION -> "Notre gestionnaire de réservation vous recontactera sous 5 minutes.";
            case AUTRE -> "Votre demande a été priorisée, un responsable vous contactera rapidement.";
        };
    }

    private String getCategoryLabel(ComplaintCategories category) {
        return switch (category) {
            case SERVICE -> "Service Client";
            case NETTOYAGE -> "Problème de Nettoyage";
            case EQUIPEMENT -> "Défaillance d'Équipement";
            case RESERVATION -> "Erreur de Réservation";
            case AUTRE -> "Autre Réclamation";
        };
    }
}