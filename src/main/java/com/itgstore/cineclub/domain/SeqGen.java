package com.itgstore.cineclub.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SeqGen.
 */
@Entity
@Table(name = "seq_gen")
public class SeqGen implements Serializable {

    private static final long serialVersionUID = 1L;

    public SeqGen() {
		super();
	}

	public SeqGen(String code, Long nextid) {
		super();
		this.code = code;
		this.nextid = nextid;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "nextid", nullable = false)
    private Long nextid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public SeqGen code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNextid() {
        return nextid;
    }

    public SeqGen nextid(Long nextid) {
        this.nextid = nextid;
        return this;
    }

    public void setNextid(Long nextid) {
        this.nextid = nextid;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public SeqGen increment() {
    	this.nextid += 1;
    	return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeqGen seqGen = (SeqGen) o;
        if (seqGen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seqGen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeqGen{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nextid=" + getNextid() +
            "}";
    }
}
