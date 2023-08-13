package com.plot.plotserver.domain;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar (36)")
    private String name;

    @Comment("즐겨찾기 여부")
    @Column(name = "star", nullable = false, columnDefinition = "bit (1)")
    private boolean star;

    @Column(name = "emoji", nullable = true, columnDefinition = "text")
    private String emoji;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint")
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "category_group_id",nullable = false)
    private CategoryGroup categoryGroup;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,orphanRemoval = true)
    private final List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,orphanRemoval = true)
    private final List<Tag> tags = new ArrayList<>();

//    public void updateTags(String tags){
//        String[] tagList = tags.split("/");
//        List<Tag> updatedTags = new ArrayList<>();
//
//        for (String tag : tagList) {
//            updatedTags.add(tag);
//        }
//    }
}