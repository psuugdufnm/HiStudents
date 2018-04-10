package org.birdback.histudents.utils;

import android.os.Environment;

import java.io.File;

public interface BaseConfig {
     String SP_DEFAULT = "HI_STUDENTS_SHARED_PREFERENCES";
     String PACKAGE = "org.birdback.histudents";
     String PATH_SD = Environment.getExternalStorageDirectory().getPath() + File.separator + PACKAGE;
     String PATH_IMG = PATH_SD + File.separator + "Image" + File.separator;
     String PATH_LOG = PACKAGE + File.separator + "Logs" + File.separator;
     String PATH_DATA = PATH_SD + File.separator + "Data" + File.separator;


}
