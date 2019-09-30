package biz.advance_it_group.taxiride_backend.profiles.webs;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.services.interfaces.UserService;
import biz.advance_it_group.taxiride_backend.commons.entities.ApiResponse;
import biz.advance_it_group.taxiride_backend.commons.helpers.TaxiRideCommonServices;
import biz.advance_it_group.taxiride_backend.commons.helpers.TaxiRideStorageServices;
import biz.advance_it_group.taxiride_backend.commons.webs.CommonController;
import biz.advance_it_group.taxiride_backend.profiles.dto.*;
import biz.advance_it_group.taxiride_backend.profiles.entities.*;
import biz.advance_it_group.taxiride_backend.profiles.services.helpers.ProfileServices;
import biz.advance_it_group.taxiride_backend.profiles.services.interfaces.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Cette classe regroupe l'ensemble des API permettant la gestion de profile d'un utilisateur
 * de la plateforme TaxiRide. Pour chacune des APIs, la réponse est soit l'élement recherché ou
 * une réponse générique sous forme de l'objet ApiResponse.
 * @author KITIO
 * @version 1.0, 16/09/2019
 * @see ApiResponse
 */

@Api(value = "Profile Rest API", description = "Définition des API permettant la gestion du profile d'un utilisateur.")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    TaxiRideCommonServices taxiRideCommonServices;

    @Autowired
    TaxiRideStorageServices taxiRideStorageServices;

    @Autowired
    OfficialDocumentService officialDocumentService;

    @Autowired
    PaymentModeService paymentModeService;

    @Autowired
    EmergencyContactService emergencyContactService;

    @Autowired
    PromotionalCodeService promotionalCodeService;

    @Autowired
    UserPaymentModeService userPaymentModeService;

    @Autowired
    UserPromotionalCodeService userPromotionalCodeService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehiclePictureService vehiclePictureService;

    private static final Logger logger = Logger.getLogger(CommonController.class);


    /**
     * Cette méthode permet de rechercher un utilisateur suivant son numéro de téléphone. (Ok)
     * @param phone
     * @return l'utilisateur ayant le numéro de téléphone passé en paramètre ou une ApiResponse.
     */
    @GetMapping("/findbyphonenumber/{phone}")
    @ApiOperation(value = "Recherche d'un utilisateur suivant son numéro de téléphone.")
    public ResponseEntity<?> findUserByPhoneNumber(@PathVariable("phone") String phone){
        try{
            Optional<Users> user =  userService.findByPhoneNumber(phone);
            if(user.isPresent()){
                return new ResponseEntity<Users>(user.get(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse("The user account does not exists on the system.", false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse("An error occured while searching the user", false),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Cette méthode permet de rechercher un utilisateur suivant son adresse mail. (Ok)
     * @param email
     * @return l'utilisateur ayant le numéro de téléphone passé en paramètre ou une ApiResponse.
     */
    @GetMapping("/findbyemail/{email}")
    @ApiOperation(value = "Recherche d'un utilisateur suivant son adresse mail.")
    public ResponseEntity<?> findUserByEmail(@PathVariable("email") String email){

        try{
            Optional<Users> user =  userService.findByEmail(email);

            if(user.isPresent()){
                return new ResponseEntity<Users>(user.get(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse("The user account does not exists on the system.", false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse("An error occured while searching the user", false),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     *  Cette méthode permet de rechercher un utilisateur suivant son identifiant sur TaxiRide.(Ok)
     * @param id
     * @return l'utilisateur ayant l'id passé en paramètre.
     */
    @GetMapping("/findbyid/{id}")
    @ApiOperation(value = "Recherche d'un utilisateur suivant son identifiant.")
    public ResponseEntity<?> findUserById(@PathVariable("id") Long id){
        try{
            Optional<Users> user =  userService.findById(id);

            if(user.isPresent()){
                return new ResponseEntity<Users>(user.get(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse("The user account does not exists on the system.", false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse("An error occured while searching the user", false),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Cette méthode permet à un utilisateur de mettre à jour les informations de base de son profil. (Ok)
     * @param profileDTO
     * @return Les informations mises à jour (ProfileDTO)
     * {@link ProfileDTO}
     */
    @ApiOperation(value = "Permet à un utilisateur de mettre à jour les informations de base de son profil.")
    @PostMapping("/saveProfilePersonnalInformation")
    public ResponseEntity<?> saveProfilePersonnalInformation(@Valid @RequestBody ProfileDTO profileDTO) {

        try{
            // Réchercher l'utilisateur en fonction de son numéro de téléphone
            Optional<Users> user = userService.findByPhoneNumber(profileDTO.getPhoneNumber());
            if(user.isPresent()){
                Users usertoUpdate = user.get();

                usertoUpdate.setFirstName(profileDTO.getFirstName());
                usertoUpdate.setLastName(profileDTO.getLastName());
                usertoUpdate.setBirthDate(profileDTO.getBirthDate());
                usertoUpdate.setProfession(profileDTO.getProfession());
                usertoUpdate.setLanguage(profileDTO.getLanguage());
                usertoUpdate.setCountry(profileDTO.getCountry());
                usertoUpdate.setGender(profileDTO.getGender());
                usertoUpdate.setAddress(profileDTO.getAddress());
                usertoUpdate.setCity(profileDTO.getCity());
                usertoUpdate.setDriverOperatingCityCode(profileDTO.getDriverOperatingCityCode());
                usertoUpdate.setDriverOperatingCountryCode(profileDTO.getDriverOperatingCountryCode());
                usertoUpdate.setCodeTripOption(profileDTO.getCodeTripOption());
                usertoUpdate.setStatus(profileDTO.getStatus());
                usertoUpdate.setExternalReferalCode(profileDTO.getExternalReferalCode()); // Mais il faudra vérifier que ce code existe

                usertoUpdate.setMinimalNotificationDistance(profileDTO.getMinimalNotificationDistance());
                usertoUpdate.setSubscribeToSMS(profileDTO.getSubscribeToSMS());
                usertoUpdate.setSubscribeToEmail(profileDTO.getSubscribeToEmail());
                usertoUpdate.setSubscribeToPush(profileDTO.getSubscribeToPush());

                // Mettre à jour la date de modification de l'utilisateur
                usertoUpdate.setUpdatedAt(new Date());

                // Mettre à jour l'utilisateur
                Users savedUser = userService.save(usertoUpdate);
                profileDTO.setId(savedUser.getId());
                return ResponseEntity.status(HttpStatus.OK).body(profileDTO);
            }
            return new ResponseEntity<>(new ApiResponse("Unable to find the user account.", false),
                    HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse("An error occured while updating the profiles.", false),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Cette méthode a pour but de sauvegarder la photo de profile d'un utilisateur dans un répertoire du serveur. (Ok)
     * Cette méthode met à jour l'url de la photo de profile de l'utilisateur après stockage.
     * @param phoneNumber
     * @param file
     * @return Message de chargement de la photo avec succès.
     */
    @ApiOperation(value = "Upload de la photo de profil d'un utilisateur sur le serveur.")
    @PostMapping("/uploadProfilePhoto")
    public ResponseEntity<?> uploadProfilePhoto(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("file") MultipartFile file){

        try{
            // Essayer de retrouver l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if(optionalUser.isPresent()){

                // L'utilisateur a été retrouvé, il faut le récupérer
                Users user = optionalUser.get();

                // Fabriquer le nom de la photo à sauvegarder avec pour format (id_phoneNumber_photo)
                String fileName = "PHOTO_" + user.getId() + "_" +  user.getPhoneNumber() + "_" + taxiRideCommonServices.generateRandomUuid();

                // Sauvegarder la photo dans le répertoire de stockage
                String savedFile = taxiRideStorageServices.store(file, fileName);

                // Metre à jour le nom de la photo dans la table users
                user.setImageUrl(savedFile);
                user.setProfilePictureMimeType(FilenameUtils.getExtension(file.getOriginalFilename())); // Mettre à jour l'extension de la photo de profiles de l'utilisateur
                userService.save(user);

                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The profile picture uploaded successfully.", true));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user is not found.", false));
            }

        }catch(Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse("Unable to load the file.", false));
        }
    }

    /**
     * Cette méthode a pour but de charger une pièce exigible à un chauffeur pour pouvoir opérer sur TaxiRide.(Ok)
     * @param phoneNumber
     * @param file
     * @param number
     * @return Message de chargement avec sussès de la pièce.
     */
    @ApiOperation(value = "Chargement d'une pièce administrative d'un chauffeur sur la plateforme.")
    @PostMapping("/saveProfileAttachements")
    public ResponseEntity<?> saveProfileAttachements(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("file") MultipartFile file, @RequestParam("number") String number){

        try {
            // Retrouver l'utilisateur et vérifier qu'il n'est pas encore vérifié pour effectuer toute action de chargement de document
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if (optionalUser.isPresent()) {
                Users user = optionalUser.get(); // Récupération de l'utilisateur concrèt

                if (user.getIsVerified()) { // Statut déjà vérifié
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Your status as a driver has already been verified.", true));
                } else { // Statut non encore vérifié

                    // Fabriquer le nom de la pièce à sauvegarder
                    Integer numero = Integer.parseInt(number);
                    String fileName = "ATTACH_" + user.getId() + "_" + number + "_" + taxiRideCommonServices.generateRandomUuid() ; // Convention de nommage du document officiel

                    /*
                     * Créer un type de document et l'enregistrer avec les paramètres de la requête au cas où l'utilisateur
                     * n'aurai pas déjà ce document dans la liste de ses documents.
                     */
                    if (userService.hasDocument(user, numero) && officialDocumentService.findByUserAndNumber(user, numero).isPresent()) { // Il possède déjà un document pareil, effectuer juste la mise à jour

                        // Retrouver l'ancienne pièce sauvegardée
                        OfficialDocument document = officialDocumentService.findByUserAndNumber(user, numero).get();

                        // Sauvegarder la nouvelle pièce dans le répertoire de stockage
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        document.setUrl(savedFile);
                        document.setMimeType(file.getContentType());

                        officialDocumentService.save(document);

                        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Document updated successfully.", true));

                    }else{ // L'utilisateur ne possède pas encore ce document, le créer

                        // Sauvegarder le document sur le support physique
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        // Créer et stocker le document dans la base de données
                        OfficialDocument document = new OfficialDocument(user, numero, savedFile, file.getContentType());

                        officialDocumentService.save(document);
                        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Document uploaded successfully.", true));
                    }
                }
            }else{
                return ResponseEntity.status(404).body(new ApiResponse("Document created uploaded successfully.", false));
            }

        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse("Unable to upload the document.", true));
        }

    }


    /**
     * Méthode qui permettant de retourner tous les documents d'un utilisateur à partir de son numéro de téléphone.
     * Une fois la liste des documents retrouvés, chacun peut être téléchargé suivant son lien avec la méthode de
     * téléchargement downloadResource du controlleur CommonController. (Ok)
     * @param phone
     * @return La liste des documents que possède un utilisateur
     * {@link CommonController}
     */
    @ApiOperation(value = "Recherche des documents associés à un utilisateur.")
    @GetMapping("/findAttachementsByPhone/{phone}")
    @ResponseBody
    public ResponseEntity<?> findAttachementsByPhone(@PathVariable String phone){
        try{
            // Examiner la potentielle existence de l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> user = userService.findByPhoneNumber(phone);

            if(user.isPresent()){
                // Retrouver tous les potentiels documents de l'utilisateurs
                List<OfficialDocument> mesDocuments = officialDocumentService.findAllByUser(user.get());
                if(!mesDocuments.isEmpty()) { // L'utilisateur possède au moins un document

                    // Filtrer pour convertir en OfficialDocumentDTO
                    List<OfficialDocumentDTO> documents = mesDocuments.stream()
                            .map(document -> ProfileServices.convertOfficialDocumentToDTO(document))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(documents);
                }else { // L'utilsateur n'a pas de document
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("You have no document loaded on TaxiRide.", true));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User account not found on the system.", false));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("And error occured while searching for your documents.", false));
        }
    }

    /**
     * Cette méthode permet de sauvegarder dans un répertoire du serveur une image associée à un véhicule, puis dans la base de données
     * les informations associée à cette image. (Ok)
     * @param phoneNumber
     * @param pictureNumber
     * @param file
     * @return Les informations sur l'image du véhicule qui est sauvegardée.
     */
    @ApiOperation(value = "Upload de l'image d'un véhicule.")
    @PostMapping("/saveProfileCabImage")
    public ResponseEntity<?> saveProfileCabImage(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("pictureNumber") String pictureNumber,  @RequestParam("file") MultipartFile file){

        try{
            // Essayer de retrouver l'utilisateur concerné
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if(optionalUser.isPresent()){ // L'utilisateur existe

                // Retrouver le véhicule de cet utilisateur
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(optionalUser.get());

                if(mesVehicules.size() != 0){ // L'utilisateur a au moins un véhicule (et l'unique) dans le système

                    // Le véhicule de l'utilisateur est forcément le premier de la liste
                    Vehicle vehicle = mesVehicules.get(0);

                    // Retrouver l'ensemble des images du véhicule de l'utilisateur
                    List<VehiclePicture> vehiclePictures = vehiclePictureService.findAllByVehicle(vehicle);

                    // Retrouver l'ensemble des images dont le numéro est celui en création
                    List<VehiclePicture> alreadyExistPictures = vehiclePictures
                            .stream()
                            .filter(picture -> picture.getPictureNumber() == Integer.valueOf(pictureNumber))
                            .collect(Collectors.toList());

                    if(alreadyExistPictures.size() == 0 && vehiclePictures.size() < 6){ // le numéro de l'image n'existe pas encore et le nombre d'images est inférieur à 6 pour ce véhicule

                        // Fabriquer le nom de l'image à sauvegarder avec pour format (VEHICULE_idUser_pictureNumber)
                        String fileName = "VEHICLE_" +  optionalUser.get().getId() + "_" + pictureNumber + "_" + taxiRideCommonServices.generateRandomUuid();

                        // Sauvegarder le fichier image dans le répertoire de stockage
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        // Sauvagarder l'image du véhicule
                        VehiclePicture newVehiclePicture = new VehiclePicture(vehicle, Integer.valueOf(pictureNumber), savedFile, file.getContentType());
                        VehiclePicture savedVehiclePicture =  vehiclePictureService.save(newVehiclePicture);

                        return ResponseEntity.status(HttpStatus.OK).body(ProfileServices.convertVehiculePictureToDTO(savedVehiclePicture));
                    }else{ // Le numéro de l'image existe déjà
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("The picture number " + pictureNumber + " has already been provided.", false));
                    }
                }else{ // L'utilisateur n'a pas encore de véhicule dans le système
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("You have no cab registered on the TaxiRide.", false));
                }
            }else{ // L'utilisateur n'existe pas
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user is not found.", false));
            }
        }catch(Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse("Unable to load the file.", false));
        }
    }


    /**
     * Cette méthode retourne toutes les images associées à un véhicule. Chaque image peut donc être téléchargée à partir de son url
     * avec la méthode générique downloadResource du controller CommonController. (Ok)
     * @param phone
     * @return La liste des images du véhicule de l'utilisateur dont le numéro de téléphone est passé en paramètre.
     * {@link CommonController}
     */
    @ApiOperation(value = "Retourner toutes les images d'un véhicule appartenant à un utilisateur dont le numéro de téléphone est mentionné.")
    @GetMapping("/findCabImagesByPhone/{phone}")
    public ResponseEntity<?> findCabImagesByPhone(@PathVariable String phone){
        try{
            // Examiner la potentielle existence de l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur existe
               // Charger le véhicule de cet utilisateur
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(optionalUser.get());
                if(mesVehicules.size() != 0){ // Au moins un véhicule a été trouvé pour l'utilisateur;
                    // Charger la liste des images du premier (et d'ailleurs l'unique) véhicule.
                    List<VehiclePicture> vehiclePictures = vehiclePictureService.findAllByVehicle(mesVehicules.get(0));

                    // Filtrer pour convertir en VehiclePictureDTO
                    List<VehiclePictureDTO> pictures = vehiclePictures.stream()
                            .map(picture -> ProfileServices.convertVehiculePictureToDTO(picture))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(pictures);
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No cab picture found for your account.", false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account not found on TaxiRide.", false));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occured while searching the cad pictures.", false));
        }
    }

    /**
     * Cette méthode permet d'enregistrer les informations de l'automobile d'un chauffeur sur la plateforme TaxiRide. (Ok)
     * @param vehicleDTO
     * @return Les informations sur le véhicule enregistré (y compris son identifiant)
     * {@link VehicleDTO}
     */
    @ApiOperation(value = "Enregistrement des informations de l'automobile.")
    @PostMapping("/saveProfileCabInformation")
    public ResponseEntity<?> saveProfileCabInformation(@Valid @RequestBody VehicleDTO vehicleDTO){
        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(vehicleDTO.getPhoneNumber());

            if(optionalUser.isPresent()){ // L'utilisateur existe sur le système

                Users user = optionalUser.get();

                // Se rassurer que cet utilisateur n'a pas encore un véhicule enregistré sur TaxiRide
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(user);

                if(mesVehicules.size() == 0){ // L'utilisateur n'a pas encore de véhicule dans le système
                    // Construire un véhicule à partir des informations reçues de la requête et l'enregistrer
                    Vehicle newVehicle = new Vehicle();

                    newVehicle.setUser(user);

                    newVehicle.setVehicleType(vehicleDTO.getVehicleType());
                    newVehicle.setNumberOfSeat(vehicleDTO.getNumberOfSeat());
                    newVehicle.setModel(vehicleDTO.getModel());
                    newVehicle.setBrand(vehicleDTO.getBrand());
                    newVehicle.setMatriculationNumber(vehicleDTO.getMatriculationNumber());
                    newVehicle.setFirstUseDate(vehicleDTO.getFirstUseDate());
                    newVehicle.setColor(vehicleDTO.getColor());
                    newVehicle.setNumbersWheel(vehicleDTO.getNumbersWheel());
                    newVehicle.setDescription(vehicleDTO.getDescription());

                    Vehicle savedVehicle = vehicleService.save(newVehicle);
                    vehicleDTO.setId(savedVehicle.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(vehicleDTO);
                }else{ // L'utilisateur a déjà un véhicule enregistré dans le système
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("You already register a cab on TaxiRide.", false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account is not found on the system.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while creating the cab.", false));
        }
    }

    /**
     * Cette méthode recherche les informations sur l'automobile d'un chauffeur à partir de son numéro de téléphone. (Ok)
     * @param phone
     * @return Les informations sur le véhicule (supposé être unique dans le système) d'un chauffeur.
     */
    @ApiOperation(value = "Recherche des informations sur l'automobile d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findCabInformationByPhone/{phone}")
    public ResponseEntity<?> findCabInformationByPhone(@PathVariable String phone){

        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est retrouvé

                // Rechercher tous les véhicules de cet utilisateur sur le système
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(optionalUser.get());

                if(mesVehicules.size() > 0){ // On a retrouvé au moins un véhicule, retourner le premier véhicule de la liste (le seul en fait)
                    return ResponseEntity.status(HttpStatus.OK).body(ProfileServices.convertVehicleToDTO(mesVehicules.get(0)));
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("You have no cab registered on TaxiRide.", false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account is not found on the system.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while creating the cab.", false));
        }
    }


    /**
     * Cette méthode permet d'enregistrer un moyen de paiement au profil d'un utilisateur. (Ok)
     * @param userPaymentModeDTO
     * @return Les informations sur le mode de paiement enregistré
     * {@link UserPaymentModeDTO}
     */
    @ApiOperation(value = "Enregistrement d'un moyen de paiement au profil d'un utilisateur")
    @PostMapping("/saveProfilePaymentInformation")
    public ResponseEntity<?> saveProfilePaymentInformation(@Valid @RequestBody UserPaymentModeDTO userPaymentModeDTO){

        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(userPaymentModeDTO.getPhoneNumber());

            // Retrouver le mode de payment concerné
            Optional<PaymentMode> optionalPaymentMode = paymentModeService.findByPaymentType(userPaymentModeDTO.getPaymentType());

            if(optionalUser.isPresent() && optionalPaymentMode.isPresent()){ // L'utilisateur existe ainsi que le mode de paiement sollicité
                Users user = optionalUser.get();
                PaymentMode paymentMode = optionalPaymentMode.get();

                // Retrouver et filtrer tous les modes de paiement de l'utilisateur pour voir s'il ya un qui possède le type du mode en création
                List<UserPaymentMode> mesModesExistants = userPaymentModeService.findAllByUser(user)
                        .stream()
                        .filter(mode -> mode.getPaymentMode().getPaymentType() == userPaymentModeDTO.getPaymentType())
                        .collect(Collectors.toList());

                if(mesModesExistants.size() == 0){ // L'utilisateur n'a pas encore ce mode de payment dans son profil

                    UserPaymentMode userPaymentMode = new UserPaymentMode(user, paymentMode, userPaymentModeDTO.getValue(), userPaymentModeDTO.getIsDefault());
                    UserPaymentMode savedUserPaymentMode = userPaymentModeService.save(userPaymentMode);

                    // Mettre à jour le mode de payment par défaut de l'utilisateur
                    if(userPaymentModeDTO.getIsDefault()){ // Ce mode de payment est supposé être celui par défaut
                        user.setDefaultPaymentmode(userPaymentModeDTO.getPaymentType());
                        userService.save(user);
                    }

                    // Retourner les informations sur le mode de paiement qui vient d'être enregistré
                    userPaymentModeDTO.setId(savedUserPaymentMode.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(userPaymentModeDTO);
                }else{
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("You already added that payment mode in your account.", true));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account not found or payment mode dont exist.", false));
            }

        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while saving the payment method.", false));
        }
    }

    /**
     * Cette méthode permet de retrouver tous les modes de paiement qu'un utilisateur a enregistré dans son profil. (Ok)
     * @param phone
     * @return La liste des moyens de paiement qu'a enregistré un utilisateur dans son profil.
     */
    @ApiOperation(value = "Recherche des moyens de paiement d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findPaymentInformationByPhone/{phone}")
    public ResponseEntity<?> findPaymentInformationByPhone(@PathVariable String phone){

        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses modes de paiement
                List<UserPaymentMode> mesModesPaiement = userPaymentModeService.findAllByUser(optionalUser.get());
                if(! mesModesPaiement.isEmpty()){ // Il existe au moins un moyen de paiement pour cet utilisateur

                    // Filtrer pour convertir en UserPaiementModeDTO
                    List<UserPaymentModeDTO> mesModes = mesModesPaiement.stream()
                            .map(mode -> ProfileServices.convertUserPaymentModeToDTO(mode))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(mesModes);
                }else{
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("You have no payment mode registered for your account.", true));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("This user account was not found", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet d'enregistrer un numéro d'urgence ou de confiance d'un utilisateur de la plateforme TaxiRide. (Ok)
     * @param emergencyContactDTO
     * @return Les informations sur le contact qui vien d'être enregistré.
     * {@link EmergencyContactDTO}
     */
    @ApiOperation(value = "Enregistrement d'un numéro d'urgence ou de confiance d'un utilisateur.")
    @PostMapping("/saveProfileSafetyNumber")
    public ResponseEntity<?> saveProfileSafetyNumber(@Valid @RequestBody EmergencyContactDTO emergencyContactDTO){

        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(emergencyContactDTO.getPhoneNumber());

            Integer contactType = Integer.valueOf(emergencyContactDTO.getContactType());

            if(optionalUser.isPresent() && (contactType == 0 || contactType == 1)){ // L'utilisateur existe sur le système

                Users user = optionalUser.get();

                // Se rassurer que cet utilisateur n'a pas encore atteint son quota de numéros de la catégorie sollicitée
                List<EmergencyContact> mesContacts = emergencyContactService.findAllByUserAndContactType(user, Integer.valueOf(emergencyContactDTO.getContactType()));

                if(mesContacts.size() < 3){ // L'utilisateur n'a pas encore dépassé 3 contacts dans la catégorie sollicitée
                    // Construire un contact à partir des informations reçues et l'enregistrer
                    EmergencyContact newContact = new EmergencyContact();

                    newContact.setUser(user);
                    newContact.setName(emergencyContactDTO.getName());
                    newContact.setPhone(emergencyContactDTO.getPhone());
                    newContact.setContactType(Integer.valueOf(emergencyContactDTO.getContactType()));

                    EmergencyContact savedContact = emergencyContactService.save(newContact);
                    emergencyContactDTO.setId(savedContact.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(emergencyContactDTO);
                }else{ // L'utilisateur a épuisé son quota de contact de la catégorie sélectionnée (03 contacts)
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("You Cannot register more than 03 contacts of the selected category.", false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account is not found or contact type does not exists.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while creating emergency contacts.", false));
        }
    }

    /**
     * Cette méthpde permet de retrouver tous les contacts d'urgence ou de confiance enrégistré sur le profil d'un utilisateur. (Ok)
     * @param phone
     * @return La liste des numéro d'urgence ou de confiance qu'un utilisateur a enregistré sur son profil.
     */
    @ApiOperation(value = "Recherche des contacts d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findSafetyNumberByPhone/{phone}")
    public ResponseEntity<?> findSafetyNumberByPhone(@PathVariable String phone){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses contacts

                List<EmergencyContact> mesContacts = emergencyContactService.findAllByUser(optionalUser.get());
                if(mesContacts.size() != 0){ // Au moins un contact est retrouvé

                    // Filtrer pour convertir en EmergencyContactDTO
                    List<EmergencyContactDTO> contactts = mesContacts.stream()
                            .map(contact -> ProfileServices.convertEmergencyContactToDTO(contact))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(contactts);
                }else{ // Aucun contact retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("You have no safety contact registered on the system.", false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("This user account was not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet de rechercher uniquement soit les numéro d'urgence ou les numéro de confiance qu'un utilisateur a enregistré (Ok)
     * sur son profil.
     * @param phone
     * @param type
     * @return La liste des contacts d'une catégorie donnée qu'un utilisateur a enregistré sur son profil.
     */
    @ApiOperation(value = "Recherche des contacts de confiance ou d'émergence d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findConfidenceOrAlertNumberByPhone/{phone}/{type}")
    public ResponseEntity<?> findConfidenceOrAlertNumberByPhone(@PathVariable String phone, @PathVariable String type){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses contacts

                // Filtrer la liste des contacts de l'utilisateur pour retrouver uniquement les contacts de confiance
                List<EmergencyContact> mesContactsConfiance = emergencyContactService.findAllByUser(optionalUser.get())
                        .stream()
                        .filter(contact -> contact.getContactType() == Integer.valueOf(type))
                        .collect(Collectors.toList());
                if(mesContactsConfiance.size() != 0){ // Au moins un contact de confiance est retrouvé

                    // Filtrer pour convertir en EmergencyContactDTO
                    List<EmergencyContactDTO> contactts = mesContactsConfiance.stream()
                            .map(contact -> ProfileServices.convertEmergencyContactToDTO(contact))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(contactts);
                }else{ // Aucun contact de confiance retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("You have no contact of that category registered on the system.", false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("This user account was not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet de vérifier l'existence d'un code de parainage sur le système taxiRide. (Ok)
     * @param referalCodeDTO
     * @return Si oui ou non le code de parainage existe.
     * {@link ReferalCodeDTO}
     */
    @ApiOperation(value = "Valider un code de parainage.")
    @PostMapping("/validateReferalCode")
    public ResponseEntity<?> validateReferalCode(@Valid @RequestBody ReferalCodeDTO referalCodeDTO){

        try{
            // Rechercher le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(referalCodeDTO.getPhoneNumber());

            // Vérifie que cet utilisateur existe et que son code de référence existe aussi
            if(optionalUser.isPresent() && userService.existsByReferalCodeAndPhone(referalCodeDTO.getReferalCode(), referalCodeDTO.getPhoneNumber())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The referal code is found successfully.", true));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The referal code is not found on the system.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet à un utilisatur d'ajouter un code promotionnel à son profil. (Ok)
     * @param userPromotionalCodeDTO
     * @return Les détails du code promotionnel qui vient d'être enregistré.
     * {@link UserPromotionalCodeDTO}
     */
    @ApiOperation(value = "Enregistrement du code promotionnel d'un profil utilisateur.")
    @PostMapping("/saveProfilePromoCode")
    public ResponseEntity<?> saveProfilePromoCode(@Valid @RequestBody UserPromotionalCodeDTO userPromotionalCodeDTO){

        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(userPromotionalCodeDTO.getPhoneNumber());

            // Rechercher le code promotionnel associé dans le système
            Optional<PromotionalCode> optionalPromotionalCode = promotionalCodeService.findByKey(userPromotionalCodeDTO.getCodeKey());

            if(optionalUser.isPresent() && optionalPromotionalCode.isPresent()){ // L'utilisateur existe sur le système

                Users user = optionalUser.get();
                PromotionalCode promotionalCode = optionalPromotionalCode.get();

                // Se rassurer que cet utilisateur n'a pas encore enregistré ce code promotionnel pour son profil
                List<UserPromotionalCode> codesPromoExistants = userPromotionalCodeService.findAllByUser(user)
                        .stream()
                        .filter(code -> code.getPromotionalCode().getCodeKey().equals(promotionalCode.getCodeKey()))
                        .collect(Collectors.toList());

                if(codesPromoExistants.size() == 0){ // L'utilisateur n'a pas encore enregistré un tel code promotionnel dans son profil

                    // Construire un code promotionnel de l'utilisateur à partir des informations reçues de la requête et l'enregistrer
                    UserPromotionalCode newUserCode = new UserPromotionalCode();

                    newUserCode.setUser(user);
                    newUserCode.setPromotionalCode(promotionalCode);
                    newUserCode.setPromotionalCodeUsed(false); // Le code promotionnel vient d'être généré, il n'est pas encore utilisé

                    UserPromotionalCode savedCode = userPromotionalCodeService.save(newUserCode);
                    userPromotionalCodeDTO.setId(savedCode.getId());
                    userPromotionalCodeDTO.setPromotionalCodeUsed(savedCode.getPromotionalCodeUsed());

                    return ResponseEntity.status(HttpStatus.OK).body(userPromotionalCodeDTO); // Renvoyer les informations sur le code enregistré
                }else{ // L'utilisateur a déjà un véhicule enregistré dans le système
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("You already register this promotional code on your TaxiRide account.", false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Promotional code or user account not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while creating the cab.", false));
        }
    }

    /**
     * Cette méthode permet de vérifier qu'un code promotionnel enregistré sur le profil de l'utilisateur est valide.
     * La validité d'un code promotionnel tient compte du fait que le code est non expiré et est non utilisé. (Ok)
     * @param userPromotionalCodeDTO
     * @return Si vrai ou non un code promotionnel est valide.
     */
    @ApiOperation(value = "Valider un code de promotionnel du profil utilisateur.")
    @PostMapping("/validatePromoCode")
    public ResponseEntity<?> validatePromoCode(@Valid @RequestBody UserPromotionalCodeDTO userPromotionalCodeDTO){

        try{
            // Retrouver l'utilisateur concerné
            Optional<Users> optionalUser = userService.findByPhoneNumber(userPromotionalCodeDTO.getPhoneNumber());

            // Retrouver le code promotionnel de l'utilisateur de clé associée
            Optional<PromotionalCode> optionalCode = promotionalCodeService.findByKey(userPromotionalCodeDTO.getCodeKey());

            // Vérifier que ce code promotionnel est enrégistré pour l'utilisateur
            if(optionalUser.isPresent() && optionalCode.isPresent()){

                // Récupérer la liste des code promotionnels valides de cette utilisateur et dont la clé est celle donnée en paramètre
                List<UserPromotionalCode> userCodes = userPromotionalCodeService.findAllByUser(optionalUser.get())
                        .stream()
                        .filter(code -> (code.getPromotionalCode().getCodeKey().equals(optionalCode.get().getCodeKey()) && code.isValid()))
                        .collect(Collectors.toList());

                if(userCodes.size() != 0){
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The promotional code is found.", true));
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The promotional code not found.", false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The promotional code or the user account not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while validating the promotional code.", false));
        }
    }

    /**
     * Cette méthode permet de rechercher tous les codes promotionnels (valides ou non) qu'un utilisateur a enregistré sur son profil.
     * @param phone
     * @return La liste des codes promotionnels qu'un utilisateur a enregistré sur son profil.
     */
    @ApiOperation(value = "Recherche des codes promotionnels d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findPromoCodeByPhone/{phone}")
    public ResponseEntity<?> findPromoCodeByPhone(@PathVariable String phone){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses codes promotionnels

                List<UserPromotionalCode> mesCodesPromotionnels = userPromotionalCodeService.findAllByUser(optionalUser.get());
                if(mesCodesPromotionnels.size() != 0){ // Au moins un code promotionnel est retrouvé pour cet utilisateur

                    // Filtrer pour convertir en UserPromotionalCodeDTO
                    List<UserPromotionalCodeDTO> mesCodes = mesCodesPromotionnels.stream()
                            .map(code -> ProfileServices.convertPromotionalCodeToDTO(code))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(mesCodes);
                }else{ // Aucun code promotionnel retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("You have no promotional code registered on the system.", false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("This user account was not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet de rechercher uniquement les codes promotionnels valides (non expirés et pas encore utilisés) qu'un utilisateur
     * a enrégistré sur son profil. (Ok)
     * @param phone
     * @return La liste des codes promotionnels valides qu'un utilisateur a enregistré sur son profil.
     */
    @ApiOperation(value = "Recherche des codes promotionnels non encore utilisés et non expirés d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findAvailablePromoCodeByPhone/{phone}")
    public ResponseEntity<?> findAvailablePromoCodeByPhone(@PathVariable String phone){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses codes promotionnels

                List<UserPromotionalCode> mesCodesPromotionnelsValides = userPromotionalCodeService.findAllByUser(optionalUser.get())
                        .stream()
                        .filter(code -> code.isValid())
                        .collect(Collectors.toList());
                if(mesCodesPromotionnelsValides.size() != 0){ // Au moins un code promotionnel encore utilisable est retrouvé pour cet utilisateur
                    // Filtrer pour convertir en UserPromotionalCodeDTO
                    List<UserPromotionalCodeDTO> mesCodes = mesCodesPromotionnelsValides.stream()
                            .map(code -> ProfileServices.convertPromotionalCodeToDTO(code))
                            .collect(Collectors.toList());
                    return ResponseEntity.status(HttpStatus.OK).body(mesCodes);
                }else{ // Aucun code promotionnel encore utilisable n'est retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("You have no available promotional code registered on the system.", false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("This user account was not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }



}
