package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Import(BoardRepository.class) // 내가 만든 클래스는 import 해줘야 함.
@DataJpaTest // DB 관련 객체들이 IoC에 뜬다.
public class BoardRepositoryTest {

    @Autowired // Test에서 DI 하는 코드
    private BoardRepository boardRepository;

    @Test
    public void deleteById_test(){
            // given
            int deleteId= 1;

            // when
            boardRepository.deleteById(deleteId);

            // then
            Board board = boardRepository.selectOne(deleteId);
            Assertions.assertThat(board).isNull();
    }

    @Test
    public void update_test(){
        // given
        int uId = 1;

        String nTitle = "제목100";
        String nContent = "내용100";
        String nAuthor = "이순신100";

        // when
        boardRepository.update(nTitle, nContent, nAuthor);

        // then
        Board uBoard = boardRepository.selectOne(uId);

        Assertions.assertThat(uBoard.getTitle()).isEqualTo(nTitle);
        Assertions.assertThat(uBoard.getContent()).isEqualTo(nContent);
        Assertions.assertThat(uBoard.getAuthor()).isEqualTo(nAuthor);
    }

    @Test
    public void selectAll_test(){
        // given

        // when
        List<Board> boardList = boardRepository.selectAll();
        System.out.println(boardList.size());

        // then (id=1, title=제목1, content=내용1, author=홍길동)
        // System.out.println(boardList);
        Assertions.assertThat(boardList.get(0).getTitle()).isEqualTo("제목1");
        Assertions.assertThat(boardList.get(0).getContent()).isEqualTo("내용1");
        Assertions.assertThat(boardList.get(0).getAuthor()).isEqualTo("홍길동");
        Assertions.assertThat(boardList.size()).isEqualTo(8);
    }

    @Test
    public void selectOne_test(){
        // given
        int id = 1;

        // when
        Board board = boardRepository.selectOne(id);

        // then (상태 검사)
        // System.out.println(board);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
        Assertions.assertThat(board.getAuthor()).isEqualTo("홍길동");
    }

    @Test
    public void insert_test(){ // 테스트 메서드는 파라미터가 없다. 리턴도 없다.
        // given
        String title = "제목10";
        String content = "내용10";
        String author = "이순신";

        // when
        boardRepository.insert(title, content, author);

        // then -> 눈으로 확인 (쿼리)
    } // Rollback (자동)
}