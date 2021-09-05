package javacasestudy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PresentCodecastUserCaseTest {
  private PresentCodecastUseCase useCase;
  private User user;
  private Codecast codecast;

  @BeforeEach
  public void setUp() {
    Context.gateway = new MockGateway();
    useCase = new PresentCodecastUseCase();
    user = new User("User");
    Context.gateway.save(user);
    codecast = new Codecast("Codecast", "Today");
    Context.gateway.save(codecast);
  }

  @Test
  public void user_canNotViewCodecast_whenHasNoViewLicense() {
    assertFalse(useCase.isLicensedToViewCodecast(user, codecast));
  }

  @Test
  public void user_canViewCodecast_whenHasViewLicense() {
    License license = new License(user, codecast);
    Context.gateway.save(license);

    assertTrue(useCase.isLicensedToViewCodecast(user, codecast));
  }

  @Test
  public void user_canNotViewOtherUsersCodecast_whenHasNoLicenses() {
    User otherUser = new User("OtherUser");
    Context.gateway.save(otherUser);
    License license = new License(user, codecast);
    Context.gateway.save(license);

    assertFalse(useCase.isLicensedToViewCodecast(otherUser, codecast));
  }

  @Test
  public void codecasts_areNotPresenting_whenThereAreNoCodecasts() {
    Context.gateway.delete(codecast);
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);

    assertEquals(presentableCodecasts.size(), 0);
  }

  @Test
  public void oneCodecast_isPresenting_whenThereIsOnlyOne() {
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);

    assertEquals(presentableCodecasts.size(), 1);

    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertEquals(presentableCodecast.title(), codecast.getTitle());
    assertEquals(presentableCodecast.publicationDate(), codecast.getPublicationDate());
  }

  @Test
  public void codecast_isNotViewable_whenNoLicense() {
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertFalse(presentableCodecast.isViewable());
  }

  @Test
  public void codecast_isViewable_whenThereIsALicense() {
    Context.gateway.save(new License(user, codecast));
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertTrue(presentableCodecast.isViewable());
  }
}
