package javacasestudy.tests.doubles;

import javacasestudy.entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GatewayUtilities<T extends Entity> {
  private final HashMap<String, T> entities;

  public GatewayUtilities() {
    entities = new HashMap<>();
  }

  public List<T> getEntities() {
    List<T> clonedEntities = new ArrayList<>();
    for (T entity : entities.values())
      addCloneToList(entity, clonedEntities);
    return clonedEntities;
  }

  @SuppressWarnings("unchecked")
  private void addCloneToList(T entity, List<T> clonedEntities) throws RuntimeException {
    try {
      clonedEntities.add((T) entity.clone());
    } catch (CloneNotSupportedException e) {
      throw new UnclonableEntity();
    }
  }

  public void save(T entity) {
    if (entity.getId() == null)
      entity.setId(UUID.randomUUID().toString());
    String id = entity.getId();
    saveCloneInMap(id, entity);
  }

  @SuppressWarnings("unchecked")
  private void saveCloneInMap(String id, T entity) {
    try {
      entities.put(id, (T) entity.clone());
    } catch (CloneNotSupportedException e) {
      throw new UnclonableEntity();
    }
  }

  public void delete(T entity) {
    entities.remove(entity.getId());
  }

  private static class UnclonableEntity extends RuntimeException {
  }
}
