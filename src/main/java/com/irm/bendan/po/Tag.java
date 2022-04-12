package com.irm.bendan.po;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_tags")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    private String name; //标签名

    @ManyToMany(mappedBy = "tags")
    private List<BenDan> benDans = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BenDan> getBendans() {
        return benDans;
    }

    public void setBendans(List<BenDan> benDans) {
        this.benDans = benDans;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
