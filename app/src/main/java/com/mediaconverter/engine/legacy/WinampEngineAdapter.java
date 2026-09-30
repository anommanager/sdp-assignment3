package com.mediaconverter.engine.legacy;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.exception.MediaProcessingException;

public class WinampEngineAdapter implements ConversionEngine {

  private final LegacyWinamp legacyWinamp;

  public WinampEngineAdapter(LegacyWinamp legacyWinamp) {
    this.legacyWinamp = legacyWinamp;
  }

  @Override
  public void convert(String inputFile, String outputFormat) throws MediaProcessingException {
    if (inputFile == null || inputFile.isBlank()) {
      throw new MediaProcessingException("Winamp: input file not found", inputFile, outputFormat);
    }
    if (outputFormat == null || outputFormat.isBlank()) {
      throw new MediaProcessingException("Winamp: output format not supported", inputFile, outputFormat);
    }

    String inputFormat = extractFormat(inputFile);
    String outputFile = toOutputFilename(inputFile, outputFormat);

    int code = legacyWinamp.transcodeMedia(outputFormat, inputFormat, outputFile, inputFile);

    if (code != LegacyWinamp.OK) {
      throw new MediaProcessingException(toMessage(code), inputFile, outputFormat);
    }
  }

  private String extractFormat(String inputFile) {
    int dot = inputFile.lastIndexOf('.');
    return (dot >= 0) ? inputFile.substring(dot + 1) : "unknown";
  }

  private String toOutputFilename(String inputFile, String outputFormat) {
    int dot = inputFile.lastIndexOf('.');
    String base = (dot >= 0) ? inputFile.substring(0, dot) : inputFile;
    return base + "." + outputFormat;
  }

  private String toMessage(int code) {
    return switch (code) {
      case LegacyWinamp.ERR_FILE_NOT_FOUND -> "Winamp: input file not found";
      case LegacyWinamp.ERR_FORMAT_UNSUPPORTED -> "Winamp: output format not supported";
      default -> "Winamp: unknown error (code " + code + ")";
    };
  }
}
