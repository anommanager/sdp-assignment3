package com.mediaconverter.engine;

import com.mediaconverter.exception.MediaProcessingException;

public class FfmpegEngine implements ConversionEngine {

  @Override
  public void convert(String inputFile, String outputFormat) throws MediaProcessingException {
    if (inputFile == null || inputFile.isBlank()) {
      throw new MediaProcessingException("FFmpeg: invalid input", inputFile, outputFormat);
    }
    System.out.println("FFmpeg converting " + inputFile + " -> " + outputFormat);
  }
}
