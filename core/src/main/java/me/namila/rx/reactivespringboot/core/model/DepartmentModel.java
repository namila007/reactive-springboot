package me.namila.rx.reactivespringboot.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.namila.rx.reactivespringboot.core.constant.JSON;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Document("department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentModel {
  @Id
  @JsonProperty(index = 1)
  private String id;

  @Indexed(unique = true)
  @JsonProperty(value = JSON.DepartmentJSON.ID, index = 2)
  private Integer departmentNo;

  @NotNull
  @JsonProperty(value = JSON.DepartmentJSON.NAME, index = 3)
  private String name;

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  private Date lastModifiedDate;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DepartmentModel that = (DepartmentModel) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
