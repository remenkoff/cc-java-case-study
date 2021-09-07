package javacasestudy.tests.useCases;

import javacasestudy.Context;
import javacasestudy.entities.*;
import javacasestudy.tests.TestSetup;
import javacasestudy.useCases.presentCodecast.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static javacasestudy.entities.License.Type.*;
import static javacasestudy.tests.TestUtils.randomStringLowercased;
import static org.junit.jupiter.api.Assertions.*;

public class PresentCodecastUseCaseTest {
  private PresentCodecastUseCase useCase;
  private User user;

  @BeforeEach
  public void setUp() {
    TestSetup.setupContext();
    useCase = new PresentCodecastUseCase();
    user = new User("User");
    Context.userGateway.save(user);
  }

  @Nested
  protected class GivenNoCodecasts {
    @Test
    public void codecasts_areNotPresented_whenThereAreNoCodecasts() {
      List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);

      assertEquals(presentableCodecasts.size(), 0);
    }
  }

  @Nested
  protected class GivenOneCodecast {
    private Codecast codecast;

    @BeforeEach
    public void setUp() {
      Date publicationDate = new GregorianCalendar(2021, Calendar.SEPTEMBER, 6).getTime();
      codecast = new Codecast(randomStringLowercased(), publicationDate);
      Context.codecastGateway.save(codecast);
    }

    @Test
    public void oneCodecast_isPresented_whenThereIsOnlyOne() {
      List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);

      assertEquals(presentableCodecasts.size(), 1);

      PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

      assertEquals(presentableCodecast.title(), codecast.getTitle());
      assertEquals(presentableCodecast.publicationDate(), "9/06/2021");
    }

    @Nested
    protected class GivenNoLicenses {
      @Test
      public void codecast_isNotViewableNorDownloadable_whenUserHasNoLicenses() {
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

        assertFalse(presentableCodecast.isViewable());
        assertFalse(presentableCodecast.isDownloadable());
      }

      @Test
      public void user_canNotViewCodecast_whenHasNoViewLicense() {
        assertFalse(useCase.isLicensedToViewCodecast(user, codecast));
      }

      @Test
      public void user_canNotViewOtherUsersCodecast_whenHasNoLicenses() {
        User otherUser = new User("OtherUser");
        Context.userGateway.save(otherUser);
        License license = new License(VIEWING, user, codecast);
        Context.licenseGateway.save(license);

        assertFalse(useCase.isLicensedToViewCodecast(otherUser, codecast));
      }
    }

    @Nested
    protected class GivenOneLicense {
      @Test
      public void user_canViewCodecast_whenHasViewLicense() {
        License license = new License(VIEWING, user, codecast);
        Context.licenseGateway.save(license);

        assertTrue(useCase.isLicensedToViewCodecast(user, codecast));
      }

      @Test
      public void user_canDownloadCodecast_whenHasDownloadLicense() {
        License license = new License(DOWNLOADING, user, codecast);
        Context.licenseGateway.save(license);

        assertTrue(useCase.isLicensedToDownloadCodecast(user, codecast));
      }

      @Test
      public void codecast_isViewableAndNotDownloadable_whenUserHasViewLicense() {
        Context.licenseGateway.save(new License(VIEWING, user, codecast));
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

        assertTrue(presentableCodecast.isViewable());
        assertFalse(presentableCodecast.isDownloadable());
      }

      @Test
      public void codecast_isDownloadableAndNotViewable_whenUserHasADownloadLicense() {
        Context.licenseGateway.save(new License(DOWNLOADING, user, codecast));
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

        assertTrue(presentableCodecast.isDownloadable());
        assertFalse(presentableCodecast.isViewable());
      }
    }
  }
}
