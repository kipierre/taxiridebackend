package biz.advance_it_group.taxiride_backend.profiles.services.helpers;

import biz.advance_it_group.taxiride_backend.profiles.dto.*;
import biz.advance_it_group.taxiride_backend.profiles.entities.*;
import org.springframework.stereotype.Service;

@Service
public class ProfileServices {

    /**
     * Conversion d'un code promotionnel de l'utilisateur en DTO
     * @param code
     * @return
     */
    public static UserPromotionalCodeDTO convertPromotionalCodeToDTO(UserPromotionalCode code){
        return new UserPromotionalCodeDTO(
                code.getId(),
                code.getUser().getPhoneNumber(),
                code.getUser().getId(),
                code.getPromotionalCode().getCodeKey(),
                code.getPromotionalCodeUsed());
    }

    /**
     * Conversion d'un contact d'urgence en DTO
     * @param contact
     * @return
     */
    public static EmergencyContactDTO convertEmergencyContactToDTO(EmergencyContact contact){
        return new EmergencyContactDTO(
                contact.getId(),
                contact.getUser().getPhoneNumber(),
                contact.getUser().getId(),
                contact.getName(),
                contact.getPhone(),
                contact.getContactType().toString());
    }

    /**
     * Conversion du moyen de paiement d'un utilisateur en DTO
     * @param payment
     * @return
     */
    public static UserPaymentModeDTO convertUserPaymentModeToDTO(UserPaymentMode payment){
        return new UserPaymentModeDTO(
                payment.getId(),
                payment.getUser().getPhoneNumber(),
                payment.getUser().getId(),
                payment.getPaymentMode().getId(),
                payment.getPaymentMode().getPaymentType(),
                payment.getValue(),
                payment.getIsDefault());

    }

    /**
     * Conversion d'un véhicule en DTO
     * @param vehicle
     * @return
     */
    public static VehicleDTO convertVehicleToDTO(Vehicle vehicle){
        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getUser().getPhoneNumber(),
                vehicle.getUser().getId(),
                vehicle.getVehicleType(),
                vehicle.getNumberOfSeat(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getMatriculationNumber(),
                vehicle.getFirstUseDate(),
                vehicle.getColor(),
                vehicle.getNumbersWheel(),
                vehicle.getDescription());
    }

    /**
     * Conversion d'un document en DTO
     * @param document
     * @return
     */
    public static OfficialDocumentDTO convertOfficialDocumentToDTO(OfficialDocument document){
        return new OfficialDocumentDTO(
                document.getId(),
                document.getUser().getPhoneNumber(),
                document.getUser().getId(),
                document.getUrl(),
                document.getNumber(),
                document.getMimeType());
    }

    /**
     * Conversion d'une image de véhicule en DTO
     * @param picture
     * @return VehiculePictureDTO
     */
    public static VehiclePictureDTO convertVehiculePictureToDTO(VehiclePicture picture){
        return new VehiclePictureDTO(
                picture.getId(),
                picture.getVehicle().getId(),
                picture.getVehicle().getMatriculationNumber(),
                picture.getPictureNumber(),
                picture.getPictureMimeType(),
                picture.getPictureURL());
    }

}
