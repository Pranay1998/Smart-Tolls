/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.samples.vision.ocrreader.ui.camera.SendOCR;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private SendOCR ocr;
    private Activity myAct;
    private DateTime first;
    private boolean firstTime = true;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, Activity activity) {
        mGraphicOverlay = ocrGraphicOverlay;
        this.myAct = activity;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());

                // String to be scanned to find the pattern.



                // Create a Pattern object


                // Now create matcher object.



                    if(firstTime) {
                        String URL = "http://192.168.43.62/toll_pass.php";
                        ocr = new SendOCR(myAct, URL, item.getValue().replaceAll("\\S", ""));
                        first = new DateTime();
                        firstTime = false;
                    }

                    else if (new DateTime().isAfter(first.plus(new Duration(40000)))){
                        String URL = "http://192.168.43.62/toll_pass.php";
                        ocr = new SendOCR(myAct, URL, item.getValue().replaceAll("\\S", ""));
                        first = new DateTime();
                    }






            }
            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
