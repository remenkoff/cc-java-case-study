package javacasestudy.entities;

import java.util.Objects;

public class Entity implements Cloneable {
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isTheSame(Entity entity) {
    return id != null && Objects.equals(id, entity.getId());
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
