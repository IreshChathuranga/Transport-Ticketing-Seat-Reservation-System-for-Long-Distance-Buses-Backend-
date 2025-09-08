package org.example.longdistancebusbackend.exception;

public class ResourseNotFound extends RuntimeException{
    public ResourseNotFound(String massage){
        super(massage);
    }
}
