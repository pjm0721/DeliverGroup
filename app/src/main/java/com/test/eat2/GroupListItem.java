package com.test.eat2;


import com.google.firebase.firestore.DocumentReference;
;

public class GroupListItem {
    private DocumentReference Document_Reference;
    private String Nick;
    private String Title;
    private String Cotent;
    private String Time;
    private String Sex;
    private String Open_Kakao;
    private String Document;
    private String Group_number;
    private String Master;
    private String Dorm;

    public DocumentReference get_Document_Reference() {
        return Document_Reference;
    }

    public void setDMI_Document_Reference(DocumentReference DMI_Document_Reference) {
        this.Document_Reference = Document_Reference;
    }
    public String getGroup_number() {
        return Group_number;
    }
    public void set_Dorm(String Dorm){this.Dorm=Dorm;}
    public String get_Dorm(){return Dorm;}

    public void setGroup_number(String Group_number) {
        this.Group_number = Group_number;
    }
    public String get_Master() {
        return Master;
    }

    public void set_Master(String Master) {
        this.Master = Master;
    }
    public String get_Title() {
        return Title;
    }

    public void set_Title(String Title) {
        this.Title = Title;
    }

    public String get_Content() {
        return Cotent;
    }

    public void set_Cotent(String Content) {
        this.Cotent = Content;
    }

    public String get_Time() {
        return Time;
    }

    public  void set_Time(String Time){this.Time=Time;}
    public String get_Nick() {
        return Nick;
    }

    public void set_Nick(String Nick) {
        this.Nick = Nick;
    }

    public String get_Sex() {
        return Sex;
    }

    public void set_Sex(String Sex) {
        this.Sex = Sex;
    }


    public String get_Open_Kakao() {
        return Open_Kakao;
    }

    public void set_Open_Kakao(String Open_Kakao) {
        this.Open_Kakao = Open_Kakao;
    }


    public GroupListItem(DocumentReference Document_Reference,
                                  String Title,
                                  String Cotent,
                                  String Time,
                                  String Sex,
                                  String Open_Kakao,
                                  String Nick,
                                  String Group_number,
                                  String Master,
                                  String Dorm) {
        this.Document_Reference = Document_Reference;
        this.Nick = Nick;
        this.Title = Title;
        this.Cotent = Cotent;
        this.Time = Time;
        this.Sex = Sex;
        this.Open_Kakao = Open_Kakao;
        this.Group_number=Group_number;
        this.Master=Master;
        this.Dorm=Dorm;
    }
}
