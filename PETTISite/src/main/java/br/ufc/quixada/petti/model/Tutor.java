package br.ufc.quixada.petti.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@DiscriminatorValue(value = "tutor")
public class Tutor extends Usuario{

}
