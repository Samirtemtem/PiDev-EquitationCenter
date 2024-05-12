package Entities;

import java.util.Date;

public class Commentaire {

    private int id;
    private Date date;

    public Commentaire(Date date, String commentaire) {
        this.date = date;
        this.commentaire = commentaire;
    }

    private String commentaire;

    public Commentaire(int id, Date date, String commentaire) {
        this.id = id;
        this.date = date;
        this.commentaire = commentaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}