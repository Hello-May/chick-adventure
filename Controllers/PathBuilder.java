/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import values.ImagePath;

/**
 *
 * @author user1
 */
public class PathBuilder {
    private static final String ROOT = "/resources";
    
    //圖片
    private static final String IMG_ROOT = "/imgs";
    public static String getImg(String path){
        return ROOT + IMG_ROOT + path;
    }
    
    //音樂
    private static final String AUDIO_ROOT = "/audios";
    public static String getAudio(String path){
        return ROOT + AUDIO_ROOT + path;
    }
}
