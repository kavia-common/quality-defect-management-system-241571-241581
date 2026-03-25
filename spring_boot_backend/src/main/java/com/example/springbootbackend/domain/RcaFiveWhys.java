package com.example.springbootbackend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rca_five_whys")
public class RcaFiveWhys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "defect_id", nullable = false, unique = true)
    private Defect defect;

    @Column(length = 2000)
    private String why1;

    @Column(length = 2000)
    private String why2;

    @Column(length = 2000)
    private String why3;

    @Column(length = 2000)
    private String why4;

    @Column(length = 2000)
    private String why5;

    @Column(length = 2000)
    private String rootCause;

    public Long getId() {
        return id;
    }

    public Defect getDefect() {
        return defect;
    }

    public void setDefect(Defect defect) {
        this.defect = defect;
    }

    public String getWhy1() {
        return why1;
    }

    public void setWhy1(String why1) {
        this.why1 = why1;
    }

    public String getWhy2() {
        return why2;
    }

    public void setWhy2(String why2) {
        this.why2 = why2;
    }

    public String getWhy3() {
        return why3;
    }

    public void setWhy3(String why3) {
        this.why3 = why3;
    }

    public String getWhy4() {
        return why4;
    }

    public void setWhy4(String why4) {
        this.why4 = why4;
    }

    public String getWhy5() {
        return why5;
    }

    public void setWhy5(String why5) {
        this.why5 = why5;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
}
