package javacasestudy.useCases.presentCodecast;

public record PresentableCodecast(
  String title,
  String pubDate,
  boolean isViewable,
  boolean isDownloadable
) {
}
