package me.namila.rx.reactivespringboot.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Document("employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {

  @Id
  private String id;

  @Indexed(unique = true)
  private String username;

  @NotNull
  @Email
  private String email;

  @Transient
  private DepartmentModel department;

  @JsonIgnore
  private Integer departmentNo;

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  private Date lastModifiedDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmployeeModel employeeModel = (EmployeeModel) o;
    return username.equals(employeeModel.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  @Override
  public String toString() {
    return "EmployeeModel{" +
        "id='" + id + '\'' +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", departmentNo=" + departmentNo +
        ", createdAt=" + createdAt +
        ", lastModifiedDate=" + lastModifiedDate +
        '}';
  }
}
