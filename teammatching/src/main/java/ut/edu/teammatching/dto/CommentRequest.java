package ut.edu.teammatching.dto;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class CommentRequest {
    @JsonProperty("comment")    // 👈 map JSON "comment" => content
    private String content;

    @JsonProperty("postId")     // 👈 map JSON "postId" => blogId
    private Long blogId;
    @JsonProperty("commentbyid")
    private Long UserId; // 👈 Thêm dòng này để nhận userId từ client
}
