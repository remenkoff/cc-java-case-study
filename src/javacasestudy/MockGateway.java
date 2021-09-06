package javacasestudy;

import java.util.*;

public class MockGateway implements Gateway {
  private final List<Codecast> codecasts;
  private final List<User> users;
  private final List<License> licenses;

  public MockGateway() {
    codecasts = new ArrayList<>();
    users = new ArrayList<>();
    licenses = new ArrayList<>();
  }

  public List<Codecast> findAllCodecastsChronoSorted() {
    List<Codecast> sortedCodecasts = new ArrayList<>(codecasts);
    sortedCodecasts.sort(Comparator.comparing(Codecast::getPublicationDate));
    return sortedCodecasts;
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
