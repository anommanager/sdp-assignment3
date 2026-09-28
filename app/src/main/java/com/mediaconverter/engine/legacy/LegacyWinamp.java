package com.mediaconverter.engine.legacy;

public class LegacyWinamp {
  public static final int OK = 0;
  public static final int ERR_FILE_NOT_FOUND = 1;
  public static final int ERR_FORMAT_UNSUPPORTED = 2;
  public static final int ERR_UNKNOWN = 99;

  public int transcodeMedia(String outputFormat, String inputFormat, String outputFile, String inputFile) {
    if (inputFile == null || inputFile.isBlank())
      return ERR_FILE_NOT_FOUND;
    if (outputFormat == null || outputFormat.isBlank())
      return ERR_FORMAT_UNSUPPORTED;
    System.out.println("LegacyWinamp converting " + inputFile + "(" + inputFormat + ")"
        + " to " + outputFile + "(" + outputFormat + ")");
    return OK;
  }
}
