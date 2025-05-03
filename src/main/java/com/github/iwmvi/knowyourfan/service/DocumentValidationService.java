package com.github.iwmvi.knowyourfan.service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DocumentValidationService {

    public String validarDocumento(String pathDoDocumento) throws Exception {
        try (ImageAnnotatorClient visionClient = ImageAnnotatorClient.create()) {
            // Lê a imagem do arquivo
            ByteString imgBytes = ByteString.readFrom(Files.newInputStream(Paths.get(pathDoDocumento)));

            // Configura o recurso de OCR
            Feature feature = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .setImage(com.google.cloud.vision.v1.Image.newBuilder().setContent(imgBytes).build())
                    .addFeatures(feature)
                    .build();

            // Processa a imagem
            AnnotateImageResponse response = visionClient.batchAnnotateImages(java.util.List.of(request)).getResponsesList().get(0);

            if (response.hasError()) {
                throw new Exception("Erro ao processar imagem: " + response.getError().getMessage());
            }

            // Retorna o texto extraído da imagem
            return response.getFullTextAnnotation().getText();
        }
    }
}
