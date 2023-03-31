package hexlet.code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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


    @NotNull
    @Size(min = 1)
    private String firstName;


    @NotNull
    @Size(min = 1)
    private String lastName;


    @NotNull
    @Email
    private String email;


    @NotNull
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Task> tasks_author;

    @OneToMany(mappedBy = "executor")
    private List<Task> tasks_executor;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;


}
