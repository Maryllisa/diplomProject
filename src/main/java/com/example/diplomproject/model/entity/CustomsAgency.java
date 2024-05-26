package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.entity.chat.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomsAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomsAgency;
    @Column
    private double mark;
    @OneToOne
    private DeliveryProduct deliveryProduct;
    @OneToOne
    private Otpusk otpusk;
    @OneToOne
    private MarkingInfo markingInfo;
    @Column
    private Boolean isMark = false;
    @OneToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private Account account;
    @OneToMany
    private List<MarkForAgency> markForAgencies = new ArrayList<>();

    public void setListMarkForAgency (MarkForAgency markForAgency){
        this.markForAgencies.add(markForAgency);
    }
}
