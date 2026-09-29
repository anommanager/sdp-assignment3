package com.mediaconverter;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.engine.FfmpegEngine;
import com.mediaconverter.engine.GstreamerEngine;
import com.mediaconverter.engine.legacy.LegacyWinamp;
import com.mediaconverter.engine.legacy.WinampEngineAdapter;
import com.mediaconverter.exception.MediaProcessingException;
import com.mediaconverter.task.AudioTask;
import com.mediaconverter.task.MediaTask;
import com.mediaconverter.task.VideoTask;

public class App {
  public static void main(String[] args) {
    String files[] = { "voice.mp3", "film.avi", "podcast.wma" };

    for (String file : files) {
      ConversionEngine engine = selectEngine(file);
      MediaTask task = selectTask(file, engine);

      try {
        task.process(file);
      } catch (MediaProcessingException e) {
        System.err.println("Failed to convert file: " + e.getMessage());
      }
    }
  }

  private static ConversionEngine selectEngine(String filename) {
    String format = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

    return switch (format) {
      case "mp3", "wav", "flac" -> new FfmpegEngine();
      case "mp4", "mkv", "avi" -> new GstreamerEngine();
      default -> new WinampEngineAdapter(new LegacyWinamp());
    };
  }

  private static MediaTask selectTask(String filename, ConversionEngine engine) {
    String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    return switch (ext) {
      case "mp3", "flac", "wav", "wma" -> new AudioTask(engine);
      default -> new VideoTask(engine);
    };
  }
}
