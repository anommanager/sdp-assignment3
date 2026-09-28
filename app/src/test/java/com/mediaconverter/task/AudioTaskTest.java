package com.mediaconverter.task;

import com.mediaconverter.engine.ConversionEngine;
import com.mediaconverter.exception.MediaProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioTaskTest {

  @Mock
  ConversionEngine engine;

  @Test
  void process_callsEngineWithMp3() throws MediaProcessingException {
    AudioTask task = new AudioTask(engine);
    task.process("song.flac");
    verify(engine).convert("song.flac", "mp3");
  }

  @Test
  void process_propagatesException() throws MediaProcessingException {
    doThrow(new MediaProcessingException("fail", "song.flac", "mp3"))
        .when(engine).convert("song.flac", "mp3");

    AudioTask task = new AudioTask(engine);
    org.junit.jupiter.api.Assertions.assertThrows(
        MediaProcessingException.class,
        () -> task.process("song.flac"));
  }
}
