package me.namila.rx.reactivespringboot.core.constant.id;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Objects;


@AllArgsConstructor
@Value
public class DepartmentId implements Serializable {

  private String id;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepartmentId that = (DepartmentId) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
