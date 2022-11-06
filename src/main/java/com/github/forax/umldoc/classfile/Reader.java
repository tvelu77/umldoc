package com.github.forax.umldoc.classfile;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * The interface used to differentiate multiple file reader.
 *
 */
public sealed interface Reader permits MarkdownReader{

  /**
   *
   * Check the file extension.
   * Note : the existence of the file should be checked before calling this method.
   *
   * @param path File's path (in absolute or relative)
   * @return Reader A MarkdownReader, a PlantReader or a MermaidReader.
   */
  static Reader checkFileExtension(Path path) {
    var fileName = path.getFileName().toString();
    var lastIndex = fileName.lastIndexOf('.');
    if (lastIndex < 0) {
      throw new IllegalArgumentException("Unknown type of file");
    } else {
      var extension = fileName.substring(lastIndex + 1);
      return switch (extension) {
        case "md" -> new MarkdownReader(path);
        case "pu", "plantuml" -> new MarkdownReader(path); // TODO : CHANGE TO THE CORRESPONDING CLASS :)
        case "mmd", "mermaid" -> new MarkdownReader(path); // TODO : CHANGE TO THE CORRESPONDING CLASS :)
        default -> throw new IllegalArgumentException(extension + " is not allowed");
      };
    }
  }

  /**
   *
   * Used to read the file (.md, .pu, .plantuml, .mms, .mermaid).
   *
   * @throws IOException If the file is not accessible or doesn't exist
   */
  void readFile() throws IOException;

}
