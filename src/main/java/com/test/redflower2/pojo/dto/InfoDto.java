package com.test.redflower2.pojo.dto;

public class InfoDto {

    private Integer id;

    private String name;

    private Integer gender;

    private String definiton;

    private int relation;

    private String wxid;

    public InfoDto(){}

    public InfoDto(Integer id, String name, Integer gender, String definiton, int relation, String wxid){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.definiton = definiton;
        this.relation = relation;
        this.wxid = wxid;
    }

    public String getName() {
        return name;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public int getRelation() {
        return relation;

    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getDefiniton() {

        return definiton;
    }

    public void setDefiniton(String definiton) {
        this.definiton = definiton;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
