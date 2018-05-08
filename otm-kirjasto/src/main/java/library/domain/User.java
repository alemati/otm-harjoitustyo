/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.domain;

import java.util.Objects;

public class User {
    
    private String name;
    private String pass;
    public User(String name, String pass) {
        
        this.name = name;
        this.pass = pass;
    }
    
    public String getName() {
        return this.name;
    }
    public String getPass() {
        return this.pass;
    }
    public String toString() {
        return this.name + ", " + this.pass;
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
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.pass, other.pass)) {
            return false;
        }
        return true;
    }
    
}
