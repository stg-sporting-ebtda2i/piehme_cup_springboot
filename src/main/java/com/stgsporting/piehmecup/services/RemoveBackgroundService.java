package com.stgsporting.piehmecup.services;

import com.stgsporting.piehmecup.helpers.Http;
import okhttp3.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class RemoveBackgroundService {

    @Value("${rembg.api}")
    private String API_KEY;

    @Value("${rembg.url}")
    private String BASE_URL;

    private Integer counter = 0;

    public class CustomMultipartFile implements MultipartFile {

        private final byte[] fileData;
        private final String fileName;


        public CustomMultipartFile(byte[] fileData, String fileName) {
            this.fileData = fileData;
            this.fileName = fileName;
        }

        @Override
        public String getName() {
            return fileName;
        }

        @Override
        public String getOriginalFilename() {
            return fileName;
        }

        @Override
        public String getContentType() {
            return "image/png"; // Adjust based on the file type
        }

        @Override
        public boolean isEmpty() {
            return fileData == null || fileData.length == 0;
        }

        @Override
        public long getSize() {
            return fileData.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return fileData;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(fileData);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            try (FileOutputStream fos = new FileOutputStream(dest)) {
                fos.write(fileData);
            }
        }
    }

    public MultipartFile handle(MultipartFile image) {
        try {

            String extension = FilenameUtils.getExtension(image.getOriginalFilename());
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file",
                            "image_name." + extension,
                            RequestBody.create(image.getBytes(), MediaType.parse("image/" + extension))
                    )
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .method("POST", requestBody)
                    .build()
                    ;

            OkHttpClient client = new OkHttpClient.Builder()
                    .callTimeout(Duration.ofSeconds(45))
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody body = response.body();

                if(body == null) {
                    return image;
                }

                byte[] imageBytes = body.bytes();

                return new CustomMultipartFile(imageBytes, "output.png");
            } else if (response.code() == 502) {
                counter += 1;
                System.err.println("Failed, Counter: " + counter.toString());

                if (counter <= 3) {
                    return handle(image);
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
