package hexlet.code.model;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;


}
