package tn.esprit.innoxpert.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.innoxpert.Entity.ComplaintCategories;

import java.util.List;
import java.util.Map;

@Service
public class MistralAIService {

    @Value("${mistral.api.key}")
    private String apiKey;


    private static final String MODEL = "mistral-small-latest";
    private static final String API_URL = "https://api.mistral.ai/v1/chat/completions";

    private final RestTemplate restTemplate;

    public MistralAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateInitialSolution(ComplaintCategories category, String description) {
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

            return extractAISolution(response.getBody());
        } catch (Exception e) {
            return getFallbackSolution(category);
        }
    }

    private String buildPrompt(ComplaintCategories category, String description) {
        return String.format("""
            En tant que concierge expert d'un hôtel 5 étoiles, proposez une solution immédiate et concise (max 150 mots) 
            pour une réclamation de type '%s'. La description est : '%s'.
            
            Exemple de réponse attendue :
            - Solution technique rapide
            - Mesure d'apaisement client
            - Échéance de résolution
            
            Réponse :
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