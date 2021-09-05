package javacasestudy;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class MockGateway implements Gateway {
  private List<Codecast> codecasts;
  private List<User> users;
  private List<License> licenses;

  public MockGateway() {
    codecasts = new ArrayList<>();
    users = new ArrayList<>();
    licenses = new ArrayList<>();
  }

  @Override
  public List<Codecast> findAllCodecasts() {
    return codecasts;
  }

  @Override
  public Codecast findCodecast(String title) {
    for (Codecast codecast : codecasts) {
      if (codecast.getTitle().equals(title))
        return codecast;
    }
    return null;
  }

  @Override
  public void save(Codecast codecast) {
    establishId(codecast);
    codecasts.add(codecast);
  }

  @Override
  public void delete(Codecast codecast) {
    codecasts.remove(codecast);
  }

  @Override
  public User findUser(String username) {
    for (User user : users) {
      if (user.getUsername().equals(username))
        return user;
    }
    return null;
  }

  @Override
  public void save(User user) {
    establishId(user);
    users.add(user);
  }

  private void establishId(Entity entity) {
    if (entity.getId() == null)
      entity.setId(UUID.randomUUID().toString());
  }

  @Override
  public void save(License license) {
    licenses.add(license);
  }

  @Override
  public List<License> findLicensesForUserAndCodecast(User user, Codecast codecast) {
    List<License> foundLicenses = new ArrayList<>();
    for (License license : licenses) {
      if (license.getUser().isTheSame(user) && license.getCodecast().isTheSame(codecast))
        foundLicenses.add(license);
    }
    return foundLicenses;
  }

  @Override
  public List<License> findLicensesFor(User user) {
    List<License> foundLicenses = new ArrayList<>();
    for (License license : licenses) {
      if (license.getUser().isTheSame(user))
        foundLicenses.add(license);
    }
    return foundLicenses;
  }
}
