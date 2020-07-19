package com.slowalk.base.domain.board.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "${tablename}", indexes = {
@Index(name = "IDX_BOARD__TITLE", columnList = "TITLE", unique = false),
@Index(name = "IDX_BOARD__SLUG", columnList = "SLUG", unique = false),
@Index(name = "IDX_BOARD__MENU_UID", columnList = "MENU_UID", unique = false),
})
@EntityListeners({AuditingEntityListener.class, AuditingIpListener.class})
public class Board {

/**
* 게시판 ID
*/
@Id
@GeneratedValue(generator = "native")
@GenericGenerator(name = "native", strategy = "native")
@Column(name = "BOARD_ID")
private Long id;

/**
* 등록 ID
*/
@CreatedBy
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "REG_ID", updatable = false, foreignKey = @ForeignKey(name = "FK_BOARD_TO_USER"))
private User createdBy;

/**
* 수정 ID
*/
@LastModifiedBy
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "CHG_ID", foreignKey = @ForeignKey(name = "FK_BOARD_TO_USER2"))
private User modifiedBy;

/**
* 등록일시
*/
@CreatedDate
@Column(name = "REG_DT", updatable = false)
private LocalDateTime createdDate;

/**
* 수정 일시
*/
@LastModifiedDate
@Column(name = "CHG_DT")
private LocalDateTime modifiedDate;

/**
* 등록 아이피
*/
@CreatedIp
@Column(name = "REG_IP", updatable = false, length = 15)
private String createdIp;

/**
* 수정 아이피
*/
@ModifiedIp
@Column(name = "CHG_IP", length = 15)
private String modifiedIp;

/**
* 대표이미지
*/
@Column(name = "BOARD_REPRESENT_IMG", length = 300)
private String representImg;


/**
* 게시판 첨부 파일 IDS
*/
@Column(name = "BOARD_FILE_IDS", length = 1000)
@Convert(converter = ListLongToStringAttributeConverter.class)
private List<Long> fileIds;


  /**
  * 제목
  */
  @Convert(converter = XssPreventNoneConverter.class)
  @Column(name = "TITLE", length = 300)
  private String title;

  /**
  * 슬러그
  */
  @Column(name = "SLUG", length = 300)
  private String slug;

  /**
  * 내용
  */
  @Lob
  @Convert(converter = XssPreventRelaxedConverter.class)
  @Column(name = "CONTENT")
  private String content;

  /**
  * 카테고리
  */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATE_ID", foreignKey = @ForeignKey(name = "FK_BOARD_TO_BOARD_CATE"))
  private BoardCategory ctgry;

  /**
  * 상단 공지글
  */
  @Column(name = "TOP_YN")
  private Boolean topYn;

  /**
  * 조회수
  */
  @Column(name = "VIEW_CNT", updatable = false)
  private Long viewCount;

  /**
  * 댓글수
  */
  @Column(name = "COMMENT_CNT", updatable = false)
  private Long commentCount;


  /**
  * 좋아요
  */
  @Column(name = "LIKE_CNT", updatable = false)
  private Long likeCount;

  /**
  * 메뉴 Uid
  */
  @Column(name = "MENU_UID", length = 50)
  private String menuUid;


  /**
  * 공개유무
  */
  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", length = 10)
  private Status status;

  @Convert(converter = JsonToListAttributeConverter.class)
  @Column(name = "BOARD_TAG", length = 3000)
  private List<String> tags;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPROVE_STATUS", length = 10)
    private ApproveStatus approveStatus;

    @Column(name = "APPROVE_DATE")
    private LocalDateTime approveDate;

    @Column(name = "REJECT_DATE")
    private LocalDateTime rejectDate;

    @Column(name = "CANCEL_DATE")
    private LocalDateTime cancelDate;


    public Board(String representImg, List<Long> fileIds, String title, String content,
      BoardCategory ctgry, String menuUid, Boolean topYn, List<String> tags, Status status) {

        this.representImg = representImg;
        this.fileIds = fileIds;
        this.title = title;
        this.content = content;
        this.ctgry = ctgry;
        this.menuUid = menuUid;
        this.topYn = topYn;
        this.tags = tags;
        //TODO 슬러그 입력 메소드를 붙여야한다.
        //this.slug  =

        this.viewCount = 0L;
        this.commentCount = 0L;
        this.likeCount = 0L;
        this.status = status != null ? status : Status.OPEN;
        this.approveStatus = ApproveStatus.WAITING;
        }

        public static Board createBoard(BoardCategory boardCategory, BoardCreateDto createDto) {
        return new Board(
        createDto.getRepresentImg(),
        createDto.getFileIds(),
        createDto.getTitle(),
        createDto.getContent(),
        boardCategory,
        createDto.getMenuUid(),
        createDto.getTopYn(),
        createDto.getTags(),
        createDto.getStatus()
        );
        }

        public Board updateBoard(BoardCategory boardCategory, BoardUpdateDto updateDto) {
        this.representImg = updateDto.getRepresentImg();
        this.fileIds = updateDto.getFileIds();
        this.title = updateDto.getTitle();
        this.content = updateDto.getContent();
        this.ctgry = boardCategory;
        this.status = updateDto.getStatus() != null ? updateDto.getStatus() : this.status;
        this.tags = updateDto.getTags();
        this.approveStatus = ApproveStatus.WAITING; // 수정시 다시 대기 상태로 변환시킨다.
        return this;
        }

        public Board openBoard() {
        this.status = Status.OPEN;
        return this;
        }

        public Board closeBoard() {
        this.status = Status.CLOSED;
        return this;
        }

        public Board deleteBoard() {
        this.status = Status.DELETE;
        return this;
        }

        /**
        * 상단 공지글 등록
        *
        * @return
        */
        public Board topBoard() {
        this.topYn = true;
        return this;
        }

        /**
        * 상단 공지글 등록 해제
        *
        * @return
        */
        public Board topCancel() {
        this.topYn = false;
        return this;
        }

        /**
        * 승인
        *
        * @return
        */
        public Board approveBoard() {
        this.approveStatus = ApproveStatus.APPROVE;
        this.approveDate = LocalDateTime.now().withNano(0);
        return this;
        }

        /**
        * 반려
        *
        * @return
        */
        public Board rejectBoard() {
        this.approveStatus = ApproveStatus.REJECT;
        this.rejectDate = LocalDateTime.now().withNano(0);
        return this;
        }

        /**
        * 취소
        *
        * @return
        */
        public Board cancelBoard() {
        this.approveStatus = ApproveStatus.CANCEL;
        this.cancelDate = LocalDateTime.now().withNano(0);
        return this;
        }
}
