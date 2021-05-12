package com.kayla.Model;

public class PaymentModel1 {
    private int image;
    private String  card_no;
    private String  expiry_date;
    private String  cvv_no;

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCvv_no() {
        return cvv_no;
    }

    public void setCvv_no(String cvv_no) {
        this.cvv_no = cvv_no;
    }

    public String getCard_no() {
        return card_no;
    }


    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
