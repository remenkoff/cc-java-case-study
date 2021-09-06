package javacasestudy;

import org.junit.jupiter.api.*;

import java.util.*;

import static javacasestudy.License.Type.*;
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
    codecast = new Codecast("Codecast", new GregorianCalendar(2021, Calendar.SEPTEMBER, 6).getTime());
    Context.gateway.save(codecast);
  }

  @Test
  public void user_canNotViewCodecast_whenHasNoViewLicense() {
    assertFalse(useCase.isLicensedToViewCodecast(user, codecast));
  }

  @Test
  public void user_canViewCodecast_whenHasViewLicense() {
    License license = new License(VIEWING, user, codecast);
    Context.gateway.save(license);

    assertTrue(useCase.isLicensedToViewCodecast(user, codecast));
  }

  @Test
  public void user_canNotViewOtherUsersCodecast_whenHasNoLicenses() {
    User otherUser = new User("OtherUser");
    Context.gateway.save(otherUser);
    License license = new License(VIEWING, user, codecast);
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
    assertEquals(presentableCodecast.publicationDate(), "9/06/2021");
  }

  @Test
  public void codecast_isNotViewableNorDownloadable_whenUserHasNoLicenses() {
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertFalse(presentableCodecast.isViewable());
    assertFalse(presentableCodecast.isDownloadable());
  }

  @Test
  public void codecast_isViewableAndNotDownloadable_whenUserHasViewLicense() {
    Context.gateway.save(new License(VIEWING, user, codecast));
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertTrue(presentableCodecast.isViewable());
    assertFalse(presentableCodecast.isDownloadable());
  }

  @Test
  public void codecast_isDownloadableAndNotViewable_whenUserHasADownloadLicense() {
    Context.gateway.save(new License(DOWNLOADING, user, codecast));
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertTrue(presentableCodecast.isDownloadable());
    assertFalse(presentableCodecast.isViewable());
  }
}
