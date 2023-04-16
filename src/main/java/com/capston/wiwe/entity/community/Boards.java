package com.capston.wiwe.entity.community;

import com.capston.wiwe.entity.Auditor;
import com.capston.wiwe.entity.user.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "boards")
public class Boards extends Auditor {

    @Id
    @Column(name = "boards_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardsId;

    private String boardsTitle;
    @Lob
    private String boardsContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Boards(String boardsTitle, String boardsContent, User user) {
        this.boardsTitle = boardsTitle;
        this.boardsContent = boardsContent;
        this.user = user;
    }
}
