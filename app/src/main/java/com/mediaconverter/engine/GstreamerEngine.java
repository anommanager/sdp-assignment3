package com.mediaconverter.engine;

import com.mediaconverter.exception.MediaProcessingException;

public class GstreamerEngine implements ConversionEngine {

  @Override
  public void convert(String inputFile, String outputFormat) throws MediaProcessingException {
    if (inputFile == null || inputFile.isBlank()) {
      throw new MediaProcessingException("GstreamerEngine: invalid input", inputFile, outputFormat);
    }
    System.out.println("GstreamerEngine converting " + inputFile + " -> " + outputFormat);
  }
}
