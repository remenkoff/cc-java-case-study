package javacasestudy.useCases.presentCodecast;

public record PresentableCodecast(
  String title,
  String publicationDate,
  boolean isViewable,
  boolean isDownloadable
) {
}
