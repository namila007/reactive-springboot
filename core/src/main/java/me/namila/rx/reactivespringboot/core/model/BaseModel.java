package me.namila.rx.reactivespringboot.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  private Date lastModifiedDate;

}
