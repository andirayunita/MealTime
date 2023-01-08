package com.example.mealtime;

public class Meal {
    private String documentId;
    private String name;
    private String date;
    private String statusRepeat;

    public String getDocumentId(){
        return  documentId;
    }

    public void setDocumentId(String documentId){
        this.documentId  = documentId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatusRepeat() {
        return statusRepeat;
    }

    public void setStatusRepeat(String statusRepeat) {
        this.statusRepeat = statusRepeat;
    }




}
