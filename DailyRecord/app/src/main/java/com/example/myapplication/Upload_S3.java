package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

public class Upload_S3 {
    File file;
    String filename;
    Context context;
    String bucketname;

    public Upload_S3(File file, String filename, Context context, Boolean isRecord) {
        this.file = file;
        this.filename = filename;   // filename = 버킷에 올라갈 filename
        this.context = context;
        if (isRecord)
            bucketname = "test20220524/record";
        else
            bucketname = "test20220524/image";

    }

    public void upload() {

        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAZHQAX6BCPMZ2RLMU", "fMcehxuM7xud6BGIB+Nxe+h75m0J//0KYLVIY3P0");    // IAM 생성하며 받은 키 입력
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(context).build();
        TransferNetworkLossHandler.getInstance(context);

        TransferObserver uploadObserver = transferUtility.upload(bucketname, filename, file);    // (bucket api, file이름, file객체)

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                }
            }

            @Override
            public void onProgressChanged(int id, long current, long total) {
                int done = (int) (((double) current / total) * 100.0);
                Log.d("MYTAG", "UPLOAD - - ID: $id, percent done = $done");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d("MYTAG", "UPLOAD ERROR - - ID: $id - - EX:" + ex.toString());
            }
        });
    }
}