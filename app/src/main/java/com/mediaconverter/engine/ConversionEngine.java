package com.mediaconverter.engine;

import com.mediaconverter.exception.MediaProcessingException;

public interface ConversionEngine {
  void convert(String sourceFile, String targetFormat) throws MediaProcessingException;
}
