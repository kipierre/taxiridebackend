package biz.advance_it_group.taxiride_backend.commons.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;

@Service
public class TaxiRideCommonServices {

    @Autowired
    public JavaMailSender emailSender;

    private static final Random RANDOM = new SecureRandom();

    // Choisir parmi les lettres et les chiffres des éléments qui
    // prêtent moins à la confusion. Par exemple, exclure o O et 0 puis 1 l et L.
    public static final String ALL_LETTERS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
    public static final String SMALL_LETTERS = "abcdefghjkmnpqrstuvwxyz";
    public static final String CAPITAL_LETTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String DIGITS_LETTERS = "abcdefghjkmnpqrstuvwxyz23456789";
    public static final String ALL_DIGITS_LETTERS = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";


    private static String format = "yyyy-MM-dd hh:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat(format);

    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public String toJson(Object object){
        return gson.toJson(object);
    }

    public Integer generateRandomDigit() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    /**
     * Generer une chaine aléatoire d'une taille donnée
     */
    public  String generateRandomCode(int length) {
        String password = "";
        for (int i=0; i < length; i++)
        {
            int index = (int)(RANDOM.nextDouble()*SMALL_LETTERS.length());
            password += SMALL_LETTERS.substring(index, index+1);
        }
        return password;
    }

    // Générer un code aléatoire ne contenant que des chiffres et possédant une certaine longueur
    public String generateRandomDigitsCode(int length) {
        String code = "";
        for (int i=0; i < length; i++)
        {
            int index = (int)(RANDOM.nextDouble()*DIGITS.length());
            code += DIGITS.substring(index, index+1);
        }
        return code;
    }

    // Générer un code aléatoire contenant des chiffres et des lettres ayant une taille donnée
    public String generateRandomDigitsLettersCode(int length) {
        String code = "";
        for (int i=0; i < length; i++)
        {
            int index = (int)(RANDOM.nextDouble()*DIGITS_LETTERS.length());
            code += DIGITS_LETTERS.substring(index, index+1);
        }
        return code;
    }

    // Génération d'un code UUID aléatoire
    public String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }

    public static Random getRANDOM() {
        return RANDOM;
    }

    public static String getSmallLetters(){
        return SMALL_LETTERS;
    }

    public static String getCapitalLetters(){
        return CAPITAL_LETTERS;
    }

    public static String getAllLetters(){
        return ALL_LETTERS;
    }

    public static String getFormat() {
        return format;
    }

    public static void setFormat(String format) {
        TaxiRideCommonServices.format = format;
    }

    public  SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        TaxiRideCommonServices.sdf = sdf;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    // Parser un InputStream et retourner le JSON associé
    public static String readInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }


    // Methode permettant de remplacer les espaces par le caractère undescore dans une chaine de caractère
    public String replaceSpaceByUndescore(String chaine){
        String [] morceaux = chaine.split(" ");
        String resultat = "";
        for (int i = 0; i < morceaux.length; i++) {
            resultat += morceaux[i]+"_";
        }
        return resultat.substring(0, resultat.length()-1);
    }

    // Evaluer le nombre d'années qui sépare 2 dates
    public int getDiffYears(Date dateDebut, Date dateFin){
        Calendar a = getCalendar(dateDebut);
        Calendar b = getCalendar(dateFin);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    // Retrouver l'instance Calendar d'une date
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

}
