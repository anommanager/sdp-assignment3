package com.mediaconverter.task;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.exception.MediaProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoTaskTest {

  @Mock
  ConversionEngine engine;

  @Test
  void process_callsEngineWithMp4() throws MediaProcessingException {
    VideoTask task = new VideoTask(engine);
    task.process("movie.avi");
    verify(engine).convert("movie.avi", "mp4");
  }

  @Test
  void process_propagatesException() throws MediaProcessingException {
    doThrow(new MediaProcessingException("fail", "movie.avi", "mp4"))
        .when(engine).convert("movie.avi", "mp4");

    VideoTask task = new VideoTask(engine);
    assertThrows(
        MediaProcessingException.class,
        () -> task.process("movie.avi"));
  }
}
