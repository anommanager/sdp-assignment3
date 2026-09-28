package com.mediaconverter.exception;

public class MediaProcessingException extends RuntimeException {
  private final String sourceFile;
  private final String targetFormat;

  public MediaProcessingException(String message, String sourceFile, String targetFormat) {
    super(message);
    this.sourceFile = sourceFile;
    this.targetFormat = targetFormat;
  }

  public MediaProcessingException(String message, String sourceFile, String targetFormat, Throwable cause) {
    super(message, cause);
    this.sourceFile = sourceFile;
    this.targetFormat = targetFormat;
  }

  public String getSourceFile() {
    return this.sourceFile;
  }

  public String getTargetFormat() {
    return this.targetFormat;
  }
}
