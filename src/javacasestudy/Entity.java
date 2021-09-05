package javacasestudy;

import java.util.Objects;

public class Entity {
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
}
