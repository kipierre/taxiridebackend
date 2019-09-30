package biz.advance_it_group.taxiride_backend.commons.helpers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TaxiRideStorageServices {

    @Value("${app.storage.directory}")
    private String storageDirectory;

    // Créer le dossier contenant les documents générés ou reutiliser s'il existe déjà.
    //File OUT_DIR = new File(System.getProperty("user.home") + File.separator + "taxiride" + File.separator + "documents");

    File OUT_DIR;

    // Construire une chemin de stockage des fichiers portable sur tous les systèmes
    public void getDirectoryURL(){
        String [] repertoires = storageDirectory.split("/");
        String directoryURL = File.separator;
        for(String s : repertoires) {
            directoryURL = new StringBuilder().append(directoryURL).append(s).append(File.separator).toString();
        }
        OUT_DIR = new File(directoryURL);
    }

    // Récupérer le chemin vers le répertoire de stockage
    public Path getRootLocation(){
        getDirectoryURL();
        return Paths.get(OUT_DIR + "");
    }

    // Méthode permettant de sauvegarder un fichier et de retourner le nom du fichier sauvegardé
    public String store(MultipartFile file, String name){

        // Obtenir l'extention du fichier
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        // Créer un nom de fichier unique (décommenter cette ligne au cas où il n'existe aucune convention de nommage des fichiers uploadés)
        // String randomName = UUID.randomUUID().toString();

        // Construire le nom complet du fichier
        String fileName = name + "." + fileExtension;
        try{
            // Copier le fichier à l'emplacement de stockage approprié
            Files.copy(file.getInputStream(), getRootLocation().resolve(fileName));
            return fileName;
        }catch(Exception e){
            throw new RuntimeException("Unable to upload the file.");
        }
    }

    // Méthode permettant de lire un fichier connaissant son nom et de retourner la resource associée à ce fichier
    public Resource loadFile(String filename){
        try{
            Path file = getRootLocation().resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource; // La resource existe et est accessible
            }else{
                throw new RuntimeException("Unable to download the file.");
            }
        }catch(MalformedURLException e){
            throw new RuntimeException("The specified path of the file is not found.");
        }
    }

    // Méthode pour supprimer tous les fichiers du répertoire de stockage
    public void deleteAll(){
        FileSystemUtils.deleteRecursively(getRootLocation().toFile());
    }

    // Méthode de création du répertoire contenant les fichiers (méthode utilisée lors de l'initialisation de l'application)
    public void init(){
        try{
            getDirectoryURL();
            OUT_DIR.mkdirs();

        }catch(Exception e){
            throw new RuntimeException("Unable to create the storage folder.");
        }
    }

}
