package com.example.resin_id_sorter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class PlasticAnalyzer {

    private FirebaseVisionImageLabeler labeler;

    FirebaseAutoMLRemoteModel remoteModel = new FirebaseAutoMLRemoteModel.Builder("seven_plastics_2020117235328").build();
    FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel);
    FirebaseVisionOnDeviceAutoMLImageLabelerOptions options = optionsBuilder
            .setConfidenceThreshold(0.3f)  // Evaluate your model in the Firebase console
            // to determine an appropriate threshold.
            .build();






    public PlasticAnalyzer() {
        try {
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }
    }

    FirebaseVisionImage image = null;

    public void processImage(final Context context, File file) {
        try {
            image = FirebaseVisionImage.fromFilePath(context, Uri.fromFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        labeler.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                        if (labels.size() > 0) {
                            Toast.makeText(context, labels.get(0).getText() + " - " + labels.get(0).getConfidence(),
                                    Toast.LENGTH_LONG).show();
                        }

                        for (FirebaseVisionImageLabel label : labels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            System.out.println("LABEL: " + text + " : " + confidence);
                        }

                        if (labels.size() > 0) {
                            Intent intent = new Intent(context, ResultActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("CODE", labels.get(0).getText());
                            context.startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Scanner", e.getMessage());
            }
        });
        file.delete();
    }
}
