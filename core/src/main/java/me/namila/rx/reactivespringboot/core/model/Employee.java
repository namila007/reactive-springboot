package me.namila.rx.reactivespringboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

  @Id
  private String id;

  @Indexed(unique = true)
  private String username;

  @NotNull
  @Email
  private String email;

  @DBRef
  private DepartmentModel department;

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  private Date lastModifiedDate;


}
