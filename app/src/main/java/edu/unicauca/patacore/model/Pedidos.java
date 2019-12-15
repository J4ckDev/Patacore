package edu.unicauca.patacore.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Pedidos {
    private String imgCard;
    private String txtNombre;
    private String txtPrecio;
    private int img;

    public Pedidos( String imgCard, String txtNombre, String txtPrecio) {
        this.imgCard = imgCard;
        this.txtNombre = txtNombre;
        this.txtPrecio = txtPrecio;
    }
    public Pedidos(){}

    public int getImg() {
        return img;
    }
    public void setImg(int img) {
        this.img = img;
    }

    public String getImgCard() {
        return imgCard;
    }


    public void setImgCard(String imgCard) {
        this.imgCard = imgCard;
    }


    public String getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(String txtNombre) {
        this.txtNombre = txtNombre;
    }

    public String getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(String txtPrecio) {
        this.txtPrecio = txtPrecio;
    }



}

