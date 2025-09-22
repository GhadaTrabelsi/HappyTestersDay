package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestersDateBugDrawer {

    public static void main(String[] args) {
        // Réduire les logs verbeux
        System.setProperty("webdriver.chrome.verboseLogging", "false");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        options.addArguments("--window-size=1280,720"); // utile si plein écran désactivé
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-extensions");

        WebDriver driver = new ChromeDriver(options);

        try {
            // Créer un fichier HTML temporaire
            File htmlFile = createHtmlFile();

            // Naviguer vers le fichier HTML
            driver.navigate().to("file://" + htmlFile.getAbsolutePath());

            // Attendre que la page soit complètement chargée
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("bugCanvas")));

            System.out.println(" Page chargée avec succès !");

            // Dessiner le bug avec JavaScript
            drawBug(driver);

            // Afficher un message de célébration
            celebrateTestersDay(driver);

            // Garder la fenêtre ouverte
            System.out.println("🐛 Bug dessiné avec succès pour le Testers Day ! 🎉           ");
            System.out.println("Regardez la fenêtre Chrome pour voir votre bug !");
            System.out.println("Appuyez sur Entrée pour fermer...");
            System.in.read();

            // Supprimer le fichier temporaire
            htmlFile.delete();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static File createHtmlFile() throws IOException {
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH));

        String htmlContent = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <title>🐛 Testers Day Bug Drawing</title>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <style>\n" +
            "    html, body {\n" +
            "      height: 100%;\n" +
            "      margin: 0;\n" +
            "      overflow: hidden; /* pas de scroll */\n" +
            "      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
            "      font-family: Arial, sans-serif;\n" +
            "      color: white;\n" +
            "      display: flex;\n" +
            "      flex-direction: column;\n" +
            "      align-items: center;\n" +
            "      justify-content: center;\n" +
            "      gap: 10px;\n" +
            "    }\n" +
            "    h1 { font-size: 2.5em; text-shadow: 2px 2px 4px rgba(0,0,0,0.5); margin: 0; }\n" +
            "    .date { font-size: 1.2em; margin: 0 0 6px 0; }\n" +
            "    canvas {\n" +
            "      flex: 1; max-width: 90%; max-height: 70%;\n" +
            "      border-radius: 12px; background: #fff; margin: 6px 0;\n" +
            "    }\n" +
            "    .celebration { font-size: 1.1em; animation: bounce 2s infinite; }\n" +
            "    @keyframes bounce { 0%,20%,50%,80%,100%{transform:translateY(0);} 40%{transform:translateY(-6px);} 60%{transform:translateY(-3px);} }\n" +
            "\n" +
            "    /* Signature groupes */\n" +
            "    .signature { display:flex; gap:18px; flex-wrap:wrap; align-items:center; justify-content:center; margin-top:4px; }\n" +
            "    .sig-item { display:flex; align-items:center; gap:8px; text-decoration:none; color:#fff; background:rgba(0,0,0,0.35); padding:8px 14px; border-radius:12px; }\n" +
            "    .sig-item span { font-size: 1.05em; }\n" +
            "    .sig-item:hover { background: rgba(0,0,0,0.5); }\n" +
            "    .icon { width:24px; height:24px; display:inline-block; }\n" +
            "  </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "  <h1>🐛 Happy Testers Day! 🐛</h1>\n" +
            "  <div class='date'>" + formattedDate + "</div>\n" +
            "  <canvas id='bugCanvas' width='800' height='600'></canvas>\n" +
            "  <div class='celebration' id='celebration'>Selenium dessine des bugs pour les testeurs ! 🎨</div>\n" +
            "\n" +
            "  <!-- Signature groupes avec logos -->\n" +
            "  <div class='signature'>\n" +
            "    <a class='sig-item' href='https://www.facebook.com/groups/585214809554140' target='_blank' rel='noopener'>\n" +
            "      <!-- Logo Facebook (SVG inline) -->\n" +
            "      <svg class='icon' viewBox='0 0 24 24' aria-hidden='true'>\n" +
            "        <circle cx='12' cy='12' r='12' fill='#1877F2'/>\n" +
            "        <path d='M13.5 8.5h1.75V6h-1.75C11.57 6 10 7.57 10 9.5V11H8v2.5h2V18h2.5v-4.5h2l.5-2.5h-2.5V9.5c0-.55.45-1 1-1Z' fill='#fff'/>\n" +
            "      </svg>\n" +
            "      <span>Test Logiciel بالتونسي</span>\n" +
            "    </a>\n" +
            "    <a class='sig-item' href='https://www.linkedin.com/groups/12954419/' target='_blank' rel='noopener'>\n" +
            "      <!-- Logo LinkedIn (SVG inline) -->\n" +
            "      <svg class='icon' viewBox='0 0 24 24' aria-hidden='true'>\n" +
            "        <rect width='24' height='24' rx='4' fill='#0A66C2'/>\n" +
            "        <path d='M7.1 9H4.9V19h2.2V9ZM6 4.8a1.3 1.3 0 1 0 0 2.6 1.3 1.3 0 0 0 0-2.6ZM19.1 19V13.6c0-2.6-1.4-3.8-3.3-3.8a2.8 2.8 0 0 0-2.6 1.4h-.1V9H10.9V19h2.2v-4.9c0-1.3.2-2.6 1.9-2.6s1.9 1.5 1.9 2.7V19h2.2Z' fill='#fff'/>\n" +
            "      </svg>\n" +
            "      <span>Test Logiciel & Automatisation @TN</span>\n" +
            "    </a>\n" +
            "  </div>\n" +
            "</body>\n" +
            "</html>";

        File tempFile = File.createTempFile("testers_day_bug", ".html");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(htmlContent);
        }

        System.out.println("📄 Fichier HTML créé: " + tempFile.getAbsolutePath());
        return tempFile;
    }

    private static void drawBug(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("🎨 Début du dessin du bug...");

        String drawScript =
            "const canvas = document.getElementById('bugCanvas');" +
            "const ctx = canvas.getContext('2d');" +

            // Corps principal (ovale)
            "ctx.fillStyle = '#4CAF50';" +
            "ctx.beginPath();" +
            "ctx.ellipse(400, 350, 120, 80, 0, 0, 2 * Math.PI);" +
            "ctx.fill();" +

            // Tête
            "ctx.fillStyle = '#45a049';" +
            "ctx.beginPath();" +
            "ctx.arc(400, 250, 60, 0, 2 * Math.PI);" +
            "ctx.fill();" +

            // Yeux
            "ctx.fillStyle = 'white';" +
            "ctx.beginPath();" +
            "ctx.arc(380, 240, 15, 0, 2 * Math.PI);" +
            "ctx.fill();" +
            "ctx.beginPath();" +
            "ctx.arc(420, 240, 15, 0, 2 * Math.PI);" +
            "ctx.fill();" +

            // Pupilles
            "ctx.fillStyle = 'black';" +
            "ctx.beginPath();" +
            "ctx.arc(385, 245, 8, 0, 2 * Math.PI);" +
            "ctx.fill();" +
            "ctx.beginPath();" +
            "ctx.arc(425, 245, 8, 0, 2 * Math.PI);" +
            "ctx.fill();" +

            // Sourire
            "ctx.strokeStyle = 'black';" +
            "ctx.lineWidth = 3;" +
            "ctx.beginPath();" +
            "ctx.arc(400, 260, 20, 0.2 * Math.PI, 0.8 * Math.PI);" +
            "ctx.stroke();" +

            // Antennes
            "ctx.strokeStyle = '#333';" +
            "ctx.lineWidth = 4;" +
            "ctx.beginPath();" +
            "ctx.moveTo(385, 200);" +
            "ctx.lineTo(375, 170);" +
            "ctx.moveTo(415, 200);" +
            "ctx.lineTo(425, 170);" +
            "ctx.stroke();" +

            // Bouts des antennes
            "ctx.fillStyle = '#FF5722';" +
            "ctx.beginPath();" +
            "ctx.arc(375, 170, 6, 0, 2 * Math.PI);" +
            "ctx.fill();" +
            "ctx.beginPath();" +
            "ctx.arc(425, 170, 6, 0, 2 * Math.PI);" +
            "ctx.fill();" +

            // Pattes gauches
            "ctx.strokeStyle = '#333';" +
            "ctx.lineWidth = 6;" +
            "ctx.beginPath();" +
            "ctx.moveTo(320, 320); ctx.lineTo(280, 300);" +
            "ctx.moveTo(300, 350); ctx.lineTo(250, 340);" +
            "ctx.moveTo(320, 380); ctx.lineTo(280, 400);" +
            "ctx.stroke();" +

            // Pattes droites
            "ctx.beginPath();" +
            "ctx.moveTo(480, 320); ctx.lineTo(520, 300);" +
            "ctx.moveTo(500, 350); ctx.lineTo(550, 340);" +
            "ctx.moveTo(480, 380); ctx.lineTo(520, 400);" +
            "ctx.stroke();" +

            // Points décoratifs
            "ctx.fillStyle = '#388E3C';" +
            "for(let i = 0; i < 5; i++) {" +
            "  ctx.beginPath();" +
            "  ctx.arc(360 + i * 20, 340, 6, 0, 2 * Math.PI);" +
            "  ctx.fill();" +
            "}" +

            // Ailes
            "ctx.fillStyle = 'rgba(173, 216, 230, 0.7)';" +
            "ctx.beginPath();" +
            "ctx.ellipse(360, 300, 40, 25, -0.3, 0, 2 * Math.PI);" +
            "ctx.fill();" +
            "ctx.beginPath();" +
            "ctx.ellipse(440, 300, 40, 25, 0.3, 0, 2 * Math.PI);" +
            "ctx.fill();" +

            // Texte dans le canvas (haut + bas)
            "ctx.font = 'bold 24px Arial';" +
            "ctx.fillStyle = '#333';" +
            "ctx.textAlign = 'center';" +
            "ctx.fillText('🐛 \"Je ne suis pas un bug, je suis une fonctionnalité !\" 🐛', 400, 100);" +
            "ctx.font = '18px Arial';" +
            "ctx.fillText('Créé avec amour avec Selenium Java pour le Testers Day', 400, 550);" +

            "'Bug dessiné avec succès!';";

        try {
            String result = (String) js.executeScript(drawScript);
            System.out.println("✅ " + result);
        } catch (Exception e) {
            System.out.println("❌ Erreur lors du dessin: " + e.getMessage());
        }

        // Animation de clignotement des yeux
        System.out.println("👁️ Animation des yeux...");
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1500);

            // Fermer les yeux
            js.executeScript(
                "const canvas = document.getElementById('bugCanvas');" +
                "const ctx = canvas.getContext('2d');" +
                "ctx.fillStyle = '#45a049';" +
                "ctx.beginPath();" +
                "ctx.ellipse(380, 240, 15, 3, 0, 0, 2 * Math.PI);" +
                "ctx.fill();" +
                "ctx.beginPath();" +
                "ctx.ellipse(420, 240, 15, 3, 0, 0, 2 * Math.PI);" +
                "ctx.fill();"
            );

            Thread.sleep(200);

            // Rouvrir les yeux
            js.executeScript(
                "const canvas = document.getElementById('bugCanvas');" +
                "const ctx = canvas.getContext('2d');" +
                "ctx.fillStyle = 'white';" +
                "ctx.beginPath();" +
                "ctx.arc(380, 240, 15, 0, 2 * Math.PI);" +
                "ctx.fill();" +
                "ctx.beginPath();" +
                "ctx.arc(420, 240, 15, 0, 2 * Math.PI);" +
                "ctx.fill();" +
                "ctx.fillStyle = 'black';" +
                "ctx.beginPath();" +
                "ctx.arc(385, 245, 8, 0, 2 * Math.PI);" +
                "ctx.fill();" +
                "ctx.beginPath();" +
                "ctx.arc(425, 245, 8, 0, 2 * Math.PI);" +
                "ctx.fill();"
            );
        }

        System.out.println("🎉 Animation terminée !");
    }

    private static void celebrateTestersDay(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String[] messages = {
            "🎉 Bonne fête aux testeurs du monde entier ! 🎉",
            "🐛 Sans bugs, pas de testeurs ! Sans testeurs, trop de bugs ! 🐛",
            "🏆 Les testeurs sont les héros méconnus du développement ! 🏆",
            "🔍 Merci de trouver ce que nous, développeurs, préférons ignorer ! 🔍",
            "🎨 Et voilà, Selenium peut même faire de l'art ! 🎨"
        };

        System.out.println("💬 Messages de célébration...");
        for (String message : messages) {
            js.executeScript("document.getElementById('celebration').textContent = arguments[0];", message);
            Thread.sleep(2000);
        }
    }
}
