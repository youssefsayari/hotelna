package tn.esprit.innoxpert.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import tn.esprit.innoxpert.Entity.Complaint;
import tn.esprit.innoxpert.Entity.ComplaintStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
@Component
public class EmailClass {

  private final String username = "esprit.stagedepartement@gmail.com";
  private final String password = "xmqu futu clki tskp";

  private Session createEmailSession() {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");

    return Session.getInstance(props, new jakarta.mail.Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
  }

  public void sendMeetingReminder(String receiver, String organiserName, String participantName, String meetingDate, String meetingTime, String meetingLink) {
    try {
      Session session = createEmailSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
      message.setSubject("🔔 Rappel de votre réunion - Jitsi Meet");

      String formattedMessage = String.format(
              "<html><body>"
                      + "<h2>📢 Rappel de réunion</h2>"
                      + "<p>Bonjour,</p>"
                      + "<p>Un rappel pour votre réunion prévue entre :</p>"
                      + "<ul>"
                      + "<li><b>📌 Organisateur :</b> %s</li>"
                      + "<li><b>👤 Participant :</b> %s</li>"
                      + "</ul>"
                      + "<p><b>📅 Date :</b> %s</p>"
                      + "<p><b>⏰ Heure :</b> %s</p>"
                      + "<p><b>🔗 Lien de la réunion :</b> <a href='%s'>Cliquez ici pour rejoindre</a></p>"
                      + "<p>Merci de vous connecter à l'heure prévue.</p>"
                      + "<p>Cordialement,<br>Votre équipe.</p>"
                      + "</body></html>",
              organiserName, participantName, meetingDate, meetingTime, meetingLink
      );

      message.setContent(formattedMessage, "text/html; charset=utf-8");
      Transport.send(message);
      System.out.println("✔️ Email de rappel envoyé à " + receiver);
    } catch (MessagingException e) {
      System.err.println("❌ Erreur d'envoi de l'email à " + receiver + " : " + e.getMessage());
    }
  }

  public void sendHtmlEmail(String receiver, String subject, String htmlContent) {
    try {
      Session session = createEmailSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
      message.setSubject(subject);
      message.setContent(htmlContent, "text/html; charset=utf-8");

      Transport.send(message);
      System.out.println("✔️ Email HTML envoyé à " + receiver);
    } catch (MessagingException e) {
      System.err.println("❌ Erreur d'envoi de l'email à " + receiver + " : " + e.getMessage());
    }
  }

  public void sendOtpEmail(String receiver, Long otp) {
    try {
      Session session = createEmailSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
      message.setSubject("🔐 Password Recovery OTP");

      String htmlContent = String.format(
              "<html><body>"
                      + "<h2>🔐 Password Recovery - OTP</h2>"
                      + "<p>Hello,</p>"
                      + "<p>You requested a password recovery for your account. Your OTP code is: <b>%d</b></p>"
                      + "<p>Please use this OTP to reset your password. The OTP is valid for a limited time.</p>"
                      + "<p>If you didn't request this change, please ignore this email.</p>"
                      + "<p>Regards,<br>Your Security Team</p>"
                      + "</body></html>",
              otp
      );

      message.setContent(htmlContent, "text/html; charset=utf-8");
      Transport.send(message);
      System.out.println("✔️ OTP email sent for password recovery to " + receiver);
    } catch (MessagingException e) {
      System.err.println("❌ Error sending OTP email for password recovery to " + receiver + " : " + e.getMessage());
    }
  }
  public void sendEmail(String recipient, String body, String subject) {
    try {
      Session session = createEmailSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
      message.setSubject(subject);
      message.setText(body);  // Envoi du texte brut

      Transport.send(message);
      System.out.println("✔️ Email envoyé avec succès à " + recipient);
    } catch (MessagingException e) {
      System.err.println("❌ Erreur d'envoi de l'email à " + recipient + " : " + e.getMessage());
    }
  }
  /*---------------------------SAYARI-----------------------*/
  public void sendComplaintStatusUpdate(String receiverEmail, String userName, Complaint complaint,
                                        String oldStatus, String newStatus) {
    try {
      Session session = createEmailSession();
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
      message.setSubject("📢 Mise à jour de votre réclamation #" + complaint.getId());

      String htmlContent = buildComplaintStatusHtml(userName, complaint, oldStatus);
      message.setContent(htmlContent, "text/html; charset=utf-8");

      Transport.send(message);
      System.out.println("✔️ Notification envoyée à " + receiverEmail);
    } catch (MessagingException e) {
      System.err.println("❌ Erreur d'envoi à " + receiverEmail + " : " + e.getMessage());
    }
  }

  private String buildComplaintStatusHtml(String userName, Complaint complaint, String oldStatus) {
    String logoBase64 = getLogoAsBase64();
    String newStatus = complaint.getStatus().name();

    // Contenu du QR code
    String qrContent = String.format(
            "Reclamation: %s\nStatut: %s\nCategorie: %s\nDescription: %s\nDate: %s\nSolution: %s\nResolution: %s",
            complaint.getId(),
            newStatus,
            complaint.getCategory(),
            complaint.getDescription(),
            complaint.getComplaintDate().format(DateTimeFormatter.ofPattern("ddMMyy")),
            complaint.getComplaintSolutionIA().getSolutionProposee(),
            complaint.getResolutionDetails() != null ? complaint.getResolutionDetails() : ""
    );

    String qrCodeBase64 = generateQRCodeBase64(qrContent);

    return String.format(
            "<html><body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background: #f5f5f5;'>" +
                    "<div style='max-width: 600px; margin: 20px auto; padding: 30px; background: white; border-radius: 15px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);'>" +
                    "<div style='text-align: center; margin-bottom: 25px;'>" +
                    "<img src='data:image/png;base64,%s' alt='Logo' style='height: 60px; margin-bottom: 15px;'>" +
                    "<h1 style='color: #2c3e50; margin: 0; font-size: 24px;'>Réclamation #%s</h1>" +
                    "</div>" +
                    "<p style='font-size: 16px; color: #34495e;'>Bonjour %s,</p>" +
                    "<p style='font-size: 16px; color: #34495e;'>Le statut de votre réclamation a évolué :</p>" +

                    "<div style='display: flex; gap: 15px; margin: 25px 0;'>" +
                    "<div style='flex: 1; padding: 15px; background: %s; border-radius: 8px; text-align: center;'>" +
                    "<p style='margin: 0; color: white; font-weight: bold;'>Ancien Statut<br>%s</p>" +
                    "</div>" +
                    "<div style='flex: 1; padding: 15px; background: %s; border-radius: 8px; text-align: center;'>" +
                    "<p style='margin: 0; color: white; font-weight: bold;'>Nouveau Statut<br>%s</p>" +
                    "</div>" +
                    "</div>" +

                    "<div style='display: flex; justify-content: center; gap: 30px; margin: 30px 0; align-items: center;'>" +
                    "<div>" +
                    "<a href='%s/admin/complaints' style='background: #3498db; color: white; padding: 12px 25px; border-radius: 25px; text-decoration: none; display: inline-block; font-weight: bold;'>" +
                    "📩 Voir les détails" +
                    "</a>" +
                    "</div>" +
                    "<div style='text-align: center; background: white; padding: 10px; display: inline-block;'>" +
                    "<img src='data:image/png;base64,%s' alt='QR Code' style='width: 150px; height: 150px;'>" +
                    "<p style='font-size: 12px; color: #7f8c8d; margin-top: 5px;'>Scanner pour plus d'infos</p>" +
                    "</div>" +
                    "</div>" +

                    "%s" + // Détails de résolution si RESOLU

                    "<div style='border-top: 1px solid #eeeeee; padding-top: 20px; text-align: center; color: #7f8c8d;'>" +
                    "<p>Cet email a été envoyé automatiquement - merci de ne pas y répondre</p>" +
                    "</div>" +
                    "</div></body></html>",

            // Paramètres :
            logoBase64,
            complaint.getId(),
            escapeHtml(userName),
            getStatusColor(oldStatus),    // Couleur ancien statut
            oldStatus,                   // Texte ancien statut
            getStatusColor(newStatus),   // Couleur nouveau statut
            newStatus,                   // Texte nouveau statut
            getApplicationBaseUrl(),
            qrCodeBase64,
            complaint.getStatus() == ComplaintStatus.RESOLU && complaint.getResolutionDetails() != null ?
                    "<div style='background: #f8f9fa; padding: 15px; border-radius: 8px; margin-top: 20px;'>" +
                            "<h3 style='color: #2c3e50; margin-top: 0;'>📌 Détails de résolution :</h3>" +
                            "<p style='color: #34495e;'>" + escapeHtml(complaint.getResolutionDetails()) + "</p>" +
                            "</div>"
                    : ""
    );
  }
  private String getLogoAsBase64() {
    try {
      ClassPathResource resource = new ClassPathResource("logo-hotelna.png");
      byte[] fileContent = FileCopyUtils.copyToByteArray(resource.getInputStream());
      return Base64.getEncoder().encodeToString(fileContent);
    } catch (IOException e) {
      System.err.println("❌ Erreur de chargement du logo");
      return "";
    }
  }

  private String escapeHtml(String input) {
    return input.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
  }

  private String getApplicationBaseUrl() {
    return "http://localhost:4200"; // À remplacer par une config externe
  }

  private String getStatusColor(String status) {
    switch (status.toUpperCase()) {
      case "OUVERT": return "#3498db";
      case "EN_PROGRESSION": return "#f39c12";
      case "RESOLU": return "#2ecc71";
      default: return "#95a5a6";
    }
  }


  private String generateQRCodeBase64(String text) {
    try {
      Map<EncodeHintType, Object> hints = new HashMap<>();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
      hints.put(EncodeHintType.MARGIN, 2); // Marge importante
      hints.put(EncodeHintType.ERROR_CORRECTION, "L"); // Niveau de correction d'erreur

      QRCodeWriter qrCodeWriter = new QRCodeWriter();
      BitMatrix bitMatrix = qrCodeWriter.encode(
              text,
              BarcodeFormat.QR_CODE,
              400, 400, hints); // Taille augmentée

      ByteArrayOutputStream os = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, "PNG", os);
      return Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (Exception e) {
      System.err.println("Erreur génération QR: " + e.getMessage());
      return "";
    }
  }
}

