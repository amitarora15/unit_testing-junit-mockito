package com.amit.junit;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MyOperations {

    private int a;

    private int b;

    // Some mathematical operations
    public int add() {
        return a + b;
    }

    public int divide(int x, int y) {
        if (y == 0)
            throw new IllegalArgumentException("Invalid divisor : " + y);
        return x / y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    // Some relations operations
    public boolean gt(int x, int y){
        return x > y;
    }

    public boolean lt(int x, int y){
        return x < y;
    }

    // Some String operations
    public String greet(String str){
        return "Hello " + str;
    }

    public String[] concatenate(String[] input, String str){
        if(input == null  || str == null || str == ""){
            return input;
        }
        return Arrays.stream(input).map(a -> str + a).collect(Collectors.toList()).toArray(new String[0]);
    }

    public List<String> concatenate(List<String> input, String str){
        if(input == null  || str == null || str == ""){
            return input;
        }
        return new ArrayList<>(input.stream().map(a -> str + a).collect(Collectors.toList()));
    }

    // Some sleep operations for timeout
    public boolean sleep(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  false;
        }
        return true;
    }

}
