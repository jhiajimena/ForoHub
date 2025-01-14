package lescano.forohub.entities;

import lescano.forohub.dto.topic.UpdateTopicDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="topic")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true ,length = 500)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicType type;

    @Column(nullable = false, unique = true)
    private String message;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TopicStatus status = TopicStatus.UNANSWERED;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="author_id")
    private ForumUser author;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Response> responses;

    public void setType(String typeValue) {
        this.type = TopicType.fromString(typeValue);
    }

    public void updateTopic(@Valid UpdateTopicDTO data) {
        if(data.title() != null){
            this.title= data.title();
        }
        if(data.message() != null){
            this.message = data.message();
        }
    }
    public void answeredStatus(){
        this.status=TopicStatus.ANSWERED;
    }
    public void unansweredStatus (){
        this.status=TopicStatus.UNANSWERED;
    }
}
