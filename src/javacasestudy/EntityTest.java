package javacasestudy;

import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
  Entity entity;

  @BeforeEach
  public void setUp() {
    entity = new Entity();
  }

  @Test
  public void oneEntity_isTheSameAsItself() {
    entity.setId(UUID.randomUUID().toString());
    assertTrue(entity.isTheSame(entity));
  }

  @Test
  public void entitiesWithTheSameIds_areTheSame() {
    String theSameId = UUID.randomUUID().toString();
    entity.setId(theSameId);
    Entity theSameEntity = new Entity();
    theSameEntity.setId(theSameId);

    assertTrue(entity.isTheSame(theSameEntity));
  }

  @Test
  public void entitiesWithNullIds_areNeverTheSame() {
    Entity anotherEntity = new Entity();

    assertFalse(entity.isTheSame(anotherEntity));
  }

  @Test
  public void twoDifferentEntities_areNotTheSame() {
    entity.setId(UUID.randomUUID().toString());
    Entity anotherEntity = new Entity();
    anotherEntity.setId(UUID.randomUUID().toString());

    assertFalse(entity.isTheSame(anotherEntity));
  }
}
