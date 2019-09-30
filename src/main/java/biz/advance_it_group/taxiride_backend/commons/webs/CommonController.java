package biz.advance_it_group.taxiride_backend.commons.webs;

import biz.advance_it_group.taxiride_backend.commons.entities.ApiResponse;
import biz.advance_it_group.taxiride_backend.commons.helpers.TaxiRideCommonServices;
import biz.advance_it_group.taxiride_backend.commons.helpers.TaxiRideStorageServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Cette classe sert de controlleur regroupant les API utilisées en commun dans l'application
 * @author KITIO
 * @version 1.0, 16/09/2019
 */

@Api(value = "Common Rest API", description = "Définition des API communes à tous les modules de l'application.")
@RestController
@RequestMapping("/api/commons")
public class CommonController {

    @Autowired
    TaxiRideStorageServices taxiRideStorageServices;

    @Autowired
    TaxiRideCommonServices taxiRideCommonServices;

    private static final Logger logger = Logger.getLogger(CommonController.class);

    /**
     * Cette API permet de télécharger une resource depuis le serveur connaisant l'URL de la resource
     * @param url
     * @return Le resource à télécharger.
     */
    @ApiOperation(value = "Téléchargement d'une resource depuis le serveur connaissant son url")
    @GetMapping("/download/{url}")
    @ResponseBody
    public ResponseEntity<Resource> downloadResource(@PathVariable String url){
        try{
                Resource resource = taxiRideStorageServices.loadFile(url);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()+"\"")
                        .body(resource);
        }catch (Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Cette API permet d'uploader une ressource sur le serveur.
     * @param file
     * @return Message de succès du chargement de la resource sur le serveur
     */
    @ApiOperation(value = "Upload d'une resource sur le serveur ayant le fichier associé")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadResource(@RequestParam("file") MultipartFile file){

        try{
                // Fabriquer le nom de la resource en utilisant un UUID
                String fileName = taxiRideCommonServices.generateRandomUuid();

                // Sauvegarder la resource dans le répertoire de stockage
                String savedFile = taxiRideStorageServices.store(file, fileName);

                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The resource is successfully saved under the name : " + savedFile, true));

        }catch(Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse("Unable to load the file.", false));
        }
    }


}
