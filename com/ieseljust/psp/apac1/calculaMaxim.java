/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ieseljust.psp.apac1;

import java.util.ArrayList;

/**
 *
 * @author eljust
 */
public class calculaMaxim {

    public Long max (ArrayList <Long> mArray) {
        Long result = 0L;
        Long argumento = Long.MIN_VALUE;
        try {
            for (int i = 0; i < mArray.size(); i++) {
                argumento= mArray.get(i);
                if (argumento>result) {
                    result = argumento;
                }
            }
        } catch (NullPointerException e) {
            return 0L;
        }
        //System.out.println(result);
        return result;

    }

    public static void main(String[] args) {
        calculaMaxim c = new calculaMaxim();
        ArrayList<Long> rArray = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            rArray.add(Long.parseLong(args[i]));
        }
        Long r = c.max(rArray);
        // El resultat el bolcarem per l'eixida estàndard
        System.out.println(r);
    }

}
