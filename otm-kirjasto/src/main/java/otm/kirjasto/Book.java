/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otm.kirjasto;

import java.util.Objects;

public class Book implements Comparable<Book>  {
    private String name;
    private String writer;
    private int year;
    private int id;
    private String owner;
    public Book(int id, String nimi, String kirjailija, int vuosi) {
        this.id = id;
        this.name = nimi;
        this.writer = kirjailija;
        this.year = vuosi;
        this.owner = "admin";
    }
    public void setOmistajaAdmin() {
        this.owner = "admin";
    }
    public void setOmistaja(String tulevaOmistaja) {
        this.owner = tulevaOmistaja;
    }
    public String getOmistaja(){
        return this.owner;
    }
    public int getId(){
        return this.id;
    }
    public String getNimi(){
        return this.name;
    }
    public String getKirjailija(){
        return this.writer;
    }
    public int getVuosi(){
        return this.year;
    }
    public String toString() {
        if(this.owner.equals("admin")) {
           return this.name + ", " + this.writer + ", " + this.year + ", the book is free";  
        } 
        return this.name + ", " + this.writer + ", " + this.year + ", this book has already been borrowed, owner: " + this.owner;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.writer, other.writer)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Book o) {
    return 0;
    }
}