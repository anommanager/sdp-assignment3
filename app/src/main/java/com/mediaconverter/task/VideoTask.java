package com.mediaconverter.task;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.exception.MediaProcessingException;

public class VideoTask extends MediaTask {

  public VideoTask(ConversionEngine engine) {
    super(engine);
  }

  @Override
  public void process(String inputFile) throws MediaProcessingException {
    System.out.println("[VideoTask] Starting video conversion: " + inputFile);
    engine.convert(inputFile, "mp4");
    System.out.println("[VideoTask] Done.");
  }
}
