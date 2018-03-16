package com.tq.simple_retrofit.progress;

public interface ProgressListener {

   void update(long bytesRead, long contentLength);
}
