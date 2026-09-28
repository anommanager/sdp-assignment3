package com.mediaconverter.task;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.exception.MediaProcessingException;

public abstract class MediaTask {

  protected final ConversionEngine engine;

  protected MediaTask(ConversionEngine engine) {
    this.engine = engine;
  }

  public abstract void process(String inputFile) throws MediaProcessingException;
}
