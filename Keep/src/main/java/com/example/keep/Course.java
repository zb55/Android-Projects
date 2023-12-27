package com.example.keep;

public class Course {
    int imageId;
    String name;
    String level;
    public Course getCourse(String courseMassage){
        Course course = null;
        String[] massages = courseMassage.split(";");

        course.setImageId(Integer.parseInt(massages[0]));
        course.setName(massages[1]);
        course.setLevel(massages[2]);
        return course;
    }
    public String toString(){
        String string = new String(Integer.toString(this.getImageId()) + ";" + this.getName() + ";" + this.getLevel()) + ";";
        return string;
    }
    public Course(int imageId, String name, String level) {
        this.imageId = imageId;
        this.name = name;
        this.level = level;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
