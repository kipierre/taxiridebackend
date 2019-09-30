package biz.advance_it_group.taxiride_backend.commons.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Cette classe représente la classe principale de laquelle hérite les autres classes.
 * Tous les attributs communs à toutes les classes (date de création, date de modification, etc)
 * doivent être ajoutés dans cette classe pour un comportement par défaut.
 * @author KITIO
 *
 */

@MappedSuperclass
@Data
public abstract class MainEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @LastModifiedDate
    private Date updatedAt;


    public MainEntity(){
        createdAt = new Date();
    }

}
