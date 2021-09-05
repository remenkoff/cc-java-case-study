package javacasestudy;

import java.util.List;
import java.util.ArrayList;

public class MockGateway implements Gateway {
  private List<Codecast> codecasts;
  private List<User> users;
  private List<License> licenses;

  public MockGateway() {
    codecasts = new ArrayList<Codecast>();
    users = new ArrayList<User>();
    licenses = new ArrayList<License>();
  }

  @Override
  public List<Codecast> findAllCodecasts() {
    return codecasts;
  }

  @Override
  public Codecast findCodecast(String title) {
    for (Codecast codecast: codecasts) {
      if (codecast.getTitle().equals(title))
        return codecast;
    }
    return null;
  }

  @Override
  public void save(Codecast codecast) {
    codecasts.add(codecast);
  }

  @Override
  public void delete(Codecast codecast) {
    codecasts.remove(codecast);
  }

  @Override
  public User findUser(String username) {
    for (User user: users) {
      if (user.getUsername().equals(username))
        return user;
    }
    return null;
  }

  @Override
  public void save(User user) {
    users.add(user);
  }

  @Override
  public void save(License license) {
    licenses.add(license);
  }
}
