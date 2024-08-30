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

};

// 페이지 로드 후 초기화
$(document).ready(function () {
    main.init();
});
