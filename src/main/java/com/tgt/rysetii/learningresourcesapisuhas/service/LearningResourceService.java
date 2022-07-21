package com.tgt.rysetii.learningresourcesapisuhas.service;

import com.tgt.rysetii.learningresourcesapisuhas.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapisuhas.entity.LearningResourceStatus;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LearningResourceService {
    private List<LearningResource> getLearningResources(){
        File learningResourcesFile= new File("LearningResources.csv");
        List<LearningResource> lr= readLearningResourcesFromCSV("LearningResources.csv");
        return lr;
    }

    private List<LearningResource> readLearningResourcesFromCSV(String fileName) {
       List<LearningResource> learningResources=new ArrayList<>();
       try {
           FileReader reader = new FileReader(fileName);
           BufferedReader bufferedReader = new BufferedReader(reader);
           String line="";
           line = bufferedReader.readLine();
           while(line!=null){
               String[] attributes = line.split(",");
               LearningResource learningResource= createLearningResourceObject(attributes);
               learningResources.add(learningResource);
               line=bufferedReader.readLine();
           }
       }
       catch(IOException e){
           e.printStackTrace();
       }
       return learningResources;
    }

    private LearningResource createLearningResourceObject(String[] attributes) {
        DateTimeFormatter df= DateTimeFormatter.ofPattern("dd-mm-yyyy");
        Integer id =Integer.parseInt(attributes[0]);
        String name=attributes[1];
        Double costPrice= Double.parseDouble(attributes[2]);
        Double sellingPrice=Double.parseDouble(attributes[3]);
        LearningResourceStatus status= LearningResourceStatus.valueOf(attributes[4]);
        LocalDate createdDate=LocalDate.parse(attributes[5],df);
        LocalDate publishedDate=LocalDate.parse(attributes[6],df);
        LocalDate retiredDate=LocalDate.parse(attributes[7],df);
        LearningResource lr=new LearningResource(id,name,costPrice,sellingPrice,status,createdDate,publishedDate,retiredDate);
        return lr;
    }
    private void saveLearningResourceToCSV(ArrayList<LearningResource> learningResources){
        final String delimiter=",";
        try{
            File file=new File("LearningResources.csv");
            FileWriter wr=new FileWriter(file.getName(),true);
            BufferedWriter bf= new BufferedWriter(wr);
            for(LearningResource learningResource:learningResources){
                bf.newLine();
                StringBuffer line=new StringBuffer();
                line.append(learningResource.getId());
                line.append(delimiter);
                line.append(learningResource.getName());
                line.append(delimiter);
                line.append(learningResource.getCostPrice());
                line.append(delimiter);
                line.append(learningResource.getSellingPrice());
                line.append(delimiter);
                line.append(learningResource.getProductStatus());
                line.append(delimiter);
                line.append(learningResource.getCreatedDate());
                line.append(delimiter);
                line.append(learningResource.getPublishedDate());
                line.append(delimiter);
                line.append(learningResource.getRetiredDate());
                bf.write(line.toString());
                }
            bf.close();
            }catch(IOException e){
            e.printStackTrace();
        }
        }
        private List<Double> getProfitMargins(){
        List<LearningResource> learningResources = getLearningResources();
        List<Double> profitMargins=new ArrayList<>();
        for(LearningResource lr: learningResources){
            Double profit = (lr.getSellingPrice()-lr.getCostPrice());
            profitMargins.add(profit);

        }
        return profitMargins;


        }
    private List<LearningResource> sortLearningResourcesByProfitMargins(){
        List<LearningResource> learningResources = getLearningResources();

        learningResources.sort((ele1,ele2) -> {
            Double margin1 = (ele1.getSellingPrice() - ele1.getCostPrice())/ele1.getSellingPrice();
            Double margin2 = (ele2.getSellingPrice() - ele2.getCostPrice())/ele2.getSellingPrice();

            return margin2.compareTo(margin1);
        });
        return learningResources;
    }
    }


