const main = {
    init: function () {
        const _this = this;
        // 게시글 저장
        $('#btn-save').on('click', function () {
            _this.save();
        });
        // 게시글 수정
        $('#btn-update').on('click', function () {
            _this.update();
        });
        // 게시글 삭제
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        // 댓글 저장
        $('#btn-comment-save').on('click', function () {
            _this.commentSave();
        });
        // 댓글 삭제
        $('#btn-comment-delete').on('click', function () {
            _this.commentDelete();
        });
        // 댓글 수정
        $(document).on('click', '.btn-comment-update', function () {
            _this.showEditForm($(this).closest('.card'));
        });
        // 댓글 저장
        $(document).on('click', '.btn-save-update', function () {
            _this.commentUpdate($(this).closest('.card'));
        });
        // 댓글 취소
        $(document).on('click', '.btn-cancel-update', function () {
            _this.hideEditForm($(this).closest('.card'));
        });
    },

    /** 글 작성 */
    save: function () {
        const boardId = $('#boardId').val(); // boardId 읽기
        const data = {
            title: $('#title').val(), content: $('#content').val()
        };
        // 공백 및 빈 문자열 체크
        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/boards/' + boardId + '/posts',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('등록되었습니다.');
                window.location.href = '/boards/' + boardId;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 글 수정 */
    update: function () {
        const postId = $('#postId').val(); // postId 읽기
        const data = {
            title: $('#title').val(), content: $('#content').val()
        };

        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'PATCH',
                url: '/api/posts/' + postId,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert("수정되었습니다.");
                window.location.href = '/posts/' + postId;
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }

    },

    /** 글 삭제 */
    delete: function () {
        const boardId = $('#boardId').val();
        const postId = $('#postId').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/posts/' + postId,
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert("삭제되었습니다.");
            window.location.href = '/boards/' + boardId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    /** 댓글 저장 */
    commentSave: function () {
        const postId = $('#postId').val();
        const data = {
            content: $('#content').val()
        }
        // 공백 및 빈 문자열 체크
        if (!data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/posts/' + postId + '/comments',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('댓글이 등록되었습니다.');
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 댓글 삭제 */
    commentDelete: function () {
        const commentId = $('#commentId').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/comments/' + commentId,
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert("삭제되었습니다.");
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },


    /** 댓글 수정 폼 표시 */
    showEditForm: function (card) {
        card.find('.comment-content').hide(); // 기존 댓글 내용 숨기기
        card.find('.comment-edit-form').removeClass('d-none'); // 수정 폼 보이기
        const content = card.find('.comment-content').text();
        card.find('.comment-edit-form textarea').val(content); // 수정 폼에 댓글 내용 설정
    },

    /** 댓글 수정 */
    commentUpdate: function (card) {
        const commentId = $('#commentId').val();
        const newContent = card.find('.comment-edit-form textarea').val();

        if (!newContent || newContent.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'PATCH',
                url: '/api/comments/' + commentId,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({ content: newContent })
            }).done(function () {
                alert('댓글이 수정되었습니다.');
                card.find('.comment-content').text(newContent).show(); // 댓글 내용 업데이트 및 보이기
                card.find('.comment-edit-form').addClass('d-none'); // 수정 폼 숨기기
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 댓글 수정 폼 숨기기 */
    hideEditForm: function (card) {
        card.find('.comment-content').show(); // 댓글 내용 보이기
        card.find('.comment-edit-form').addClass('d-none'); // 수정 폼 숨기기
    }

};

// 페이지 로드 후 초기화
$(document).ready(function () {
    main.init();
});
